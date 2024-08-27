package com.library.jafa.services.officer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.library.jafa.entities.BorrowingBook;
import com.library.jafa.repositories.BorrowingBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanFileServiceImpl implements LoanFileService {

    @Autowired
    BorrowingBookRepository BorrowingBookRepository;

    @Override
    public byte[] generateReport() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);

        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font tableFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            Paragraph title = new Paragraph("Lending Report", titleFont);
            title.setAlignment(com.lowagie.text.Element.ALIGN_CENTER);
            document.add(title);

            Paragraph emptyLine = new Paragraph("\n");
            document.add(emptyLine);
            
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1, 3, 3, 3, 3});

            PdfPCell cell;

            cell = new PdfPCell(new Paragraph("No", headerFont));
            cell.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph("Borrower Name", headerFont));
            cell.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph("Book Title", headerFont));
            cell.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph("Load Date", headerFont));
            cell.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph("Return Date", headerFont));
            cell.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
            table.addCell(cell);

            List<BorrowingBook> BorrowingBooks = BorrowingBookRepository.findAll();
            if (BorrowingBooks.isEmpty()) {
                cell = new PdfPCell(new Paragraph("No Data Available", tableFont));
                cell.setColspan(5);
                cell.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
                table.addCell(cell);
            } else {
                int rowNum = 1;
                for (BorrowingBook BorrowingBook : BorrowingBooks) {
                    table.addCell(new PdfPCell(new Paragraph(String.valueOf(rowNum), tableFont)));
                    table.addCell(new PdfPCell(new Paragraph(BorrowingBook.getMember().getMemberName() == null ? "-" : BorrowingBook.getMember().getMemberName(), tableFont)));
                    table.addCell(new PdfPCell(new Paragraph(BorrowingBook.getBook().getBookTitle(), tableFont)));
                    table.addCell(new PdfPCell(new Paragraph(BorrowingBook.getLoanDate().toString(), tableFont)));
                    table.addCell(new PdfPCell(new Paragraph(BorrowingBook.getReturnDate().toString(), tableFont)));
                    rowNum++;
                }
            }

            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            if (document != null) {
                document.close();
            }
        }

        return byteArrayOutputStream.toByteArray();
    }
}
