package com.techelevator.dao;

import com.techelevator.model.Forum;
import com.techelevator.model.Post;
import com.techelevator.model.PostDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.validation.constraints.Null;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component

public class JdbcPostDAO implements PostDAO {

    /*** CONSTRUCTOR ***/
    private final JdbcTemplate jdbcTemplate;

    public JdbcPostDAO (JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }




    /*** SINGLE POST GETTERS ***/

    @Override
    public Post getPostByID(int postID){
        String sql = "SELECT * FROM posts" +
                " WHERE post_id = ?";
        Post returnPost = new Post();
        SqlRowSet postRowSet = jdbcTemplate.queryForRowSet(sql, postID);
        if (postRowSet.next()) {
            returnPost = mapRowToPost(postRowSet);
        }

        return returnPost;
    }
    @Override
    public Post getPostByTitle(String postTitle){

        String sql = "SELECT * FROM posts" +
                " WHERE title = ?";
        Post returnPost = new Post();


        SqlRowSet postRowSet = jdbcTemplate.queryForRowSet(sql, postTitle);
        if (postRowSet.next()) {
            returnPost = mapRowToPost(postRowSet);
        }

        return returnPost;
    }

    /*** POST PROPERTY GETTERS ***/
    @Override
    public int getUserID(int postID){
        String sql = "SELECT user_id FROM posts" +
                " WHERE post_id = ?";

        return jdbcTemplate.queryForObject(sql, int.class, postID);
    }
    public String getUserName(int postID){
        String sql = "SELECT username from posts " +
                "JOIN users ON posts.user_id = users.user_id " +
                "WHERE post_id = ? ";
        return jdbcTemplate.queryForObject(sql, String.class, postID);

    }

    /*** LIST POST GETTERS ***/
    @Override
    public List<Post> getAllPosts(){
        String sql = "SELECT * FROM posts";
        List<Post> postList = new ArrayList<>();
        SqlRowSet result= jdbcTemplate.queryForRowSet(sql);
        while(result.next()) {
            postList.add(mapRowToPost(result));
        }
        return postList;
    }
    @Override
    public List<Post> getPostsByUsername(String Username){
        List<Post> rtnList = new ArrayList<Post>();
        return rtnList;
    }
    @Override
    public List<Post> getPostsByUserID(int userID){
        List<Post> rtnList = new ArrayList<Post>();
        return rtnList;
    }

    @Override
    public List<Post> getPostsByKeyword(String keyword) {
        List<Post> posts = new ArrayList<>();

        String sql = "SELECT * FROM posts WHERE title ILIKE CONCAT('%', ? ,'%')" +
                "OR message ILIKE CONCAT('%', ? ,'%')";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, keyword, keyword);

        while(results.next()) {
            Post post = mapRowToPost(results);
            posts.add(post);
        }
        return posts;
    }
    /*********************************************************/
    /***** GET POSTS BY FORUM NAME, GET POSTS BY FORUM ID ***/
    /********************************************************/
    @Override
    public List<Post> getPostsByForumName(String forumName){
        String sql = "SELECT * FROM posts" +
                " JOIN forum ON posts.forum_id = forum.forum_id" +
                " WHERE forum.forum_name = ?";
        List<Post> rtnList = new ArrayList<Post>();

        SqlRowSet postRowSet = jdbcTemplate.queryForRowSet(sql, forumName);
        while (postRowSet.next()) {
            Post post = mapRowToPost(postRowSet);
            rtnList.add(post);
        }
        return rtnList;
    }
    @Override
    public List<Post> getPostsByForumID(int forumID){
        String sql = "SELECT * FROM posts" +
                " WHERE forum_id = ? " +
                "ORDER BY time_stamp DESC";
        List<Post> rtnList = new ArrayList<Post>();
        SqlRowSet postRowSet = jdbcTemplate.queryForRowSet(sql, forumID);
        while (postRowSet.next()) {
            Post post = mapRowToPost(postRowSet);
            rtnList.add(post);
        }
        return rtnList;
    }

    /*** CREATE AND DELETE POSTS ***/
    @Override
    public void createPost(Principal currUser, PostDto postDto,
                           int forumID /* path variable */){

        String sql = "INSERT INTO posts VALUES (DEFAULT, (select user_id from users where username = ?), ?, ?, ? , 0, 0, CURRENT_TIMESTAMP, ?)";

        jdbcTemplate.update(sql, currUser.getName(), forumID, postDto.getTitle(), postDto.getMessage(), postDto.getLocation());
    }
    /*
    post_id     --  serial
    user_id     --  from principal
    forum_id    --  from url path
    title       --  PARAMETER
    message     --  PARAMETER
    up_votes    --  set to zero
    down_votes  --  set to zero
    time_stamp  -- CURRENT_TIMESTAMP
    location    -- STRING PARAMETER
     */


