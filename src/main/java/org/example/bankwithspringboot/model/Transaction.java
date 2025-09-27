package org.example.bankwithspringboot.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.example.bankwithspringboot.enums.TransactionType;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    private String accountCredited;

    private String accountDebited;

    @Column(nullable = false)
    private Double balance;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(updatable = false)
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh-mm-ss a", timezone = "Asia/Kolkata")
    private LocalDateTime timestamp;
}
