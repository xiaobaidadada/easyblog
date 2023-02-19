package fun.xb.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;




@SpringBootApplication
@ComponentScan("fun.xb")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
