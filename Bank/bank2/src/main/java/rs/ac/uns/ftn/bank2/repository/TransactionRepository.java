package rs.ac.uns.ftn.bank2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.bank2.model.Transaction;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findByAcquirerOrderId(String acquirerOrderId);
}
