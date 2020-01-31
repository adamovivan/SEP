package rs.ac.uns.ftn.authentication_service.config;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
@Data
public class UnfinishedTransactionCheckerConfig {

    private boolean running;
    private int timeout;


    @Bean(name = "utc-checker")
    public Executor threadPoolTaskExecutor() {
        this.running = false;
        this.timeout = 3000;
        return new ThreadPoolTaskExecutor();
    }
}
