package cs309.sr2.chirrupfrontend.messaging;

import java.time.LocalDateTime;

/**
 * Represents a message sent from a server
 *
 * @author William Zogg
 */
public class MessageInfo {
    //message
    private String message;
    //date
    private LocalDateTime date;
    //sender
    private String sender;

    /**
     * Creates a message.
     *
     * @param message message content
     * @param date date sent
     * @param sender user who sent the message
     */
    public MessageInfo(String message, LocalDateTime date, String sender) {
        this.message = message;
        this.date = date;
        this.sender = sender;
    }

    /**
     * Get the message content
     *
     * @return message content
     */
    public String getMessage() {return message;}

    /**
     * Get the date of the message
     *
     * @return date the message was sent
     */
    public LocalDateTime getDate() {return date;}

    /**
     * User ID that sent the message
     *
     * @return User ID of message sender
     */
    public String getSender() {return sender;}
}
