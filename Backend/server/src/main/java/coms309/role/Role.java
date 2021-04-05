package coms309.role;

import org.springframework.core.style.ToStringCreator;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Role {
    
    //Role properties
    @ApiModelProperty(notes = "Id of the Role",name="id",required=true)
    @Id
    @GeneratedValue
    private int id;

    @ApiModelProperty(notes = "Name of the Role",name="role",required=true)
    private String role;
    
    // @OneToMany(targetEntity = Role.class, cascade = CascadeType.ALL)
    // @JoinColumn(name="ur_fk", referencedColumnName = "id")
    // private List<User> users;

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

    public Role(int id, String role) {
        this.id = id;
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
