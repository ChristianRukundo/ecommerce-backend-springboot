package com.springboot.api.service;

import com.springboot.api.payload.LoginDto;
import com.springboot.api.payload.RegisterDto;
import com.springboot.api.payload.UserDTO;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(UserDTO userDTO);
}
