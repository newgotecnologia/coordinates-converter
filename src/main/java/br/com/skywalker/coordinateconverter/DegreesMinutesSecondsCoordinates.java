package br.com.skywalker.coordinateconverter;

public class DegreesMinutesSecondsCoordinates extends Coordinates {

	private DegreesMinutesSeconds latitudeY;
	private DegreesMinutesSeconds longitudeX;

	public DegreesMinutesSecondsCoordinates(Location latLocation, long latDegree, long latMin, double latSec,
			Location lonLocation, long lonDegree, long lonMin, double lonSec, Datum datum) {
		this(new DegreesMinutesSeconds(latLocation, latDegree, latMin, latSec),
				new DegreesMinutesSeconds(lonLocation, lonDegree, lonMin, lonSec), datum);
	}

	public DegreesMinutesSecondsCoordinates(DegreesMinutesSecondsCoordinates coordinates) {
		this(coordinates.getLatitudeY().getLocation(), coordinates.getLatitudeY().getDegrees(),
				coordinates.getLatitudeY().getMinutes(), coordinates.getLatitudeY().getSeconds(),
				coordinates.getLongitudeX().getLocation(), coordinates.getLongitudeX().getDegrees(),
				coordinates.getLongitudeX().getMinutes(), coordinates.getLongitudeX().getSeconds(),
				coordinates.getDatum());
	}

	public DegreesMinutesSecondsCoordinates(DegreesMinutesSeconds latitudeY, DegreesMinutesSeconds longitudeX,
			Datum datum) {
		super(datum);
		this.latitudeY = latitudeY;
		this.longitudeX = longitudeX;
	}

	public DegreesMinutesSecondsCoordinates(DecimalDegreesCoordinates decimalDegreesCoordinates) {
		super(decimalDegreesCoordinates.getDatum());
		this.latitudeY = DegreesMinutesSeconds.fromDecimalDegreesLatitude(decimalDegreesCoordinates.getLatitudeY());
		this.longitudeX = DegreesMinutesSeconds.fromDecimalDegreesLongitude(decimalDegreesCoordinates.getLongitudeX());
	}

	public DegreesMinutesSeconds getLatitudeY() {
		return latitudeY;
	}

	public void setLatitudeY(DegreesMinutesSeconds latitudeY) {
		this.latitudeY = latitudeY;
	}

	public DegreesMinutesSeconds getLongitudeX() {
		return longitudeX;
	}

	public void setLongitudeX(DegreesMinutesSeconds longitudeX) {
		this.longitudeX = longitudeX;
	}

	public void setHemisphere(Hemisphere hemisphere) {
		this.latitudeY.setLocation(hemisphere == Hemisphere.NORTH ? Location.NORTH : Location.SOUTH);
	}
	
	@Override
	public String toString() {
		return String.format("%s %s", this.latitudeY, this.longitudeX);
	}

	public static class DegreesMinutesSeconds {

		private Location location;
		private long degrees;
		private long minutes;
		private double seconds;

		public DegreesMinutesSeconds(Location location, long degrees, long minutes, double seconds) {
			super();
			this.location = location;
			this.degrees = degrees;
			this.minutes = minutes;
			this.seconds = seconds;
		}

		public static DegreesMinutesSeconds fromDecimalDegreesLatitude(double latitudeY) {
			double originalLatitudeY = latitudeY;
			latitudeY = Math.abs(latitudeY);
			long degrees = (long) (Math.floor(latitudeY));
			long minutes = (long) (Math.floor((latitudeY - degrees) * 60));
			double seconds = (((latitudeY - degrees) * 60) - minutes) * 60;
			Location location;
			if (originalLatitudeY < 0) {
				location = Location.SOUTH;
			} else {
				location = Location.NORTH;
			}

			return new DegreesMinutesSeconds(location, degrees, minutes, seconds);
		}

		public static DegreesMinutesSeconds fromDecimalDegreesLongitude(double longitudeX) {
			double originalLongitudeX = longitudeX;
			longitudeX = Math.abs(longitudeX);
			long degrees = (long) (Math.floor(longitudeX));
			long minutes = (long) (Math.floor((longitudeX - degrees) * 60));
			double seconds = (((longitudeX - degrees) * 60) - minutes) * 60;
			Location location;
			if (originalLongitudeX < 0) {
				location = Location.WEST;
			} else {
				location = Location.EAST;
			}

			return new DegreesMinutesSeconds(location, degrees, minutes, seconds);
		}

		public Location getLocation() {
			return location;
		}

		public void setLocation(Location location) {
			this.location = location;
		}

		public long getDegrees() {
			return degrees;
		}

		public void setDegrees(long degrees) {
			this.degrees = degrees;
		}

		public long getMinutes() {
			return minutes;
		}

		public void setMinutes(long minutes) {
			this.minutes = minutes;
		}

		public double getSeconds() {
			return seconds;
		}

		public void setSeconds(double seconds) {
			this.seconds = seconds;
		}

		@Override
		public String toString() {
			return String.format("%d° %d' %.4f\" %s", this.degrees, this.minutes, this.seconds,
					this.location.getAcronym());
		}

	}

}