/*
    @Override
    public void addForum(Principal currUser, String forumName) {
        forumName = decoder(forumName);
        String sql = "INSERT into forum VALUES (DEFAULT, " +
                "(select user_id from users where username = ?)," +
                "?, CURRENT_TIMESTAMP);";
        jdbcTemplate.update(sql, currUser.getName(), forumName);
    }
*/


    /*** METHODS FOR VOTING ON POSTS ***/
    public void upvotePost(int postID){
        String sql = "UPDATE posts SET up_votes = up_votes + 1 WHERE post_id = ?";
        jdbcTemplate.update(sql, postID);
    }
    public void downvotePost(int postID){
        String sql = "UPDATE posts SET down_votes = down_votes + 1 WHERE post_id = ?";
        jdbcTemplate.update(sql, postID);
    }
    public int getPostScore(int postID){
        String sql = "SELECT * FROM posts WHERE post_id = ?";
        Post returnPost = new Post();
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, postID);

        if (rowSet.next()) {
            returnPost = mapRowToPost(rowSet);
        }

        return returnPost.getScore();
    }


    public List<Post> get10MostPopularPosts(){
        String sql = "SELECT * FROM posts WHERE time_stamp >= NOW() - '1 DAY'::INTERVAL order by up_votes-down_votes DESC limit 10 "; // from t-24 hr
        //String sql = "SELECT * FROM posts order by up_votes-down_votes DESC limit 10 ";  // not time limited
        List<Post> postList = new ArrayList<Post>();
        SqlRowSet result= jdbcTemplate.queryForRowSet(sql);
        while(result.next()) {
            postList.add(mapRowToPost(result));
        }
        return postList;
    }

    @Override
    public void deletePost(int postId) {

        String[] sql;
        sql = new String[] {"DELETE FROM comments WHERE post_id = ?;",
                "DELETE FROM votes_post WHERE post_id = ?;" ,
                "DELETE FROM posts WHERE post_id = ?;"};

        //String sql =

        for (int i = 0; i<sql.length; i++) {
            jdbcTemplate.update(sql[i], postId);
        }
    }
    @Override
    public void deletePostsByUserID(int userID){
        String sql = "DELETE FROM posts WHERE user_id = ?";
        jdbcTemplate.update(sql, userID);
    }

    private String decoder(String input){
        input = URLDecoder.decode(input, StandardCharsets.UTF_8);
        if (input.endsWith("=")){
            input = input.substring(0,input.length()-1);
        }
        return input;
    }

    private Post mapRowToPost(SqlRowSet rs) {
        Post post = new Post();
        post.setPostID(rs.getInt("post_id"));
        post.setUserID(rs.getInt("user_id"));
        post.setForumID(rs.getInt("forum_id"));
        post.setTitle(rs.getString("title"));
        post.setMessageDetails(rs.getString("message"));
        post.setUpVotes(rs.getInt("up_votes"));
        post.setDownVotes(rs.getInt("down_votes"));
        if (rs.getTimestamp("time_stamp") != null) {
            post.setTimeStamp(rs.getTimestamp("time_stamp").toLocalDateTime());
        }
        post.setLocation(rs.getString("location"));
        return post;
    }



}
