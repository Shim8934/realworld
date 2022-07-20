package com.github.realworld.realworldShim.model.user;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

public class User {

    private final Long seq;

    private Email email;

    private String password;

    private Profile profile;

    public User(Long seq, Email email, Profile profile, String password) {
        checkArgument(email != null, "email must be provided.");
        checkArgument(profile != null, "profile must be provided.");
        checkArgument(password != null, "password must be provided.");

        this.seq = seq;
        this.email = email;
        this.profile = profile;
        this.password = password;
    }

    public User(Email email, Profile profile, String password) {
        this(null, email, profile, password);
    }

    public Long getSeq() {
        return seq;
    }

    public Email getEmail() {
        return email;
    }

    public Profile getProfile() {
        return profile;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(seq, user.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("seq", seq)
                .append("email", email)
                .append("profile", profile)
                .append("password", "[PROTECTED]")
                .toString();
    }

    static public class Builder {
        private Long seq;
        private Email email;
        private Profile profile;
        private String password;

        public Builder() {
        }

        public Builder(User user) {
            this.seq = user.seq;
            this.email = user.email;
            this.profile = user.profile;
            this.password = user.password;
        }

        public Builder seq(Long seq) {
            this.seq = seq;
            return this;
        }

        public Builder email(Email email) {
            this.email = email;
            return this;
        }

        public Builder profile(Profile profile) {
            this.profile = profile;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public User build() {
            return new User(seq, email, profile, password);
        }
    }
}
