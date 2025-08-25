package com.examly.springapp.service;

import com.examly.springapp.dto.UserProfileDTO;
import com.examly.springapp.model.User;
import com.examly.springapp.model.product.CustomerCart;
import com.examly.springapp.repository.UserRepository;
import com.examly.springapp.repository.product.CartItemRepository;
import com.examly.springapp.repository.product.CustomerCartRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CustomerCartRepository customerCartRepository;
    private final CartItemRepository cartItemRepository;
    private static final String USER_NOT_FOUND = "User not found";

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
         return userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND + email));
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND + email));
    }

    public String deleteUserByEmail(String email){
        userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND + email));
        userRepository.deleteUserByEmail(email);

        return "User deleted Successfully!";
    }

public User updateUserProfile(String email, UserProfileDTO dto) {
    User existingUser = userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

    existingUser.setUserName(dto.getUserName());
    existingUser.setPhone(dto.getPhone());
    existingUser.setDateOfBirth(dto.getDateOfBirth());
    existingUser.setAddress(dto.getAddress());
    existingUser.setCity(dto.getCity());
    existingUser.setState(dto.getState());
    existingUser.setPinCode(dto.getPinCode());
    existingUser.setCountry(dto.getCountry());
    existingUser.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

    return userRepository.save(existingUser);
}

@Transactional
public String deleteUser() {
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    // Delete all CartItems for user's carts
    List<CustomerCart> carts = customerCartRepository.findAllByUser_Id(user.getId());
    for (CustomerCart cart : carts) {
        cartItemRepository.deleteAllByCart_CartId(cart.getCartId());
    }

    // Delete all CustomerCarts
    customerCartRepository.deleteAllByUser_Id(user.getId());

    // Delete the user
    userRepository.deleteById(user.getId());

    return "User deleted successfully!";
}


}
