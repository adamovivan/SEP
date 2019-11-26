package rs.ac.uns.ftn.paypal_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PaypalApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaypalApiApplication.class, args);
	}

}
