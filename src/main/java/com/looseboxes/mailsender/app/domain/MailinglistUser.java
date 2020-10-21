package com.looseboxes.mailsender.app.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author hp
 */
@Entity
@Table(name = "mailinglist_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MailinglistUser implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "email_address", length = 255, nullable = false)
    private String emailAddress;
    
    @Column(name = "last_name", length = 127, nullable = true)
    private String lastName;
    
    @Column(name = "first_name", length = 127, nullable = true)
    private String firstName;
    
    @Column(name = "datein", nullable = false)
    private Instant datein = Instant.now();
    
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "email_status", nullable = true)
    private EmailStatus emailStatus = EmailStatus.none;
    
    @Column(name = "extra_details", length = 255, nullable = true)
    private String extraDetails;
    
    @Column(name = "unsubscribe_key", length = 32, nullable = true)            
    private String unsubscribeKey;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Instant getDatein() {
        return datein;
    }

    public void setDatein(Instant datein) {
        this.datein = datein;
    }

    public EmailStatus getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(EmailStatus emailStatus) {
        this.emailStatus = emailStatus;
    }

    public String getExtraDetails() {
        return extraDetails;
    }

    public void setExtraDetails(String extraDetails) {
        this.extraDetails = extraDetails;
    }

    public String getUnsubscribeKey() {
        return unsubscribeKey;
    }

    public void setUnsubscribeKey(String unsubscribeKey) {
        this.unsubscribeKey = unsubscribeKey;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.emailAddress);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MailinglistUser other = (MailinglistUser) obj;
        if (!Objects.equals(this.emailAddress, other.emailAddress)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UnofficialEmails{" + "emailAddress=" + emailAddress + ", lastName=" + lastName + 
                ", firstName=" + firstName + ", datein=" + datein + ", emailStatus=" + emailStatus + 
                ", extraDetails=" + extraDetails + ", unsubscribeKey=************" + '}';
    }
}
