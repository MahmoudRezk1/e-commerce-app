package com.bar.payment.entity;

import com.bar.payment.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "payment")
@EntityListeners(AuditingEntityListener.class)
public class Payment {
    @Id
    @GeneratedValue
    private Integer id;
    private BigDecimal amount;
    @Enumerated(STRING)
    private PaymentMethod paymentMethod;
    private Integer orderId;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdDate;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;
}
