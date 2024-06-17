package com.techelevator.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


public class Post {

    @JsonProperty("post_id")
    private int postID;
    @JsonProperty("user_id")
    private int userID;
    @JsonProperty("forum_id")
    private int forumID;
    @JsonProperty("title")
    private String title;
    @JsonProperty("message")
    private String messageDetails;
    @JsonProperty("up_votes")
    private int upVotes;
    @JsonProperty("down_votes")
    private int downVotes;
    @JsonProperty("time_stamp")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timeStamp;
    @JsonProperty("location")
    private String location;



    public Post(int postID, int userID, int forumID, String title, String messageDetails,
                int upVotes, int downVotes, LocalDateTime timeStamp, String location) {
        this.postID = postID;
        this.userID = userID;
        this.forumID = forumID;
        this.title = title;
        this.messageDetails = messageDetails;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
        this.timeStamp = timeStamp;
        this.location = location;
    }

    //overload the constructor so that it can be declared before setting variables in the DAO
    public Post(){

    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getForumID() {
        return forumID;
    }

    public void setForumID(int forumID) {
        this.forumID = forumID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessageDetails() {
        return messageDetails;
    }

    public void setMessageDetails(String messageDetails) {
        this.messageDetails = messageDetails;
    }

    public int getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(int upVotes) {
        this.upVotes = upVotes;
    }

    public int getDownVotes() {
        return downVotes;
    }

    public void setDownVotes(int downVotes) {
        this.downVotes = downVotes;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
    public void setLocation(String location){
        this.location = location;
    }
    public String getLocation(){
        return location;
    }

    public void incrementUpVotes(){
        this.upVotes++;
    }
    public void incrementDownVotes(){
        this.downVotes++;
    }
    public int getScore(){
        return this.upVotes - this.downVotes;
    }

    //overloaded setter for converting Date parameter to LocalDateTime property
    /*public void setTimeStamp(Date timeStamp){
        Timestamp localDateTime = timeStamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.timeStamp = localDateTime;
    }*/

}
