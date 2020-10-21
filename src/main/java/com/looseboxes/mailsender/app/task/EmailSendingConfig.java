package com.looseboxes.mailsender.app.task;

import com.looseboxes.mailsender.app.domain.EmailStatus;
import java.util.Arrays;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.domain.PageRequest;

/**
 * @author hp
 */
public class EmailSendingConfig {
    
    @NotBlank
    private String template = "mail/intro"; 
    
    @NotBlank
    private String subject; 
    
    @NotEmpty
    private List<String> statusList = Arrays.asList(EmailStatus.registered_user.name(), EmailStatus.verified.name()); 
    
    @NotNull
    @PositiveOrZero
    private int offset = 0;
    
    @NotNull
    @PositiveOrZero
    private int pageSize = 10;
    
    @NotNull
    @PositiveOrZero
    private int limit = Integer.MAX_VALUE;
    
    @NotNull
    @PositiveOrZero
    private long interval = 60_000;
    
    public PageRequest getPageRequest() {
        return PageRequest.of(offset, Math.min(pageSize, limit));
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    @Override
    public String toString() {
        return "EmailSendingConfig{" + "templateName=" + template + ", subject=" + subject + 
                ", statusList=" + statusList + ", offset=" + offset + ", pageSize=" + pageSize + 
                ", limit=" + limit + ", interval=" + interval +  '}';
    }
}
