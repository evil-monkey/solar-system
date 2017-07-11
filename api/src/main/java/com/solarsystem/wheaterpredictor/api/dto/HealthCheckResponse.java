package com.solarsystem.wheaterpredictor.api.dto;

import java.io.Serializable;

public class HealthCheckResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1012711196871274756L;

	private String name;
	private String version;

	public HealthCheckResponse(String serviceName, String packageVersion) {
		this.name = serviceName;
		this.version = packageVersion;
	}

	public String getVersion() {
		return version;
	}

	public String getName() {
		return name;
	}

}
