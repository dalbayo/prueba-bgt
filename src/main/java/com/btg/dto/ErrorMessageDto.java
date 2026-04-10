package com.btg.dto;

public record ErrorMessageDto(
	    int statusCode,
	    java.time.LocalDateTime timestamp,
	    String message,
	    String description
	) {}
