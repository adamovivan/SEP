package rs.ac.uns.ftn.authentication_service.service;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import rs.ac.uns.ftn.authentication_service.config.UnfinishedTransactionCheckerConfig;
import rs.ac.uns.ftn.authentication_service.response.PaymentLinkResponse;
import rs.ac.uns.ftn.authentication_service.response.SimpleResponseDTO;
import rs.ac.uns.ftn.authentication_service.response.UtcConfigResponse;

import java.time.LocalDateTime;

@Service
public class UtcService {

    @Autowired
    private UnfinishedTransactionCheckerConfig utcConfig;

    @Autowired
    private RestTemplate restTemplate;

    public void startUTC() throws InterruptedException {
        utcConfig.setRunning(true);
        unfinishedTransactionChecker();
    }

    public void stopUTC(){
        utcConfig.setRunning(false);
        System.out.println("Stopped");
    }

    public void setTimeoutUtc(Integer timeout){
        utcConfig.setTimeout(timeout);
    }

    @Async("utc-checker")
    public void unfinishedTransactionChecker() throws InterruptedException {
        while(utcConfig.isRunning()) {
            System.out.println("Running: " + LocalDateTime.now());
            System.out.println("Timeout: " + utcConfig.getTimeout());

            ResponseEntity<String[]> res = restTemplate.getForEntity("http://localhost:8761/fetchNames", String[].class);
            System.out.println("AA");

            if(res.getBody() == null){
                return;
            }

            for(String paymentService: res.getBody()){
                try {
                    restTemplate.getForEntity("https://localhost:8765/api-" + paymentService.toLowerCase() + "/unfinished-transactions-check", SimpleResponseDTO.class);
                }
                catch(HttpClientErrorException e){

                }
            }

            Thread.sleep(utcConfig.getTimeout());
        }
    }

    public UtcConfigResponse utcConfig(){
        return new UtcConfigResponse(utcConfig.isRunning(), utcConfig.getTimeout());
    }
}
