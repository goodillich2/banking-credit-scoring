package com.prihno.illia.bankingapp.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.prihno.illia.bankingapp.entity.loan.LoanStatus;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LoanApplicationResponseDTO {
    private LoanStatus loanStatus;
}

