package br.com.skywalker.coordinateconverter;

public class Coordinates {

	protected Datum datum;

	public Coordinates(Datum datum) {
		this.datum = datum;
	}

	public Datum getDatum() {
		return datum;
	}

}