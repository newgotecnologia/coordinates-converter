package br.com.skywalker.coordinateconverter;

public enum Location {

	NORTH("N"), EAST("E"), SOUTH("S"), WEST("W");

	private String acronym;

	private Location(String location) {
		this.acronym = location;
	}

	public String getAcronym() {
		return acronym;
	}

}
