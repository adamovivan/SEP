package rs.ac.uns.ftn.bank_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimpleResponseDTO {
    private Boolean success;
    private String message;

    public SimpleResponseDTO(String message){
        this.success = true;
        this.message = message;
    }
}
