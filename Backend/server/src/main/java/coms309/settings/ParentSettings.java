package coms309.settings;

import coms309.user.User;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.style.ToStringCreator;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


/**
* This is a parent's settings class that is used
* to config what children they have. 
*
* @author  Jacob Boicken
*/


@Entity
public class ParentSettings extends StandardSettings {

    @OneToMany
    @JoinColumn(name="children", referencedColumnName = "user_id")
    private List<ChildSettings> children = new ArrayList<ChildSettings>();
    
    public ParentSettings(){
        super();
    }

    public ParentSettings(User u){
        super(u);
    }

    public ParentSettings(User user, boolean d, int up, int t){
        super(user, d, up, t);
    }

    public List<ChildSettings> getChildren() {
        return children;
    }

    public void setChildren(List<ChildSettings> c) {
        children = c;
    }

    public void addChild(ChildSettings c){
        children.add(c);
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
