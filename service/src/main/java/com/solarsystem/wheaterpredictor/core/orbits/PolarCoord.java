package com.solarsystem.wheaterpredictor.core.orbits;

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

		@Override
		public String toString() {
			return new StringBuilder("r(").append(this.x).append(", ").append(this.y).append(")").toString();
		}
	}

	private Integer azimuth;

	private Integer radius;

	public PolarCoord() {
		this.radius = 0;
		this.azimuth = 0;

	}

	public PolarCoord(int radius, int azimuth) {
		this.radius = radius;
		this.azimuth = azimuth;
	}

	public Integer getAzimuth() {
		return azimuth;
	}

	public void setAzimuth(int azimuth) {
		this.azimuth = azimuth;
	}

	public Integer getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public RectangularCoord getRectangularCoord() {
		RectangularCoord rectangularCoord = new RectangularCoord();
		rectangularCoord.setX(Math.cos(Math.toRadians(azimuth)) * radius);
		rectangularCoord.setY(Math.sin(Math.toRadians(azimuth)) * radius);
		return rectangularCoord;
	}

	@Override
	public int hashCode() {
		return Math.abs(getRadius()) * 1000 + getRelativeAzimuth();
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = obj != null && obj instanceof PolarCoord;
		if (result) {
			PolarCoord other = (PolarCoord) obj;
			result = Math.abs(other.getRadius()) == Math.abs(this.getRadius())
					&& getRelativeAzimuth() == other.getRelativeAzimuth();
		}
		return result;
	}

	public int getRelativeAzimuth() {
		int inversion = this.getRadius() < 0 ? 180 : 0;
		int relative = (this.getAzimuth() + inversion) % 360;
		return relative >= 0 ? relative : 360 + relative;
	}

	@Override
	public String toString() {
		return new StringBuilder("p(").append(this.radius).append(", ").append(this.azimuth).append(")").toString();
	}

}
