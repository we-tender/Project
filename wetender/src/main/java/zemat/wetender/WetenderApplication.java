package zemat.wetender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

@EnableJpaAuditing
@SpringBootApplication
public class WetenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(WetenderApplication.class, args);
	}

	// 작성자 설정란.. uuid를 추후에 멤버 아이디 또는 닉네임으로 변경하면 됨.
//	@Bean
	public AuditorAware<String> auditorProvider(){
		return new AuditorAware<String>() {
			@Override
			public Optional<String> getCurrentAuditor() {
				return Optional.of(UUID.randomUUID().toString());
			}
		};
	}
}
