package com.library.jafa.dao.officer;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import com.library.jafa.dto.PageResponse;
import com.library.jafa.entities.LibraryOfficer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


@Repository
public class OfficerDaoImpl implements OfficerDao{


    @Autowired
    EntityManager entityManager;

    @Override
    public PageResponse<LibraryOfficer> findAll(String officerName, String officerAddress, Integer officerAge, int page, int size, String sortBy, String sortOrder) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    
        CriteriaQuery<LibraryOfficer> criteriaQuery = criteriaBuilder.createQuery(LibraryOfficer.class);
        Root<LibraryOfficer> officerRoot = criteriaQuery.from(LibraryOfficer.class);
    
    
        if (sortBy != null && !sortBy.isEmpty()) {
            Path<Object> orderByPath = officerRoot.get(sortBy);
            Order order= null;
            if (sortOrder != null && sortOrder.equalsIgnoreCase("desc")) {
                order = criteriaBuilder.desc(orderByPath);
            }else if(sortOrder != null && sortOrder.equalsIgnoreCase("asc")) {
                order = criteriaBuilder.asc(orderByPath);
            }else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"invalid orderBy type");
            criteriaQuery.orderBy(order);
        }
        
        List<LibraryOfficer> result = entityManager.createQuery(criteriaQuery)
        .setFirstResult((page - 1) * size)
        .setMaxResults(size)
        .getResultList();

        
        // paginasi
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<LibraryOfficer> officerRootCount = countQuery.from(LibraryOfficer.class);
        countQuery.select(criteriaBuilder.count(officerRootCount))
        .where(createPredicate(criteriaBuilder, officerRootCount, officerName, officerAddress,officerAge));
        Long totalItem = entityManager.createQuery(countQuery).getSingleResult();
    
        return PageResponse.success(result, page, size, totalItem);
    }
    

    private Predicate[] createPredicate(CriteriaBuilder criteriaBuilder, Root<LibraryOfficer> root, String officerName, String officerAddress, Integer officerAge) {
        List<Predicate> predicates = new ArrayList<>();
        if (officerName != null && !officerName.isBlank() && !officerName.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("officerName"), "%" + officerName + "%"));
        }
    
        if (officerAddress != null && !officerAddress.isBlank() && !officerAddress.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("officerAddress"), "%" + officerAddress + "%"));
        }
    
        if (officerAge != null && officerAge > 0) {
            try {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("officerAge"), officerAge));
            } catch (DateTimeParseException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format");
            }
        }
    
        return predicates.toArray(Predicate[]::new);
    }

}



