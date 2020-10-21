package com.looseboxes.mailsender.app.task;

import com.looseboxes.mailsender.app.domain.EmailStatus;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author hp
 */
@Component
public class SendEmailTask implements Task<EmailSendingConfig, EmailSendingState>{

    private final Logger log = LoggerFactory.getLogger(SendEmailTask.class);
    
    private final PageProvider pageProvider;
    private final PageProcessor pageProcessor;
    
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final AtomicBoolean stopRequested = new AtomicBoolean(false);
    
    private long total = 0;
    private long processed = 0;

    public SendEmailTask(PageProvider pageProvider, PageProcessor pageProcessor) {
        this.pageProvider = Objects.requireNonNull(pageProvider);
        this.pageProcessor = Objects.requireNonNull(pageProcessor);
    }

    @Async
    @Override
    public void start(EmailSendingConfig config) {
        
        if(this.isRunning()) {
            throw new IllegalStateException("Task is already running");
        }
        
        log.info("STARTED MAIL SENDING\n{}\n=====================================", config);
        
        List<EmailStatus> statuses = config.getStatusList().stream()
                .map(EmailStatus::valueOf).collect(Collectors.toList());
        
        Pageable pageRequest = config.getPageRequest();
        
        try{
            
            running.compareAndSet(false, true);
        
            Page page;
            do{

                page = this.pageProvider.fetchPage(pageRequest, statuses);
                
                if(total < 1) {
                    total = page.getTotalElements();
                }
                
                pageRequest = page.nextOrLastPageable();
                
                if(this.isStopRequested()) {
                    break;
                }
                
                log.info("Processing: {}", pageRequest);
                
                this.pageProcessor.processPage(config.getTemplate(), config.getSubject(), page);

                processed += page.getNumberOfElements();
                
                this.waitBeforeNextBatch(config.getInterval());
                
            }while(processed < config.getLimit() && ! this.isStopRequested() && ! page.isLast());
            
        }finally{
        
            log.info("  ENDED MAIL SENDING\n{}\n{}\n=====================================", config, getSnapshot());
            
            running.compareAndSet(true, false);
            
            stopRequested.compareAndSet(true, false);
            total = 0;
            processed = 0;
        }
    }

    @Override
    public void stop() {
        stopRequested.compareAndSet(false, true);
    }

    @Override
    public boolean isRunning() {
        return running.get();
    }
    
    @Override
    public boolean isStopRequested() {
        return stopRequested.get();
    }

    @Override
    public EmailSendingState getSnapshot() {
        EmailSendingState state = new EmailSendingState();
        state.setProcessed(processed);
        state.setRunning(this.isRunning());
        state.setStopRequested(this.isStopRequested());
        state.setTotal(total);
        return state;
    }

    private synchronized void waitBeforeNextBatch(long interval) {
        try {
            if (interval > 0L) {
             
                log.debug("Waiting for {} milliseconds", interval);

                wait(interval);

                log.trace("Done waiting for {} milliseconds", interval);
            }
        } catch (InterruptedException e) {
            log.error("Crawler interrupted waiting before next batch. Crawler: \n" + this, e);
        } finally {
            notifyAll();
        }
    }
}
