package kopo.poly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringBootPrjApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootPrjApplication.class, args);
    }

}
