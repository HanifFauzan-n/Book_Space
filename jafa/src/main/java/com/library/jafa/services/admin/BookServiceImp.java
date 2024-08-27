package com.library.jafa.services.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.library.jafa.dao.book.BookDao;
import com.library.jafa.dto.PageResponse;
import com.library.jafa.dto.admin.BookRequestDto;
import com.library.jafa.entities.Book;
import com.library.jafa.entities.Bookshelf;
import com.library.jafa.repositories.BookRepository;
import com.library.jafa.repositories.BookshelfRepository;
import com.library.jafa.repositories.RoleRepository;

@Service
public class BookServiceImp implements BookService {

    @Autowired
    RoleRepository rolesRepository;

    @Autowired
    BookshelfRepository bookshelfRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookDao bookDao;

    private final String AVAILABLE = "TERSEDIA";

    @Override
    @Transactional
    public Book addBook(BookRequestDto dto) {
        validasi(dto);

        if (bookRepository.findAll().isEmpty()) {
            Bookshelf bookshelf = saveBookshelf(dto);
            Book book = saveBook(dto, bookshelf);
            return book;
        }
        if (bookRepository.findByBookTitle(dto.getBookTitle()) != null) {

            if (dto.getBookTitle().equalsIgnoreCase(bookRepository.findByBookTitle(dto.getBookTitle()).getBookTitle())
                    && dto.getAuthor().equalsIgnoreCase(bookRepository.findByBookTitle(dto.getBookTitle()).getAuthor())) {
                Book book = bookRepository.findByBookTitle(dto.getBookTitle());
                book.setBookTitle(dto.getBookTitle());
                book.setAuthor(dto.getAuthor());
                Bookshelf bookshelf = bookshelfRepository.findByCategoryBook(dto.getCategoryBook());
                bookshelf.setFillBookshelf(bookshelf.getFillBookshelf() + dto.getFill());
                book.setBookshelf(bookshelf);
                book.setDescription(dto.getDescriptionBook());
                book.setRecordingDate(LocalDate.now());
                book.setStatusBook(AVAILABLE);
                book.setStockBook(book.getStockBook() + dto.getFill());
                return book;
            }
        }


        Book book = new Book();
        book.setBookTitle(dto.getBookTitle());
        book.setAuthor(dto.getAuthor());

        // Cari atau buat rak buku baru sesuai dengan kategori buku
        Bookshelf bookshelf = saveBookshelf(dto);
        book.setBookshelf(bookshelf);

        book.setDescription(dto.getDescriptionBook());
        book.setRecordingDate(LocalDate.now());
        book.setStatusBook(AVAILABLE);
        book.setStockBook(dto.getFill()); // Atur stok buku sesuai dengan jumlah yang diisi pertama kali

        // Simpan buku ke dalam database
        return bookRepository.save(book);
    }

    private void validasi(BookRequestDto dto) {
        // Validate book title
        if (dto.getBookTitle() == null || dto.getBookTitle().isEmpty()) {
            throw new IllegalArgumentException("Book title must not be empty.");
        }
    
        if (dto.getBookTitle().length() < 2 || dto.getBookTitle().length() > 40) {
            throw new IllegalArgumentException("Book title length must be between 2 and 40 characters.");
        }
        // Validate author
        if (dto.getAuthor() == null || dto.getAuthor().isEmpty()) {
            throw new IllegalArgumentException("Author name must not be empty.");
        }
        if (dto.getAuthor().length() < 2 || dto.getAuthor().length() > 40) {
            throw new IllegalArgumentException("Author name length must be between 2 and 40 characters.");
        }
        // Check allowed characters (only letters, spaces, apostrophes, and hyphens)
        if (!dto.getAuthor().matches("^[a-zA-Z\\s'-]*$")) {
            throw new IllegalArgumentException(
                    "Author name can only consist of letters, spaces, apostrophes, and hyphens.");
        }
        if (bookshelfRepository.findByCategoryBook(dto.getCategoryBook()) == null) {
            throw new IllegalArgumentException("Cannot find matching book category.");
        }
        if (dto.getFill() + bookshelfRepository.findByCategoryBook(dto.getCategoryBook())
                .getFillBookshelf() > bookshelfRepository.findByCategoryBook(dto.getCategoryBook()).getCapacity()) {
            throw new IllegalArgumentException("Cannot add books exceeding bookshelf capacity.");
    
        }
    }
    

