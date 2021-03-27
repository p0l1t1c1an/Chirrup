package coms309.settings;

import coms309.user.User;
import coms309.role.Role;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.style.ToStringCreator;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

/**
* This is a child settings class that is used
* to config a permissions and limits of the acc. 
*
* @author  Jacob Boicken
*/


@Entity
public class ChildSettings extends StandardSettings {

    @ApiModelProperty(notes = "Is the child locked out?", name="locked", required=true)
    private boolean locked;

    @ApiModelProperty(notes = "How long can the child be on chirrup?", name="timeLimit", required=true)
    private int timeLimit;

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Role> roleWhitelist = new ArrayList<Role>(); 

    public ChildSettings(){
        super();
    }

    public ChildSettings(ChildSettings c){
       super(c);
       locked = c.locked;
       timeLimit = c.timeLimit;
    }

    public ChildSettings(User u){
        super(u);
        locked = false;
        timeLimit = -1;
    }

    public ChildSettings(User u, boolean l, int t){
        super(u);
        locked = l;
        timeLimit = t;
    }

    public ChildSettings(User user, boolean d, int up, int text, boolean l, int time){
        super(user, d, up, text);
        locked = l;
        timeLimit = time;
    }


    //Locked
    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean l) {
        locked = l;
    }

    //Time Limit
    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer t) {
        timeLimit = t;
    } 
 
    public List<Role> getWhitelist() {
        return roleWhitelist;
    }

    public void setWhitelist(List<Role> w) {
        roleWhitelist = w;
    }

    public void addToWhitelist(Role r){
        roleWhitelist.add(r);
    }

    public void removeFromWhitelist(Role r){
        roleWhitelist.remove(r);
    }

    public void updateSettings(ChildSettings c) {
        super.updateSettings(c);
        locked = c.locked;
        timeLimit = c.timeLimit;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("id", this.getId())
                .append("darkMode", this.getDarkMode())
                .append("updateTime", this.getUpdateTime())
                .append("textSize", this.getTextSize())
                .append("locked", this.getLocked())
                .append("timeLimit", this.getTimeLimit())
                .toString();
    }
}
