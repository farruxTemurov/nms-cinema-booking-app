package com.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingRequest {
	@NotNull
    private Long movieId;

    @NotNull
    private Long theaterId;

    @NotBlank
    private String bookingTime;
}
