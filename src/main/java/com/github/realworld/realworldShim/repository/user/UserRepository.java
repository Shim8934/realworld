package com.github.realworld.realworldShim.repository.user;

import com.github.realworld.realworldShim.model.user.Email;
import com.github.realworld.realworldShim.model.user.User;

import java.util.Optional;

public interface UserRepository {

  User insert(User user);

  void update(User user);

  Optional<User> findById(Long userId);

  Optional<User> findByEmail(Email email);

}
