package br.com.skywalker.coordinateconverter;

public class Main {

	public static void main(String[] args) {

		Datums toDatum = Datums.WGS84;

		DecimalDegreesCoordinates decimalDegreesCoordinates = new DecimalDegreesCoordinates(-46.17957, -23.51511,
				Datums.SAD69);
		DegreesMinutesSecondsCoordinates degreesMinutesSecondsCoordinates = new DegreesMinutesSecondsCoordinates(
				decimalDegreesCoordinates);
		System.out.printf("Decimal degree coordinate in Datum %s:\n     %s.\n",
				decimalDegreesCoordinates.getDatum().name(), decimalDegreesCoordinates);
		System.out.printf("Converting decimal degree coordinate %s to DMS:\n     %s.\n", decimalDegreesCoordinates,
				new DegreesMinutesSecondsCoordinates(decimalDegreesCoordinates));
		System.out.printf("Converting back to decimal degree coordinate:\n     %s.\n",
				new DecimalDegreesCoordinates(degreesMinutesSecondsCoordinates));
		DecimalDegreesCoordinates toDatumDecimalDegreesCoordinates = decimalDegreesCoordinates.convertDatum(toDatum);
		System.out.printf("In %s:\n     %s.\n", toDatum.name(), toDatumDecimalDegreesCoordinates);

		System.out.println("==========");

		double[] centralMeridian = decimalDegreesCoordinates.calculateCentralMeridian();
		System.out.printf("Central meridian: %.10f %.10f\n", centralMeridian[0], centralMeridian[1]);

		System.out.println("==========");

		UTMCoordinates utmCoordinates = new UTMCoordinates(toDatumDecimalDegreesCoordinates);
		System.out.printf("Converting decimal degree coordinate %s to UTM (%s to %s):\n     %s.\n",
				decimalDegreesCoordinates, decimalDegreesCoordinates.getDatum(), toDatum, utmCoordinates);
		System.out.printf("Converting back to decimal degree coordinate from UTM:\n     %s.\n",
				new DecimalDegreesCoordinates(utmCoordinates));

		
	}

}
