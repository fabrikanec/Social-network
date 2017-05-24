package main.java.column.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.cassandra.mapping.UserDefinedType;

import java.util.Date;
import java.util.UUID;

@UserDefinedType
@Persistent
public class Message {

    @Id
    private UUID message_id;
    
    private UUID user_id;

    private boolean receaverMsgDeletedFlag;

    private boolean posterMsgDeletedFlag;

    private String text;

    private Date date;

    public Message(UUID message_id, UUID user_id, String text, Date date) {
        this.message_id = message_id;
        this.user_id = user_id;
        this.text = text;
        this.date = date;
    }

    public UUID getMessage_id() {
        return message_id;
    }

    public void setMessage_id(UUID message_id) {
        this.message_id = message_id;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isReceaverMsgDeletedFlag() {
        return receaverMsgDeletedFlag;
    }

    public void setReceaverMsgDeletedFlag(boolean receaverMsgDeletedFlag) {
        this.receaverMsgDeletedFlag = receaverMsgDeletedFlag;
    }

    public boolean isPosterMsgDeletedFlag() {
        return posterMsgDeletedFlag;
    }

    public void setPosterMsgDeletedFlag(boolean posterMsgDeletedFlag) {
        this.posterMsgDeletedFlag = posterMsgDeletedFlag;
    }
}