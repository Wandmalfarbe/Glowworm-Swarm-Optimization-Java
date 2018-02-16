package de.pascalwagler.gso.functions;

/**
 * Circles Function (J3)
 */
public class CirclesFunction extends ObjectiveFunction {

	public CirclesFunction() {
		this.minX = -10;
		this.maxX = 10;

		this.minY = -10;
		this.maxY = 10;
	}

	public double getValueAt(double x, double y) {
		return Math.pow(x*x + y*y, 0.25) * ((Math.pow(Math.sin(50*Math.pow(x*x+y*y, 0.1)), 2))+0.1);
	}
	
	@Override public String toString() {
		return "Circles Function (J3)";
	}
}