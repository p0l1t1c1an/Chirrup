package settings;

import user.User;
import role.Role;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;


/**
* This is a standard settings class that is used
* to config appearance and notification settings. 
*
* @author  Jacob Boicken
*/


@Inheritance
@Entity
@Table(name = "settings")
public class ChildSettings extends Settings {

    @Column(name = "locked")
    @NotFound(action = NotFoundAction.IGNORE)
    private boolean locked;

    @Column(name = "timeLimit")
    @NotFound(action = NotFoundAction.IGNORE)
    private int timeLimit;

    @OneToMany
    private List<User> parents;

    @OneToMany
    private List<Role> roleWhitelist;

    public ChildSettings(){
        super();
        parents = new ArrayList<User>();
        roleWhitelist = new ArrayList<Role>();
    }

    public ChildSettings(int i, boolean l, int t){
        this.setId(i);
        locked = l;
        timeLimit = t;
        parents = new ArrayList<User>();
        roleWhitelist = new ArrayList<Role>();
    }

    public ChildSettings(int i, boolean d, int u, int text, boolean l, int time){
        super(i, d, u, text);
        locked = l;
        timeLimit = time;
        parents = new ArrayList<User>();
        roleWhitelist = new ArrayList<Role>();
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

    
    public List<User> getParents() {
        return parents;
    }

    public void setParents(List<User> p) {
        parents = p;
    }

    public void addParent(User p){
        parents.add(p);
    }

    public List<Role> getWhitelist() {
        return roleWhitelist;
    }

    public void setWhitelist(List<Role> w) {
        roleWhitelist = w;
    }

    public void addRoleToWhitelist(Role r){
        roleWhitelist.add(r);
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("id", this.getId())
                .append("darkMode", this.getDarkMode())
                .append("updateTime", this.getUpdateTime())
                .append("textSize", this.getTextSize())
                .toString();
    }
}
