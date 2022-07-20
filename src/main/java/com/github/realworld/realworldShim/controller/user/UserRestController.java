package com.github.realworld.realworldShim.controller.user;

import com.github.realworld.realworldShim.controller.ApiResult;
import com.github.realworld.realworldShim.model.user.Email;
import com.github.realworld.realworldShim.model.user.User;
import com.github.realworld.realworldShim.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.github.realworld.realworldShim.controller.ApiResult.OK;

@RestController
@RequestMapping("api")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "users")
    @ApiOperation(value = "User Sign Up (No Authentication Required)")
    public ApiResult<JoinResult> join(@RequestBody @Valid UserPostRequest userPostRequest) {
        User user = userService.join(new Email(userPostRequest.getEmail()), userPostRequest.getUsername(), userPostRequest.getPassword());
        return OK(
                new JoinResult(new UserDto(user))
        );
    }


}
