package rs.ac.uns.ftn.authentication_service.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleResponseDTO {
    private Boolean success;
    private String message;

    public SimpleResponseDTO(String message){
        this.success = true;
        this.message = message;
    }
}
