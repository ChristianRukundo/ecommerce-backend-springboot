package com.springboot.api.service.impl;

import com.springboot.api.entity.User;
import com.springboot.api.exception.EcommerceAPIException;
import com.springboot.api.exception.ResourceNotFoundException;
import com.springboot.api.payload.AddressDTO;
import com.springboot.api.payload.UserDTO;
import com.springboot.api.payload.UserResponse;
import com.springboot.api.repository.UserRepository;
import com.springboot.api.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public UserResponse getUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<User> pageUsers = userRepository.findAll(pageDetails);
        List<User> users = pageUsers.getContent();

        if (users.isEmpty()) {
            throw new EcommerceAPIException(HttpStatus.NOT_FOUND, "No User exists !!!");
        }

        List<UserDTO> userDTOS = users.stream().map(user -> {
            UserDTO dto = modelMapper.map(user, UserDTO.class);

            if (!user.getAddresses().isEmpty()) {
                dto.setAddress(modelMapper.map(user.getAddresses().stream().findFirst().get(), AddressDTO.class));
            }


            return dto;
        }).toList();

        UserResponse userResponse = new UserResponse();

        userResponse.setContents(userDTOS);
        userResponse.setPageNumber(pageUsers.getNumber());
        userResponse.setPageSize(pageUsers.getSize());
        userResponse.setTotalElements(pageUsers.getTotalElements());
        userResponse.setTotalPages(pageUsers.getTotalPages());
        userResponse.setLastPage(pageUsers.isLast());

        return userResponse;
        }

    @Override
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));

       UserDTO userDTO =  modelMapper.map(user, UserDTO.class);

       userDTO.setAddress(modelMapper.map(user.getAddresses().stream().findFirst().get(), AddressDTO.class));

       return userDTO;
    }
}
