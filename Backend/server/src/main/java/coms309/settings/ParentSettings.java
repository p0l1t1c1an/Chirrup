package coms309.settings;

import coms309.user.User;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.style.ToStringCreator;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
* This is a parent's settings class that is used
* to config what children they have. 
*
* @author  Jacob Boicken
*/


@Entity
public class ParentSettings extends StandardSettings {

    @ManyToMany  //Technically could be many to many, but would need way to add another parent
    @JsonIgnore
    private List<User> children = new ArrayList<User>();
    
    public ParentSettings(){
        super();
    }

    public ParentSettings(User u){
        super(u);
    }

    public ParentSettings(User user, boolean d, int up, int t){
        super(user, d, up, t);
    }

    @JsonIgnore
    public List<User> getChildren() {
        return children;
    }

    public void setChildren(List<User> c) {
        children = c;
    }

    public void addChild(User c){
        children.add(c);
    }

    public void removeChild(User c){
        children.remove(c);
    }

    public void updateSettings(ParentSettings p) {
        super.updateSettings(p);
        children = p.children;
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
