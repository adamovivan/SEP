package rs.ac.uns.ftn.authentication_service.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.authentication_service.model.Submission;
import rs.ac.uns.ftn.authentication_service.model.SubmissionState;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubmissionRepository extends CrudRepository<Submission, Long> {

    Optional<Submission> findByCompanyNameAndState(String companyName, SubmissionState state);

    @Query("select s from Submission s where s.email = ?1 and (s.state = ?2 or s.state = ?3)")
    Optional<Submission> findByEmailAndStateOrState(String email, SubmissionState state1, SubmissionState state2);

    @Query("select s from Submission s where s.companyName = ?1 and (s.state = ?2 or s.state = ?3)")
    Optional<Submission> findByCompanyNameAndStateOrState(String companyName, SubmissionState state1, SubmissionState state2);

    @Query("select s from Submission s where s.phoneNumber = ?1 and (s.state = ?2 or s.state = ?3)")
    Optional<Submission> findByPhoneNumberAndStateOrState(String phoneNumber, SubmissionState state1, SubmissionState state2);

    List<Submission> findAllByState(SubmissionState state);

}
