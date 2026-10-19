package com.prihno.illia.bankingapp.dto;

import com.prihno.illia.bankingapp.entity.loan.LoanGrade;
import com.prihno.illia.bankingapp.entity.loan.LoanIntent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanApplicationDTO {
    private LoanIntent intent;
    private BigDecimal amount;
    private int interestRate;
    private LoanGrade grade;
    private String toAccount;
}
