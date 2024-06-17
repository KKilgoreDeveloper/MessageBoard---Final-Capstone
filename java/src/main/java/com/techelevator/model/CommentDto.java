package com.techelevator.model;
/*
    The acronym DTO is being used for "data transfer object". It means that this type of class is specifically
    created to transfer data between the client and the server. For example, LoginDto represents the data a
    client must pass to the server for a login endpoint, and LoginResponseDto represents the object that's returned
    from the server to the client from a login endpoint.
 */
public class CommentDto {

    private int postId;
    private String message;
    private String location;
    private int replyToId;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getReplyToId() {
        return replyToId;
    }

    public void setReplyToId(int replyToId) {
        this.replyToId = replyToId;
    }

    public CommentDto(int postId, String message, String location, int replyToId) {
        this.message = message;
        this.location = location;
        this.replyToId = replyToId;
    }
}
