package coms309.group;

import coms309.directmessage.DirectMessage;
import coms309.user.User;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.springframework.core.style.ToStringCreator;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Group {
    @ApiModelProperty(notes = "Id of the message",name="group_id",required=true)
    @Id
    @GeneratedValue
    private int group_id;

    private String group_name;

    @ManyToMany(mappedBy = "user_id")
    private Set<User> members = new HashSet<User>();

    @OneToMany(mappedBy = "from_id")
    private Set<DirectMessage> messages = new HashSet<DirectMessage>();

    //group_id
    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    //group_name
    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
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
            .append("group_id", this.getGroup_id())
            .append("group_name", this.getGroup_name()).toString();
    }
}