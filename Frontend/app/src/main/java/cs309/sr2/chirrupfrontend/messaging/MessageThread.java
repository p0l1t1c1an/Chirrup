package cs309.sr2.chirrupfrontend.messaging;

/**
 * Parent class for all subsequent message threads.
 *
 * @author wszogg
 */
public abstract class MessageThread {

    /**
     * Constructor to display a conversation
     */
    public MessageThread() {}

    /**
     * Get messages with specific contact.
     *
     * @return an array of messages
     */
    abstract public String[] getMessages();

    /**
     * Return user's ID
     *
     * @return user's ID
     */
    abstract public int getUserID();
}
