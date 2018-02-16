package de.pascalwagler.gso.functions;

/**
 * Equal Peaks Function (J9)
 */
public class EqualPeaksFunction extends ObjectiveFunction {

	public EqualPeaksFunction() {
		this.minX = -5;
		this.maxX = 5;

		this.minY = -5;
		this.maxY = 5;
	}

	public double getValueAt(double x, double y) {
		return Math.pow(Math.cos(x), 2) + Math.pow(Math.sin(y), 2);
	}
	
	@Override public String toString() {
		return "Equal Peaks Function (J9)";
	}
}