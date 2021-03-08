package com.example.profilesettings;

public class newProfileData {

    private String userName;
    private String firstName;
    private String lastName;
    private String bio;
    private int userID;

    public newProfileData(String userName, String firstName, String lastName, String bio, int userID) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.userID = userID;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getBio() {
        return this.bio;
    }

    public int getID() {return this.userID;}
}
