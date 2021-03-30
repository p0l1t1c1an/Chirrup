package cs309.sr2.chirrupfrontend.account;

/**
 * simple class to keep track of the currently logged in user.
 *
 * @author Jeremy Noesen
 */
public class Session {

    /**
     * id of the currently logged in user, defaults to -1 when nobody is logged in
     */
    private static int userID = -1;

    /**
     * set the currently logged in user by id
     *
     * @param userID id of user logged in
     */
    static void setUser(int userID) {
        Session.userID = userID;
    }

    /**
     * get the id of the user that is currently logged in
     *
     * @return id of currently logged in user
     */
    public static int getUser() {
        return userID;
    }
}
