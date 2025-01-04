package com.scalableanalyzer.microservice.cart.service;

import com.scalableanalyzer.microservice.cart.entity.Cart;
import com.scalableanalyzer.microservice.cart.entity.CartItem;
import com.scalableanalyzer.microservice.cart.entity.Product;
import com.scalableanalyzer.microservice.cart.repository.CartItemRepository;
import com.scalableanalyzer.microservice.cart.repository.CartRepository;
import com.scalableanalyzer.microservice.cart.repository.ProductRepository;
import com.scalableanalyzer.microservice.cart.dto.CartItemDto;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemService implements ICartItemService{
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;


    public CartItemService(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<?> addItem(Long cartId, CartItemDto cartItem) {
        System.out.println(cartItem);
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        System.out.println(cartOptional);
        if (cartOptional.isEmpty()) {
            return ResponseEntity.status(404).body("Cart not found");
        }

        Long productId = cartItem.getProductId();
        System.out.println(productId);
        Optional<Product> productOptional = productRepository.findById(productId);


        if (productOptional.isEmpty()) {
            return ResponseEntity.status(404).body("Product not found");
        }

        Cart cart = cartOptional.get();
        Product product = productOptional.get();

        System.out.println(cart);
        System.out.println(product);

        // Check if item already exists in the cart
        CartItem cartItemm = cartItemRepository.findByCartAndProduct(cart, product);
        System.out.println(cartItemm);



        CartItem cartItemTemp;
        if (cartItemm != null) {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(cartItem.getQuantity());
            newItem.setPrice(cartItem.getQuantity() * cartItem.getPrice());

            cartItemTemp = cartItemRepository.save(newItem);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(cartItem.getQuantity());
            newItem.setPrice(cartItem.getQuantity() * cartItem.getPrice());
            cartItemTemp = cartItemRepository.save(newItem);
        }


        return ResponseEntity.ok().body(toDto(cartItemTemp));

    }

    public ResponseEntity<?> deleteCartItemFromCart(Long cartId, Long cartItemId) {
        // Fetch the cart by its ID
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        System.out.println(cartOptional);

        // Check if the cart exists
        if (cartOptional.isEmpty()) {
            return ResponseEntity.status(404).body("Cart not found");
        }

        Cart cart = cartOptional.get();

        // Find the cart item within the cart by its ID
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);

        // Check if the cart item exists
        if (cartItemOptional.isEmpty()) {
            return ResponseEntity.status(404).body("CartItem not found");
        }

        CartItem cartItem = cartItemOptional.get();

        // Ensure the cart item belongs to the given cart
        if (!cartItem.getCart().getCartId().equals(cart.getCartId())) {
            return ResponseEntity.status(400).body("CartItem does not belong to the specified Cart");
        }

        // Delete the cart item
        cartItemRepository.delete(cartItem);

        // Return a success response
        return ResponseEntity.status(200).body("CartItem successfully deleted from the Cart");




    }

    public CartItemDto toDto(CartItem cartItem) {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setPrice(cartItem.getPrice());
        cartItemDto.setCartItemId(cartItem.getCartItemId());
        cartItemDto.setPrice(cartItem.getPrice());
        cartItemDto.setQuantity(cartItem.getQuantity());
        return cartItemDto;
    }
}
