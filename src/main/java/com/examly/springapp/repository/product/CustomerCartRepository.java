package com.examly.springapp.repository.product;

import com.examly.springapp.model.User;
import com.examly.springapp.model.product.CustomerCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerCartRepository extends JpaRepository<CustomerCart, Long> {
    Optional<CustomerCart> findByUser(User user);

    List<CustomerCart> findAllByUserId(Long id);

    void deleteAllByUser_Id(Long userId);

    List<CustomerCart> findAllByUser_Id(Long id);
}
