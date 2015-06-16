/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.email;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author tigran
 */
@Entity
@Table(name = "SENT")
public class Sent {

    @Id
    @GeneratedValue
    @Column(name = "messageNum")
    private int messageNum;
    
    @Column(name = "message_text")
    private String messageText;
    
    @Column(name = "message_subject")
    private String messageSubject;
    
    @Column(name = "message_recipients")
    private String recipients;

    public Sent() {

    }

    public Sent(String message, String subject, String recipients) {
        this.messageSubject = subject;
        this.messageText = message;
        this.recipients = recipients;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageSubject() {
        return messageSubject;
    }

    public void setMessageSubject(String messageSubject) {
        this.messageSubject = messageSubject;
    }

    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }
    
    @Override
    public String toString() {
        String sendStringData = "{\"messageText\": \""
                + getMessageText() + "\", \"messageSubject\":\""
                + getMessageSubject() + "\", \"recipients\":\""
                + getRecipients() + "\"}";
        return sendStringData;
    }
    
}
