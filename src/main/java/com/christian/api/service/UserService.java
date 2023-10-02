package com.christian.api.service;

import com.christian.api.payload.UserDTO;
import com.christian.api.payload.UserResponse;

public interface UserService {

    UserResponse getUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    UserDTO getUserById (Long userId);
}
