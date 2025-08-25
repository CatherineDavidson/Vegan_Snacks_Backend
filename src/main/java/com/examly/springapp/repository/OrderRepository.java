package com.examly.springapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUserId(Long userId);

    @Query("SELECT DISTINCT o FROM Orders o JOIN o.items i WHERE i.product.vendor.id = :vendorId")
    List<Orders> findOrdersByVendorId(@Param("vendorId") Long vendorId);

}