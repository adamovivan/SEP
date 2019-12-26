package rs.ac.uns.ftn.authentication_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.ac.uns.ftn.authentication_service.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
	Transaction findByUuid(String uuid);
}
