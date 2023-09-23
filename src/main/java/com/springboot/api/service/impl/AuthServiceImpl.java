package com.springboot.api.service.impl;

import com.springboot.api.entity.Address;
import com.springboot.api.entity.Role;
import com.springboot.api.entity.User;
import com.springboot.api.exception.EcommerceAPIException;
import com.springboot.api.payload.LoginDto;
import com.springboot.api.payload.RegisterDto;
import com.springboot.api.payload.UserDTO;
import com.springboot.api.repository.AddressRepository;
import com.springboot.api.repository.RoleRepository;
import com.springboot.api.repository.UserRepository;
import com.springboot.api.security.JwtTokenProvider;
import com.springboot.api.service.AuthService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;

    private AddressRepository addressRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;


    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           AddressRepository addressRepository,
                           JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }

   @Transactional
    @Override
    public String register(UserDTO userDTO) {
        // Create a new user entity
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setMobileNumber(userDTO.getMobileNumber());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        // Create a set of roles and add the "ROLE_USER" role
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new EcommerceAPIException(HttpStatus.NOT_FOUND, "Role not found"));
        roles.add(userRole);
        // Set the roles for the user
        user.setRoles(roles);

       String country = userDTO.getAddress().getCountry();
       String state = userDTO.getAddress().getState();
       String city = userDTO.getAddress().getCity();
       String pincode = userDTO.getAddress().getPincode();
       String street = userDTO.getAddress().getStreet();
       String buildingName = userDTO.getAddress().getBuildingName();

       Address address = addressRepository.findByCountryAndStateAndCityAndPincodeAndStreetAndBuildingName(country, state,
               city, pincode, street, buildingName);

       if (address == null) {
           address = new Address(country, state, city, pincode, street, buildingName);
           address = addressRepository.save(address);
       }

       user.setAddresses(List.of(address));

        // Save the user, which will update the user_roles table
        userRepository.save(user);

        return "User registered successfully!";
    }
}
