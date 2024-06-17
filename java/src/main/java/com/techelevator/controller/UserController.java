package com.techelevator.controller;

// import data access objects for comment, forum, post, and user
import com.techelevator.dao.CommentDAO;
import com.techelevator.dao.ForumDAO;
import com.techelevator.dao.PostDAO;
import com.techelevator.dao.UserDAO;

//import spring framework resources
import com.techelevator.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
public class UserController {

    private final CommentDAO commentDAO;
    private final ForumDAO forumDAO;
    private final PostDAO postDAO;
    private final UserDAO userDAO;

    public UserController(com.techelevator.dao.CommentDAO commentDAO,
                          com.techelevator.dao.ForumDAO forumDAO,
                          com.techelevator.dao.PostDAO postDAO,
                          com.techelevator.dao.UserDAO userDAO

                          ) {
        this.commentDAO = commentDAO;
        this.forumDAO = forumDAO;
        this.postDAO = postDAO;
        this.userDAO = userDAO;
    }

    // GETS ALL FORUMS
    @RequestMapping(value = "/forums", method = RequestMethod.GET)
    public List<Forum> getAllForums() {
        return forumDAO.getAllForums();
    }

    // GETS FORUMS BY KEYWORD
    @RequestMapping(value = "/forums/keyword/{keyword}", method = RequestMethod.GET)
    public List<Forum> getForumsByKeyword(@PathVariable String keyword) {
        return forumDAO.getForumsByKeyword(keyword);
    }

    // GETS FORUMS BY FORUM NAME
    @RequestMapping(value = "/forums/forumname/{name}", method = RequestMethod.GET)
    public List<Forum> getForumsByForumName(@PathVariable String name) {
        return forumDAO.getForumsByUsername(name);
    }

    // GETS FORUMS BY FORUM ID
    @RequestMapping(value = "/forums/{forumId}", method = RequestMethod.GET)
    public Forum getForumById(@PathVariable int forumId) {
        return forumDAO.getForumById(forumId);
    }


    // GETS FAVORITE FORUMS
    @PreAuthorize("isAuthenticated()")
    @RequestMapping (value= "/forums/favorite", method = RequestMethod.GET)
    public List<Forum> getFavoriteForumsByUser(Principal currUser){
        return forumDAO.getFavoriteForumsByUsername(currUser.getName());
    }


    // CREATES A FORUM
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/forums", method = RequestMethod.POST)
    public void addForum(Principal currUser, @RequestBody Forum forumName) {
        forumDAO.addForum(currUser, forumName);
    }


    // GETS 5 MOST ACTIVE FORUMS BY POSTS TIMESTAMP
    @RequestMapping(value="/forums/active", method= RequestMethod.GET)
    public List<Forum> getActiveForums() {
        return forumDAO.getActiveForums();
    }


