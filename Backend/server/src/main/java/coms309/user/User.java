package coms309.user;

import org.springframework.core.style.ToStringCreator;

import coms309.profile.Profile;
import coms309.settings.Settings;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
public class User {
    
    //User properties
    @Id
    @GeneratedValue
    private int id;
    private String email;
    private String password;
    private String username;
    private String firstname;
    private String lastname;
    private int role;
    private String telephone;
    private String birthday;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Settings settings;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Profile profile;

    @ManyToMany(mappedBy = "following", cascade = CascadeType.ALL)
    private Set<User> followers = new HashSet<User>();

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<User> following = new HashSet<User>();

    public User(){
        
    }

    public User(User user){
        this.id = user.id;
        this.email = user.email;
        this.password = user.password;
        this.username = user.username;
        this.firstname = user.firstname;
        this.lastname = user.lastname;
        this.role = user.role;
        this.telephone = user.telephone;
        this.birthday = user.birthday;
        this.profile = user.profile;
        this.settings = user.settings;
    }

    public User(int id, String email, String password, String username, String firstname, String lastname, int role, String telephone, String birthday, Profile profile, Settings settings) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
        this.telephone = telephone;
        this.birthday = birthday;
        this.profile = profile;
        this.settings = settings;
    }

    //id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    //email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //password
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //username
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //firstname
    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    //lastname
    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    //role
    public int getRole() {
        return this.role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    //telephone
    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    //birthday
    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    //profile
    // public int getProfile() {
    //     return this.profile.getId();
    // }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    //add someone to this users following list
    public void setSettings(Settings settings) {
        this.settings = settings;
    }

	public void addFollowing(User follow) {
        this.following.add(follow);
	}

    //get following
    public Set<User> getFollowing() {
        return this.following;
    }

    //get followers
    public Set<User> getFollowers() {
        return this.followers;
    }

    void updateInfo(User user) {
        this.id = user.id;
        this.email = user.email;
        this.password = user.password;
        this.username = user.username;
        this.firstname = user.firstname;
        this.lastname = user.lastname;
        this.role = user.role;
        this.telephone = user.telephone;
        this.birthday = user.birthday;
        this.profile = user.profile;
        this.settings = user.settings;
    }
    
    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("id", this.getId())
                .append("email", this.getEmail())
                .append("password", this.getPassword())
                .append("username", this.getUsername())
                .append("firstname", this.getFirstname())
                .append("lastname", this.getLastname())
                .append("role", this.getRole())
                .append("telephone", this.getTelephone())
                .append("birthday", this.getBirthday())
                .toString();
    }

}
