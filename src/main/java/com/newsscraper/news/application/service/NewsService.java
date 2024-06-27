package com.newsscraper.news.application.service;

import com.newsscraper.news.domain.dto.response.NewsDto;
import com.newsscraper.news.infrastructure.exception.NewsValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class NewsService {
	@Value("${NEW_CONTAINER_PATTERN}")
	private String NEW_CONTAINER_PATTERN;
	@Value("${URL_PATTERN_STRING}")
	private String URL_PATTERN_STRING;
	@Value("${DATE_PATTERN_STRING}")
	private String DATE_PATTERN_STRING;
	@Value("${TITLE_PATTERN_STRING}")
	private String TITLE_PATTERN_STRING;
	@Value("${RESUME_PATTERN_STRING}")
	private String RESUME_PATTERN_STRING;
	@Value("${IMAGE_PATTERN_STRING}")
	private String IMAGE_PATTERN_STRING;
	@Value("${news.source.base.url}")
	private String newSourceBaseUrl;
	@Value("${news.source.search.url}")
	private String newSourceSearchUrl;

	private final WebScraperService webScraperService;

	private final NewsObjectValidation newsObjectValidation;

	// Search news by one variable
    public List<NewsDto> search(String searchString) throws Exception {
		// Validate param
		var validationMessage = newsObjectValidation.validateNewsSearch(searchString);
		if (validationMessage != null)
			throw new NewsValidationException(404, "g267", validationMessage);

		// Get url web content
		var url = newSourceSearchUrl.replace("_search_", searchString);
		var webContent = webScraperService.getWebContent(url);

		// Transform web content to formated data
		var responseDto = transformWebContent(webContent);

		// Validate response length
		validationMessage = newsObjectValidation.validateNewsResponse(responseDto, searchString);
		if (validationMessage != null)
			throw new NewsValidationException(400, "g268", validationMessage);

		return responseDto;
	}

	// Transform Web Content HTML to required format
	private List<NewsDto> transformWebContent(String webContent) {
		var news = new ArrayList<NewsDto>();
		var divContents = new ArrayList<String>();

		var regex = Pattern.compile(NEW_CONTAINER_PATTERN, Pattern.DOTALL);
		var matcher = regex.matcher(webContent);
		while (matcher.find()) {
			// Append the matched content
			divContents.add(matcher.group());
		}

		// Transform divs contents to NewDto format
		for(var divContent: divContents) {
			var newsDto = new NewsDto();
			newsDto.setDate(transformDate(extractData(divContent, DATE_PATTERN_STRING)));
			newsDto.setUrl(newSourceBaseUrl + extractData(divContent, URL_PATTERN_STRING));
			newsDto.setTitle(extractData(divContent, TITLE_PATTERN_STRING));
			newsDto.setResume(extractData(divContent, RESUME_PATTERN_STRING));
			newsDto.setImageUrl(newSourceBaseUrl + extractData(divContent, IMAGE_PATTERN_STRING));
			news.add(newsDto);
		}
		return news;
	}

	// Extract htmlContent data with a pattern (regex)
	private String extractData(String htmlContent, String patternString) {
		var pattern = Pattern.compile(patternString);
		var matcher = pattern.matcher(htmlContent);

		var extractedTexts = new ArrayList<String>();
		while (matcher.find()) {
			extractedTexts.add(matcher.group(1));
		}
		return concatenateWithStringBuilder(extractedTexts);
	}

	// Concat result in one string
	private static String concatenateWithStringBuilder(List<String> array) {
		var sb = new StringBuilder();
		for (var str : array) {
			sb.append(str);
		}
		return sb.toString();
	}

	// Transform date to valid Java date
	private Date transformDate(String dateString) {
		try {
			// Define the format of the input date string
			var inputFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH);

			// Fix dateString
			dateString = dateString.replace("&nbsp;", " ");
			dateString = dateString.replace("A. M.", "AM");
			dateString = dateString.replace("P. M.", "PM");

			// Parse the input date string into a Date object
			var date = inputFormat.parse(dateString);

			return date;
		} catch (ParseException e) {
			System.out.println("Error parsing date: " + e.getMessage());
			e.printStackTrace();
		}
        return null;
    }
}
