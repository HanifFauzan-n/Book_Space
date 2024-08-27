package com.library.jafa.dao.book;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
// import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import com.library.jafa.dto.PageResponse;
import com.library.jafa.entities.Book;
import com.library.jafa.repositories.BookshelfRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
// import lombok.extern.slf4j.Slf4j;

// @Slf4j
@Repository
public class BookDaoImp implements BookDao {

    @Autowired
    EntityManager entityManager;

    @Autowired
    BookshelfRepository bookshelfRepository;

    @Override
    public PageResponse<Book> findAll(String author,String statusBook,String category,int page,int size, String sortBy, String sortOrder){
        
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> bookRoot = criteriaQuery.from(Book.class);

        if (sortBy != null && !sortBy.isEmpty()) {
            if(sortBy.equals("category")){
                sortBy = "bookshelf";
            }
            Path<Object> orderByPath = bookRoot.get(sortBy);
            Order order= null;
            if (sortOrder != null && sortOrder.equalsIgnoreCase("desc")) {
                order = criteriaBuilder.desc(orderByPath);
            }else if(sortOrder != null && sortOrder.equalsIgnoreCase("asc")) {
                order = criteriaBuilder.asc(orderByPath);
            }else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"invalid orderBy type");
            criteriaQuery.orderBy(order);
        }
       

        List<Book> result = entityManager.createQuery(criteriaQuery).setFirstResult((page - 1) * size).setMaxResults(size).getResultList();

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Book> bookRootCount = countQuery.from(Book.class);
        countQuery.select(criteriaBuilder.count(bookRootCount)).where(createPredicate(criteriaBuilder , bookRootCount, author, statusBook, category));
        Long totalItem = entityManager.createQuery(countQuery).getSingleResult();
        
        return PageResponse.success(result, page, size, totalItem);
    }

    private Predicate[] createPredicate(CriteriaBuilder criteriaBuilder, Root<Book> root, String author, String statusBook, String category){
        List<Predicate> predicates = new ArrayList<>();
        if (author != null && !author.isBlank() && !author.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("author"), "%" + author + "%"));
        }

        if (statusBook != null && !statusBook.isBlank() && !statusBook.isEmpty()){
            predicates.add(criteriaBuilder.like(root.get("statusBook"), "%" + statusBook + "%"));
        }

        if (category != null && !category.isBlank() && !category.isEmpty()) {
            Join<Object, Object> bookshelfJoin = root.join("bookshelf");
            predicates.add(criteriaBuilder.equal(bookshelfJoin.get("categoryBook"), category));
        }
        
        return predicates.toArray(new Predicate[0]);
    }
}
