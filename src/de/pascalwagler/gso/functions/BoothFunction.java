package de.pascalwagler.gso.functions;

/**
 * Booth Function
 */
public class BoothFunction extends ObjectiveFunction {

	public BoothFunction() {
		this.minX = -10;
		this.maxX = 10;

		this.minY = -10;
		this.maxY = 10;
	}

	public double getValueAt(double x, double y) {
		return Math.pow(x+2*y-7, 2) + Math.pow(2*x+y-5, 2);
	}
	
	@Override public String toString() {
		return "Booth Function";
	}
}