package com.solarsystem.wheaterpredictor.api.dto;

import java.io.Serializable;
import java.util.Comparator;

public class WheaterStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7333317650513679717L;
	
	public static final Comparator<WheaterStatus> comparator= new Comparator<WheaterStatus>() {

		@Override
		public int compare(WheaterStatus o1, WheaterStatus o2) {
			return o1 == null || o1.getDia() == null ? -1
					: (o2 == null ? 1 : o1.getDia().compareTo(o2.getDia()));
		}
	};

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

	@Override
	public String toString() {
		return new StringBuilder().append("{\"dia\":").append(this.dia).append("\",\"clima\":\"").append(this.clima)
				.append("\"}").toString();
	}
	
}
