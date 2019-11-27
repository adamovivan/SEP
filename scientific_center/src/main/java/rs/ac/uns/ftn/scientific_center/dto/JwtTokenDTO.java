package rs.ac.uns.ftn.scientific_center.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtTokenDTO {
    private String accessToken;
}
