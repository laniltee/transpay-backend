package lk.sliit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CsseAssignment4BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsseAssignment4BackendApplication.class, args);
	}
}
