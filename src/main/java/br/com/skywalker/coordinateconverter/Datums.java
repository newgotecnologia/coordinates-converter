package br.com.skywalker.coordinateconverter;

public enum Datums implements Datum {

	NONE(0.000000e+00, 0.000000e+00, 0.000000e+00, 0.000000e+00, 0.000000e+00),
	SAD69(6.3781600e+06, 3.35289187e-03, -6.735000e+01, 3.880000e+00, -3.822000e+01),
	CorregoAlegre(6.3783880e+06, 3.36700337e-03, -2.060500e+02, 1.682800e+02, -3.820000e+00),
	AstroChua(6.3783880e+06, 3.36700337e-03, -1.443500e+02, 2.433700e+02, -3.322000e+01),
	WGS84(6.3781370e+06, 3.35281066e-03, 0.000000e+00, 0.000000e+00, 0.000000e+00),
	SIRGAS2000(6.3781370e+06, 3.35281068e-03, 0.000000e+00, 0.000000e+00, 0.000000e+00);

	private double semiAxis;
	private double flatness;
	private double deltaX;
	private double deltaY;
	private double deltaZ;

	private Datums(double semiAxis, double flatness, double deltaX, double deltaY, double deltaZ) {
		this.semiAxis = semiAxis;
		this.flatness = flatness;
		this.deltaX = deltaX;
		this.deltaY = deltaY;
		this.deltaZ = deltaZ;
	}

	public double getSemiAxis() {
		return semiAxis;
	}

	public double getFlatness() {
		return flatness;
	}

	public double getDeltaX() {
		return deltaX;
	}

	public double getDeltaY() {
		return deltaY;
	}

	public double getDeltaZ() {
		return deltaZ;
	}

}
