package coms309.profile;

import org.springframework.core.style.ToStringCreator;

import coms309.user.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "profile")
public class Profile {
    
    @Id
    @Column(name = "user_id")
    private int id;

    private String biography;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    public Profile(){
        
    }

    public Profile(Profile profile) {
        this.id = profile.id;
        this.biography = profile.biography;
        this.user = profile.user;
    }

    public Profile(User user) {
        this.user = user;
        this.biography = "";
        this.id = user.getId();
	}

	//id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    //userid
    // public 
    // int getUser() {
    //     return this.user.getId();
    // }

    public void setUser(User user) {
        this.user = user;
    }

    //biography
    public String getBiography() {
        return this.biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
            .append("id", this.getId())
            .append("biography", this.getBiography())
            .toString();
    }
}