package com.newsscraper.news.infrastructure.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDetails {
    @JsonProperty("codigo")
    private String code;
    @JsonProperty("error")
    private String error;
}
