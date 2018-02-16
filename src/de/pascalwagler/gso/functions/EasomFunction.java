package de.pascalwagler.gso.functions;

/**
 * Easom Function
 */
public class EasomFunction extends ObjectiveFunction {

	public EasomFunction() {
		this.minX = -50;
		this.maxX = 50;

		this.minY = -50;
		this.maxY = 50;
	}

	public double getValueAt(double x, double y) {
		return -Math.cos(x)*Math.cos(y)*Math.exp(-(Math.pow(x-Math.PI, 2)+Math.pow(y-Math.PI, 2)));
	}
	
	@Override public String toString() {
		return "Easom Function";
	}
}