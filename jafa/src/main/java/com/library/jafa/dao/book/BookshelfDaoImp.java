package com.library.jafa.dao.book;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import com.library.jafa.dto.PageResponse;
import com.library.jafa.entities.Bookshelf;
import com.library.jafa.repositories.BookshelfRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
// import lombok.extern.slf4j.Slf4j;

// @Slf4j
@Repository
public class BookshelfDaoImp implements BookshelfDao {

    @Autowired
    EntityManager entityManager;

    @Autowired
    BookshelfRepository bookshelfRepository;

    @Override
    public PageResponse<Bookshelf> findAll(String category, Integer capacity, int page, int size, String sortBy, String sortOrder) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Bookshelf> criteriaQuery = criteriaBuilder.createQuery(Bookshelf.class);
        Root<Bookshelf> BookShelfRoot = criteriaQuery.from(Bookshelf.class);
         if (sortBy != null && !sortBy.isEmpty()) {
            Path<Object> orderByPath = BookShelfRoot.get(sortBy);
            Order order= null;
            if (sortOrder != null && sortOrder.equalsIgnoreCase("desc")) {
                order = criteriaBuilder.desc(orderByPath);
            }else if(sortOrder != null && sortOrder.equalsIgnoreCase("asc")) {
                order = criteriaBuilder.asc(orderByPath);
            }else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"invalid orderBy type");
            criteriaQuery.orderBy(order);
        }
 
        List<Bookshelf> result = entityManager.createQuery(criteriaQuery)
        .setFirstResult((page - 1) * size)
        .setMaxResults(size)
        .getResultList();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Bookshelf> bookRootCount = countQuery.from(Bookshelf.class);
        countQuery.select(criteriaBuilder.count(bookRootCount))
        .where(createPredicate(criteriaBuilder, bookRootCount,category,capacity));
        Long totalItem = entityManager.createQuery(countQuery).getSingleResult();
    
         return PageResponse.success(result, page, size, totalItem);
    }

    private Predicate[] createPredicate(CriteriaBuilder criteriaBuilder, Root<Bookshelf> root, String category,
            Integer capacity) {
        List<Predicate> predicates = new ArrayList<>();
        if (category != null && !category.isBlank() && !category.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("categoryBook"), "%" + category + "%"));
        }
        if (capacity != null && capacity != 0 ) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("capacity"), capacity));
        }
        return predicates.toArray(new Predicate[0]);
    }
}
