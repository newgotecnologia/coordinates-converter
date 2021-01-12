package br.com.skywalker.coordinateconverter;

public interface Datum {

	double getSemiAxis();

	double getFlatness();

	double getDeltaX();

	double getDeltaY();

	double getDeltaZ();

	String name();

	int getEpsgCode();

	default boolean isEqual(Datum other) {
	    return null != other &&
                this.getSemiAxis() == other.getSemiAxis() &&
                this.getFlatness() == other.getFlatness() &&
                this.getDeltaX() == other.getDeltaX() &&
                this.getDeltaY() == other.getDeltaY() &&
                this.getDeltaZ() == other.getDeltaZ();
    }
}
