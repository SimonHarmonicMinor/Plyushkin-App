package com.plyushkin.shared;

import com.cosium.spring.data.jpa.entity.graph.repository.support.EntityGraphJpaRepositoryFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(value = "com.plyushkin", repositoryFactoryBeanClass = EntityGraphJpaRepositoryFactoryBean.class)
public class JpaConfig {
}
