package user;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    private int id;

    @Column(name = "email")
    @NotFound(action = NotFoundAction.IGNORE)
    private String email;

    @Column(name = "password")
    @NotFound(action = NotFoundAction.IGNORE)
    private String password;

    @Column(name = "username")
    @NotFound(action = NotFoundAction.IGNORE)
    private String username;

    @Column(name = "firstname")
    @NotFound(action = NotFoundAction.IGNORE)
    private String firstname;

    @Column(name = "lastname")
    @NotFound(action = NotFoundAction.IGNORE)
    private String lastname;

    @Column(name = "role")
    @NotFound(action = NotFoundAction.IGNORE)
    private int role;

    @Column(name = "telephone")
    @NotFound(action = NotFoundAction.IGNORE)
    private String telephone;

    @Column(name = "birthday")
    @NotFound(action = NotFoundAction.IGNORE)
    private String birthday;

    public User(){
        
    }

    public User(int id, String email, String password, String username, String firstname, String lastname, int role, String telephone,String birthday){
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
        this.telephone = telephone;
        this.birthday = birthday;

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
    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
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
