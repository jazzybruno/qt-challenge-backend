package com.supamenu.www.services.implementations;

import com.supamenu.www.exceptions.BadRequestException;
import com.supamenu.www.mailHandler.Mail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;

@Service
public class MailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String appEmail;

    @Value("${app_name}")
    private String appName;


    @Autowired
    public MailService(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }



    public void sendTransactionEmail(String subject , String fullNames ,  String to , String message ) {
        Mail mail = new Mail(
                appName,
                subject,
                fullNames,
                to,
                message,
                "banking-operation"
        );

        sendMail(mail);
    }

    @Async
    public void sendMail(Mail mail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            Context context = new Context();
            context.setVariable("app_name",mail.getAppName());
            context.setVariable("fullNames", mail.getFullNames());
            context.setVariable("message", mail.getMessage());
            context.setVariable("otherData", mail.getOtherData());
            context.setVariable("subject",mail.getSubject());


            String html = templateEngine.process(mail.getTemplate(), context);
            helper.setTo(mail.getToEmail());
            helper.setText(html, true);
            helper.setSubject(mail.getSubject());
            helper.setFrom(appEmail);
            mailSender.send(message);
        } catch (MessagingException exception) {
            exception.printStackTrace();
            throw new BadRequestException("Failed To Send An Email : z"+  exception.getMessage());
        }
    }

}