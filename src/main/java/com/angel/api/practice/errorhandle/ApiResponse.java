package com.angel.api.practice.errorhandle;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ApiResponse {
    private boolean success;
    private boolean error;
    private Object data;
    private String message;

    // Constructors, getters, and setters

    public ApiResponse(boolean success, boolean error, Object data) {
        this.success = success;
        this.error = error;
        this.data = data;
    }

    public ApiResponse(boolean success, boolean error, String message) {
        this.success = success;
        this.error = error;
        this.message = message;
    }

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

    
    // Getters and setters...

    // You can add more methods or properties as needed
}