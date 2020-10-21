package com.looseboxes.mailsender.app.task;

import com.looseboxes.mailsender.app.domain.EmailStatus;
import com.looseboxes.mailsender.app.domain.MailinglistUser;
import com.looseboxes.mailsender.app.util.RandomStringUtils;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * @author hp
 */
@Component
@Profile("!prod")
public class EmailSendingPageContextDev implements PageProvider, PageProcessor{
    
    private final Logger log = LoggerFactory.getLogger(EmailSendingPageContextDev.class);
    
    @Override
    public Page<MailinglistUser> fetchPage(Pageable pageRequest, List<EmailStatus> statuses) {
        return new PageImpl(this.generateUsers(pageRequest, statuses), pageRequest, Integer.MAX_VALUE);
    }
    
    private List<MailinglistUser> generateUsers(Pageable pageRequest, List<EmailStatus> statuses) {
        log.info("Generating {} MailinglistUsers for page: {}", pageRequest);
        final int size = pageRequest.getPageSize();
        List<MailinglistUser> result = new ArrayList<>(size);
        for(int i=0; i<size; i++) {
            MailinglistUser user = new MailinglistUser();
            user.setDatein(Instant.now());
            user.setEmailAddress(RandomStringUtils.randomAlphanumeric(10) + "@gmail.com");
            user.setEmailStatus(statuses.get(0));
            result.add(user);
        }
        return result;
    }

    @Override
    public void processPage(String templateName, String subject, Page<MailinglistUser> page) {
        log.info("NOT PRODUCTION MODE. Hence, Page will not be processed.\nTemplate name: {}, subject: {}, pageNumber: {}, numberOfElements: {}, totalElements: {}",
                templateName, subject, page.getNumber(), page.getNumberOfElements(), page.getTotalElements());
    }
}
