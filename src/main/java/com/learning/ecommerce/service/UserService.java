package com.learning.ecommerce.service;

import com.learning.ecommerce.dto.UserDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);

    UserDTO getUserById(UUID userId);

    List<UserDTO> getAllUsers();

    UserDTO updateUser(UUID userId , UserDTO updatedUserDetails);

    void deleteUser(UUID userId);

}
