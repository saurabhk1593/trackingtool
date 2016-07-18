package com.techm.trackingtool.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.techm.trackingtool")
@EnableScheduling
public class LindeToolAppConfig extends WebMvcConfigurerAdapter {

	@Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
	
	 @Bean
	    public MessageSource messageSource() {
	        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
	        messageSource.setBasename("messages");
	        return messageSource;
	    }
	 
	 
	    @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry.addResourceHandler("/**").addResourceLocations("/");
	    }
	    
	    @Bean
		public CommonsMultipartResolver multipartResolver(){
			CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		    commonsMultipartResolver.setDefaultEncoding("utf-8");
		    commonsMultipartResolver.setMaxUploadSize(50000000);
		    return commonsMultipartResolver;

		}
	    
	    @Bean
	    public ContiSchdeuler sendCREmailNotifications()
	    {
	    	return new ContiSchdeuler();
	    	
	    }
}
