package coms309.servers;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;

import coms309.people.PeopleRepository;
import coms309.people.Person;

/**
 * Provides the Definition/Structure for the server table
 *
 * @author Tyler Green
 */

@Entity
@Table(name = "servers")
public class Server {

    // GenerationType.IDENTITY automatically generates ids i.e. you do not have to set this field
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    private int id;

    @Column(name = "name")
    @NotFound(action = NotFoundAction.IGNORE)
    private String name;

    public Server(){
        
    }

    public Server(int id, String name){
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)

                .append("id", this.getId())
                .append("name", this.getName()).toString();
    }
}
