package com.newsscraper.news.application.service;


import com.newsscraper.news.domain.dto.response.NewsDto;

import java.util.List;

public class NewsObjectValidation {
	public String validateNewsSearch(String q) {
		if (q == null || q.isEmpty())
			return "Parámetros inválidos";

		return null;
	}
	public String validateNewsResponse(List<NewsDto> response, String searchString) {
		if (response.size() == 0)
			return "No se encuentran noticias para el texto: " + searchString;
		return null;
	}
}
