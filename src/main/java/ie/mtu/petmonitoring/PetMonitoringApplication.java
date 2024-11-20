package ie.mtu.petmonitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class PetMonitoringApplication {
    public static void main(String[] args) {
        SpringApplication.run(PetMonitoringApplication.class, args);
    }
}