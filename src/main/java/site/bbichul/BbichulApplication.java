package site.bbichul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
@PropertySource("classpath:/app.properties")
public class BbichulApplication {

	public static void main(String[] args) {
		SpringApplication.run(BbichulApplication.class, args);
	}

}