    private Bookshelf saveBookshelf(BookRequestDto dto) {
        Bookshelf bookshelf = bookshelfRepository.findByCategoryBook(dto.getCategoryBook());
        bookshelf.setFillBookshelf(bookshelf.getFillBookshelf() + dto.getFill());
        return bookshelf;
    }

    private Book saveBook(BookRequestDto dto, Bookshelf bookshelf) {
        Book book = Book.builder().bookTitle(dto.getBookTitle()).author(dto.getAuthor())
                .description(dto.getDescriptionBook()).recordingDate(LocalDate.now()).statusBook(AVAILABLE)
                .stockBook(dto.getFill()).bookshelf(bookshelf).build();
        return bookRepository.save(book);

    }

    public String removeBook(String id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            Bookshelf bookshelf = bookshelfRepository.findById(book.getBookshelf().getId()).orElse(null);
            bookshelf.setFillBookshelf(bookshelf.getFillBookshelf() - book.getStockBook());

            bookRepository.delete(book);
            return "Success Remove book";
        } else {
            return "Book ID not found";
        }
    }

    public Book updateBook(String id, BookRequestDto dto) {
        validasi(dto);
        if (bookRepository.findByBookTitle(dto.getBookTitle()) != null) {

            if (dto.getBookTitle().equals(bookRepository.findByBookTitle(dto.getBookTitle()).getBookTitle())
                    && dto.getAuthor().equalsIgnoreCase(bookRepository.findByBookTitle(dto.getBookTitle()).getAuthor())) {
                throw new IllegalArgumentException(
                        "Maybe you entered the wrong ID, because there is the same book title and author");

            }
        }
        Bookshelf bookshelf = bookshelfRepository.findByCategoryBook(dto.getCategoryBook());
        Book book = bookRepository.findById(id).orElse(null);
        book.setBookTitle(dto.getBookTitle());
        book.setAuthor(dto.getAuthor());
        book.setDescription(dto.getDescriptionBook());
        if (bookshelf.getCategoryBook().equals(book.getBookshelf().getCategoryBook())) {
            bookshelf.setFillBookshelf(bookshelf.getFillBookshelf() - book.getStockBook() + dto.getFill());
        } else {
            bookshelfRepository.findByCategoryBook(book.getBookshelf().getCategoryBook()).setFillBookshelf(
                    bookshelfRepository.findByCategoryBook(book.getBookshelf().getCategoryBook()).getFillBookshelf()
                            - book.getStockBook());
            bookshelf.setFillBookshelf(bookshelf.getFillBookshelf() + dto.getFill());
        }
        book.setBookshelf(bookshelf);
        book.setStockBook(dto.getFill());
        return bookRepository.save(book);

    }

    @Override
    public PageResponse<Book> findAll(String author, String statusBook, String category, int page, int size,
            String sortBy, String sortOrder) {
        return bookDao.findAll(author, statusBook, category, page, size, sortBy, sortOrder);
    }

    public void uploadBookPhoto(String id, MultipartFile photo)
            throws IOException, SQLException {
        String[] filename = Objects.requireNonNull(photo.getResource().getFilename()).split("\\.");
        if (!filename[filename.length - 1].equalsIgnoreCase("jpg")
                && !filename[filename.length - 1].equalsIgnoreCase("jpeg")
                && !filename[filename.length - 1].equalsIgnoreCase("png")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported filetype");
        }
        System.out.println(filename);

        Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            book.setPhotoBook(new SerialBlob(photo.getBytes()));
            bookRepository.save(book);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book notFound");
        }
    }

}
