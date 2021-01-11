package br.com.skywalker.coordinateconverter;

public class UTMCoordinates extends Coordinates {

	private double x;
	private double y;
	private int zone;
	private Hemisphere hemisphere;

	public UTMCoordinates(double x, double y, Hemisphere hemisphere, int zone, Datum datum) {
		super(datum);
		this.x = x;
		this.y = y;
		this.zone = zone;
		this.hemisphere = hemisphere;
	}
	
	public UTMCoordinates(double x, double y, Hemisphere hemisphere, String zone, Datum datum) {
		super(datum);
		this.x = x;
		this.y = y;
		this.zone = Integer.valueOf(zone.substring(2));;
		this.hemisphere = hemisphere;
	}

	public UTMCoordinates(UTMCoordinates coordinates) {
		this(coordinates.getX(), coordinates.getY(), coordinates.getHemisphere(), coordinates.getZone(),
				coordinates.getDatum());
	}

	public UTMCoordinates(DecimalDegreesCoordinates decimalDegreesCoordinates) {
		super(decimalDegreesCoordinates.getDatum());
		double latAsRadians = decimalDegreesCoordinates.getLatitudeYAsRadians();
		double lonAsRadians = decimalDegreesCoordinates.getLongitudeXAsRadians();
		double centralMeridianAsRadians = decimalDegreesCoordinates.calculateCentralMeridianAsRadians()[0];

		this.hemisphere = decimalDegreesCoordinates.getHemisphere();

		double $offy;
		if (hemisphere == Hemisphere.NORTH)
			$offy = 0.;
		else
			$offy = 10000000;

		double $k0 = 1. - (1. / 2500.);
		double $equad = 2. * datum.getFlatness() - Math.pow(datum.getFlatness(), 2d);
		double $elinquad = $equad / (1. - $equad);

		double $aux1 = $equad * $equad;
		double $aux2 = $aux1 * $equad;
		double $aux3 = Math.sin((double) 2 * latAsRadians);
		double $aux4 = Math.sin((double) 4 * latAsRadians);
		double $aux5 = Math.sin((double) 6 * latAsRadians);
		double $aux6 = (1. - $equad / 4. - 3. * $aux1 / 64. - 5. * $aux2 / 256.) * latAsRadians;
		double $aux7 = (3. * $equad / 8. + 3. * $aux1 / 32. + 45. * $aux2 / 1024.) * $aux3;
		double $aux8 = (15. * $aux1 / 256. + 45. * $aux2 / 1024.) * $aux4;
		double $aux9 = (35. * $aux2 / 3072.) * $aux5;

		double $n = datum.getSemiAxis() / Math.sqrt((double) 1 - $equad * Math.pow(Math.sin(latAsRadians), (double) 2));
		double $t = Math.pow(Math.tan(latAsRadians), (double) 2);
		double $c = $elinquad * Math.pow(Math.cos(latAsRadians), (double) 2);
		double $ag = (lonAsRadians - centralMeridianAsRadians) * Math.cos(latAsRadians);
		double $m = datum.getSemiAxis() * ($aux6 - $aux7 + $aux8 - $aux9);

		double $aux10 = (1. - $t + $c) * $ag * $ag * $ag / 6.;
		double $aux11 = (5. - 18. * $t + $t * $t + 72. * $c - 58. * $elinquad) * (Math.pow($ag, (double) 5)) / 120.;
		double $aux12 = (5. - $t + 9. * $c + 4. * $c * $c) * $ag * $ag * $ag * $ag / 24.;
		double $aux13 = (61. - 58. * $t + $t * $t + 600. * $c - 330. * $elinquad) * (Math.pow($ag, (double) 6)) / 720.;

		this.x = 500000. + $k0 * $n * ($ag + $aux10 + $aux11);
		this.y = $offy + $k0 * ($m + $n * Math.tan(latAsRadians) * ($ag * $ag / 2. + $aux12 + $aux13));
		this.zone = (int) Math.ceil((decimalDegreesCoordinates.getLongitudeX() + 180) / 6);
	}

	public int calculateCentralMeridian() {
		int deltaZone = this.zone - 31; // 31 is the reference zone for central meridian equals 3.
		return 3 + deltaZone * 6;
	}

	public double calculateCentralMeridianInRadians() {
		int centralMeridian = calculateCentralMeridian();
		return centralMeridian * Math.PI / 180;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public Hemisphere getHemisphere() {
		return hemisphere;
	}

	public void setHemisphere(Hemisphere hemisphere) {
		this.hemisphere = hemisphere;
	}

	public int getZone() {
		return zone;
	}

	public Datum getDatum() {
		return datum;
	}

	@Override
	public String toString() {
		return String.format("%s %s", this.x, this.y);
	}

}
