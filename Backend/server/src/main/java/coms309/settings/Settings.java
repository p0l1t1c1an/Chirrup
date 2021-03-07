package coms309.settings;

import coms309.user.User;

import org.springframework.core.style.ToStringCreator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;


/**
* This is a standard settings class that is used
* to config appearance and notification settings. 
*
* @author  Jacob Boicken
*/

@Inheritance
@Entity
@Table(name = "settings")
public abstract class Settings {

    @Id
    @Column(name = "user_id")
    private int id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    public Settings(){
        
    }

    public Settings(Settings s){
        id = s.id;
        user = s.user;
    }

    public Settings(User u){
        id = u.getId();
        user = u;
    }

    //id
    public Integer getId() {
        return id;
    }

    
    public void setId(Integer id) {
        this.id = id;
    }
    

    public User getUser() {
        return user;
    }

    public void setUser(User u){
        id = u.getId();
        user = u;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("id", this.getId())
                .toString();
    }
}
