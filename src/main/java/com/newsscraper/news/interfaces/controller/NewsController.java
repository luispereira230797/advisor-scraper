package com.newsscraper.news.interfaces.controller;

import com.newsscraper.news.application.service.NewsService;
import com.newsscraper.news.domain.dto.response.NewsDto;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
@Api("Es un controlador para los servicios de noticias del país.")
public class NewsController {

	private final NewsService newsService;

	@GetMapping("consulta")
	public ResponseEntity<List<NewsDto>> search(@RequestParam(defaultValue = "") String q) throws Exception {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(newsService.search(q));
	}
}
