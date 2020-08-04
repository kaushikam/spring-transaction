package com.kaushikam.transactions.spring.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import java.util.*

@Configuration
class ApplicationConfig {
    @Bean
    fun locale(@Value("\${application.languageTag}") languageTag: String): Locale {
        return Locale.forLanguageTag(languageTag)
    }

    /*@Bean
    fun messageSource(): MessageSource {
        val messageSource = ReloadableResourceBundleMessageSource()
        messageSource.setBasename("classpath:messages")
        messageSource.setDefaultEncoding(Charsets.UTF_8.name())
        return messageSource
    }*/

    @Bean
    fun validator(messageSource: MessageSource): LocalValidatorFactoryBean {
        val bean = LocalValidatorFactoryBean()
        bean.setValidationMessageSource(messageSource)
        return bean
    }
}