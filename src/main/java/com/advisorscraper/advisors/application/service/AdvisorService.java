package com.advisorscraper.advisors.application.service;

import com.advisorscraper.advisors.domain.dto.response.AdvisorDto;
import com.advisorscraper.advisors.infrastructure.exception.AdvisorValidationException;
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
public class AdvisorService {
	@Value("${ADVISOR_PATTERN}")
	private String ADVISOR_PATTERN;
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
	@Value("${advisor.source.base.url}")
	private String advisorSourceBaseUrl;
	@Value("${advisor.source.search.url}")
	private String advisorSourceSearchUrl;

	private final WebScraperService webScraperService;

	private final AdvisorObjectValidation advisorObjectValidation;

	// Search advisors by one variable
    public List<AdvisorDto> search(String searchString) throws Exception {
		// Validate param
		var validationMessage = advisorObjectValidation.validateAdvisorSearch(searchString);
		if (validationMessage != null)
			throw new AdvisorValidationException(404, "g267", validationMessage);

		// Get url web content
		var url = advisorSourceSearchUrl.replace("_search_", searchString);
		var webContent = webScraperService.getWebContent(url);

		// Transform web content to formated data
		var responseDto = transformWebContent(webContent);

		// Validate response length
		validationMessage = advisorObjectValidation.validateAdvisorResponse(responseDto, searchString);
		if (validationMessage != null)
			throw new AdvisorValidationException(400, "g268", validationMessage);

		return responseDto;
	}

	// Transform Web Content HTML to required format
	private List<AdvisorDto> transformWebContent(String webContent) {
		var advisors = new ArrayList<AdvisorDto>();
		var divContents = new ArrayList<String>();

		var regex = Pattern.compile(ADVISOR_PATTERN, Pattern.DOTALL);
		var matcher = regex.matcher(webContent);
		while (matcher.find()) {
			// Append the matched content
			divContents.add(matcher.group());
		}

		// Transform divs contents to AdvisorDto format
		for(var divContent: divContents) {
			var advisor = new AdvisorDto();
			advisor.setDate(transformDate(extractData(divContent, DATE_PATTERN_STRING)));
			advisor.setUrl(advisorSourceBaseUrl + extractData(divContent, URL_PATTERN_STRING));
			advisor.setTitle(extractData(divContent, TITLE_PATTERN_STRING));
			advisor.setResume(extractData(divContent, RESUME_PATTERN_STRING));
			advisor.setImageUrl(advisorSourceBaseUrl + extractData(divContent, IMAGE_PATTERN_STRING));
			advisors.add(advisor);
		}
		return advisors;
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
