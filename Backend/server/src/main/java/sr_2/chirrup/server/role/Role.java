package sr_2.chirrup.server.role;

import org.springframework.core.style.ToStringCreator;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    private int id;

    @Column(name = "role")
    @NotFound(action = NotFoundAction.IGNORE)
    private String role;

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