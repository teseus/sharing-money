package com.base.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing //Application에 있으면  WebTestClient 로딩시 에러.
public class JpaAuditing {
}
