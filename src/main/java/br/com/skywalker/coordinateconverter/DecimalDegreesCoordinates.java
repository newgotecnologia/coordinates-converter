package br.com.skywalker.coordinateconverter;

import br.com.skywalker.coordinateconverter.DegreesMinutesSecondsCoordinates.DegreesMinutesSeconds;

public class DecimalDegreesCoordinates extends Coordinates {

	private double latitudeY;
	private double longitudeX;

	public DecimalDegreesCoordinates(double latitudeY, double longitudeX, Datum datum) {
		super(datum);
		this.latitudeY = latitudeY;
		this.longitudeX = longitudeX;
	}

	public DecimalDegreesCoordinates(DecimalDegreesCoordinates coordinates) {
		this(coordinates.getLatitudeY(), coordinates.getLongitudeX(), coordinates.getDatum());
	}

	public DecimalDegreesCoordinates(DegreesMinutesSecondsCoordinates degreesMinutesSecondsCoordinates) {
		super(degreesMinutesSecondsCoordinates.getDatum());
		DegreesMinutesSeconds latitudeDMS = degreesMinutesSecondsCoordinates.getLatitudeY();
		double repeated = (1d / 60);
		this.latitudeY = latitudeDMS.getDegrees() + (latitudeDMS.getMinutes() * repeated)
				+ (latitudeDMS.getSeconds() * repeated * repeated);
		if (latitudeDMS.getLocation() == Location.SOUTH) {
			this.latitudeY *= -1;
		}

		DegreesMinutesSeconds longitudeDMS = degreesMinutesSecondsCoordinates.getLongitudeX();
		this.longitudeX = longitudeDMS.getDegrees() + (longitudeDMS.getMinutes() * repeated)
				+ (longitudeDMS.getSeconds() * repeated * repeated);
		if (longitudeDMS.getLocation() == Location.WEST) {
			this.longitudeX *= -1;
		}
	}

	public DecimalDegreesCoordinates(UTMCoordinates utmCoordinates) {
		super(utmCoordinates.getDatum());
		double xInMeters = utmCoordinates.getX();
		double yInMeters = utmCoordinates.getY();
		double semiAxis = datum.getSemiAxis();
		double flatness = datum.getFlatness();
		Hemisphere hemisphere = utmCoordinates.getHemisphere();
		double centralMeridianInRadians = utmCoordinates.calculateCentralMeridianInRadians();

		if (hemisphere == Hemisphere.NORTH)
			yInMeters = yInMeters + 10000000;

		double $k0 = 1. - (1. / 2500.);
		double $equad = 2. * flatness - Math.pow(flatness, (double) 2);
		double $elinquad = $equad / (1. - $equad);
		double $e1 = (1. - Math.sqrt((double) 1 - $equad)) / (1. + Math.sqrt((double) 1 - $equad));

		double $aux1 = $equad * $equad;
		double $aux2 = $aux1 * $equad;
		double $aux3 = $e1 * $e1;
		double $aux4 = $e1 * $aux3;
		double $aux5 = $aux4 * $e1;

		double $m = (yInMeters - 10000000.) / $k0;
		double $mi = $m / (semiAxis * (1. - $equad / 4. - 3. * $aux1 / 64. - 5. * $aux2 / 256.));

		double $aux6 = (3. * $e1 / 2. - 27. * $aux4 / 32.) * Math.sin((double) 2 * $mi);
		double $aux7 = (21. * $aux3 / 16. - 55. * $aux5 / 32.) * Math.sin((double) 4 * $mi);
		double $aux8 = (151. * $aux4 / 96.) * Math.sin((double) 6 * $mi);

		double $lat1 = $mi + $aux6 + $aux7 + $aux8;
		double $c1 = $elinquad * Math.pow(Math.cos($lat1), (double) 2);
		double $t1 = Math.pow(Math.tan($lat1), (double) 2);
		double $n1 = semiAxis / Math.sqrt((double) 1 - $equad * Math.pow(Math.sin($lat1), (double) 2));
		double $quoc = Math.pow(((double) 1 - $equad * Math.sin($lat1) * Math.sin($lat1)), (double) 3);
		double $r1 = semiAxis * (1. - $equad) / Math.sqrt($quoc);
		double $d = (xInMeters - 500000.) / ($n1 * $k0);

		double $aux9 = (5. + 3. * $t1 + 10. * $c1 - 4. * $c1 * $c1 - 9. * $elinquad) * $d * $d * $d * $d / 24.;
		double $aux10 = (61. + 90. * $t1 + 298. * $c1 + 45. * $t1 * $t1 - 252. * $elinquad - 3. * $c1 * $c1)
				* Math.pow($d, (double) 6) / 720.;
		double $aux11 = $d - (1. + 2. * $t1 + $c1) * $d * $d * $d / 6.;
		double $aux12 = (5. - 2. * $c1 + 28. * $t1 - 3. * $c1 * $c1 + 8. * $elinquad + 24. * $t1 * $t1)
				* Math.pow($d, (double) 5) / 120.;

		this.latitudeY = Math.toDegrees($lat1 - ($n1 * Math.tan($lat1) / $r1) * ($d * $d / 2. - $aux9 + $aux10));
		this.longitudeX = Math.toDegrees(centralMeridianInRadians + ($aux11 + $aux12) / Math.cos($lat1));
	}

