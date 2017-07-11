package com.solarsystem.wheaterpredictor.api.dto;

import java.io.Serializable;

public class WheaterStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7333317650513679717L;

	private Integer day;
	private String clima;

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public String getClima() {
		return clima;
	}

	public void setClima(String clima) {
		this.clima = clima;
	}

	

}
