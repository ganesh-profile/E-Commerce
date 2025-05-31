package com.E_CommerceApplication.App.service;

import com.E_CommerceApplication.App.DTOs.*;
import com.E_CommerceApplication.App.models.Address;
import com.E_CommerceApplication.App.models.User;
import com.E_CommerceApplication.App.repositories.AddressRepo;
import com.E_CommerceApplication.App.repositories.RoleRepo;
import com.E_CommerceApplication.App.repositories.UserRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class UserServiceImp implements UserService{
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private CartService cartService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder){
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sortByAndOrder);

        Page<User> pageUsers = userRepo.findAll(pageDetails);

        List<User> users = pageUsers.getContent();

        if(users.size() == 0){
            throw new APIException("No User exists !!!");
        }

        List<UserDTO> userDTOs = users.stream().map(user -> {
            UserDTO dto = modelMapper.map(user, UserDTO.class);

            if(user.getAddresses().size() != 0){
                dto.setAddress(modelMapper.map(user.getAddresses().stream().findFirst().get(), AddressDTO.class));
                            }
            CartDTO cart = modelMapper.map(user.getCart(), CartDTO.class);

            List<ProductDTO> products = user.getCart().getCartItems().stream()
                    .map(item -> modelMapper.map(item.getProduct(),ProductDTO.class)).collect(Collectors.toList());
            dto.setCart(cart);
            dto.getCart().setProducts(products);
            return dto;
        }).collect(Collectors.toList());

        UserResponse userResponse = new UserResponse();

        userResponse.setContent(userDTOs);
        userResponse.setPageNumber(pageUsers.getNumber());
        userResponse.setPageSize(pageUsers.getSize());
        userResponse.setTotalElements(pageUsers.getTotalElements());
        userResponse.setTotalPages(pageUsers.getTotalPages());
        userResponse.setLastPage(pageUsers.isLast());

        return userResponse;
    }

    @Override
    public UserDTO getUserById(Long userId){
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseNotFoundException("User", "UserId",userId));
    UserDTO userDTO = modelMapper.map(user,UserDTO.class);

    userDTO.setAddress(modelMapper.map(user.getAddresses().stream().findFirst().get(),AddressDTO.class));

    CartDTO cart = modelMapper.map(user.getCart(), CartDTO.class);

    List<ProductDTO> products = user.getCart().getCartItems().stream().
            map(item -> modelMapper.map(item.getProduct(),ProductDTO.class)).collect(Collectors.toList());

    userDTO.setCart(cart);

    userDTO.getCart().setProducts(products);

    return userDTO;
    }


}
