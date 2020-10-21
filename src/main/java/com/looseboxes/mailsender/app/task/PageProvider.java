package com.looseboxes.mailsender.app.task;

import com.looseboxes.mailsender.app.domain.EmailStatus;
import com.looseboxes.mailsender.app.domain.MailinglistUser;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author hp
 */
public interface PageProvider {
    Page<MailinglistUser> fetchPage(Pageable pageRequest, List<EmailStatus> statuses);
}
