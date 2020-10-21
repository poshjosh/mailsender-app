package com.looseboxes.mailsender.app.repository;

import com.looseboxes.mailsender.app.domain.EmailStatus;
import com.looseboxes.mailsender.app.domain.MailinglistUser;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AccountDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MailinglistUserRepository extends JpaRepository<MailinglistUser, String>, JpaSpecificationExecutor<MailinglistUser> {
    
    Page<MailinglistUser> findByEmailStatusIs(Pageable pageable, EmailStatus status);
    
    Page<MailinglistUser> findByEmailStatusIn(Pageable pageable, List<EmailStatus> statusList);

    Page<MailinglistUser> findByEmailStatusNotIn(Pageable pageable, List<EmailStatus> statusList);
}
