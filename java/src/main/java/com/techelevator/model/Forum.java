package com.techelevator.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Forum {

    @JsonProperty("forum_id")
    private int forumId;
    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("forum_name")
    private String name;
    @JsonProperty("time_stamp")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;



    public Forum(int forumId, int userId, String name, LocalDateTime timestamp) {
        this.forumId = forumId;
        this.userId = userId;
        this.name = name;
        this.timestamp = timestamp;
    }
    //overload the constructor so that it can be declared before setting variables in the DAO
    public Forum(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserID(int userId) {
        this.userId = userId;
    }

    public int getForumId() {
        return forumId;
    }

    public void setForumId(int forumId) {
        this.forumId = forumId;
    }


}
