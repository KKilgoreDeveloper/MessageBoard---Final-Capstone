package com.techelevator.dao;

import com.techelevator.model.Comment;
import com.techelevator.model.CommentDto;
import com.techelevator.model.Post;
import com.techelevator.model.PostDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCommentDAO implements CommentDAO {

    private final JdbcTemplate jdbcTemplate;
    private final CommentRowMapper commentRowMapper;

    public JdbcCommentDAO(JdbcTemplate jdbcTemplate){
        this.commentRowMapper = new CommentRowMapper();
        this.jdbcTemplate = jdbcTemplate;
    }

    //Add addComment method eventually
    @Override
    public void addComment(Comment comment) {
        String sql = "INSERT INTO comments (user_id, reply_to_id, post_id, message, time_stamp, location) VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, comment.getUserID());
            ps.setInt(2, comment.getPostID());
            ps.setObject(3, comment.getReplyToID() == 0 ? null : comment.getReplyToID());
            ps.setString(4, comment.getMessageDetails());
            ps.setObject(5, comment.getTimeStamp());
            ps.setString(6, comment.getLocation());
            return ps;
        }, keyHolder);

        comment.setCommentID(keyHolder.getKey().intValue());
    }

    @Override
    public Comment getCommentById(int commentID) {
        String sql = "SELECT * FROM comments WHERE comment_id = ?";
        return jdbcTemplate.queryForObject(sql, new CommentRowMapper(), commentID);

        // MATT'S GARBAGE 2  - THE SQL (GET IT?) STILL SORRY
//        String sql = "SELECT * FROM comments" +
//                " WHERE comment_id = ?";
//        Comment returnComment = new Comment();
//        SqlRowSet postRowSet = jdbcTemplate.queryForRowSet(sql, commentID);
//        if (postRowSet.next()) {
//            returnComment = commentRowMapper.mapRow(postRowSet);
//        }
//
//        return returnComment;
    }

    @Override
    public List<Comment> getAllComments(){
        String sql = "SELECT * FROM comments";
        return jdbcTemplate.query(sql, new CommentRowMapper());
    }

    @Override
    public List<Comment> getCommentsByPostId(int postID) {
        String sql = "SELECT * FROM comments WHERE post_id = ? ORDER BY time_stamp DESC";
        List<Comment> rtnComments = new ArrayList<Comment>();
        rtnComments = jdbcTemplate.query(sql, new CommentRowMapper(), postID);
        return rtnComments;
    }

    @Override
    public List<Comment> getRepliesByCommentId (int commentID) {
        String sql = "SELECT * FROM comments WHERE reply_to = ? ORDER BY time_stamp";
        return jdbcTemplate.query(sql, new CommentRowMapper(), commentID);
    }

    @Override
    public void updateComment(Comment comment) {
        String sql = "UPDATE comments SET user_id = ?, post_id = ?, reply_to = ?, message = ?, time_stamp = ?, location = ? WHERE comment_id = ?";
        jdbcTemplate.update(sql, comment.getUserID(), comment.getPostID(), comment.getReplyToID() == 0 ? null : comment.getReplyToID(), comment.getMessageDetails(), comment.getTimeStamp(), comment.getLocation(), comment.getCommentID());
    }

    @Override
    public void deleteComment(int commentID) {
        String sql = "DELETE FROM comments WHERE comment_id = ?";
        jdbcTemplate.update(sql, commentID);
    }
    @Override
    public void createComment(Principal currUser, CommentDto commentDto, int postId /* path variable */){

        String sql = "INSERT INTO comments VALUES (DEFAULT, (select user_id from users where username = ?), ?," +
                "CURRENT_TIMESTAMP, ?, NULL, ? )";

        jdbcTemplate.update(sql, currUser.getName(), commentDto.getMessage(), postId, commentDto.getLocation());
    }
        /*

            /*
        comment_id     --  serial
        user_id     --  from principal
        message     --  PARAMETER               NEEDED


        time_stamp  -- CURRENT_TIMESTAMP
        post_id     -- what post is it under    NEEDED
        reply_to    -- if replying to comment   NEEDED
        location    -- STRING PARAMETER         NEEDED


    comment_id     --  serial
    user_id     --  from principal
    message     --  PARAMETER
    time_stamp  -- CURRENT_TIMESTAMP
    post_id     -- what post is it under
    reply_to    -- if replying to comment
    location    -- STRING PARAMETER

    CREATE TABLE comments (
    comment_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    message VARCHAR(1000) NOT NULL,
    time_stamp TIMESTAMP NOT NULL,
    post_id INT NOT NULL,
    reply_to INT,
    location VARCHAR(50) NOT NULL,
    --Reply_to foreign key set to comment_id (idk if this will work how we want it to, but hopefully)
    FOREIGN KEY (reply_to) REFERENCES comments(comment_id),
    FOREIGN KEY (post_id) REFERENCES posts(post_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)

     */

}
