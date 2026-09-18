package com.learning.ecommerce.service;

import com.learning.ecommerce.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);

    UserDTO getUserById(Long userId);

    List<UserDTO> getAllUsers();

    UserDTO updateUser(Long userId , UserDTO updatedUserDetails);

}
