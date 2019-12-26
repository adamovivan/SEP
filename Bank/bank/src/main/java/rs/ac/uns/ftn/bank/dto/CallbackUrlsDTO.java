package rs.ac.uns.ftn.bank.dto;

import lombok.Data;

@Data
public class CallbackUrlsDTO {
    private String successUrl;
    private String failedUrl;
    private String errorUrl;
}
