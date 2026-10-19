package com.prihno.illia.bankingapp.entity.other;


import com.prihno.illia.bankingapp.entity.loan.LoanApplication;
import com.prihno.illia.bankingapp.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "accounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private BigDecimal balance;


    @Column(nullable = false, length = 16, unique = true)
    private String cardNumber;

    @Column(nullable = false)
    private int expiryMonth;

    @Column(nullable = false)
    private int expiryYear;

    @Column(nullable = false, length = 3)
    private String cvv;

    @Column(nullable = false)
    private String accountType;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status = AccountStatus.ACTIVE;

    @OneToMany(mappedBy = "fromAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> outgoingTransactions;

    @OneToMany(mappedBy = "toAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> incomingTransactions;

    @OneToMany(mappedBy = "toAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LoanApplication> loanApplications;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }
}
