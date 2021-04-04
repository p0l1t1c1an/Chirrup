package coms309.settings;

import coms309.user.User;

import org.springframework.core.style.ToStringCreator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.Api;

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

    @ApiModelProperty(notes = "User's Id linked to user", name="id", required=true)
    @Id
    @Column(name = "user_id")
    private int id;

    @ApiModelProperty(notes = "The user linked these settings", name="user", required=true)
    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "user_id")
    @JsonIgnore
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
    

    public void setUser(User u){
        id = u.getId();
        user = u;
    }

    public void updateSettings(Settings s) {
        id = s.id;
        user = s.user;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("id", this.getId())
                .toString();
    }
}
