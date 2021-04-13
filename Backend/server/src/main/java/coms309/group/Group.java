package coms309.group;

import coms309.directmessage.DirectMessage;
import coms309.user.User;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

import org.springframework.core.style.ToStringCreator;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Group {
    @ApiModelProperty(notes = "Id of the group",name="group_id",required=true)
    @Id
    @GeneratedValue
    private int group_id;

    private String name;

    @ManyToMany(mappedBy = "groups")
    private Set<User> members = new HashSet<User>();

    @PrimaryKeyJoinColumn
    @OneToMany(mappedBy = "from", cascade = CascadeType.ALL)
    private Set<DirectMessage> messages = new HashSet<DirectMessage>();

    //group_id
    public int getId() {
        return group_id;
    }

    public void setId(int id) {
        this.group_id = id;
    }

    //group_name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //members
    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }

    public void addMemberToGroup(User user) {
        this.members.add(user);
    }

    public void removeMemberFromGroup(User user) {
        this.members.remove(user);
    }

    //messages
    public Set<DirectMessage> getMessages() {
        return messages;
    }

    public void setMessages(Set<DirectMessage> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
            .append("id", this.getId())
            .append("name", this.getName()).toString();
    }
}