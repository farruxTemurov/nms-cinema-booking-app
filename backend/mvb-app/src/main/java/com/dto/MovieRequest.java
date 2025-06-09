package com.dto;

import lombok.Data;

@Data
public class MovieRequest {
	private String title;
	private String language;
	private String genre;
	private double rating;
	private Long theaterId;

}
