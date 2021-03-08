package br.com.skywalker.coordinateconverter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CoordinatesConverterTest {

	@Test
	public void testDDToDMS() {
		DecimalDegreesCoordinates coordinates = new DecimalDegreesCoordinates(-46.1795700000, -23.5151100000,
				Datums.SAD69);
		assertEquals("46° 10' 46.4520\" S 23° 30' 54.3960\" W",
				CoordinatesConverter.toDMS(coordinates).toString().replace(",", "."));
	}

	@Test
	public void testDDToDMS1() {
		DecimalDegreesCoordinates coordinates = new DecimalDegreesCoordinates(-7, -37, Datums.SAD69);
		DegreesMinutesSecondsCoordinates degreesMinutesSecondsCoordinates = CoordinatesConverter.toDMS(coordinates);
		assertEquals("7° 0' 0.0000\" S 37° 0' 0.0000\" W",
				degreesMinutesSecondsCoordinates.toString().replace(",", "."));
		System.out.println(CoordinatesConverter.toDD(degreesMinutesSecondsCoordinates).toString());
	}

	@Test
	public void testDMSToDD() {
		DegreesMinutesSecondsCoordinates degreesMinutesSecondsCoordinates = new DegreesMinutesSecondsCoordinates(
				Location.SOUTH, 7, 0, 0, Location.WEST, 37, 0, 0, Datums.WGS84);
		System.out.println(CoordinatesConverter.toDD(degreesMinutesSecondsCoordinates).toString());
		assertEquals("-7.0000000000 -37.0000000000",
				CoordinatesConverter.toDD(degreesMinutesSecondsCoordinates).toString().replace(",", "."));
	}

	@Test
	public void testDDToUTM() {
		DecimalDegreesCoordinates coordinates = new DecimalDegreesCoordinates(-46.1795700000, -23.5151100000,
				Datums.SAD69);
		UTMCoordinates utmCoordinates = CoordinatesConverter.toUTM(coordinates);
		assertEquals(305881.9593, utmCoordinates.getX(), 0.0001);
		assertEquals(4882907.8404, utmCoordinates.getY(), 0.0001);
	}

	@Test
	public void testDDToUTMCorregoAlegre() {
		DecimalDegreesCoordinates coordinates = new DecimalDegreesCoordinates(-46.1795700000, -23.5151100000,
				Datums.SAD69);
		UTMCoordinates utmCoordinates = CoordinatesConverter.toUTM(coordinates, Datums.CorregoAlegre);
		assertEquals(305777.4495, utmCoordinates.getX(), 0.01);
		assertEquals(4882850.0889, utmCoordinates.getY(), 0.01);
	}

	@Test
	public void testDDToUTMWGS84() {
		DecimalDegreesCoordinates coordinates = new DecimalDegreesCoordinates(-46.1795700000, -23.5151100000,
				Datums.SAD69);
		UTMCoordinates utmCoordinates = CoordinatesConverter.toUTM(coordinates, Datums.WGS84);
		assertEquals(305861.6300, utmCoordinates.getX(), 0.01);
		assertEquals(4882853.4451, utmCoordinates.getY(), 0.01);
	}

	@Test
	public void testDDToUTMSIRGAS2000() {
		DecimalDegreesCoordinates coordinates = new DecimalDegreesCoordinates(-46.1795700000, -23.5151100000,
				Datums.SAD69);
		UTMCoordinates utmCoordinates = CoordinatesConverter.toUTM(coordinates, Datums.SIRGAS2000);
		assertEquals(305861.6300, utmCoordinates.getX(), 0.01);
		assertEquals(4882853.4451, utmCoordinates.getY(), 0.01);
	}
	

	@Test
	public void testUTMToDMS() {
		UTMCoordinates utmCoordinates = CoordinatesConverter.toUTM(new DecimalDegreesCoordinates(-7, -37, Datums.WGS84));
		DegreesMinutesSecondsCoordinates degreesMinutesSecondsCoordinates = CoordinatesConverter.toDegreesMinutesSeconds(utmCoordinates);
		System.out.println(degreesMinutesSecondsCoordinates.toString());
	}

}
