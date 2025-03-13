package com.bfits.common.util.report;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PdfReportGenerator {

    public byte[] generateDailyReportPdf(List<Document> reportData) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Daily Sales Report");
                contentStream.newLine();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(0, -30);

                for (Document data : reportData) {
                    contentStream.showText("Total Sales: " + data.getDouble("totalSales"));
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Total Orders: " + data.getInteger("orderCount"));
                    contentStream.newLineAtOffset(0, -20);
                }
                contentStream.endText();
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            return outputStream.toByteArray();
        }
    }
}
