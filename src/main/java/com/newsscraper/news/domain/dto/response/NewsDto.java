package com.newsscraper.news.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class NewsDto {
    @JsonProperty("fecha")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date date;
    @JsonProperty("enlace")
    private String url;
    @JsonProperty("enlace_foto")
    private String imageUrl;
    @JsonProperty("titulo")
    private String title;
    @JsonProperty("resumen")
    private String resume;
}
