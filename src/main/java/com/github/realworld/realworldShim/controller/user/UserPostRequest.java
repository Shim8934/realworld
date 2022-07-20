package com.github.realworld.realworldShim.controller.user;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeName("user")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
public class UserPostRequest {

  @NotBlank
  @ApiModelProperty(value = "Username", required = true)
  private String username;

  @javax.validation.constraints.Email
  @ApiModelProperty(value = "Login Email", required = true)
  private String email;

  @NotBlank
  @ApiModelProperty(value = "Login Password", required = true)
  private String password;

  protected UserPostRequest() {}

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("username", username)
      .append("email", email)
      .append("password", password)
      .toString();
  }

}