package com.examly.springapp.service.product;

import com.examly.springapp.dto.product.CertificationDTO;
import com.examly.springapp.model.product.Product;
import com.examly.springapp.model.product.ProductCertification;
import com.examly.springapp.repository.product.ProductCertificationRepository;
import com.examly.springapp.service.product.CertificationService;
import com.examly.springapp.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificationService{

    private final ProductCertificationRepository certificationRepository;
    private final ProductService productService;

    @Transactional
    public ProductCertification add(Long productId, CertificationDTO dto) {
        Product p = productService.get(productId);
        ProductCertification c = new ProductCertification();
        c.setProduct(p);
        c.setCertificationName(dto.getCertificationName());
        c.setIssueDate(dto.getIssueDate());
        c.setExpiryDate(dto.getExpiryDate());
        c.setIssuingAuthority(dto.getIssuingAuthority());
        return certificationRepository.save(c);
    }

    @Transactional(readOnly = true)
    public List<ProductCertification> list(Long productId) {
        return certificationRepository.findByProductProductId(productId);
    }
}
