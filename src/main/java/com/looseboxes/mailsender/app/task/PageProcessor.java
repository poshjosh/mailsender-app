package com.looseboxes.mailsender.app.task;

import com.looseboxes.mailsender.app.domain.MailinglistUser;
import org.springframework.data.domain.Page;

/**
 * @author hp
 */
public interface PageProcessor {
    void processPage(String templateName, String subject, Page<MailinglistUser> users);
}
