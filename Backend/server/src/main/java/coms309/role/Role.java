package coms309.role;

import org.springframework.core.style.ToStringCreator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Role {
    
    //Role properties
    @Id
    @GeneratedValue
    private int id;
    private String role;

    //constructors
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