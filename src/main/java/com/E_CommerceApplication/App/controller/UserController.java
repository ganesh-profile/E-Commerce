package com.E_CommerceApplication.App.controller;

import com.E_CommerceApplication.App.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "E-Commerce Application")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/admin/users")
    public ResponseEntity<UserResponse> getAllUsers(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_USERS_BY,required = false) Integer sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) Integer sortOrder
    ){
        UserResponse userResponse = userService.getAllUsers(pageNumber,pageSize,sortBy,sortOrder);

        return new ResponseEntity<UserResponse>(userResponse, HttpStatus.FOUND);
    }

    @GetMapping("/public/users/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long UserId){
        UserDTO user = userService.getUserById(userId);
        return new ResponseEntity<UserDTO>(user, HttpStatus.FOUND);
    }

    @PutMapping("/public/users/{userId}")
    public ResponseEntity<UserDTO> updateUSer(@RequestBody UserDTO userDTO, @PathVariable Long userId){
        userDTO updatedUser = userService.updatedUser(userId, userDTO);
        return new ResponseEntity<userDTO>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/admin/users/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId){
        String status = userService.deleteUser(userId);

        return new ResponseEntity<String>(status, HttpStatus.OK);
    }


}
