package com.learning.ecommerce.service.impl;

import com.learning.ecommerce.dto.UserDTO;
import com.learning.ecommerce.entity.User;
import com.learning.ecommerce.exception.ResourceNotFoundException;
import com.learning.ecommerce.mapper.UserMapper;
import com.learning.ecommerce.repository.UserRepository;
import com.learning.ecommerce.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        User user = UserMapper.mapToUser(userDTO);
        User savedUser = userRepository.save(user);
        return UserMapper.mapToUserDTO(savedUser);
    }

    @Override
    public UserDTO getUserById(UUID userId) {
        User user= userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist with the given id:" + userId));
        return UserMapper.mapToUserDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users =userRepository.findAll();
        return users.stream().map(UserMapper::mapToUserDTO)
                .collect(Collectors.toList());

    }

    @Override
    public void deleteUser(UUID userId) {

    }

    @Override
    public UserDTO updateUser(UUID userId, UserDTO updatedUserDetails) {
         User user =userRepository.findById(userId).orElseThrow(
                 () ->new ResourceNotFoundException("User does not exist with the given id:" + userId));

         user.setFirstName(updatedUserDetails.getFirstName());
         user.setLastName(updatedUserDetails.getLastName());
         user.setEmail(updatedUserDetails.getEmail());

         User updatedUserDetailsObj= userRepository.save(user);

         return UserMapper.mapToUserDTO(updatedUserDetailsObj);
    }


}
