package com.spring.web.config;

import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * @Author ylli
 * @Description jakarta.validation
 */
@Configuration
public class ValidateConfig {

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(){
        MethodValidationPostProcessor processor = new MethodValidationPostProcessor();

        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure()
                //设置只要出现检验失败，立即返回不再进行后续字段校验
                .failFast(true).buildValidatorFactory();
        processor.setValidator(validatorFactory.getValidator());
        return processor;
    }
}
