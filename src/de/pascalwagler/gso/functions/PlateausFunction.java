package de.pascalwagler.gso.functions;

/**
 * Plateaus Function (J5)
 */
public class PlateausFunction extends ObjectiveFunction {

	public PlateausFunction() {
		this.minX = -(4*Math.PI);
		this.maxX = 4*Math.PI;

		this.minY = -(4*Math.PI);
		this.maxY = 4*Math.PI;
	}

	public double getValueAt(double x, double y) {
		return Math.signum(Math.cos(x)+Math.cos(y));
	}
	
	@Override public String toString() {
		return "Plateaus Function (J5)";
	}
}