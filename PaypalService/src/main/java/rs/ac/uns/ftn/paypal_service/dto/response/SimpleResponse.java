package rs.ac.uns.ftn.paypal_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleResponse {
    private Boolean success;
    private String message;

    public SimpleResponse(String message){
        this.success = true;
        this.message = message;
    }
}
