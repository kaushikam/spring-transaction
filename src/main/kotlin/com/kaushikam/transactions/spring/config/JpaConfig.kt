package com.kaushikam.transactions.spring.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(basePackages = [
    "com.kaushikam.transactions.infrastructure.repository.book",
    "com.kaushikam.transactions.infrastructure.repository.student"
])
class JpaConfig
