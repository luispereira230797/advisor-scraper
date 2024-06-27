package com.advisorscraper.advisors.interfaces.controller;

import com.advisorscraper.advisors.application.service.AdvisorService;
import com.advisorscraper.advisors.domain.dto.response.AdvisorDto;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("api/advisors")
@RequiredArgsConstructor
@Api("Es un controlador para los servicios de noticias del país.")
public class AdvisorController {

	private final AdvisorService advisorService;

	@GetMapping
	public ResponseEntity<List<AdvisorDto>> search(@RequestParam(defaultValue = "") String q) throws Exception {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(advisorService.search(q));
	}
}
