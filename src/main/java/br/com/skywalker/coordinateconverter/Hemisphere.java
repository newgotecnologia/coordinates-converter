package br.com.skywalker.coordinateconverter;

public enum Hemisphere {

	NORTH("N"), SOUTH("S");

	private String acronym;

	private Hemisphere(String hemisphere) {
		this.acronym = hemisphere;
	}

	public String getAcronym() {
		return acronym;
	}

}
