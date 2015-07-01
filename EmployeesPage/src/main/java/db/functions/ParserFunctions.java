/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.functions;

import java.util.List;
import my.email.Inbox;
import my.email.Sent;

/**
 *
 * @author tigran
 */
public class ParserFunctions {
    
    private static Inbox inboxMessag = new Inbox();
    private static Sent sentMessage = new Sent();
    private static ParserFunctions object = null;
    
    private ParserFunctions() {
        
    }
    
    public static ParserFunctions getInstance() {
        if(null == object)
            object = new ParserFunctions();
        return object;
    }
    

    public Inbox getInboxMessag() {
        return inboxMessag;
    }

    public void setInboxMessag(Inbox inboxMessag) {
        this.inboxMessag = inboxMessag;
    }

    public Sent getSentMessage() {
        return sentMessage;
    }

    public void setSentMessage(Sent sentMessage) {
        this.sentMessage = sentMessage;
    }

    public String parseInboxToTable(List<Inbox> inboxMessages) {
        String inboxTable = "<table><tr><td> MessageID</td> <td>Message Text</td>"
                + "<td>Message Subject</td><td> From </td></tr>";
        for (Inbox message : inboxMessages) {
            inboxTable += "<tr><td><a href=\"javascript:ShowTheInboxMessage(" + message.getMessageNum()
                    + ")\">" + message.getMessageNum() + "</a></td><td>" + message.getMessageText() + "</td><td>"
                    + message.getMessageSubject() + "</td><td>" + message.getSender()
                    + "</td></tr>";
        }
        inboxTable += "</table>";
        System.out.println("inboxTable = " + inboxTable);
        return inboxTable;
    }

    public String parseSentboxToTable(List<Sent> sentMessages) {
        String sentTable = "<table><tr><td> MessageID</td> <td>Message Text</td>"
                + "<td>Message Subject</td><td> To </td></tr>";
        for (Sent message : sentMessages) {
            sentTable += "<tr><td><a href=\"javascript:ShowTheSentMessage(" + message.getMessageNum()
                    + ")\">" + message.getMessageNum()
                    + "</a></td><td>" + message.getMessageText() + "</td><td>"
                    + message.getMessageSubject() + "</td><td>" + message.getRecipients()
                    + "</td></tr>";
        }
        sentTable += "</table>";
        return sentTable;
    }

    public String parseAnInboxMessage() {
        String htmlMessage = "Message Subject <input type=\"text\" id=\"subjectOfMessage\" "
                + "name=\"subjectOfMessage\" value=\"" + inboxMessag.getMessageSubject()
                + "\" size=\"40\" /> <br>"
                + "Message From <input type=\"text\" id=\"messageFrom\""
                + "name=\"messageFrom\" value=\"" + inboxMessag.getSender()
                + "\" size=\"40\" /> <br>"
                + "<h1>Message Content </h1> <br>" + "<textarea name="
                + "\"inboxMessaageText\" id=\"inboxMessaageText\" cols=\"50\" "
                + "rows=\"5\" placeholder = \"" + inboxMessag.getMessageText()
                + "\">\n</textarea>";
        return htmlMessage;
    }

    public String parseASendMessage() {
        String htmlMessage = "Message Subject <input type=\"text\" id=\"subjectOfSentMessage\" "
                + "name=\"subjectOfSentMessage\" value=\"" + sentMessage.getMessageSubject()
                + "\" size=\"40\" /> <br>"
                + "Recipient <input type=\"text\" id=\"messageTo\""
                + "name=\"messageTo\" value=\"" + sentMessage.getRecipients()
                + "\" size=\"40\" /> <br>"
                + "<h1>Message Content </h1> <br>" + "<textarea name="
                + "\"sentMessaageText\" id=\"sentMessaageText\" cols=\"50\" "
                + "rows=\"5\" placeholder = \"" + sentMessage.getMessageText()
                + "\">\n</textarea>";
        return htmlMessage;
    }
}
