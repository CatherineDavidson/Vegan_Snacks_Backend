package com.examly.springapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.vendor.VendorDocument;

public interface VendorDocumentRepository extends JpaRepository<VendorDocument,Long> {

    List<VendorDocument> findAllByVendorVendorId(Long vendorId);
    Optional<VendorDocument> findByVendorVendorIdAndDocumentType(Long vendorId, String documentType);
}
