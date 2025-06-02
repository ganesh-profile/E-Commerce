package com.E_CommerceApplication.App.service;

import com.E_CommerceApplication.App.DTOs.UserDTO;
import com.E_CommerceApplication.App.DTOs.UserResponse;

public interface UserService {

    UserDTO registerUser(UserDTO userDTO);
    UserResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    UserDTO getUserById(Long userId);
    UserDTO updatedUser(Long userId, UserDTO userDTO);
    String deleteUser(Long userId);
}
