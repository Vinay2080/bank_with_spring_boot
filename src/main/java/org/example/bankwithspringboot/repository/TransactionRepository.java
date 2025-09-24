package org.example.bankwithspringboot.repository;

import org.example.bankwithspringboot.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
