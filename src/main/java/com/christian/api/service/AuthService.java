package com.christian.api.service;

import com.christian.api.payload.LoginDto;
import com.christian.api.payload.UserDTO;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(UserDTO userDTO);
}
