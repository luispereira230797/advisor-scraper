package com.advisorscraper.advisors.infrastructure.config;

import com.advisorscraper.advisors.application.service.AdvisorObjectValidation;
import com.advisorscraper.advisors.application.service.AdvisorService;
import com.advisorscraper.advisors.application.service.WebScraperService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdvisorDependecyInjection {

	@Bean
	AdvisorObjectValidation advisorObjectValidation() {
		return new AdvisorObjectValidation();
	}

	@Bean
	AdvisorService advisorService(WebScraperService webScraperService, AdvisorObjectValidation advisorObjectValidation) {
		return new AdvisorService(webScraperService, advisorObjectValidation);
	}

}
