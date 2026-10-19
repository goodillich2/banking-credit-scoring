package com.prihno.illia.bankingapp.service;


import com.prihno.illia.bankingapp.entity.other.Transaction;
import com.prihno.illia.bankingapp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> findTransactionsByAccountId(Long accountId) {
        return transactionRepository.findByAccountIdSortedByDateDesc(accountId);
    }
}
