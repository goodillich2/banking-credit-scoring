package com.prihno.illia.bankingapp.entity.loan;

import com.prihno.illia.bankingapp.entity.other.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "loan_applications")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "intent")
    private LoanIntent intent;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "interest_rate")
    private int interestRate;

    @Column(name = "grade")
    private LoanGrade grade;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private LoanStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_account_id")
    private Account toAccount;
}
