package rs.ac.uns.ftn.authentication_service.converter;

import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.authentication_service.dto.SubmissionDTO;
import rs.ac.uns.ftn.authentication_service.model.Submission;
import rs.ac.uns.ftn.authentication_service.model.SubmissionState;

@Component
public class SubmissionConverter {

    public Submission convert(SubmissionDTO submission) {

        return new Submission(
                submission.getEmail(),
                submission.getCompanyName(),
                submission.getOrganization(),
                submission.getLocation(),
                submission.getStateLocation(),
                submission.getCountry(),
                submission.getUsage(),
                submission.getPhoneNumber(),
                submission.getPassword(),
                SubmissionState.NONE);
    }

    public SubmissionDTO convert(Submission submission) {

        return new SubmissionDTO(
                submission.getId(),
                submission.getEmail(),
                submission.getCompanyName(),
                submission.getOrganization(),
                submission.getLocation(),
                submission.getStateLocation(),
                submission.getCountry(),
                submission.getUsage(),
                submission.getPhoneNumber(),
                submission.getPassword(),
                submission.getState());
    }

}
