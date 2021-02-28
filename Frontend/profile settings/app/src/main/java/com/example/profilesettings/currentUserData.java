package com.example.profilesettings;

public class currentUserData {

    private String userName;
    private String firstName;
    private String lastName;
    private String bio;

    public currentUserData(String userName, String firstName, String lastName, String bio) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
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
}
