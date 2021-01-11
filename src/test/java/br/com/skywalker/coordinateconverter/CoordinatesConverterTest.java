package br.com.skywalker.coordinateconverter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CoordinatesConverterTest {

	@Test
	public void testDDToDMS() {
		DecimalDegreesCoordinates coordinates = new DecimalDegreesCoordinates(-46.1795700000, -23.5151100000,
				Datums.SAD69);
		assertEquals("46° 10' 46,4520\" S 23° 30' 54,3960\" W",
				CoordinatesConverter.toDMS(coordinates).toString());
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

}
