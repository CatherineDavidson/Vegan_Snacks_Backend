package com.examly.springapp.model.product;

import com.examly.springapp.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_review")
public class ProductReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer rating; // 1..5

    @Column(length = 1000)
    private String comment;

    @CreationTimestamp
    private Timestamp createdAt;
}
