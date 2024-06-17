package com.techelevator.dao;

import com.techelevator.model.RegisterUserDto;
import com.techelevator.model.User;

import java.util.List;

public interface UserDAO {

    List<User> getUsers();

    User getUserById(int id);

    User getUserByUsername(String username);

    void promoteUserToModerator(int userId);

    User createUser(RegisterUserDto user);
}
