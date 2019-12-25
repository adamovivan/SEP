package rs.ac.uns.ftn.scientific_center.config;

//import org.apache.http.client.HttpClient;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.apache.http.impl.client.HttpClients;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//import org.apache.http.ssl.SSLContextBuilder;

@Configuration
public class Config {

   /* @Value("${http.client.ssl.trust-store}")
    private String truststorePath;

    @Value("${http.client.ssl.trust-store-password}")
    private String truststorePassword;
*/
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

//    @Value("${http.client.ssl.key-store}")
//    private String keystorePath;
//
//    @Value("${http.client.ssl.key-store-password}")
//    private String keystorePassword;

    /*@Bean
    public RestTemplate restTemplate() throws Exception {

        Resource trustStore = new ClassPathResource(truststorePath);

        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(trustStore.getURL(), truststorePassword.toCharArray())
                .build();
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
        HttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(socketFactory)
                .build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(factory);
    }*/
}
