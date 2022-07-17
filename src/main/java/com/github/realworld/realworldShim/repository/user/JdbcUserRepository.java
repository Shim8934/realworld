package com.github.realworld.realworldShim.repository.user;

import com.github.realworld.realworldShim.model.user.Email;
import com.github.realworld.realworldShim.model.user.Profile;
import com.github.realworld.realworldShim.model.user.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Repository
public class JdbcUserRepository implements UserRepository {

  private final JdbcTemplate jdbcTemplate;

  public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public User insert(User user) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(conn -> {
      // TODO 이름 프로퍼티 처리
      PreparedStatement ps = conn.prepareStatement("INSERT INTO users(seq,username,email,password,bio) VALUES (null,?,?,?,?)", new String[]{"seq"});
      ps.setString(1, user.getProfile().getUsername());
      ps.setString(2, user.getEmail().getAddress());
      ps.setString(3, user.getPassword());
      ps.setString(4, user.getProfile().getBio().orElse(null));
      return ps;
    }, keyHolder);

    Number key = keyHolder.getKey();
    long generatedSeq = key != null ? key.longValue() : -1;
    return new User.Builder(user)
      .seq(generatedSeq)
      .build();
  }

  @Override
  public void update(User user) {
    // TODO 이름 프로퍼티 처리
    jdbcTemplate.update(
      "UPDATE users SET username=?, password=?, bio=? WHERE seq=?",
      user.getProfile().getUsername(),
      user.getPassword(),
      user.getProfile().getBio().orElse(null),
      user.getSeq()
    );
  }

  @Override
  public Optional<User> findById(Long userId) {
    List<User> results = jdbcTemplate.query(
      "SELECT * FROM users WHERE seq=?",
      mapper,
      userId
    );
    return ofNullable(results.isEmpty() ? null : results.get(0));
  }

  @Override
  public Optional<User> findByEmail(Email email) {
    List<User> results = jdbcTemplate.query(
      "SELECT * FROM users WHERE email=?",
      mapper,
      email.getAddress()
    );
    return ofNullable(results.isEmpty() ? null : results.get(0));
  }

  static RowMapper<User> mapper = (rs, rowNum) -> new User.Builder()
    .seq(rs.getLong("seq"))
    .profile(new Profile(rs.getString("username"), rs.getString("bio")))
    .email(new Email(rs.getString("email")))
    .password(rs.getString("password"))
    .build();

}
