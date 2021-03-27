package coms309.settings;

import coms309.user.User;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.style.ToStringCreator;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

/**
* This is a standard settings class that is used
* to config appearance and notification settings. 
*
* @author  Jacob Boicken
*/

@Entity
public class StandardSettings extends Settings {

    @ApiModelProperty(notes = "Dark or Light Background Theme", name="darkMode", required=true)
    private boolean darkMode;
    

    @ApiModelProperty(notes = "How long until new post are loaded in feed", name="updateTime", required=true)
    private int updateTime;
    
    @ApiModelProperty(notes = "Size of the user's text in px", name="textSize", required=true)
    private int textSize;

    @ManyToMany
    @JsonIgnore
    private List<User> blockedUsers = new ArrayList<User>();

    public StandardSettings(){
        
    }

    public StandardSettings(StandardSettings s){
        super(s);
        darkMode = s.darkMode;
        updateTime = s.updateTime;
        textSize = s.textSize;
    }

    public StandardSettings(User u){
        super(u);
        
        // Defaults
        darkMode = false;
        updateTime = 5;
        textSize = 12; 
    }

    public StandardSettings(User user, boolean d, int up, int t){
        super(user);
        darkMode = d;
        updateTime = up;
        textSize = t;
    }

    //Dark Mode
    public Boolean getDarkMode() {
        return darkMode;
    }

    public void setDarkMode(Boolean d) {
        darkMode = d;
    }

    //Update Time
    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer u) {
        updateTime = u;
    } 

    //Text Size
    public Integer getTextSize() {
        return textSize;
    }

    public void setTextSize(Integer t) {
        textSize = t;
    }

    public void updateSettings(StandardSettings s){
        super.updateSettings(s);
        darkMode = s.darkMode;
        updateTime = s.updateTime;
        textSize = s.textSize;
    }

    public List<User> getBlocked() {
        return blockedUsers;
    }

    public void addBlocked(User u) {
        blockedUsers.add(u);
    }

    public void removeBlocked(User u) {
        blockedUsers.remove(u);
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
