package com.library.jafa.services.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.jafa.dao.book.BookshelfDao;
import com.library.jafa.dto.PageResponse;
import com.library.jafa.dto.admin.BookshelfRequestDto;
import com.library.jafa.entities.Bookshelf;
import com.library.jafa.repositories.BookRepository;
import com.library.jafa.repositories.BookshelfRepository;
import com.library.jafa.repositories.RoleRepository;

@Service
public class BookshelfServiceImp implements BookshelfService {

    @Autowired
    RoleRepository rolesRepository;

    @Autowired
    BookshelfRepository bookshelfRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookshelfDao bookshelfDao;

    @Override
    @Transactional
    public Bookshelf addBookshelf(BookshelfRequestDto dto) {
        validasi(dto);
        Bookshelf bookshelf = saveBookshelf(dto);
        return bookshelf;
    }

    private void validasi(BookshelfRequestDto dto){
        if(dto.getCapacity() < 10 || dto.getCapacity() > 100){
            throw new IllegalArgumentException("Capacity must be between 10 and 100."); 
        }
        if(dto.getCategoryBook() == null || dto.getCategoryBook().isEmpty()){
            throw new IllegalArgumentException("Book category cannot be empty.");
        }
        if(bookshelfRepository.findByCategoryBook(dto.getCategoryBook()) != null){
            if(bookshelfRepository.findByCategoryBook(dto.getCategoryBook()).getCategoryBook().equals(dto.getCategoryBook()) ){
                throw new IllegalArgumentException("Book category is already registered.");
            }
        }
        if(dto.getDescriptionBookshelf().length() < 10 || dto.getDescriptionBookshelf().length() > 250){
            throw new IllegalArgumentException("Description must be between 10 and 250 characters.");
        }
    }
    

    private Bookshelf saveBookshelf(BookshelfRequestDto dto) {
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setCategoryBook(dto.getCategoryBook());
        bookshelf.setDescriptionBookshelf(dto.getDescriptionBookshelf());
        bookshelf.setCapacity(dto.getCapacity());
        bookshelf.setFillBookshelf(0);
        return bookshelfRepository.save(bookshelf);
    }

    public String removeBookshelf(String id) {
        // Memeriksa apakah bookshelf tidak bernilai null sebelum mengaksesnya
        Bookshelf bookshelf = bookshelfRepository.findById(id).orElse(null);
        if (bookshelf != null) {
            if (bookshelf.getFillBookshelf() == 0) {
                bookshelfRepository.delete(bookshelf);
                return "Success Remove bookshelf";
            } else {
                return "On the bookshelf there are still books available";
            }
        } else {
            return "Failed remove bookshelf: Bookshelf not found";
        }
    }
    

    public Bookshelf updateBookshelf(String id, BookshelfRequestDto dto) {
        validasi(dto);
        Bookshelf bookshelf = bookshelfRepository.findByCategoryBook(dto.getCategoryBook());
        bookshelf.setCategoryBook(dto.getCategoryBook());
        bookshelf.setDescriptionBookshelf(dto.getDescriptionBookshelf());
        bookshelf.setCapacity(dto.getCapacity());
        bookshelf.setFillBookshelf(0);
        return bookshelfRepository.save(bookshelf);
    }

    @Override
    public PageResponse<Bookshelf> findAll(String category, Integer capacity, int page, int size, String sortBy, String sortOrder) {
        return bookshelfDao.findAll(category, capacity, page, size, sortBy, sortOrder);
    }

}
