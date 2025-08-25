package com.examly.springapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.vendor.VendorCompliance;

public interface VendorComplianceRepository extends JpaRepository<VendorCompliance,Long>{
    List<VendorCompliance> findByVendor_VendorId(Long vendorId);
}
