package coms309.directmessage;

import org.springframework.core.style.ToStringCreator;

import coms309.directmessagegroup.DirectMessageGroup;
import coms309.user.User;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity

public class DirectMessage {
    @ApiModelProperty(notes = "Id of the message",name="id",required=true)
    @Id
    @GeneratedValue
    private int message_id;

    @ApiModelProperty(notes = "User who sent the dm",name="from",required=true)
    @ManyToOne
    @JoinColumn(name = "from_id")
    private User from;

    @ApiModelProperty(notes = "Group who recieved the dm",name="to",required=true)
    @ManyToOne
    @JoinColumn(name = "group_id")
    private DirectMessageGroup to;

    @ApiModelProperty(notes = "Content of the message",name="message",required=true)
    private String message;

    @ApiModelProperty(notes = "Date of the message being sent",name="dateSent",required=true)
    private LocalDateTime dateSent;

    public DirectMessage() {

    }

    public DirectMessage(DirectMessage dm) {
        this.message_id = dm.message_id;
        this.from = dm.from;
        this.to = dm.to;
        this.message = dm.message;
        this.dateSent = dm.dateSent;
    }

    public DirectMessage(int id, User from, DirectMessageGroup to, LocalDateTime dateSent, String message) {
        this.message_id = id;
        this.from = from;
        this.to = to;
        this.message = message;
        this.dateSent = dateSent;
    }

    //id
    public int getId() {
        return message_id;
    }

    public void setId(Integer id) {
        this.message_id = id;
    }

    //from
    @JsonIgnore
    public User getFrom() {
        return this.from;
    }

    @JsonGetter("from")
    public int getFromId() {
        return this.from.getId();
    }

    public void setFrom(User from) {
        this.from = from;
    }

    //to
    public DirectMessageGroup getTo() {
        return this.to;
    }

    public void setTo(DirectMessageGroup to) {
        this.to = to;
        if(to != null) {
            to.addMessage(this);
        }
    }

    @JsonGetter("to")
    public int getToId() {
        return this.to.getId();
    }

    //message
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    //dateSent
    @JsonIgnore
    public LocalDateTime getDateSent() {
        return this.dateSent;
    }

    public void setDateSent(LocalDateTime dateSent) {
        this.dateSent = dateSent;
    }

    @JsonGetter("dateSent")
    public String getDateSentString() {
        if(this.dateSent != null) {
            return this.dateSent.toString();
        }
        return "";
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("message_id", this.getId())
                .append("from", this.getFrom())
                .append("to", this.getTo())
                .append("message", this.getMessage())
                .append("dateSent", this.getDateSent()).toString();
    }
}
