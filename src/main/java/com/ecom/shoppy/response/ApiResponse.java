package com.ecom.shoppy.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class ApiResponse {

	private String message;
	private Object data;

	public ApiResponse() {}

	// Constructor with parameters
	public ApiResponse(String message, Object data) {
		this.message = message;
		this.data = data;
	}
}