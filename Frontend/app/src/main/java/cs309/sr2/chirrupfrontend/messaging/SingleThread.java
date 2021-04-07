package cs309.sr2.chirrupfrontend.messaging;

import cs309.sr2.chirrupfrontend.settings.CurrentUserData;

/**
 * Class for a messaging thread with a single person.
 *
 * @author wszogg
 */
public class SingleThread extends MessageThread {

    //ID of the user
    private int fromID;
    //ID of user in conversation
    private int toID;
    //messages in conversation
    private String[] messages;

    public SingleThread(int toID, String[] messages) {
        this.fromID = CurrentUserData.currUser.getID();
        this.toID = toID;
        this.messages = messages;
    }

    @Override
    public String[] getMessages() {
        return this.messages;
    }

    @Override
    public int getUserID() {
        return this.fromID;
    }

    @Override
    public int getToID() {
        return this.toID;
    }
}
