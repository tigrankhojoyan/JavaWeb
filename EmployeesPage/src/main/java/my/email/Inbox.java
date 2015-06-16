/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.email;

import javax.persistence.*;

/**
 *
 * @author tigran
 */
@Entity
@Table(name = "INBOX")
public class Inbox {

    @Id
    @GeneratedValue
    @Column(name = "messageNum")
    private int messageNum;

    @Column(name = "message_text")
    private String messageText;
    
    @Column(name = "message_subject")
    private String messageSubject;
    
    @Column(name = "message_sender")
    private String sender;

    public Inbox() {

    }

    public Inbox(String message, String subject, String sender) {
        this.messageSubject = subject;
        this.messageText = message;
        this.sender = sender;
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

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

}
