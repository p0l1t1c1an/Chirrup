package settings;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
* This is a standard settings class that is used
* to config appearance and notification settings. 
*
* @author  Jacob Boicken
*/


@Entity
@Table(name = "settings")
public class Settings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    private int id;

    @Column(name = "darkMode")
    @NotFound(action = NotFoundAction.IGNORE)
    private boolean darkMode;

    @Column(name = "updateTime")
    @NotFound(action = NotFoundAction.IGNORE)
    private int updateTime;

    @Column(name = "textSize")
    @NotFound(action = NotFoundAction.IGNORE)
    private int textSize;

    public Settings(){
        
    }

    public Settings(int i, boolean d, int u, int t){
        id = i;
        darkMode = d;
        updateTime = u;
        textSize = t;
    }

    //id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