    // DELETES FORUM BY FORUM NAME
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path= "forums/deleteforum/{forumname}")
    public void deleteForumByForumName(@PathVariable String name) {
        forumDAO.deleteForumByForumName(name);
    }


    // DELETES FORUM BY FORUM ID
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path= "forums/{forumId}/delete")
    public void deleteForumByForumId(@PathVariable int forumId) {
        forumDAO.deleteForumByForumId(forumId);
    }

    // DELETES FORUM BY USERNAME
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path= "forums/deleteforum/{user_id}/userId")
    public void deleteForumByUsername(@PathVariable int userId) {
        forumDAO.deleteForumsByUserId(userId);
    }

    /*** CREATE A NEW POST ***/
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/forums/{forumId}", method = RequestMethod.POST)
    public void createPost(@Valid @RequestBody PostDto postDto, Principal currUser, @PathVariable int forumId) {
        postDAO.createPost(currUser, postDto, forumId);
    }
    /* dto for create post includes: title, message, location */

    /*** GET POSTS BY FORUM ***/
    @RequestMapping(value = "/forums/{forumId}/posts", method = RequestMethod.GET)
    public List<Post> getPostsByForumId(@PathVariable int forumId) {
        return postDAO.getPostsByForumID(forumId);
    }

    /*** GET ALL POSTS ***/
    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public List<Post> getAllPosts() {
        return postDAO.getAllPosts();
    }

    /*** GET POST BY POST_ID ***/
    @RequestMapping(value = "/posts/{postId}", method = RequestMethod.GET)
    public Post getPostByPostId(@PathVariable int postId) {
        return postDAO.getPostByID(postId);
    }

    /*** GET POSTS BY KEYWORD ***/
    @RequestMapping(value = "/posts/keyword/{keyword}", method = RequestMethod.GET)
    public List<Post> getPostsByKeyword(@PathVariable String keyword) {
        return postDAO.getPostsByKeyword(keyword);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getUsers(){
        return userDAO.getUsers();
    }

    /*** GET POST BY POST_ID ***/
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/posts/{postId}/upvote", method = RequestMethod.PUT)
    public void upvotePost(@PathVariable int postId, Principal currUser) {
        postDAO.upvotePost(postId);
    }
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/posts/{postId}/downvote", method = RequestMethod.PUT)
    public void downvotePost(@PathVariable int postId) {
        postDAO.downvotePost(postId);
    }
    @RequestMapping(value = "/posts/{postId}/getscore", method = RequestMethod.GET)
    public int getPostScore(@PathVariable int postId) {
        return postDAO.getPostScore(postId);
    }
    @RequestMapping(value = "/posts/topvoted", method = RequestMethod.GET)
    public List<Post> get10MostPopularPosts() {
        return postDAO.get10MostPopularPosts();
    }

    /*** GET COMMENTS BY POST ID ***/
    @RequestMapping(value = "/posts/{postId}/comments", method = RequestMethod.GET)
    public List<Comment> getCommentsByPostId(@PathVariable int postId){
        return commentDAO.getCommentsByPostId(postId);
    }


    /*** GET COMMENT BY ID **/
    @RequestMapping (value= "/comments/{commentId}", method = RequestMethod.GET)
    public Comment getCommentById(@PathVariable int commentId){
        return commentDAO.getCommentById(commentId);
    }

    /*** ADD A NEW FAVORITED FORUM ***/
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping (value= "/forums/favorite/{forumId}", method = RequestMethod.POST)
    public void addFavoriteForum(@PathVariable int forumId, Principal currUser){
        forumDAO.addFavoriteForum(forumId, currUser.getName());
    }

    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping (value= "/forums/favorite/{forumId}", method = RequestMethod.DELETE)
    public void removeFavoriteForum(@PathVariable int forumId, Principal currUser){
        forumDAO.removeFavoriteForum(forumId, currUser.getName());
    }
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping (path = "/posts/{postId}/comments", method = RequestMethod.POST)
    public void createComment(@Valid @RequestBody CommentDto commentDto, Principal currUser, @PathVariable int postId){
        commentDAO.createComment(currUser, commentDto, postId);
    }
    @RequestMapping (value= "/comments", method = RequestMethod.GET)
    public List<Comment> getAllComments(){
        return commentDAO.getAllComments();
    }

    /*** DELETE COMMENTS BY ID ***/
    @PreAuthorize("hasAnyRole('MOD','ADMIN')")
    @RequestMapping (value= "/comments/{commentId}", method = RequestMethod.DELETE)
    public void deleteComment(@PathVariable int commentId){
        commentDAO.deleteComment(commentId);
    }
    @PreAuthorize("hasAnyRole('MOD','ADMIN')")
    @RequestMapping (value= "/posts/{postId}", method = RequestMethod.DELETE)
    public void deletePost(@PathVariable int postId){
        postDAO.deletePost(postId);
    }

    @PreAuthorize("hasAnyRole('MOD', 'ADMIN')")
    @RequestMapping(value= "/users/{userId}/promote", method= RequestMethod.PUT)
    public void promoteUserToModerator(@PathVariable int userId) {
        userDAO.promoteUserToModerator(userId);
    }

    @RequestMapping(value = "/forums/{forumId}/createdBy", method = RequestMethod.GET)
    public String getUsernameByForum(@PathVariable int forumId){
        return forumDAO.getUsername(forumId);
    }

    //get username by postid
    @RequestMapping(value = "/posts/{postId}/createdBy", method = RequestMethod.GET)
    public String getUsernameByPostId(@PathVariable int postId){
        return postDAO.getUserName(postId);
    }


}
