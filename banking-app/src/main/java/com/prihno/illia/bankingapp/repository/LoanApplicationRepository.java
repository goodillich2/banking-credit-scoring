package com.prihno.illia.bankingapp.repository;

import com.prihno.illia.bankingapp.entity.loan.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
    List<LoanApplication> findByToAccountUserEmail(String toAccountUserEmail);
}
