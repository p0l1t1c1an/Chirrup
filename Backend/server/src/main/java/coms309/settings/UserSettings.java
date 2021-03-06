package coms309.settings;

import coms309.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import org.springframework.core.style.ToStringCreator;

import javax.persistence.Entity;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
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
public class UserSettings extends Settings {

    // Generic Settings
    @ApiModelProperty(notes = "Dark or Light Background Theme", name="darkMode", required=true)
    private boolean darkMode;
    
    @ApiModelProperty(notes = "How long until new post are loaded in feed", name="updateTime", required=true)
    private int updateTime;
    
    @ApiModelProperty(notes = "Size of the user's text in px", name="textSize", required=true)
    private int textSize;

    // Child Settings
    @ApiModelProperty(notes = "Is the child locked out?", name="locked", required=true)
    private boolean locked;

    @ApiModelProperty(notes = "How long can the child be on chirrup?", name="timeLimit", required=true)
    private int timeLimit;

    @ElementCollection(targetClass=Integer.class)
    @JsonIgnore
    private List<Integer> roleWhitelist = new ArrayList<Integer>(); 

    @ManyToMany(mappedBy = "children", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<UserSettings> parents = new HashSet<UserSettings>();

    // Parent Settings
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<UserSettings> children = new HashSet<UserSettings>();
    

    public UserSettings(){
        
    }

    public UserSettings(UserSettings s){
        super(s);
        darkMode = s.darkMode;
        updateTime = s.updateTime;
        textSize = s.textSize;
        locked = s.locked;
        timeLimit = s.timeLimit;
    }

    public UserSettings(User u){
        super(u);
        
        // Defaults
        darkMode = false;
        updateTime = 5;
        textSize = 12; 
        locked = false;
        timeLimit = 30;
    }

    public UserSettings(User user, boolean d, int up, int t){
        super(user);
        darkMode = d;
        updateTime = up;
        textSize = t;
        locked = false;
        timeLimit = 30;
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

    @JsonIgnore
    public List<Integer> getWhitelist() {
        return roleWhitelist;
    }

    public void setWhitelist(List<Integer> w) {
        roleWhitelist = w;
    }

    public void addToWhitelist(Integer r){
        roleWhitelist.add(r);
    }

    public void removeFromWhitelist(Integer r){
        roleWhitelist.remove(r);
    }


    // Parent Controls
    @JsonIgnore
    public Set<UserSettings> getParents() {
        return parents;
    }

    public void setParents(Set<UserSettings> p) {
        parents = p;
    }

    public void addParent(UserSettings p){
        parents.add(p);
    }

    public void removeParent(UserSettings p){
        parents.remove(p);
    }

    @JsonIgnore
    public Set<UserSettings> getChildren() {
        return children;
    }

    public void setChildren(Set<UserSettings> c) {
        children = c;
    }

    public void addChild(UserSettings c){
        children.add(c);
    }

    public void removeChild(UserSettings c){
        children.remove(c);
    }

    public void updateSettings(UserSettings s){
        super.updateSettings(s);
        darkMode = s.darkMode;
        updateTime = s.updateTime;
        textSize = s.textSize;
        locked = s.locked;
        timeLimit = s.timeLimit;
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

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;

        if(obj == null || obj.getClass()!= this.getClass())
            return false;

        UserSettings u = (UserSettings) obj;

        return (u.getId() == getId() && u.darkMode == darkMode
                && u.updateTime == updateTime
                && u.textSize == textSize
                && u.locked == locked
                && u.timeLimit == timeLimit);
    }

    @Override
    public int hashCode(){
        return getId();
    }
}
