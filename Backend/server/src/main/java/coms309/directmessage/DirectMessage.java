package coms309.directmessage;

import org.springframework.core.style.ToStringCreator;

import coms309.user.User;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class DirectMessage {
    @ApiModelProperty(notes = "Id of the message",name="id",required=true)
    @Id
    @GeneratedValue
    private int id;

    @ApiModelProperty(notes = "User who sent the dm",name="from",required=true)
    @ManyToOne
    @JoinColumn(name = "from_id")
    private User from;

    @ApiModelProperty(notes = "User who recieved the dm",name="to",required=true)
    @ManyToOne
    @JoinColumn(name = "to_id")
    private User to;

    @ApiModelProperty(notes = "Content of the message",name="message",required=true)
    private String message;

    @ApiModelProperty(notes = "Date of the message being sent",name="dateSent",required=true)
    private LocalDateTime dateSent;

    public DirectMessage() {

    }

    public DirectMessage(DirectMessage dm) {
        this.id = dm.id;
        this.from = dm.from;
        this.to = dm.to;
        this.message = dm.message;
        this.dateSent = dm.dateSent;
    }

    public DirectMessage(int id, User from, User to, LocalDateTime dateSent, String message) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.message = message;
        this.dateSent = dateSent;
    }

    //id
    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    public User getTo() {
        return this.to;
    }

    public void setTo(User to) {
        this.to = to;
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
                .append("id", this.getId())
                .append("from", this.getFrom())
                .append("to", this.getTo())
                .append("message", this.getMessage())
                .append("dateSent", this.getDateSent()).toString();
    }
}
