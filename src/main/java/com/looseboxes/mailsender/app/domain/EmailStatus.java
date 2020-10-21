package com.looseboxes.mailsender.app.domain;

/**
 * @author hp
 */
public enum EmailStatus {
    none, unverified,  verified,  bounced,  disabled_or_discontinued, 
    unable_to_relay,  does_not_exist,  could_not_be_delivered_to,  black_listed, 
    verification_attempted_but_status_unknown,  user_opted_out_of_mailinglist, 
    registered_user,  automated_system_email,  restricted,  invalid_format;
}
