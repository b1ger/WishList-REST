package com.wishlist.service.impl;

import com.wishlist.model.Subscribition;
import com.wishlist.model.User;
import com.wishlist.repository.SubscribitionRepository;
import com.wishlist.service.EmailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    private SubscribitionRepository subscribitionRepository;

    @Value("b.gerashchencko@gmail.com")
    private String emailFrom;

    @Value("${-Demail.activation-base-url}")
    private String baseUrl;

    @Autowired
    public EmailServiceImpl(SubscribitionRepository subscribitionRepository, JavaMailSender javaMailSender, SpringTemplateEngine templateEngine) {
        this.subscribitionRepository = subscribitionRepository;
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public Subscribition subscribe(Subscribition subscribition) {
        return subscribitionRepository.save(subscribition);
    }

    @Override
    public Subscribition unsubscribe(Subscribition subscribition) {
        return null;
    }

    @Async
    public void sendEmail(final String to, final String subject, final String content, boolean isMultipart, boolean isHtml) {
        // this.logger.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
        //        isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        try {
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(to);
            message.setFrom(this.emailFrom);
            message.setSubject(subject);
            message.setText(content, isHtml);
            this.javaMailSender.send(mimeMessage);
            // this.logger.debug("Sent email to User '{}'", to);
        } catch (final Exception e) {
//            if (this.logger.isDebugEnabled()) {
//                this.logger.warn("Email could not be sent to user '{}'", to, e);
//            } else {
//                this.logger.warn("Email could not be sent to user '{}': {}", to, e.getMessage());
//            }
        }
    }

    @Async
    @Override
    public void sendActivationEmail(User user) {
        // this.logger.debug("Sending activation email to '{}'", user.getEmail());
        final Map<String, Object> context = new HashMap<>(2);
        context.put("user", user);
        context.put("baseUrl", this.baseUrl);
        this.sendEmailFromTemplate(user.getEmail(), "Active your account", "email/activationEmail", context);
    }

    @Async
    public void sendEmailFromTemplate(
            final String to,
            final String subject,
            final String templateName,
            final Map<String, Object> context
    ) {
        final Context templateContext = new Context(Locale.getDefault());
        templateContext.setVariables(context);
        final String content = this.templateEngine.process(templateName, templateContext);
        this.sendEmail(to, subject, content, false, true);
    }
}
