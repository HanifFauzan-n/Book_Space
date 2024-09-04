package com.library.jafa.services.officer;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.jafa.dao.officer.BorrowingDao;
import com.library.jafa.dto.PageResponse;
import com.library.jafa.dto.officer.RecordLoanReqDto;
import com.library.jafa.dto.officer.RecordLoanResponseDto;
import com.library.jafa.entities.Book;
import com.library.jafa.entities.BorrowingBook;
import com.library.jafa.entities.Member;
import com.library.jafa.repositories.BookRepository;
import com.library.jafa.repositories.BorrowingBookRepository;
import com.library.jafa.repositories.MemberRepository;

@Service
public class RecordLoanServiceImp implements RecordLoanService {

    @Autowired
    BorrowingBookRepository borrowingRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BorrowingDao borrowingDao;

    @Override
    @Transactional
    public RecordLoanResponseDto recordLoans(RecordLoanReqDto dto) {
        if(dto.getBorrowedBook() == null || dto.getBorrowedBook().isEmpty() || dto.getBorrowedBook().isBlank()){
            throw new IllegalArgumentException("Book name cannot be empty.");
        }
        if(dto.getBorrowerName() == null || dto.getBorrowerName().isEmpty() || dto.getBorrowerName().isBlank()){
            throw new IllegalArgumentException("Borrower name cannot be empty.");
        }
        if(bookRepository.findByBookTitle(dto.getBorrowedBook()) == null){
            throw new IllegalArgumentException("Book name not found.");
        }
        if(memberRepository.findByMemberName(dto.getBorrowerName()) == null){
            throw new IllegalArgumentException("Borrower name not found.");
        }
        if (borrowingRepository.findByDescriptionAndMember_MemberNameAndBook_BookTitle("Borrowed", dto.getBorrowerName(), dto.getBorrowedBook()) != null){
            throw new IllegalArgumentException("Cannot borrow books that you have already borrowed.");

        }
        if(bookRepository.findByBookTitle(dto.getBorrowedBook()).getStockBook() <= 0) {
            throw new IllegalArgumentException("The book stock is out.");

        }
        

        Book book = bookRepository.findByBookTitle(dto.getBorrowedBook());
        book.setStockBook(book.getStockBook() - 1);
        if(book.getStockBook() == 0) {
            book.setStatusBook("Being borrowed");
        }
        Member member = memberRepository.findByMemberName(dto.getBorrowerName()).get();
        saveRecord(dto,book, member);

        return RecordLoanResponseDto.builder().borrowerName(member.getMemberName()).borrowedBook(book.getBookTitle())
                .categoryBook(book.getBookshelf().getCategoryBook()).loanDate(LocalDate.now())
                .returnDate(LocalDate.now().plusDays(7)).status("Books are being borrowed").penalties("Membership cards will be deleted if you are late 3 times in collecting borrowed books").note("note to be careful when storing or using books").build();
    }
    private BorrowingBook saveRecord(RecordLoanReqDto dto, Book book, Member member){
        BorrowingBook borrowing = new BorrowingBook();
        borrowing.setLoanDate(LocalDate.now());
        borrowing.setReturnDate(LocalDate.now().plusDays(7));
        borrowing.setDescription("Borrowed");
        borrowing.setBook(book);
        borrowing.setMember(member);
        return borrowingRepository.save(borrowing);
    
    }

            @Override
    public PageResponse<BorrowingBook> findAll(String member, String book, int page, int size,
            String sortBy, String sortOrder) {
        return borrowingDao.findAll(member, book, page, size, sortBy, sortOrder);
    }


    // public String removeOfficer(String id) {
    // LibraryOfficer officer = officerRepository.findById(id).orElse(null);
    // if (officer != null) {
    // officerRepository.delete(officer);
    // return "Success Remove officer ";
    // } else {
    // return "Failed remove officer";
    // }
    // }

    // public IdentityResponseDto updateOfficer(String id, RegistrationDto dto){
    // LibraryOfficer officer = officerRepository.findById(id).orElse(null);
    // Users users =
    // usersRepository.findById(officer.getUsers().getId()).orElse(null);
    // officer.setOfficerName(dto.getName());
    // officer.setOfficerAge(dto.getAge());
    // officer.setGender(dto.getGender());
    // officer.setOfficerAddress(dto.getAddress());
    // officer.setEmail(dto.getEmail());
    // users.setPassword(passwordEncoder.encode(dto.getPassword()));
    // users.setUserName(dto.getEmail());
    // users.setRole(users.getRole());
    // usersRepository.save(users);
    // officerRepository.save(officer);
    // IdentityResponseDto identity = new IdentityResponseDto().response(dto,
    // users);
    // return identity;
    // }

}
