package cs309.sr2.chirrupfrontend.settings;

/**
 * Class for creating a new user for the local client.
 *
 * @author wszogg
 */
public class NewProfileData {

    //User email.
    private String email;
    //User password.
    private String password;
    //User's username.
    private String userName;
    //User's first name.
    private String firstName;
    //User's last name.
    private String lastName;
    //User's role.
    private String role;
    //User's telephone number.
    private String telephone;
    //User's birthday.
    private String birthday;
    //User's bio.
    private String bio;
    //User's ID number.
    private int userID;

    /**
     * Constructor to create the user.
     *
     * @param email User Email.
     * @param password User Password.
     * @param userName UserName.
     * @param firstName User First Name.
     * @param lastName User Last Name.
     * @param role User's Role.
     * @param telephone User Telephone.
     * @param bio User Biography.
     * @param birthday User Birthday.
     * @param userID User's ID.
     */
    public NewProfileData(String email, String password, String userName, String firstName,
                          String lastName, String role, String telephone, String bio,
                          String birthday, int userID) {

        this.password = password;
        this.email = email;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.telephone = telephone;
        this.birthday = birthday;
        this.bio = bio;
        this.userID = userID;
    }

    /**
     * Get the user's password.
     *
     * @return The user's password.
     */
    public String getPassword() {return this.password;}

    /**
     * Get the user's email.
     *
     * @return The user's email.
     */
    public String getEmail() {return this.email;}

    /**
     * Get the user's username.
     *
     * @return The user's username.
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * Get the user's first name.
     *
     * @return The user's first name.
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Get the user's last name.
     *
     * @return The user's last name.
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Get the user's role.
     *
     * @return The user's role.
     */
    public String getRole() {return this.role;}

    /**
     * Get the user's telephone.
     *
     * @return The user's telephone.
     */
    public String getTelephone() {return this.telephone;}

    /**
     * Get the user's birthday.
     *
     * @return The user's birthday.
     */
    public String getBirthday() {return this.birthday;}

    /**
     * Get the user's bio.
     *
     * @return The user's bio.
     */
    public String getBio() {
        return this.bio;
    }

    /**
     * Get the user's ID.
     *
     * @return The user's ID
     */
    public int getID() {return this.userID;}

    /**
     * Change the user's password.
     *
     * @param password The new password.
     */
    public void setPassword(String password) {this.password = password;}

    /**
     * Change the user's email.
     *
     * @param email The new email.
     */
    public void setEmail(String email) {this.email = email;}

    /**
     * Change the user's username.
     *
     * @param username The new username.
     */
    public void setUserName(String username) {this.userName = username;}

    /**
     * Change the user's first name.
     *
     * @param firstname The new first name.
     */
    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    /**
     * Change the user's last name.
     *
     * @param lastname The new last name.
     */
    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    /**
     * Change the user's role.
     *
     * @param role The new role.
     */
    public void setRole(String role) {this.role = role;}

    /**
     * Change the user's telephone.
     *
     * @param telephone The new telephone.
     */
    public void setTelephone(String telephone) {this.telephone = telephone;}

    /**
     * Change the user's birthday.
     *
     * @param birthday The new birthday.
     */
    public void setBirthday(String birthday) {this.birthday = birthday;}

    /**
     * Change the user's bio.
     *
     * @param bio The new bio.
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * Change the user's ID.
     *
     * @param ID The new ID.
     */
    public void setID(int ID) {this.userID = ID;}
}
