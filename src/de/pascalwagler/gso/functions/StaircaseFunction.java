package de.pascalwagler.gso.functions;

/**
 * Staircase Function (J4)
 */
public class StaircaseFunction extends ObjectiveFunction {

	public StaircaseFunction() {
		this.minX = -3;
		this.maxX = 3;

		this.minY = -3;
		this.maxY = 3;
	}

	public double getValueAt(double x, double y) {
		return 25 - Math.floor(x) - Math.floor(y);
	}
	
	@Override public String toString() {
		return "Staircase Function (J4)";
	}
}