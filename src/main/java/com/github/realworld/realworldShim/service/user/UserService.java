package com.github.realworld.realworldShim.service.user;

import com.github.realworld.realworldShim.model.user.Email;
import com.github.realworld.realworldShim.model.user.Profile;
import com.github.realworld.realworldShim.model.user.User;
import com.github.realworld.realworldShim.repository.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Service
public class UserService {

  private final PasswordEncoder passwordEncoder;

  private final UserRepository userRepository;

  public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
  }

  @Transactional
  public User join(Email email, String username, String password) {
    checkArgument(isNotEmpty(password), "password must be provided.");
    checkArgument(
      password.length() >= 4 && password.length() <= 15,
      "password length must be between 4 and 15 characters."
    );

    User user = new User(email, new Profile(username), passwordEncoder.encode(password));
    return insert(user);
  }

  @Transactional(readOnly = true)
  public Optional<User> findById(Long userId) {
    checkArgument(userId != null, "userId must be provided.");

    return userRepository.findById(userId);
  }

  @Transactional(readOnly = true)
  public Optional<User> findByEmail(Email email) {
    checkArgument(email != null, "email must be provided.");

    return userRepository.findByEmail(email);
  }

  private User insert(User user) {
    return userRepository.insert(user);
  }

  private void update(User user) {
    userRepository.update(user);
  }

}
