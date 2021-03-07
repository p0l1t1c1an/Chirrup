package coms309.settings;

import coms309.user.User;

import org.springframework.core.style.ToStringCreator;

import javax.persistence.Entity;

/**
* This is a standard settings class that is used
* to config appearance and notification settings. 
*
* @author  Jacob Boicken
*/

@Entity
public class StandardSettings extends Settings {

    private boolean darkMode;
    private int updateTime;
    private int textSize;

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
        this.setUser(s.getUser());
        darkMode = s.darkMode;
        updateTime = s.updateTime;
        textSize = s.textSize;
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
