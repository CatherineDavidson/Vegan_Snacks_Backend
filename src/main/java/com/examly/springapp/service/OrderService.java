package com.examly.springapp.service;

import com.examly.springapp.model.Orders;
import com.examly.springapp.dto.OrderItemDTO;
import com.examly.springapp.dto.OrderResponseDTO;
import com.examly.springapp.model.OrderItem;
import com.examly.springapp.model.User;
import com.examly.springapp.model.product.CartItem;
import com.examly.springapp.model.product.CustomerCart;
import com.examly.springapp.repository.product.CartItemRepository;
import com.examly.springapp.service.product.CartService;

import jakarta.transaction.Transactional;

import com.examly.springapp.repository.OrderRepository;
import com.examly.springapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepo;
    private final UserRepository userRepo;
    private final CartService cartService;

//    public Orders placeOrder(List<Long> cartItemIds) {
//     User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//     // Fetch user's cart
//     CustomerCart cart = cartService.getOrCreate(user);

//     if (cart.getItems().isEmpty()) {
//         throw new RuntimeException("Cart is empty, cannot place order");
//     }

//     // Filter cart items based on IDs received from frontend
//     List<CartItem> selectedCartItems = cart.getItems().stream()
//             .filter(item -> cartItemIds.contains(item.getCartItemId()))
//             .toList();

//     if (selectedCartItems.isEmpty()) {
//         throw new RuntimeException("No items selected for checkout");
//     }

//     // Map selected cart items to order items
//     List<OrderItem> orderItems = selectedCartItems.stream()
//             .map(cartItem -> {
//                 OrderItem oi = new OrderItem();
//                 oi.setProduct(cartItem.getProduct());
//                 oi.setQuantity(cartItem.getQuantity());
//                 oi.setPriceAtPurchase(cartItem.getProduct().getPrice());
//                 return oi;
//             }).toList();

//     // Calculate total
//     double total = orderItems.stream()
//             .mapToDouble(i -> i.getPriceAtPurchase() * i.getQuantity())
//             .sum();

//     // Create Order
//     Orders order = new Orders();
//     order.setUser(user);
//     order.setItems(orderItems);
//     order.setOrderDate(LocalDateTime.now());
//     order.setTotalAmount(total);

//     // Save the order
//     Orders savedOrder = orderRepo.save(order);

//     // Remove selected cart items from cart
//     for (CartItem cartItem : selectedCartItems) {
//         cart.getItems().remove(cartItem); // remove from in-memory list
//         cartItem.setCart(null);           // remove back-reference
//         cartService.deleteCartItem(cartItem); // delete from DB
//     }

//     return savedOrder;
// }

public Orders placeOrder(List<Long> cartItemIds) {
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    CustomerCart cart = cartService.getOrCreate(user);

    if (cart.getItems().isEmpty()) {
        throw new RuntimeException("Cart is empty, cannot place order");
    }

    List<CartItem> selectedCartItems = cart.getItems().stream()
            .filter(item -> cartItemIds.contains(item.getCartItemId()))
            .toList();

    if (selectedCartItems.isEmpty()) {
        throw new RuntimeException("No items selected for checkout");
    }

    Orders order = new Orders();
    order.setUser(user);
    order.setOrderDate(LocalDateTime.now());

    List<OrderItem> orderItems = selectedCartItems.stream()
            .map(cartItem -> {
                OrderItem oi = new OrderItem();
                oi.setProduct(cartItem.getProduct());
                oi.setQuantity(cartItem.getQuantity());
                oi.setPriceAtPurchase(cartItem.getProduct().getPrice());
                oi.setOrder(order); // 🔑 set parent order
                return oi;
            }).toList();

    double total = orderItems.stream()
            .mapToDouble(i -> i.getPriceAtPurchase() * i.getQuantity())
            .sum();

    order.setItems(orderItems);
    order.setTotalAmount(total);

    Orders savedOrder = orderRepo.save(order);

    for (CartItem cartItem : selectedCartItems) {
        cart.getItems().remove(cartItem);
        cartItem.setCart(null);
        cartService.deleteCartItem(cartItem);
    }

    return savedOrder;
}

    public List<Orders> getUserOrders() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return orderRepo.findByUserId(user.getId());
    }

    public List<OrderResponseDTO> getAllOrders() {
        return orderRepo.findAll().stream().map(order -> new OrderResponseDTO(
                order.getId(),
                order.getUser() != null ? order.getUser().getUserName() : null,
                order.getUser() != null ? order.getUser().getEmail() : null,
                order.getItems().stream()
                        .map(item -> new OrderItemDTO(
                                item.getProduct().getName(),
                                item.getQuantity(),
                                item.getPriceAtPurchase()
                        ))
                        .collect(Collectors.toList()),
                order.getTotalAmount(),
                order.getOrderDate()
        )).collect(Collectors.toList());
    }
    
}

