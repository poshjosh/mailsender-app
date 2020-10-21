package com.looseboxes.mailsender.app;

import com.looseboxes.mailsender.app.config.MailSenderProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties({MailSenderProperties.class})
public class MailSenderApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MailSenderApplication.class, args);
    }
}
