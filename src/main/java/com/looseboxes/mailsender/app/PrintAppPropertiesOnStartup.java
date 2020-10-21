package com.looseboxes.mailsender.app;

import com.looseboxes.mailsender.app.config.MailSenderProperties;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author hp
 */
@Component
public class PrintAppPropertiesOnStartup implements CommandLineRunner{
    
    private final Logger log = LoggerFactory.getLogger(PrintAppPropertiesOnStartup.class);
    
    private final MailSenderProperties appProperties;

    public PrintAppPropertiesOnStartup(MailSenderProperties appProperties) {
        this.appProperties = Objects.requireNonNull(appProperties);
    }

    @Override
    public void run(String... args) {
        log.info("Printing app properties\n{}", appProperties);
        log.info("Printing user home: {}", System.getProperty("user.home"));
    }
}
