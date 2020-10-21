package com.looseboxes.mailsender.app.web.service;

import com.looseboxes.mailsender.app.util.RandomStringUtils;
import com.looseboxes.mailsender.app.config.MailSenderProperties;
import com.looseboxes.mailsender.app.domain.EmailStatus;
import com.looseboxes.mailsender.app.domain.MailinglistUser;
import com.looseboxes.mailsender.app.repository.MailinglistUserRepository;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author hp
 */
@Service
public class UnsubscribeService {

    private final Logger log = LoggerFactory.getLogger(UnsubscribeService.class);

    public static final String ENDPOINT = "/unsubscribe";
    
    // From database column length - DO NOT CHANGE ABITRARILY
    private final int keyMaxLen = 32; 
    
    private final Charset charset = StandardCharsets.UTF_8;
    
    private final MailSenderProperties appProperties;
    
    private final MailinglistUserRepository repository;

    public UnsubscribeService(MailSenderProperties appProperties, MailinglistUserRepository repository) {
        this.appProperties = Objects.requireNonNull(appProperties);
        this.repository = Objects.requireNonNull(repository);
    }
    
    public String generateUnsubscibeLinkAndUpdateDatabase(String email) {
        final String key = this.generateKey();
        final MailinglistUser user = repository.findById(email)
                .orElseThrow(() -> new RuntimeException("Not Found: user having email: " + email));
        final String link = Objects.requireNonNull(appProperties.getBaseUrl()) + 
                ENDPOINT + "?email=" + this.encode(email) + "&key=" + key;
        user.setUnsubscribeKey(key);
        repository.saveAndFlush(user);
        return link;
    }

    public String unsubscribe(String emailBase64Encoded, String key) {
        
        final String email = this.decode(emailBase64Encoded);
    
        Optional<MailinglistUser> optional = repository.findById(email);
        
        if( ! optional.isPresent()) {
        
            return "An invalid email address was provided";
            
        }else{
        
            MailinglistUser user = optional.get();
            
            if( ! key.equals(user.getUnsubscribeKey())) {

                return "Invalid request. You may have already been unsubscribed";
                
            }else{
                
                user.setUnsubscribeKey(null);
                user.setEmailStatus(EmailStatus.user_opted_out_of_mailinglist);
                
                repository.saveAndFlush(user);
            
                return "You have been unsubscribed";
            }
        }
    }

    private String generateKey() {
        return RandomStringUtils.randomAlphanumeric(keyMaxLen);
    }
    
    private String encode(String s) {
        return new String(java.util.Base64.getUrlEncoder().encode(s.getBytes(charset)), charset);
    }
    
    private String decode(String s) {
        return new String(java.util.Base64.getUrlDecoder().decode(s), charset);
    }
}
