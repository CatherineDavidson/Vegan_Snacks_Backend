// package com.examly.springapp.service.product;

// import com.examly.springapp.dto.product.CartItemDTO;
// import com.examly.springapp.model.User;
// import com.examly.springapp.model.product.CartItem;
// import com.examly.springapp.model.product.CustomerCart;
// import com.examly.springapp.model.product.Product;
// import com.examly.springapp.repository.product.CartItemRepository;
// import com.examly.springapp.repository.product.CustomerCartRepository;
// import com.examly.springapp.service.product.CartService;
// import com.examly.springapp.service.product.ProductService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// @Service
// @RequiredArgsConstructor
// public class CartService {

//     private final CustomerCartRepository cartRepository;
//     private final CartItemRepository itemRepository;
//     private final ProductService productService;

//     @Transactional
//     public CustomerCart getOrCreate(User user) {
//         return cartRepository.findByUser(user).orElseGet(() -> {
//             CustomerCart c = new CustomerCart();
//             c.setUser(user);
//             return cartRepository.save(c);
//         });
//     }

//     @Transactional
//     public CustomerCart addItem(User user, CartItemDTO item) {
//         CustomerCart cart = getOrCreate(user);
//         Product p = productService.get(item.getProductId());
//         CartItem ci = new CartItem();
//         ci.setCart(cart);
//         ci.setProduct(p);
//         ci.setQuantity(item.getQuantity());
//         itemRepository.save(ci);
//         cart.getItems().add(ci);
//         return cart;
//     }

//     @Transactional
//     public CustomerCart removeItem(User user, Long cartItemId) {
//         CustomerCart cart = getOrCreate(user);
//         cart.getItems().removeIf(i -> {
//             if (i.getCartItemId().equals(cartItemId)) {
//                 itemRepository.deleteById(cartItemId);
//                 return true;
//             }
//             return false;
//         });
//         return cart;
//     }

//     @Transactional
//     public CustomerCart clear(User user) {
//         CustomerCart cart = getOrCreate(user);
//         cart.getItems().forEach(i -> itemRepository.deleteById(i.getCartItemId()));
//         cart.getItems().clear();
//         return cart;
//     }
// }
package com.examly.springapp.service.product;

import com.examly.springapp.dto.product.CartItemDTO;
import com.examly.springapp.model.User;
import com.examly.springapp.model.product.CartItem;
import com.examly.springapp.model.product.CustomerCart;
import com.examly.springapp.model.product.Product;
import com.examly.springapp.repository.product.CartItemRepository;
import com.examly.springapp.repository.product.CustomerCartRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
public class CartService {

    private final CustomerCartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartItemRepository itemRepository;
    private final ProductService productService;

    @Transactional
    public CustomerCart getOrCreate(User user) {
        return cartRepository.findByUser(user).orElseGet(() -> {
            CustomerCart c = new CustomerCart();
            c.setUser(user);
            return cartRepository.save(c);
        });
    }

    @Transactional(readOnly = true)
    public CustomerCart getCart(User user) {
        return getOrCreate(user);
    }

    @Transactional
    public CustomerCart addItem(User user, CartItemDTO itemDto) {
        CustomerCart cart = getOrCreate(user);
        Product product = productService.get(itemDto.getProductId());

        // check if product already exists in cart
        CartItem existing = cart.getItems().stream()
            .filter(ci -> ci.getProduct().getProductId().equals(product.getProductId()))
            .findFirst()
            .orElse(null);

        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + itemDto.getQuantity());
            itemRepository.save(existing);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(itemDto.getQuantity());
            itemRepository.save(newItem);
            cart.getItems().add(newItem);
        }

        return cartRepository.save(cart);
    }

    @Transactional
    public CustomerCart updateItem(User user, Long cartItemId, int quantity) {
        CustomerCart cart = getOrCreate(user);

        CartItem item = itemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));

        if (!item.getCart().getCartId().equals(cart.getCartId())) {
            throw new RuntimeException("Item does not belong to user cart");
        }

        item.setQuantity(quantity);
        itemRepository.save(item);

        return cartRepository.save(cart);
    }

    @Transactional
    public CustomerCart removeItem(User user, Long cartItemId) {
        CustomerCart cart = getOrCreate(user);

        CartItem item = itemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));

        if (!item.getCart().getCartId().equals(cart.getCartId())) {
            throw new RuntimeException("Item does not belong to user cart");
        }

        cart.getItems().remove(item);
        itemRepository.delete(item);

        return cartRepository.save(cart);
    }

    @Transactional
    public CustomerCart clear(User user) {
        CustomerCart cart = getOrCreate(user);
        itemRepository.deleteAll(cart.getItems());
        cart.getItems().clear();
        return cartRepository.save(cart);
    }

     public void save(CustomerCart cart) {
        // First save/update the cart entity
        cartRepository.save(cart);

        // Then save/update each cart item
        if (cart.getItems() != null) {
            cart.getItems().forEach(cartItemRepository::save);
        }
}
public void clearCartItems(List<CartItem> items) {
        cartItemRepository.deleteAll(items);
    }

    public void deleteCartItem(CartItem cartItem) {
    cartItemRepository.delete(cartItem);
}



}