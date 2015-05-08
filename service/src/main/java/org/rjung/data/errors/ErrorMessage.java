package org.rjung.data.errors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ErrorMessage {
	private String message;
	private String hint;
	private Integer code;

	public ErrorMessage(String message, String hint, Integer code) {
		this.message = message;
		this.hint = hint;
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public String getHint() {
		return hint;
	}

	public Integer getCode() {
		return code;
	}
}
