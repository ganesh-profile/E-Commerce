package com.E_CommerceApplication.App.service;

import com.E_CommerceApplication.App.DTOs.CartDTO;

import java.util.List;

public interface CartService {

    CartDTO addProductToCart(Long cartId, Long ProductId, Integer quantity);

    CartDTO getCartById(String emailId, Long cartId);

    List<CartDTO> getAllCarts();


    CartDTO updateProductQuantityInCart(Long cartId, Long productId, Integer quantity);

    void updateProductInCarts(Long cartId, Long productId);

    String deleteProductFromCart(Long cartId, Long productId);

}
