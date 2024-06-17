package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class Comment {
    @JsonProperty("comment_id")
    private int commentID;

    @JsonProperty("user_id")
    private int userID;

    @JsonProperty("message")
    private String messageDetails;

    @JsonProperty("time_stamp")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timeStamp;
    @JsonProperty("post_id")
    private int postID;

    @JsonProperty("reply_to")
    private int replyToID; //null if replying to a post

    @JsonProperty("location")
    private String location;
    //Getters

    public int getCommentID() {
        return this.commentID;
    }

    public int getUserID(){
        return this.userID;
    }

    public int getReplyToID(){
        return this.replyToID;
    }

    public int getPostID(){
        return this.postID;
    }

    public String getMessageDetails(){
        return this.messageDetails;
    }

    public LocalDateTime getTimeStamp() {
        return this.timeStamp;
    }
    public String getLocation(){
        return this.location;
    }

    //Setters
    public void setCommentID(int commentID){
        this.commentID = commentID;
    }
    public void setUserID(int userID){
        this.userID = userID;
    }

    public void setReplyToID(int replyToID) {
        this.replyToID = replyToID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public void setMessageDetails(String messageDetails){
        this.messageDetails = messageDetails;
    }

    public void setTimeStamp(LocalDateTime  timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setLocation(String location){
        this.location = location;
    }

}
