package com.advisorscraper.advisors.application.service;


import com.advisorscraper.advisors.domain.dto.response.AdvisorDto;

import java.util.List;

public class AdvisorObjectValidation {
	public String validateAdvisorSearch(String q) {
		if (q == null || q.isEmpty())
			return "Parámetros inválidos";

		return null;
	}
	public String validateAdvisorResponse(List<AdvisorDto> response, String searchString) {
		if (response.size() == 0)
			return "No se encuentran noticias para el texto: " + searchString;
		return null;
	}
}
