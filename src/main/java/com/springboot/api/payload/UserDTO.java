package com.springboot.api.payload;

import com.springboot.api.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private  String email;
    private String password;

    private Set<Role> roles = new HashSet<>();

    private AddressDTO address;

}
