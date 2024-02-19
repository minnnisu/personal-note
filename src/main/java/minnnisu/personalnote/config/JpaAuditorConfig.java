package minnnisu.personalnote.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * JPA auditor enable
 */
@Configuration
/**
 *
 엔티티 객체가 생성이 되거나 변경이 되었을 때 @EnableJpaAuditing 어노테이션을 활용하여 자동으로 값을 등록할 수 있다.
 */
@EnableJpaAuditing // JpaAuditing을 Enable
public class JpaAuditorConfig {
}
