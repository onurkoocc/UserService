package com.qrmenu.UserService.domain.model.request;

import com.qrmenu.UserService.domain.model.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationRequest {
    private String username;
    private String password;
    private String email;
    private Role role;
}
