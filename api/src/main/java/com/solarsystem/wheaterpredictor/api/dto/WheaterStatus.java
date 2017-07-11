package com.solarsystem.wheaterpredictor.api.dto;

import java.io.Serializable;

public class WheaterStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7333317650513679717L;

	private Integer dia;
	private String clima;

	public Integer getDia() {
		return dia;
	}

	public void setDia(Integer day) {
		this.dia = day;
	}

	public String getClima() {
		return clima;
	}

	public void setClima(String clima) {
		this.clima = clima;
	}

	

}
