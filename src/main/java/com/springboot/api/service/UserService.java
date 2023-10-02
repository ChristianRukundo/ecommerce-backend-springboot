package com.springboot.api.service;

import com.springboot.api.payload.UserDTO;
import com.springboot.api.payload.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface UserService {

    UserResponse getUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    UserDTO getUserById (Long userId);
}
