package com.solarsystem.wheaterpredictor.core;

public final class PolarCoord {

	public static class RectangularCoord {

		public RectangularCoord() {
			super();
		}
		
		public RectangularCoord(Double x, Double y) {
			this.x = x;
			this.y = y;
		}
		
		private Double x;
		private Double y;

		public Double getX() {
			return x;
		}

		public void setX(Double x) {
			this.x = x;
		}

		public Double getY() {
			return y;
		}

		public void setY(Double y) {
			this.y = y;
		}

	}

	private Integer azimuth;

	private Integer radius;

	public PolarCoord() {
		this.radius = 0;
		this.azimuth = 0;

	}

	public PolarCoord(Integer radius, Integer azimuth) {
		this.radius = radius;
		this.azimuth = azimuth;
	}

	public Integer getAzimuth() {
		return azimuth;
	}

	public void setAzimuth(Integer azimuth) {
		this.azimuth = azimuth;
	}

	public Integer getRadius() {
		return radius;
	}

	public void setRadius(Integer radius) {
		this.radius = radius;
	}

	public RectangularCoord getRectangularCoord() {
		RectangularCoord rectangularCoord = new RectangularCoord();
		rectangularCoord.setX(Math.cos(azimuth) * radius);
		rectangularCoord.setY(Math.sin(azimuth) * radius);
		return rectangularCoord;
	}

	@Override
	public int hashCode() {
		return radius * 1000 + azimuth;
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = obj != null && obj instanceof PolarCoord;
		if (result) {
			PolarCoord other = (PolarCoord) obj;
			result = other.getRadius() != null && other.getRadius().equals(this.getRadius())
					&& other.getAzimuth() != null && other.getAzimuth().equals(this.getAzimuth());
		}
		return result;
	}
}
