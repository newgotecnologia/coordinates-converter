package br.com.skywalker.coordinateconverter;

public interface Datum {

	double getSemiAxis();

	double getFlatness();

	double getDeltaX();

	double getDeltaY();

	double getDeltaZ();

	String name();

}