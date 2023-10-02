package com.springboot.api.controller;

import com.springboot.api.payload.UserDTO;
import com.springboot.api.payload.UserResponse;
import com.springboot.api.service.UserService;
import com.springboot.api.utils.AppConstants;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@SecurityRequirement(name = "E-commerce api")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/admin/users")
    public ResponseEntity<UserResponse> getUsers (
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_USERS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder
    ){
            UserResponse userResponse = userService.getUsers(pageNumber, pageSize, sortBy, sortOrder);
        

            return  new ResponseEntity<UserResponse>(userResponse, HttpStatus.OK);
    }

    @GetMapping("admin/users/{userId}")
    public  ResponseEntity<UserDTO> getUser ( @PathVariable Long userId) {
        UserDTO response = userService.getUserById(userId);

        return  new ResponseEntity<>(response, HttpStatus.OK);
    }
}
