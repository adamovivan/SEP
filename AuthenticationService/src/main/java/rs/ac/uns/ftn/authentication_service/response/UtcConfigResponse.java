package rs.ac.uns.ftn.authentication_service.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UtcConfigResponse {
    private boolean running;
    private int timeout;
}
