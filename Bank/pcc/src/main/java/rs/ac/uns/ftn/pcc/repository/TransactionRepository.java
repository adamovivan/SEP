package rs.ac.uns.ftn.pcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.pcc.model.Transaction;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findByAcquirerOrderId(String acquirerOrderId);
}
