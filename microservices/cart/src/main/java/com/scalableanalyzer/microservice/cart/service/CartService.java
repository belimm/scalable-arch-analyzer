package com.scalableanalyzer.microservice.cart.service;

import com.scalableanalyzer.microservice.cart.dto.CartDto;
import com.scalableanalyzer.microservice.cart.dto.CartItemDto;
import com.scalableanalyzer.microservice.cart.dto.UserCartDto;
import com.scalableanalyzer.microservice.cart.entity.Cart;
import com.scalableanalyzer.microservice.cart.entity.CartItem;
import com.scalableanalyzer.microservice.cart.entity.User;
import com.scalableanalyzer.microservice.cart.repository.CartRepository;
import com.scalableanalyzer.microservice.cart.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<?> createCart(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

            if(cartRepository.existsByUserCart_Id(userId)) {

                UserCartDto userCartDto = new UserCartDto();
                Cart cart = cartRepository.findByUserCart_Id(userId);

                System.out.println(cart);
                userCartDto.setUserId(userId);
                userCartDto.setCartId(cart.getCartId());


                System.out.println(cart.getCartItems());

                ArrayList<CartItemDto> cartItemDtos = new ArrayList<>();
                for(CartItem cItem: cart.getCartItems()) {
                    CartItemDto cartItemDto = new CartItemDto();
                    cartItemDto.setCartItemId(cItem.getCartItemId());
                    cartItemDto.setProductId(cItem.getProduct().getProductId());
                    cartItemDto.setQuantity(cItem.getQuantity());
                    cartItemDto.setPrice(cItem.getPrice());
                    cartItemDto.setCreatedAt(cItem.getCreatedAt());
                    cartItemDtos.add(cartItemDto);

                }

                userCartDto.setItems(cartItemDtos);



                return ResponseEntity.ok().body(userCartDto);
            }

            Cart cart = new Cart();
            cart.setUserCart(userOptional.get());
            cart.setTotalPrice(0.0); // Initialize with zero
            Cart newCart = cartRepository.save(cart);
            return ResponseEntity.status(HttpStatus.OK).body(toDto(newCart));
    }

    @Override
    public ResponseEntity<?> deleteCart(Long userId) {

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }


        System.out.println(userOptional.get());


        Cart newCart = cartRepository.findByUserCart_Id(userId);

        if(newCart == null) {
            return ResponseEntity.status(404).body("Cart not found");
        }

        System.out.println("deleting cart");

        cartRepository.deleteById(newCart.getCartId());

        return ResponseEntity.status(200).body("Cart successfully deleted");
    }




    private CartDto toDto(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setCartId(cart.getCartId());
        cartDto.setTotalPrice(cart.getTotalPrice());
        cartDto.setCreatedAt(cart.getCreatedAt());
        cartDto.setUpdatedAt(cart.getUpdatedAt());
        return cartDto;
    }

    private List<CartItemDto> toDtoCartItems(List<CartItem> cartItems) {
        List<CartItemDto> cartItemDtos = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            CartItemDto cartItemDto = new CartItemDto();
            cartItemDto.setCartItemId(cartItem.getCartItemId());
            cartItemDto.setQuantity(cartItem.getQuantity());
            cartItemDto.setPrice(cartItem.getPrice());
            cartItemDto.setCreatedAt(cartItem.getCreatedAt());
            cartItemDtos.add(cartItemDto);
        }

        return cartItemDtos;
    }
}
