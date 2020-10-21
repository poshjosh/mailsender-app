package com.looseboxes.mailsender.app.task;

import com.looseboxes.mailsender.app.domain.EmailStatus;
import com.looseboxes.mailsender.app.domain.MailinglistUser;
import com.looseboxes.mailsender.app.repository.MailinglistUserRepository;
import com.looseboxes.mailsender.app.web.service.MailService;
import java.util.List;
import java.util.Objects;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * @author hp
 */
@Component
@Profile("prod")
public class EmailSendingPageContext implements PageProvider, PageProcessor{
    
    private final MailService mailService;
    private final MailinglistUserRepository repository;
    
    public EmailSendingPageContext(MailService mailService, MailinglistUserRepository repository) {
        this.mailService = Objects.requireNonNull(mailService);
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public Page<MailinglistUser> fetchPage(Pageable pageRequest, List<EmailStatus> statuses) {
        return repository.findByEmailStatusIn(pageRequest, statuses);
    }

    @Override
    public void processPage(String templateName, String subject, Page<MailinglistUser> users) {
        
        users.stream().forEach((user) -> {
            
            mailService.sendEmail(user.getEmailAddress(), subject, templateName);
        });
    }
}
