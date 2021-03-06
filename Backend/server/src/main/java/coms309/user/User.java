package coms309.user;

import org.springframework.core.style.ToStringCreator;
import org.springframework.web.multipart.MultipartFile;

import coms309.directmessage.DirectMessage;
import coms309.directmessagegroup.DirectMessageGroup;
import coms309.post.Post;
import coms309.settings.Settings;
import io.swagger.annotations.ApiModelProperty;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

@Entity
public class User {
    
    public final static int STANDARD = 1;
    public final static int CHILD = 2;
    public final static int PARENT = 3;
    public final static int ADMIN = 4;

    //User properties
    
    @ApiModelProperty(notes = "Id of the User",name="id",required=true)
    @Id
    @GeneratedValue
    private int id;
    @ApiModelProperty(notes = "Email of the User",name="email",required=true)
    private String email;
    @ApiModelProperty(notes = "Password of the User",name="password",required=true)
    private String password;
    @ApiModelProperty(notes = "Username of the User",name="username",required=true)
    private String username;
    @ApiModelProperty(notes = "Firstname of the User",name="firstname",required=true)
    private String firstname;
    @ApiModelProperty(notes = "Lastname of the User",name="lastname",required=true)
    private String lastname;
    @ApiModelProperty(notes = "Role of the User",name="role",required=true)
    private int role;
    @ApiModelProperty(notes = "Phone number of the User",name="telephone",required=true)
    private String telephone;
    @ApiModelProperty(notes = "Birthday of the User",name="birthday",required=true)
    private Date birthday;
    @ApiModelProperty(notes = "Biography of the User",name="biography",required=true)
    private String biography;
    @ApiModelProperty(notes = "Profile picture path of the User",name="pfp",required=true)
    private String pfp;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Settings settings;

    @ManyToMany(mappedBy = "following", fetch= FetchType.EAGER)
    private Set<User> followers = new HashSet<User>();

    @ManyToMany(fetch= FetchType.EAGER)
    private Set<User> following = new HashSet<User>();

    @OneToMany(mappedBy = "creator", fetch= FetchType.EAGER)
    @PrimaryKeyJoinColumn
    private Set<Post> posts = new HashSet<Post>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
      name = "group_user",
      joinColumns = @JoinColumn(name = "user_id"), 
      inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<DirectMessageGroup> groups = new HashSet<DirectMessageGroup>();

    @ManyToMany(mappedBy = "from", fetch= FetchType.EAGER)
    private Set<DirectMessage> messages = new HashSet<DirectMessage>();

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> blocking = new HashSet<User>();

