package corp.asbp.platform.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync // for faster processing
@PropertySource("classpath:security.properties")
public class AsbpSecurity 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(AsbpSecurity.class, args);
    }
}
