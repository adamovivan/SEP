package rs.ac.uns.ftn.bank_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.bank_service.model.NotificationStatus;
import rs.ac.uns.ftn.bank_service.model.Transaction;
import rs.ac.uns.ftn.bank_service.model.TransactionStatus;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findByTransactionId(String transactionId);
    List<Transaction> findByTransactionStatus(TransactionStatus transactionStatus);
    List<Transaction> findByNotificationStatus(NotificationStatus notificationStatus);
}
