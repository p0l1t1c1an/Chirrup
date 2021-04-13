package coms309.directmessagegroup;

import coms309.directmessage.DirectMessage;
import coms309.user.User;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.core.style.ToStringCreator;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class DirectMessageGroup {
    @ApiModelProperty(notes = "Id of the group",name="group_id",required=true)
    @Id
    @GeneratedValue
    private int group_id;

    private String name;

    @ManyToMany(mappedBy = "groups", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<User> members = new HashSet<User>();

    @PrimaryKeyJoinColumn
    @OneToMany(mappedBy = "from", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<DirectMessage> messages = new HashSet<DirectMessage>();

    public DirectMessageGroup() {
        
    }

    public DirectMessageGroup(String name) {
        this.name = name;
    }

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
    @JsonIgnore
    public Set<User> getMembers() {
        return members;
    }

    @JsonGetter("members")
    public List<Integer> getMembersId() {
        List<Integer> membersId = new ArrayList<Integer>();
        this.members.forEach(member -> membersId.add(member.getId()));
        return membersId;
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

    public void dismissMembers() {
        this.members.clear();
    }

    @Override
    public boolean equals(Object obj) {
        return this.getId() == ((DirectMessageGroup) obj).getId();
    }

    @Override
    public int hashCode() {
        return this.group_id;
    }
}