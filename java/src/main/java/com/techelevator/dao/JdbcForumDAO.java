package com.techelevator.dao;

import com.techelevator.model.Forum;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcForumDAO implements ForumDAO {

    private final JdbcTemplate jdbcTemplate;

    public JdbcForumDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // CREATES A FORUM
    /*@Override
    public void addForum(Principal currUser, String forumName) {
        String sql = "INSERT into forum VALUES (DEFAULT, ?,?, CURRENT_TIMESTAMP);";
        jdbcTemplate.update(sql, currUser.getName(), forumName);
    }*/
    @Override
    public void addForum(Principal currUser, Forum forum) {

        //TODO: Ask Matt what this is for
//        forum = decoder(forum);
        String sql = "INSERT into forum VALUES (DEFAULT, " +
                "(select user_id from users where username = ?)," +
                "?, CURRENT_TIMESTAMP);";
        jdbcTemplate.update(sql, currUser.getName(), forum.getName());
    }

    // GETS USER ID OF PERSON WHO CREATED FORUM
    @Override
    public int getUserId(String name) {
        String sql = "SELECT user_id from forum WHERE forum_name = ?";

        try{
            return jdbcTemplate.queryForObject(sql, int.class, name);
        } catch (NullPointerException | EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException("Forum " + name + " was not found.");
        }
    }


    // GETS USERNAME OF PERSON WHO CREATED FORUM
    @Override
    public String getUsername(int forumId) {
        String sql = "SELECT username from users\n" +
                "JOIN forum ON users.user_id = forum.user_id\n" +
                "WHERE forum_id = ?";

        try{
            return jdbcTemplate.queryForObject(sql, String.class, forumId);
        } catch (NullPointerException | EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException("Forum " + forumId + " was not found.");
        }
    }


    // GETS FORUMS BY USERNAME
    @Override
    public List<Forum> getForumsByUsername(String username) {
        List<Forum> forums = new ArrayList<>();
        String sql = "SELECT * FROM forum\n" +
                "JOIN user_forum ON forum_id.forum = forum_id.user_forum\n" +
                "JOIN users ON user_id.user_forum = user_id.users\n" +
                "WHERE username = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);

        while(results.next()) {
            Forum forum = mapRowToForum(results);
            forums.add(forum);
        }
        return forums;
    }


    // GETS FORUMS BY FORUM NAME
    @Override
    public List<Forum> getForumsByForumName(String name) {
        List<Forum> forums = new ArrayList<>();
        String sql = "SELECT * FROM forum WHERE forum_name = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, name);

        while(results.next()) {
            Forum forum = mapRowToForum(results);
            forums.add(forum);
        }
        return forums;
    }


    // GETS FORUMS BY FORUM ID
    @Override
    public Forum getForumById(int forumId) {
        Forum returnForum = new Forum();
        String sql = "SELECT * FROM forum WHERE forum_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, forumId);

        if(results.next()) {
            returnForum = mapRowToForum(results);
        }
        return returnForum;
    }


   // GETS ALL FAVORITE FORUMS BY USERNAME
    @Override
    public List<Forum> getFavoriteForumsByUsername(String usrnm) {
        List<Forum> forums = new ArrayList<>();
        String sql = "SELECT * from forum\n" +
                "JOIN user_favorite_forum ON forum.forum_id = user_favorite_forum.forum_id\n" +
                "JOIN users ON user_favorite_forum.forum_user_id = users.user_id\n" +
                "WHERE username = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, usrnm); //THROWS ERROR HERE

        while(results.next()) {



            Forum forum = mapRowToForum(results);
            forums.add(forum);
        }
        return forums;
    }

// user mattymattmattcat

    // CREATES A FAVORITE FORUM (MAKES A FORUM A FAVORITE FOR A SPECIFIC USER)
    @Override
    public void addFavoriteForum(int forumId, String username) {
        String sql = "INSERT INTO user_favorite_forum (forum_id, forum_user_id) " +
                "VALUES (?, (SELECT user_id FROM users WHERE username = ?));";
        jdbcTemplate.update(sql, forumId, username);
    }

    // REMOVES FAVORITE FROM FORUM
    @Override
    public void removeFavoriteForum(int forumId, String username) {
        String sql = "DELETE from user_favorite_forum WHERE forum_user_id =" +
                " (SELECT user_id FROM users WHERE username = ?)" +
                " AND forum_id = ?;";
       jdbcTemplate.update(sql, username, forumId);
    }



    // GETS THE FIVE MOST RECENT ACTIVE FORUMS
    @Override
    public List<Forum> getActiveForums() {
        List<Forum> forums = new ArrayList<>();
        String sql = "SELECT *\n" +
                "FROM forum\n" +
                "JOIN (\n" +
                "    SELECT posts.forum_id, MAX(posts.time_stamp) as last_post_time\n" +
                "    FROM posts\n" +
                "    GROUP BY posts.forum_id\n" +
                ") AS recent_posts ON forum.forum_id = recent_posts.forum_id\n" +
                "ORDER BY recent_posts.last_post_time DESC\n" +
                "LIMIT 5;\n";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        while(results.next()) {
            Forum forum = mapRowToForum(results);
            forums.add(forum);
        }
        return forums;
    }


    // PULLS UP FORUMS BY KEYWORD
    @Override
    public List<Forum> getForumsByKeyword(String keyword) {
        List<Forum> forums = new ArrayList<>();

        String sql = "SELECT * FROM forum WHERE forum_name ILIKE CONCAT('%', ? ,'%')";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, keyword);

        while(results.next()) {
            Forum forum = mapRowToForum(results);
            forums.add(forum);
        }
        return forums;
    }


    // DELETES FORUMS BY FORUM NAME
    @Override
    public int deleteForumByForumName(String name) {
        int numberOfRows = 0;
        String sql = "DELETE * FROM forum WHERE forum_name = ?";
        numberOfRows = jdbcTemplate.update(sql, name);
        return numberOfRows;
    }


    // DELETES FORUMS BY FORUM ID
    @Override
    public int deleteForumByForumId(int forumId) {
        int numberOfRows = 0;
        String sql = "DELETE * FROM forum WHERE forum_id = ?";
        numberOfRows = jdbcTemplate.update(sql, forumId);
        return numberOfRows;
    }


    // DELETES FORUMS BY USERNAME - LIKE IF A USER IS BANNED
    @Override
    public int deleteForumsByUserId(int userId) {
        int numberOfRows = 0;
        String sql = "DELETE * FROM forum WHERE user_name = ?";
        numberOfRows = jdbcTemplate.update(sql, userId);
        return numberOfRows;
    }


    // PULLS UP ALL FORUMS
    @Override
    public List<Forum> getAllForums() {
        List<Forum> forums = new ArrayList<>();
        String sql = "SELECT * FROM forum";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        while(results.next()) {
            Forum forum = mapRowToForum(results);
            forums.add(forum);
        }
        return forums;
    }
    private String decoder(String input){
        input = URLDecoder.decode(input, StandardCharsets.UTF_8);
        if (input.endsWith("=")){
            input = input.substring(0,input.length()-1);
        }
        return input;
    }

    // ROW MAPPER
    @Override
    public Forum mapRowToForum(SqlRowSet results) {
        Forum forum = new Forum();
        forum.setForumId(results.getInt("forum_id"));
        forum.setName(results.getString("forum_name"));
        if (results.getTimestamp("time_stamp") != null) {
            forum.setTimestamp(results.getTimestamp("time_stamp").toLocalDateTime());    //COMMENTED OUT -- NEED TO FIX LATER
        }
        forum.setUserID(results.getInt("user_id"));
        return forum;
    }
}
