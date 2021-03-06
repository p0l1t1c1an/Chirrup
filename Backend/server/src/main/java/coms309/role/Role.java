package coms309.role;

import org.springframework.core.style.ToStringCreator;

import coms309.user.User;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Role {
    
    @Id
    @GeneratedValue
    private int id;

    private String role;
    
    // @OneToMany(targetEntity = Role.class, cascade = CascadeType.ALL)
    // @JoinColumn(name="ur_fk", referencedColumnName = "id")
    // private List<User> users;

    public Role(){
        
    }

    public Role(Role role) {
        this.id = role.id;
        this.role = role.role;
    }

    public Role(String role){
        this.role = role;
    }

    //id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    //role
    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("id", this.getId())
                .append("role", this.role).toString();
    }
}
