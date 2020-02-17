package rs.ac.uns.ftn.authentication_service.dto;

import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import rs.ac.uns.ftn.authentication_service.model.SubmissionState;

@Data
@AllArgsConstructor
public class SubmissionDTO {

    private Long id;

    private String email;

    //@NotNull(message = "Company name can't be null!")
    @Size(min = 1, message = "Company name can't be null")
    private String companyName;

   // @NotNull(message = "Organization can't be null!")
    @Size(min = 1, message = "Organization can't be null")
    private String organization;

    //@NotNull(message = "Location can't be null!")
    @Size(min = 1, message = "Location can't be null")
    private String location;

   // @NotNull(message = "State can't be null!")
    @Size(min = 1, message = "State can't be null")
    private String stateLocation;

    private String country;

   // @NotNull(message = "Usage can't be null!")
    @Size(min = 1, message = "Usage can't be null")
    private String usage;


    private String phoneNumber;

    private String password;

    private SubmissionState state;

}
