package com.nerdblistersteam.concierge.service;

import com.nerdblistersteam.concierge.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Service
public class MailService {

    private Logger logger = LoggerFactory.getLogger(MailService.class);
    private final SpringTemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;
    private final String BASE_URL = "http://localhost:8080";

    public MailService(SpringTemplateEngine templateEngine, JavaMailSender javaMailSender) {
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultiPart, boolean isHTML) {
        logger.debug("Skickar mejl...");

        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
            message.setTo(to);
            message.setFrom("noreply@concierge.com");
            message.setSubject(subject);
            message.setText(content, isHTML);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            logger.warn("Mejl kunde inte skickas till användare '{}': {}", to, e.getMessage());
        }
    }

    @Async
    public void sendEmailFromTemplate(User user, String templateName, String subject) {
        Locale locale = new  Locale("sv", "SE");
        Context context = new Context(locale);
        context.setVariable("user", user);
        context.setVariable("baseURL", BASE_URL);
        String content = templateEngine.process(templateName, context);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendActivationEmail(User user) {
        logger.debug("Skickar aktiveringsmejl till '{}'", user.getEmail());
        sendEmailFromTemplate(user, "email/activation", "Concierge aktiveringsmejl");
    }

    @Async
    public void sendInvitationEmail(User user) {
        logger.debug("Skickar inbjudningsmejl till '{}'", user.getEmail());
        sendEmailFromTemplate(user, "email/invitation", "Inbjudan till Concierge");
    }
}
