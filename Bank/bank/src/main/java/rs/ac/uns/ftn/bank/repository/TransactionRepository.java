package rs.ac.uns.ftn.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.bank.model.Transaction;
import rs.ac.uns.ftn.bank.model.TransactionStatus;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findByTransactionId(String transactionId);
    List<Transaction> findByTransactionStatus(TransactionStatus transactionStatus);
    Transaction findByMerchantOrderId(String orderId);
}
