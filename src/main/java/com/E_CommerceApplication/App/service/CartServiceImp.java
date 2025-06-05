package com.E_CommerceApplication.App.service;

import com.E_CommerceApplication.App.DTOs.CartDTO;
import com.E_CommerceApplication.App.DTOs.ProductDTO;
import com.E_CommerceApplication.App.exception.APIException;
import com.E_CommerceApplication.App.exception.ResourceNotFoundException;
import com.E_CommerceApplication.App.models.Cart;
import com.E_CommerceApplication.App.models.CartItems;
import com.E_CommerceApplication.App.models.Product;
import com.E_CommerceApplication.App.repositories.CartItemRepo;
import com.E_CommerceApplication.App.repositories.CartRepo;
import com.E_CommerceApplication.App.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImp implements CartService{

    @Autowired
    private  CartRepo cartRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CartItemRepo cartItemRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CartDTO addProductToCart(Long cartId, Long productId, Integer quantity){

        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId",productId));

        CartItems cartItem = cartItemRepo.findCartItemByProductIdAndCartId(cartId,productId);


        if (cartItem != null){
            throw new APIException("Product" + product.getProductName() + " already exists in the cart");
        }

        if (product.getQuantity() == 0){
            throw new APIException(product.getProductName() + " is not available");
        }

        if (product.getQuantity() < quantity){
            throw new APIException(" Please, make an order of the " + product.getProductName()
            + " less than or equal to the quantity " + product.getQuantity() + ".");
        }

        CartItems newCartItem = new CartItems();

        newCartItem.setProduct(product);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(quantity);
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setProductPrice(product.getSpecialPrice());

        cartItemRepo.save(newCartItem);

        product.setQuantity(product.getQuantity() - quantity);

        cart.setTotalPrice(cart.getTotalPrice() + (product.getSpecialPrice() * quantity));

        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<ProductDTO> productDTOS = cart.getCartItems().stream()
                .map(p -> modelMapper.map(p.getProduct(), ProductDTO.class)).collect(Collectors.toList());

        cartDTO.setProducts(productDTOS);
        return cartDTO;
    }

    @Override
    public List<CartDTO> getAllCarts(){
        List<Cart> carts = cartRepo.findAll();

        if(carts.size() == 0){
            throw new APIException("No cart exists");
        }

        List<CartDTO> cartDTOS = carts.stream().map(cart -> {
            CartDTO cartDTO= modelMapper.map(cart,CartDTO.class);

            List<ProductDTO> products = cart.getCartItems().stream()
                    .map(p -> modelMapper.map(p.getProduct(),ProductDTO.class)).collect(Collectors.toList());
            cartDTO.setProducts(products);

            return cartDTO;
        }).collect(Collectors.toList());
        return cartDTOS;
    }

    @Override
    public CartDTO getCartById(String emailId, Long cartId){
        Cart cart = cartRepo.findByEmailAndCartId(emailId, cartId);

        if (cart == null){
            throw new ResourceNotFoundException("Cart","cartId", cartId);
        }

        CartDTO cartDTO = modelMapper.map(cart,CartDTO.class);

        List<ProductDTO> products = cart.getCartItems().stream()
                .map(p -> modelMapper.map(p.getProduct(),ProductDTO.class)).collect(Collectors.toList());

        cartDTO.setProducts(products);
        return cartDTO;
    }

    @Override
    public void updateProductInCarts(Long cartId, Long productId){
        Cart cart = cartRepo.findById(cartId).orElseThrow(() ->
                new ResourceNotFoundException("Cart", "cartId", cartId));

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        CartItems cartItem = cartItemRepo.findCartItemByProductIdAndCartId(cartId, productId);

        if (cartItem == null) {
            throw new APIException("Product " + product.getProductName() + " not available in the cart!!!");
        }

        double cartPrice = cart.getTotalPrice() - (cartItem.getProductPrice() * cartItem.getQuantity());

        cartItem.setProductPrice(product.getSpecialPrice());

        cart.setTotalPrice(cartPrice + (cartItem.getProductPrice() * cartItem.getQuantity()));

        cartItem = cartItemRepo.save(cartItem);
    }


    @Override
    public CartDTO updateProductQuantityInCart(Long cartId, Long productId, Integer quantity) {
        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        if (product.getQuantity() == 0) {
            throw new APIException(product.getProductName() + " is not available");
        }

        if (product.getQuantity() < quantity) {
            throw new APIException("Please, make an order of the " + product.getProductName()
                    + " less than or equal to the quantity " + product.getQuantity() + ".");
        }

        CartItems cartItem = cartItemRepo.findCartItemByProductIdAndCartId(cartId, productId);

        if (cartItem == null) {
            throw new APIException("Product " + product.getProductName() + " not available in the cart!!!");
        }

        double cartPrice = cart.getTotalPrice() - (cartItem.getProductPrice() * cartItem.getQuantity());

        product.setQuantity(product.getQuantity() + cartItem.getQuantity() - quantity);

        cartItem.setProductPrice(product.getSpecialPrice());
        cartItem.setQuantity(quantity);
        cartItem.setDiscount(product.getDiscount());

        cart.setTotalPrice(cartPrice + (cartItem.getProductPrice() * quantity));

        cartItem = cartItemRepo.save(cartItem);

        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<ProductDTO> productDTOs = cart.getCartItems().stream()
                .map(p -> modelMapper.map(p.getProduct(), ProductDTO.class)).collect(Collectors.toList());

        cartDTO.setProducts(productDTOs);

        return cartDTO;

    }

    @Override
    public String deleteProductFromCart(Long cartId, Long productId) {
        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

        CartItems cartItem = cartItemRepo.findCartItemByProductIdAndCartId(cartId, productId);

        if (cartItem == null) {
            throw new ResourceNotFoundException("Product", "productId", productId);
        }

        cart.setTotalPrice(cart.getTotalPrice() - (cartItem.getProductPrice() * cartItem.getQuantity()));

        Product product = cartItem.getProduct();
        product.setQuantity(product.getQuantity() + cartItem.getQuantity());

        cartItemRepo.deleteCartItemByProductIdAndCartId(cartId, productId);

        return "Product " + cartItem.getProduct().getProductName() + " removed from the cart !!!";
    }
}
