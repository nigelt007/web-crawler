package com.scout24.webcrawler.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.scout24.webcrawler.constants.ApiConstants;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The configuration class for defining configurations which is applicable
 * across the application.
 * @author Nigel
 *
 */
@Configuration
@EnableSwagger2
public class WebCrawlerSwaggerConfig extends WebMvcConfigurationSupport {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.scout24.webcrawler.controller"))
				.paths(regex(ApiConstants.WEB_CRAWLER_CONTROLLER + ".*")).build().apiInfo(metaData());
	}

	private ApiInfo metaData() {
		return new ApiInfoBuilder().title("Web Crawler REST API")
				.description("\"REST API for crawling a Web Url\"").version("1.0.0")
				.license("Apache License Version 2.0").licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
				.contact(
						new Contact("Nigel Thomas", "https://github.com/nigelt007", "nigel"))
				.build();
	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

}
