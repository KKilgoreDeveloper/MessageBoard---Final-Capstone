package com.techelevator.dao;

import com.techelevator.model.Post;
import com.techelevator.model.PostDto;

import java.security.Principal;
import java.util.List;

public interface PostDAO {
    /*** SINGLE POST GETTERS ***/

    public Post getPostByID(int postID);
    public Post getPostByTitle(String postTitle);
    public int getUserID(int postID);
    public String getUserName(int postID);

    /*** LIST POST GETTERS ***/
    List<Post> getAllPosts();
    List<Post> getPostsByUsername(String Username);
    List<Post> getPostsByUserID(int userID);
    List<Post> getPostsByForumName(String forumName);
    List<Post> getPostsByForumID(int forumID);
    List<Post> getPostsByKeyword(String keyword);
    List<Post> get10MostPopularPosts();


        /*** CREATE AND DELETE POSTS ***/
    //void createPost(Post toPost);
        void createPost(Principal currentUser, PostDto postDto, int forumId);
    void deletePost(int postID);
    void deletePostsByUserID(int userID);


    /*** METHODS FOR VOTING ON POSTS ***/
    void upvotePost(int postID);
    void downvotePost(int postID);
    int getPostScore(int postID);


}