	public DecimalDegreesCoordinates convertDatum(Datum toDatum) {
		double fromSemiAxis = this.datum.getSemiAxis();
		double fromFlatness = this.datum.getFlatness();
		double fromDeltaX = this.datum.getDeltaX();
		double fromDeltaY = this.datum.getDeltaY();
		double fromDeltaZ = this.datum.getDeltaZ();

		double $h1 = 0; // Don't know why this was used as 0 in the original algorithm.

		double toSemiAxis = toDatum.getSemiAxis();
		double toFlatness = toDatum.getFlatness();
		double toDeltaX = toDatum.getDeltaX();
		double toDeltaY = toDatum.getDeltaY();
		double toDeltaZ = toDatum.getDeltaZ();
		double resultLat = 0;
		double resultLon = 0;

		double latitudeY = this.getLatitudeYAsRadians();
		double longitudeX = this.getLongitudeXAsRadians();

		// Calculate coordinates in the 'from' datum.
		double $equad1 = 2. * fromFlatness - Math.pow((double) 1 * fromFlatness, (double) 2);
		double $n1 = fromSemiAxis / Math.sqrt((double) 1 - $equad1 * Math.pow(Math.sin(latitudeY), (double) 2));
		double $x1 = ($n1 + $h1) * Math.cos(latitudeY) * Math.cos(longitudeX);
		double $y1 = ($n1 + $h1) * Math.cos(latitudeY) * Math.sin(longitudeX);
		double $z1 = ($n1 * (1 - $equad1) + $h1) * Math.sin(latitudeY);

		// Calculate coordinates in the 'to' datum.
		double $x2 = $x1 + (fromDeltaX - toDeltaX);
		double $y2 = $y1 + (fromDeltaY - toDeltaY);
		double $z2 = $z1 + (fromDeltaZ - toDeltaZ);

		// Convert.
		double $equad2 = 2. * toFlatness - Math.pow((double) 1 * toFlatness, (double) 2);
		resultLat = latitudeY;
		double $d;
		do {
			double $n2 = toSemiAxis / Math.sqrt((double) 1 - $equad2 * Math.pow(Math.sin(resultLat), (double) 2));
			resultLat = Math.atan(($z2 + $n2 * $equad2 * Math.sin(resultLat)) / Math.sqrt($x2 * $x2 + $y2 * $y2));
			$d = toSemiAxis / Math.sqrt((double) 1 - $equad2 * Math.pow(Math.sin(resultLat), (double) 2)) - $n2;
		} while (Math.abs($d) > 0.00000000001);
		resultLon = Math.atan($y2 / $x2);

		return DecimalDegreesCoordinates.fromRadians(resultLat, resultLon, toDatum);
	}

