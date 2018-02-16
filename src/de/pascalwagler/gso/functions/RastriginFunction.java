package de.pascalwagler.gso.functions;

/**
 * Rastrigin Function (J2)
 */
public class RastriginFunction extends ObjectiveFunction {

	public RastriginFunction() {
		this.minX = -5;
		this.maxX = 5;

		this.minY = -5;
		this.maxY = 5;
	}

	public double getValueAt(double x, double y) {
		return 20+(Math.pow(x,2)-10*Math.cos(2*Math.PI*x)+Math.pow(y,2)-10*Math.cos(2*Math.PI*y));
	}
	
	@Override public String toString() {
		return "Rastrigin Function (J2)";
	}
}