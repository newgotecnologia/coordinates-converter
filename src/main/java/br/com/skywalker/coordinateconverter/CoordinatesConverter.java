package br.com.skywalker.coordinateconverter;

public class CoordinatesConverter {

	// ************************************************************************

	public static DecimalDegreesCoordinates toDecimalDegree(UTMCoordinates fromCoordinates) {
		return new DecimalDegreesCoordinates(fromCoordinates);
	}

	public static DecimalDegreesCoordinates toDecimalDegree(UTMCoordinates fromCoordinates, Datum toDatum) {
		return toDatum(new DecimalDegreesCoordinates(fromCoordinates), toDatum);
	}

	public static DecimalDegreesCoordinates toDecimalDegree(DegreesMinutesSecondsCoordinates fromCoordinates) {
		return new DecimalDegreesCoordinates(fromCoordinates);
	}

	public static DecimalDegreesCoordinates toDecimalDegree(DegreesMinutesSecondsCoordinates fromCoordinates,
			Datum toDatum) {
		return toDatum(new DecimalDegreesCoordinates(fromCoordinates), toDatum);
	}

	public static DecimalDegreesCoordinates toDD(UTMCoordinates fromCoordinates) {
		return toDecimalDegree(fromCoordinates);
	}

	public static DecimalDegreesCoordinates toDD(UTMCoordinates fromCoordinates, Datum toDatum) {
		return toDecimalDegree(fromCoordinates, toDatum);
	}

	public static DecimalDegreesCoordinates toDD(DegreesMinutesSecondsCoordinates fromCoordinates) {
		return toDecimalDegree(fromCoordinates);
	}

	public static DecimalDegreesCoordinates toDD(DegreesMinutesSecondsCoordinates fromCoordinates, Datum toDatum) {
		return toDecimalDegree(fromCoordinates, toDatum);
	}

	public static UTMCoordinates toUTM(DecimalDegreesCoordinates fromCoordinates) {
		return new UTMCoordinates(fromCoordinates);
	}

	public static UTMCoordinates toUTM(DecimalDegreesCoordinates fromCoordinates, Datum toDatum) {
		return toDatum(new UTMCoordinates(fromCoordinates), toDatum);
	}

	public static UTMCoordinates toUTM(DegreesMinutesSecondsCoordinates fromCoordinates) {
		return new UTMCoordinates(new DecimalDegreesCoordinates(fromCoordinates));
	}

	public static UTMCoordinates toUTM(DegreesMinutesSecondsCoordinates fromCoordinates, Datum toDatum) {
		return toDatum(new UTMCoordinates(new DecimalDegreesCoordinates(fromCoordinates)), toDatum);
	}

	public static DegreesMinutesSecondsCoordinates toDegreesMinutesSeconds(UTMCoordinates fromCoordinates) {
		return new DegreesMinutesSecondsCoordinates(new DecimalDegreesCoordinates(fromCoordinates));
	}

	public static DegreesMinutesSecondsCoordinates toDegreesMinutesSeconds(UTMCoordinates fromCoordinates,
			Datum toDatum) {
		return toDatum(new DegreesMinutesSecondsCoordinates(new DecimalDegreesCoordinates(fromCoordinates)), toDatum);
	}

	public static DegreesMinutesSecondsCoordinates toDegreesMinutesSeconds(DecimalDegreesCoordinates fromCoordinates) {
		return new DegreesMinutesSecondsCoordinates(fromCoordinates);
	}

	public static DegreesMinutesSecondsCoordinates toDegreesMinutesSeconds(DecimalDegreesCoordinates fromCoordinates,
			Datum toDatum) {
		return toDatum(new DegreesMinutesSecondsCoordinates(fromCoordinates), toDatum);
	}

	public static DegreesMinutesSecondsCoordinates toDMS(UTMCoordinates fromCoordinates) {
		return toDegreesMinutesSeconds(fromCoordinates);
	}

	public static DegreesMinutesSecondsCoordinates toDMS(UTMCoordinates fromCoordinates, Datum toDatum) {
		return toDegreesMinutesSeconds(fromCoordinates, toDatum);
	}

	public static DegreesMinutesSecondsCoordinates toDMS(DecimalDegreesCoordinates fromCoordinates) {
		return toDegreesMinutesSeconds(fromCoordinates);
	}

	public static DegreesMinutesSecondsCoordinates toDMS(DecimalDegreesCoordinates fromCoordinates, Datum toDatum) {
		return toDegreesMinutesSeconds(fromCoordinates, toDatum);
	}

	// ************************************************************************

	public static UTMCoordinates toDatum(UTMCoordinates coordinates, Datum datum) {
		return new UTMCoordinates(new DecimalDegreesCoordinates(coordinates).convertDatum(datum));
	}

	public static DecimalDegreesCoordinates toDatum(DecimalDegreesCoordinates coordinates, Datum datum) {
		return coordinates.convertDatum(datum);
	}

	public static DegreesMinutesSecondsCoordinates toDatum(DegreesMinutesSecondsCoordinates coordinates, Datum datum) {
		return new DegreesMinutesSecondsCoordinates(new DecimalDegreesCoordinates(coordinates).convertDatum(datum));
	}

	// ************************************************************************

	public static UTMCoordinates toHemisphere(UTMCoordinates coordinates, Hemisphere hemisphere) {
		UTMCoordinates newCoordinates = new UTMCoordinates(coordinates);
		newCoordinates.setHemisphere(hemisphere);
		return newCoordinates;
	}

	public static DecimalDegreesCoordinates toHemisphere(DecimalDegreesCoordinates coordinates, Hemisphere hemisphere) {
		DecimalDegreesCoordinates newCoordinates = new DecimalDegreesCoordinates(coordinates);
		newCoordinates.setHemisphere(hemisphere);
		return newCoordinates;
	}

	public static DegreesMinutesSecondsCoordinates toHemisphere(DegreesMinutesSecondsCoordinates coordinates,
			Hemisphere hemisphere) {
		DegreesMinutesSecondsCoordinates newCoordinates = new DegreesMinutesSecondsCoordinates(coordinates);
		newCoordinates.setHemisphere(hemisphere);
		return newCoordinates;
	}

	// ************************************************************************

}
