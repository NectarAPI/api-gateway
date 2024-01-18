package ke.co.nectar.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(scanBasePackages = "ke.co.nectar.api")
@ConfigurationPropertiesScan("ke.co.nectar.api.configurations")
public class NectarApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(NectarApiGatewayApplication.class, args);
	}
}
