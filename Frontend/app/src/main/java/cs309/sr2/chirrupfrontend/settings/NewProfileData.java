package cs309.sr2.chirrupfrontend.settings;

public class NewProfileData {

    private String email;
    private String password;
    private String userName;
    private String firstName;
    private String lastName;
    private String role;
    private String telephone;
    private String birthday;
    private String bio;
    private int userID;

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

    public String getPassword() {return this.password;}

    public String getEmail() {return this.email;}

    public String getUserName() {
        return this.userName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getRole() {return this.role;}

    public String getTelephone() {return this.telephone;}

    public String getBirthday() {return this.birthday;}

    public String getBio() {
        return this.bio;
    }

    public int getID() {return this.userID;}

    //set methods
    public void setPassword(String password) {this.password = password;}

    public void setEmail(String email) {this.email = email;}

    public void setUserName(String username) {this.userName = username;}

    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    public void setRole(String role) {this.role = role;}

    public void setTelephone(String telephone) {this.telephone = telephone;}

    public void setBirthday(String birthday) {this.birthday = birthday;}

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setID(int ID) {this.userID = ID;}
}
