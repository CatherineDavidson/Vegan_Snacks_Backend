package com.examly.springapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.examly.springapp.enums.RegistrationStatus;
import com.examly.springapp.model.User;
import com.examly.springapp.model.vendor.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
    
    // Optional<Vendor> findByBusinessRegistrationNumber(String businessRegistrationNumber);
    // boolean existsByBusinessRegistrationNumber(String businessRegistrationNumber);
    // boolean existsByEmail(String email);

    Optional<Vendor> findByUser(User user);

    @Query("SELECT DISTINCT v FROM Vendor v " +
           "LEFT JOIN v.documents d " +
           "LEFT JOIN v.complianceRecords c " +
           "WHERE d.verificationStatus = 'PENDING' " +
           "OR c.status = 'UNDER_REVIEW' " +
           "ORDER BY v.createdAt ASC")
    List<Vendor> findVendorsWithPendingApprovals();
    List<Vendor> findByRegistrationStatusNot(RegistrationStatus status);

}
