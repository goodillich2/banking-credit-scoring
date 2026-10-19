package com.prihno.illia.bankingapp.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.prihno.illia.bankingapp.entity.loan.LoanGrade;
import com.prihno.illia.bankingapp.entity.loan.LoanIntent;
import com.prihno.illia.bankingapp.entity.user.details.HomeOwnership;
import com.prihno.illia.bankingapp.entity.user.details.PreviousDefault;
import com.prihno.illia.bankingapp.entity.user.details.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LoanApplicationRequestDTO {
    private int age;
    private double monthIncome;
    private int employmentLength;
    private HomeOwnership homeOwnership;
    private LoanIntent loanIntent;
    private BigDecimal loanAmount;
    private int loanInterestRate;
    private int previousLoans;
    private PreviousDefault previousDefault;
    private LoanGrade loanGrade;

    public static LoanApplicationRequestDTO fromUserDetailsAndLoanApplication(UserDetails userDetails, LoanApplicationDTO loanApplicationDTO) {
        return LoanApplicationRequestDTO.builder()
                .age(userDetails.getAge())
                .monthIncome(userDetails.getMonthIncome())
                .employmentLength(userDetails.getEmploymentLength())
                .homeOwnership(userDetails.getHomeOwnership())
                .loanIntent(loanApplicationDTO.getIntent())
                .loanAmount(loanApplicationDTO.getAmount())
                .loanInterestRate(loanApplicationDTO.getInterestRate())
                .previousLoans(userDetails.getPreviousLoans())
                .previousDefault(userDetails.getPreviousDefault())
                .loanGrade(loanApplicationDTO.getGrade())
                .build();
    }
}
