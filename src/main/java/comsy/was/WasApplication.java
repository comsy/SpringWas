package comsy.was;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAspectJAutoProxy
@EnableAsync
@SpringBootApplication
public class WasApplication {

    public static void main(String[] args) {
        SpringApplication.run(WasApplication.class, args);
    }

}
