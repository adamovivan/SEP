package rs.ac.uns.ftn.pcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.pcc.model.Bank;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
    Bank findByInn(String inn);
}
