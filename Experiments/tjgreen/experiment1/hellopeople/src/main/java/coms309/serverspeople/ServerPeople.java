package coms309.serverspeople;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;

/**
 * Provides the Definition/Structure for the people table
 *
 * @author Tyler Green
 */

@Entity
@Table(name = "serverpeople")
public class ServerPeople {

    // GenerationType.IDENTITY automatically generates ids i.e. you do not have to set this field
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    private int id;

    @Column(name = "serverId")
    @NotFound(action = NotFoundAction.IGNORE)
    private int serverId;

    @Column(name = "personId")
    @NotFound(action = NotFoundAction.IGNORE)
    private int personId;

    public ServerPeople(){
        
    }

    public ServerPeople(int id, int serverID, int personID){
        this.id = id;
        this.serverId = serverID;
        this.personId = personID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getServerId() {
        return serverId;
    }

    public int getPersonId() {
        return personId;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("id", this.getId())
                .append("serverId", this.getServerId())
                .append("personId", this.getPersonId()).toString();
    }
}
