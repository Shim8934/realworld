package com.github.realworld.realworldShim.model.user;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Optional.*;

public class Profile {

    private String username;
    private String bio;
    private String imageUrl;
    private boolean following;

    public Profile(String username, String bio, String imageUrl, boolean following) {
        checkArgument(username != null, "username must be provided.");
        checkArgument(bio == null || bio.length() <= 2000,
                "bio length must be less than 2000 characters.");
        checkArgument(
                imageUrl == null || imageUrl.length() <= 255,
                "imageUrl length must be less than 255 characters."
        );

        this.username = username;
        this.bio = bio;
        this.imageUrl = imageUrl;
        this.following = following;
    }

    public Profile(String username) {
        this(username, null, null, false);
    }
    public Profile(String username, String bio) {
        this(username, bio, null, false);
    }

    public String getUsername() {
        return username;
    }

    public Optional<String> getBio() {
        return ofNullable(bio);
    }

    public Optional<String> getImageUrl() {
        return ofNullable(imageUrl);
    }

    public boolean isFollowing() {
        return following;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return Objects.equals(username, profile.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("username", username)
                .append("bio", bio)
                .append("imageUrl", imageUrl)
                .append("isFollowing", following)
                .toString();
    }
}
