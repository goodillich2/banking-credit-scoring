package com.prihno.illia.bankingapp.repository;

import com.prihno.illia.bankingapp.entity.other.Account;
import com.prihno.illia.bankingapp.entity.other.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByUserEmailAndStatus(String email, AccountStatus active);

    Optional<Account> findByCardNumber(String cardNumber);
}
