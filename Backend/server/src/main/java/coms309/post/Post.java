package coms309.post;

import org.springframework.core.style.ToStringCreator;
import org.springframework.data.jpa.domain.QAbstractAuditable;

import coms309.user.User;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

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

    @ApiModelProperty(notes = "Creator of the Post",name="creator",required=true)
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @ApiModelProperty(notes = "Date post was created",name="dateCreated",required=true)
    private LocalDateTime dateCreated;

    @ApiModelProperty(notes = "Content of the post",name="content",required=true)
    private String content;

    @OneToOne
    @JoinColumn(name = "parent_id")
    private Post parent;

    @OneToMany(cascade = CascadeType.ALL,  mappedBy = "parent", fetch= FetchType.EAGER)
    private Set<Post> comments = new HashSet<Post>();

    @ManyToMany(fetch= FetchType.EAGER)
    private Set<User> likes = new HashSet<User>();

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> reports = new HashSet<User>();

    public Post() {

    }

    public Post(Post post) {
        this.id = post.id;
        this.creator = post.creator;
        this.dateCreated = post.dateCreated;
        this.content = post.content;
    }

    public Post(int id, User creator, LocalDateTime dateCreated, String content) {
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
    public LocalDateTime getDateCreated() {
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
    public void setDateCreated(LocalDateTime dateCreated) {
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

    //comments
    @JsonIgnore
    public Set<Post> getComments() {
        return comments;
    }

    @JsonGetter("comments")
    public List<Integer> getCommentIds() {
        List<Post> com = new ArrayList<Post>();
        for (Post p : this.comments) {
            com.add(p);
        }

        Comparator<Post> c = getComparator();
        
        com.sort(c);

        List<Integer> comIntegers = new ArrayList<Integer>();
        for (Post p : com) {
            comIntegers.add(p.getId());
        }

        return comIntegers;
    }

    //likes
    @JsonIgnore
    public Set<User> getLikes() {
        return likes;
    }

    @JsonGetter("likes")
    public List<Integer> getLikeIds() {
        List<Integer> likeList = new ArrayList<Integer>();
        for (User u : this.likes) {
            likeList.add(u.getId());
        }
        
        return likeList;
    }

    public void addLike(User currentUser) {
        this.likes.add(currentUser);
    }

    
    public void addComment(Post comment) {
        this.comments.add(comment);
    }

    public void removeComment(Post post) {
        this.comments.remove(post);
    }

    public void dismissComments() {
        for (Post post : comments) {
            post.dismissComments();
        }
        this.comments.clear();
    }

    public void removeLike(User currentUser) {
        Iterator<User> it = this.likes.iterator();

        while(it.hasNext()) {
            User c = it.next();
            if(c.getId() == currentUser.getId()) {
                it.remove();
            }
        }
    }

    //parent
    public void setParent(Post parentPost) {
        this.parent = parentPost;
    }

    @JsonIgnore
    public Post getParent() {
        return parent;
    }

    public void removeParent() {
        this.parent = null;
    }

    @JsonGetter("parent")
    public Integer getParentsId() {
        return this.parent == null ? null : this.parent.getId();
    }

    public static Comparator<Post> getComparator() {
        Comparator<Post> c = new Comparator<Post>(){

            @Override
            public int compare(Post o1, Post o2) {
                
                return o1.getDateCreated().isBefore(o2.getDateCreated()) == true ? -1 : 1;
            }
            
        };
        return c;
    }

    //likes
    @JsonIgnore
    public Set<User> getReports() {
        return reports;
    }

    public void addReport(User currentUser) {
        this.reports.add(currentUser);
    }

    public void removeReport(User u) {
        this.reports.remove(u);
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
