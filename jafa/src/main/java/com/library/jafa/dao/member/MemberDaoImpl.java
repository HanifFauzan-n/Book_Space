package com.library.jafa.dao.member;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import com.library.jafa.dto.PageResponse;
import com.library.jafa.entities.Member;
import com.library.jafa.repositories.MemberRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


@Repository
public class MemberDaoImpl implements MemberDao{

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @Override
    public PageResponse<Member> findAll(String memberName, String address, Integer memberAge, int page, int size, String sortBy, String sortOrder) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    
        CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
        Root<Member> memberRoot = criteriaQuery.from(Member.class);
    
    
        if (sortBy != null && !sortBy.isEmpty()) {
            Path<Object> orderByPath = memberRoot.get(sortBy);
            Order order= null;
            if (sortOrder != null && sortOrder.equalsIgnoreCase("desc")) {
                order = criteriaBuilder.desc(orderByPath);
            }else if(sortOrder != null && sortOrder.equalsIgnoreCase("asc")) {
                order = criteriaBuilder.asc(orderByPath);
            }else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"invalid orderBy type");
            criteriaQuery.orderBy(order);
        }
        
        List<Member> result = entityManager.createQuery(criteriaQuery)
        .setFirstResult((page - 1) * size)
        .setMaxResults(size)
        .getResultList();

        
        // paginasi
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Member> memberRootCount = countQuery.from(Member.class);
        countQuery.select(criteriaBuilder.count(memberRootCount))
        .where(createPredicate(criteriaBuilder, memberRootCount, memberName, address,memberAge));
        Long totalItem = entityManager.createQuery(countQuery).getSingleResult();
    
        return PageResponse.success(result, page, size, totalItem);
    }
    

    private Predicate[] createPredicate(CriteriaBuilder criteriaBuilder, Root<Member> root, String memberName, String address, Integer memberAge) {
        List<Predicate> predicates = new ArrayList<>();
        if (memberName != null && !memberName.isBlank() && !memberName.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("memberName"), "%" + memberName + "%"));
        }
    
        if (address != null && !address.isBlank() && !address.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("address"), "%" + address + "%"));
        }
    
        if (memberAge != null && memberAge > 0) {
            try {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("memberAge"), memberAge));
            } catch (DateTimeParseException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format");
            }
        }
    
        return predicates.toArray(new Predicate[0]);
    }

}



