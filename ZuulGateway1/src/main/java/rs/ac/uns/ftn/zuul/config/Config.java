package rs.ac.uns.ftn.zuul.config;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.net.ssl.SSLContext;

@Configuration
public class Config {

	@Value("${http.client.ssl.trust-store}")
	private String truststorePath;

	@Value("${http.client.ssl.trust-store-password}")
	private String truststorePassword;

	@Bean
	public CloseableHttpClient configSSL() throws Exception {
		Resource trustStore = new ClassPathResource(truststorePath);

		SSLContext sslContext = new SSLContextBuilder()
				.loadTrustMaterial(trustStore.getURL(), truststorePassword.toCharArray())
				.build();
		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
		return HttpClients.custom()
				.setSSLSocketFactory(socketFactory)
				.build();
	}
}
