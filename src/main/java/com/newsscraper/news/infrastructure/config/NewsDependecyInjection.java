package com.newsscraper.news.infrastructure.config;

import com.newsscraper.news.application.service.NewsObjectValidation;
import com.newsscraper.news.application.service.NewsService;
import com.newsscraper.news.application.service.WebScraperService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NewsDependecyInjection {

	@Bean
	NewsObjectValidation newsObjectValidation() {
		return new NewsObjectValidation();
	}

	@Bean
	NewsService newsService(WebScraperService webScraperService, NewsObjectValidation newsObjectValidation) {
		return new NewsService(webScraperService, newsObjectValidation);
	}

}
