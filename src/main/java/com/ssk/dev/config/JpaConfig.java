package com.ssk.dev.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by sik371@ktnet.co.kr 2022-08-12 오후 5:54
 */
@Configuration
@EnableJpaRepositories("com.ssk.dev.repository")
@EnableTransactionManagement
public class JpaConfig {

}
