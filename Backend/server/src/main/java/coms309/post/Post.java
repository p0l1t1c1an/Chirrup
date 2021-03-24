package coms309.post;

import org.springframework.core.style.ToStringCreator;

import coms309.user.User;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

@Entity
public class Post {
    //Role properties
    @ApiModelProperty(notes = "Id of the Post",name="id",required=true)
    @Id
    @GeneratedValue
    private int id;

    // @Column(name = "creator_id")
    // private int creatorId;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    private Date dateCreated;

    private String content;

    public Post() {

    }

    public Post(Post post) {
        this.id = post.id;
        this.creator = post.creator;
        this.dateCreated = post.dateCreated;
        this.content = post.content;
    }

    public Post(int id, User creator, Date dateCreated, String content) {
        this.id = id;
        this.creator = creator;
        this.dateCreated = dateCreated;
        this.content = content;
    }

    //id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    //creator
    @JsonIgnore
    public User getCreator() {
        return this.creator;
    }

    @JsonGetter("creator")
    public int getCreatorId() {
        return this.creator.getId();
    }
    
    @JsonSetter("creator")
    public void setCreator(User creator) {
        this.creator = creator;
    }

    //dateCreated
    @JsonIgnore
    public Date getDateCreated() {
        return this.dateCreated;
    }

    @JsonGetter("dateCreated")
    public String getDateCreatedString() {
        if(this.dateCreated != null) {
            return this.dateCreated.toString();
        }
        return "";
    }

    @JsonSetter("dateCreated")
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    //content
    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    void updateInfo(Post post) {
        this.creator = post.creator;
        this.dateCreated = post.dateCreated;
        this.content = post.content;
    }

    void updatePartialInfo(Post post) {
        this.creator = post.creator == null ? this.creator : post.creator;
        this.dateCreated = post.dateCreated == null ? this.dateCreated : post.dateCreated;
        this.content = post.content == null ? this.content : post.content;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("id", this.getId())
                .append("creator", this.getCreator())
                .append("dateCreated", this.getDateCreated())
                .append("content", this.getContent()).toString();
    }
}