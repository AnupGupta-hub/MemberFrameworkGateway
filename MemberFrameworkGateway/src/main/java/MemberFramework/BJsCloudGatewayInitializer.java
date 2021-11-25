package MemberFramework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * This is SpringBootContainer class which initiates the ApplicationContext and is responsible for handling all the Spring
 * objects
 *
 * @author Anup Gupta
 */
@SpringBootApplication
public class BJsCloudGatewayInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BJsCloudGatewayInitializer.class, args);
    }


}
