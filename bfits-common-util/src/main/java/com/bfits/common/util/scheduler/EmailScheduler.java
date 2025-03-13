package com.bfits.common.util.scheduler;

import com.bfits.common.util.email.EmailService;
import com.bfits.common.util.mongo.MongoReportRepository;
import com.bfits.common.util.report.PdfReportGenerator;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@Component
public class EmailScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private MongoReportRepository mongoReportRepository;

    @Autowired
    private PdfReportGenerator pdfReportGenerator;


    @Scheduled(cron = "0 0 8 * * ?")  // Every day at 8 AM
    public void sendDailyReportEmail() throws MessagingException, IOException {
        List<Document> reportData = mongoReportRepository.getDailyReport();
        byte[] pdfAttachment = pdfReportGenerator.generateDailyReportPdf(reportData);

        String emailBody = "<h3>Daily Sales Report</h3><p>Please find the attached report.</p>";

        emailService.sendEmailWithAttachment(
                "user@example.com",
                "Daily Sales Report",
                emailBody,
                pdfAttachment,
                "Daily_Report.pdf"
        );
        System.out.println("Daily report email sent successfully!");
    }
}