    @ManyToMany(mappedBy = "blocking", fetch = FetchType.EAGER)
    private Set<User> blockers = new HashSet<User>();


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
        this.settings = user.settings;
        this.biography = user.biography;
    }

    public User(int id, String email, String password, String username, String firstname, String lastname, int role, String telephone, Date birthday, Settings settings, String biography) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
        this.telephone = telephone;
        this.birthday = birthday;
        this.settings = settings;
        this.biography = biography;
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
    @JsonIgnore
    public Date getBirthday() {
        return this.birthday;
    }

    @JsonGetter("birthday")
    public String getBirthdayString() {
        if(this.birthday != null) {
            return this.birthday.toString();
        }
        return "";
    }

    @JsonSetter("birthday")
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    //biography
    public String getBiography() {
        return this.biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

   //settings
   @JsonIgnore
   public Settings getSettings() {
        return this.settings;
   }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

	public void addFollowing(User follow) {
        this.following.add(follow);
	}

    //get following
    @JsonIgnore
    public Set<User> getFollowing() {
        return this.following;
    }

    //get following ids
    @JsonGetter("following")
    public List<Integer> getFollowingId() {
        List<Integer> fs = new ArrayList<Integer>();
        for (User f : this.following) {
            fs.add(f.getId());
        }
        return fs;
    }

    //get followers
    @JsonIgnore
    public Set<User> getFollowers() {
        return this.followers;
    }

    //get followers ids
    @JsonGetter("followers")
    public List<Integer> getFollowersId() {
        List<Integer> fs = new ArrayList<Integer>();
        for (User f : this.followers) {
            fs.add(f.getId());
        }
        return fs;
    }

	public void addBlocking(User block) {
        this.blocking.add(block);
	}

    public void removeBlocking(User unblock) {
        this.blocking.remove(unblock);
    }

    //get blocking
    @JsonIgnore
    public Set<User> getBlocking() {
        return this.blocking;
    }

    //get blocking ids
    @JsonGetter("blocking")
    public List<Integer> getBlockingId() {
        List<Integer> blocked = new ArrayList<Integer>();
        for (User b : this.blocking) {
            blocked.add(b.getId());
        }
        return blocked;
    }

    public void removeBlocker(User unblock) {
        this.blockers.remove(unblock);
    }


    //get blockers
    @JsonIgnore
    public Set<User> getBlockers() {
        return this.blockers;
    }

    //get blockers ids
    @JsonGetter("blockers")
    public List<Integer> getBlockersId() {
        List<Integer> blocks = new ArrayList<Integer>();
        for (User b : this.blockers) {
            blocks.add(b.getId());
        }
        return blocks;
    }

    @JsonIgnore
    public Set<Post> getPosts() {
        return this.posts;
    }

    @JsonGetter("posts")
    public List<Integer> getPostsId() {
        List<Post> ps = new ArrayList<Post>();
        for (Post p : this.posts) {
            ps.add(p);
        }

        Comparator<Post> c = Post.getComparator();
        
        ps.sort(c);

        List<Integer> psIntegers = new ArrayList<Integer>();
        for (Post p : ps) {
            psIntegers.add(p.getId());
        }

        return psIntegers;
    }

    @JsonIgnore
    public String getProfilePicturePath() {
        return this.pfp;
    }

    @JsonIgnore
    public void setProfilePicturePath(String path) {
        this.pfp = path;
    }

    //changes all fields of a user, for put requests
    void updateInfo(User user) {
        this.email = user.email;
        this.password = user.password;
        this.username = user.username;
        this.firstname = user.firstname;
        this.lastname = user.lastname;
        this.role = user.role;
        this.telephone = user.telephone;
        this.birthday = user.birthday;
        this.biography = user.biography;
    }

    //only changes things which aren't null, useful for patch requests
    void updatePartialInfo(User user) {
        this.email = user.email == null ? this.email : user.email;
        this.password = user.password == null ? this.password : user.password;
        this.username = user.username == null ? this.username : user.username;
        this.firstname = user.firstname == null ? this.firstname : user.firstname;
        this.lastname = user.lastname == null ? this.lastname : user.lastname;
        this.role = user.role == 0 ? this.role : user.role;
        this.telephone = user.telephone == null ? this.telephone : user.telephone;
        this.birthday = user.birthday == null ? this.birthday : user.birthday;
        this.biography = user.biography == null ? this.biography : user.biography;
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
                .append("biography", this.getBiography())
                .append("birthday", this.getBirthday().toString())
                .toString();
    }

    public void removeFollowing(User currentUser) {
        Iterator<User> it = this.following.iterator();

        while(it.hasNext()) {
            User c = it.next();
            if(c.getId() == currentUser.getId()) {
                it.remove();
            }
        }
    }

    public void removeFollower(User currentUser) {
        Iterator<User> it = this.followers.iterator();

        while(it.hasNext()) {
            User c = it.next();
            if(c.getId() == currentUser.getId()) {
                it.remove();
            }
        }
    }

    //groups
    @JsonIgnore
    public Set<DirectMessageGroup> getGroups() {
        return groups;
    }

    @JsonGetter("groups")
    public List<Integer> getGroupsId() {
        List<Integer> groupsId = new ArrayList<Integer>();
        this.groups.forEach(group -> groupsId.add(group.getId()));
        return groupsId;
    }

    public void setGroups(Set<DirectMessageGroup> groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;

        if(obj == null || obj.getClass()!= this.getClass())
            return false;

        User u = (User) obj;

        return (u.id == id && u.role == role
                && Objects.equals(email, u.email)
                && Objects.equals(password, u.password)
                && Objects.equals(username, u.username)
                && Objects.equals(firstname, u.firstname)
                && Objects.equals(lastname, u.lastname)
                && Objects.equals(telephone, u.telephone)
                && Objects.equals(biography, u.biography)
                && Objects.equals(birthday, u.birthday) );
    }

    @Override
    public int hashCode() {
        return id;
    }

    public void addGroup(DirectMessageGroup group) {
        this.groups.add(group);
    }

    public void removeFromGroup(DirectMessageGroup group) {
        this.groups.remove(group);
    }

    public void removeMessage(int id) {
        this.messages.removeIf(message -> message.getId() == id);
    }
}
