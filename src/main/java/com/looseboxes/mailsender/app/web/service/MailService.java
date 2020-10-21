package com.looseboxes.mailsender.app.web.service;

import com.looseboxes.gmailapi.GmailFactory;
import com.looseboxes.mailsender.app.config.MailSenderProperties;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

/**
 * @author hp
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);
    
    private static final String BASE_URL = "baseUrl";
    private static final String UNSUBSCRIBE_URL = "unsubscribeUrl";
    
    private final MailSenderProperties appProperties;
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final UnsubscribeService unsubscribeService;
    private final GmailFactory gmailFactory;

    public MailService(MailSenderProperties appProperties, JavaMailSender javaMailSender, SpringTemplateEngine templateEngine, UnsubscribeService unsub, GmailFactory gmailFactory) {
        this.appProperties = appProperties;
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.unsubscribeService = unsub;
        this.gmailFactory = gmailFactory;
    }

    public MimeMessage buildMessage(String to, String from, String subject, String content, boolean isMultipart, boolean isHtml) 
            throws MessagingException{
        
        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
        message.setTo(to);
        message.setFrom(from);
        message.setSubject(subject);
        message.setText(content, isHtml);
        return mimeMessage;
    }

    public MimeMessage buildMessageFromTemplate(
            String to, String from, String subject,
            String templateName, Map<String, Object> templateVariables, Locale locale) 
            throws MessagingException{
        Context context = new Context(locale);
        // We use clientBaseUrl not baseUrl
        context.setVariable(BASE_URL, appProperties.getClientBaseUrl());
        context.setVariable(UNSUBSCRIBE_URL, unsubscribeService.generateUnsubscibeLinkAndUpdateDatabase(to));
        context.setVariables(templateVariables);
        String content = templateEngine.process(templateName, context);
        return buildMessage(to, from, subject, content, false, true);
    }

    public boolean sendEmail(String to, String subject, String templateName) {
        
        return this.sendEmail(to, appProperties.getSenderEmail(), subject, templateName, Collections.EMPTY_MAP, Locale.ENGLISH);
    }
    
    public boolean sendEmail(String to, String from, String subject,
            String templateName, Map<String, Object> templateVariables, Locale locale) {
        
        log.debug("Sending email to '{}'", to);
        
        try{
            
            MimeMessage mimeMessage = buildMessageFromTemplate(to, from, subject,
                    templateName, templateVariables, locale);
            
            gmailFactory.getSender().send(mimeMessage);
            
            return true;
            
        }catch(MessagingException e) {
            
            log.warn("FAILED. to send mail to " + to, e);
            
            return false;
        }
    }
}