	public static DecimalDegreesCoordinates fromRadians(double radiansLat, double radiansLon, Datum datum) {
		return new DecimalDegreesCoordinates(Math.toDegrees(radiansLat), Math.toDegrees(radiansLon), datum);
	}

	public Hemisphere getHemisphere() {
		if (this.latitudeY < 0) {
			return Hemisphere.SOUTH;
		}
		return Hemisphere.NORTH;
	}

	public void setHemisphere(Hemisphere hemisphere) {
		if (hemisphere == Hemisphere.NORTH) {
			if (this.latitudeY < 0) {
				this.latitudeY *= -1;
			}
		} else {
			if (this.latitudeY > 0) {
				this.latitudeY *= -1;
			}
		}
	}

	public double getLatitudeY() {
		return latitudeY;
	}

	public double getLatitudeYAsRadians() {
		return Math.toRadians(this.latitudeY);
	}

	public double getLongitudeX() {
		return longitudeX;
	}

	public double getLongitudeXAsRadians() {
		return Math.toRadians(this.longitudeX);
	}

	/**
	 * Calculate the central meridian using the longitude.
	 * 
	 * @return A double array containing the central meridian. When it has two
	 *         values, these refers to the spindle edges.
	 */
	public double[] calculateCentralMeridian() {
		double eastCentralMeridian = 0;
		double westCentralMeridian = 0;
		int $signal = 0;
		if (longitudeX != 0.)
			$signal = (int) (longitudeX / Math.abs(longitudeX));
		else {
			eastCentralMeridian = 3.;
			westCentralMeridian = -3.;
		}

		int ind1;
		double centralMeridian = 0;
		for (int i = ind1 = 0, ind2 = 6; Math.abs(longitudeX) > (double) ind2; i++, ind1 = 6 * i, ind2 = ind1 + 6)
			;
		centralMeridian = ind1 + 3.;

		if ((Math.abs(longitudeX) < (double) centralMeridian) && (longitudeX != 0.)) {
			eastCentralMeridian = westCentralMeridian = (double) centralMeridian;
		}

		if (((Math.abs(longitudeX) > (double) centralMeridian))
				&& (Math.abs(longitudeX) != ((double) centralMeridian + 3.))) {
			eastCentralMeridian = westCentralMeridian = (double) centralMeridian;
		}

		if ((Math.abs(longitudeX) == ((double) centralMeridian + 3.)) && (Math.abs(longitudeX) != 180.)) {
			eastCentralMeridian = (double) centralMeridian;
			westCentralMeridian = (double) centralMeridian + 6.;
		}

		if (longitudeX == 180.) {
			eastCentralMeridian = 177.;
			westCentralMeridian = -177.;
		}

		if (longitudeX == -180.) {
			eastCentralMeridian = -177.;
			westCentralMeridian = 177.;
		}

		if (Math.abs(longitudeX) == (double) centralMeridian) {
			eastCentralMeridian = westCentralMeridian = (double) centralMeridian;
		}

		if ((longitudeX != 0.) && (Math.abs(longitudeX) != 180)) {
			eastCentralMeridian *= $signal;
			westCentralMeridian *= $signal;
		}

		return new double[] { eastCentralMeridian, westCentralMeridian };
	}

	public double[] calculateCentralMeridianAsRadians() {
		double[] centralMeridian = calculateCentralMeridian();
		return new double[] { centralMeridian[0] * Math.PI / 180, centralMeridian[1] * Math.PI / 180 };
	}

	@Override
	public String toString() {
		return String.format("%2.10f %2.10f", latitudeY, longitudeX);
	}

}
