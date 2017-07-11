package com.solarsystem.wheaterpredictor.service.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ExceptionMessageBuilder {
	public static ExceptionMessageBuilder getInstance() {
		return new ExceptionMessageBuilder();
	}

	Map<String, Object> message;

	public ExceptionMessageBuilder() {
		message = new HashMap<String, Object>();
	}

	public ExceptionMessageBuilder addField(String key, Object value) {
		message.put(key, value);
		return this;
	}

	public Map<String, Object> build() {
		return message;
	}

	public ExceptionMessageBuilder status(int value) {
		message.put("status", value);
		return this;
	}
	
}
