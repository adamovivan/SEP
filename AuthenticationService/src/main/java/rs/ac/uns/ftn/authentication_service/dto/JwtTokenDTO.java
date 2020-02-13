package rs.ac.uns.ftn.authentication_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtTokenDTO {
    private String accessToken;
}
