package cs309.sr2.chirrupfrontend.messaging;

/**
 * This class is a message thread with a group of people.
 *
 * @author wszogg
 */
public class GroupThread extends MessageThread {
    @Override
    public String[] getMessages() {
        return new String[0];
    }

    @Override
    public int getUserID() {
        return 0;
    }
}