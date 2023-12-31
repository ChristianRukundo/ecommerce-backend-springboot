package com.christian.api.service.impl;

import com.christian.api.entity.Cart;
import com.christian.api.exception.EcommerceAPIException;
import com.christian.api.repository.AddressRepository;
import com.christian.api.repository.CartRepository;
import com.christian.api.repository.RoleRepository;
import com.christian.api.repository.UserRepository;
import com.christian.api.security.JwtTokenProvider;
import com.christian.api.entity.Address;
import com.christian.api.entity.Role;
import com.christian.api.entity.User;
import com.christian.api.payload.LoginDto;
import com.christian.api.payload.UserDTO;
import com.christian.api.service.AuthService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

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

       Cart cart = new Cart();
       user.setCart(cart);
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

       User registeredUser = userRepository.save(user);
       cart.setUser(registeredUser);

        return "User registered successfully!";
    }
}
