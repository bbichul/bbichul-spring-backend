package site.bbichul;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import site.bbichul.service.CalendarService;

@SpringBootApplication
@PropertySource("classpath:/app.properties")
@RequiredArgsConstructor
public class BbichulApplication {

	public static void main(String[] args) {
		SpringApplication.run(BbichulApplication.class, args);
	}

}
