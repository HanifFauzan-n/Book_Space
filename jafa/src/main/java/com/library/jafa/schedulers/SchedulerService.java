package com.library.jafa.schedulers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.library.jafa.entities.BorrowingBook;
import com.library.jafa.repositories.BorrowingBookRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SchedulerService {

    @Autowired
    private BorrowingBookRepository borrowingBookRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    public void dailycheck() {
        List<BorrowingBook> borrowingBooks = borrowingBookRepository.findAll();
        for (BorrowingBook b : borrowingBooks) {
            if (b.getReturnDate() == LocalDate.now()) {
                sendEmail(b.getMember().getEmail(), "Please immediately return the books you have borrowed");
                log.info("have sent reminders via email");
            }
        }
    }

    @Scheduled(cron = "0 0 0 */1 * * ") // Run every 1 day for checking
    public void sendReminderEmails() {
        dailycheck();
        log.info("Checking...");

    }

    private void sendEmail(String to, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject("Reminder: Book Return");
        mailMessage.setText(message);
        javaMailSender.send(mailMessage);
    }

}
