package com.library.jafa.dao.officer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import com.library.jafa.dto.PageResponse;
import com.library.jafa.entities.Book;
import com.library.jafa.entities.BorrowingBook;
import com.library.jafa.entities.Member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


@Repository
public class BorrowingDaoImpl implements BorrowingDao{


    @Autowired
    EntityManager entityManager;

    @Override
    public PageResponse<BorrowingBook> findAll(String member, String book, int page, int size, String sortBy, String sortOrder) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    
        CriteriaQuery<BorrowingBook> criteriaQuery = criteriaBuilder.createQuery(BorrowingBook.class);
        Root<BorrowingBook> borrowingBookRoot = criteriaQuery.from(BorrowingBook.class);
    
    
        if (sortBy != null && !sortBy.isEmpty()) {
            
            Path<Object> orderByPath = borrowingBookRoot.get(sortBy);
            if(sortBy.equals("book")){
                Path<Book> bookPath = borrowingBookRoot.get("book");
                orderByPath = bookPath.get("bookTitle");
            }
            if(sortBy.equals("member")){
                Path<Member> memberPath = borrowingBookRoot.get("member");
                orderByPath = memberPath.get("memberName");
            }
            Order order= null;
            if (sortOrder != null && sortOrder.equalsIgnoreCase("desc")) {
                order = criteriaBuilder.desc(orderByPath);
            }else if(sortOrder != null && sortOrder.equalsIgnoreCase("asc")) {
                order = criteriaBuilder.asc(orderByPath);
            }else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"invalid orderBy type");
            criteriaQuery.orderBy(order);
        }
        
        List<BorrowingBook> result = entityManager.createQuery(criteriaQuery)
        .setFirstResult((page - 1) * size)
        .setMaxResults(size)
        .getResultList();

        
        // paginasi
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<BorrowingBook> borrowingBookRootCount = countQuery.from(BorrowingBook.class);
        countQuery.select(criteriaBuilder.count(borrowingBookRootCount))
        .where(createPredicate(criteriaBuilder, borrowingBookRootCount, member, book));
        Long totalItem = entityManager.createQuery(countQuery).getSingleResult();
    
        return PageResponse.success(result, page, size, totalItem);
    }
    

    private Predicate[] createPredicate(CriteriaBuilder criteriaBuilder, Root<BorrowingBook> root, String member, String book) {
        List<Predicate> predicates = new ArrayList<>();
        if (member != null && !member.isBlank() && !member.isEmpty()) {
            Join<Object, Object> memberJoin = root.join("member");
            predicates.add(criteriaBuilder.like(memberJoin.get("memberName"), "%" + member + "%"));
        }
    
        if (book != null && !book.isBlank() && !book.isEmpty()) {
            Join<Object, Object> bookJoin = root.join("book");
            predicates.add(criteriaBuilder.like(bookJoin.get("bookTitle"), "%" + book + "%"));
        }
    

    
        return predicates.toArray(Predicate[]::new);
    }

}



