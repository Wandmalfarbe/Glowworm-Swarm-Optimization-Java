package de.pascalwagler.gso.functions;

/**
 * Matyas Function
 */
public class MatyasFunction extends ObjectiveFunction {

	public MatyasFunction() {
		this.minX = -10;
		this.maxX = 10;

		this.minY = -10;
		this.maxY = 10;
	}

	public double getValueAt(double x, double y) {
		return 0.26*(x*x+y*y)-0.48*x*y;
	}
	
	@Override public String toString() {
		return "Matyas Function";
	}
}