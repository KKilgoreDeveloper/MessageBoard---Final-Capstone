package com.techelevator.dao;

import com.techelevator.model.Forum;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.security.Principal;
import java.util.List;

public interface ForumDAO {

    int getUserId(String name);

    String getUsername(int forumId);

    List<Forum> getForumsByUsername(String username);

    void addForum(Principal currUser, Forum forum);

    Forum mapRowToForum(SqlRowSet results);

    List<Forum> getFavoriteForumsByUsername(String username);

    List<Forum> getActiveForums();

    List<Forum> getForumsByKeyword(String keyword);

    List<Forum> getAllForums();

    List<Forum> getForumsByForumName(String name);

    Forum getForumById(int forumId);

    int deleteForumByForumName(String name);

    int deleteForumByForumId(int forumId);

    int deleteForumsByUserId(int userId);

    void addFavoriteForum(int forumId, String username);

    void removeFavoriteForum(int forumId, String username);
}
