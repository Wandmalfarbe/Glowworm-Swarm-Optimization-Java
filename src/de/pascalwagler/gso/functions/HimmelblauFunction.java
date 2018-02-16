package de.pascalwagler.gso.functions;

/**
 * Himmelblau Function (J8)
 */
public class HimmelblauFunction extends ObjectiveFunction {

	public HimmelblauFunction() {
		this.minX = -5;
		this.maxX = 5;

		this.minY = -5;
		this.maxY = 5;
	}

	public double getValueAt(double x, double y) {
		return 200-Math.pow(Math.pow(x,2)+Math.pow(y,2)-11,2)-Math.pow(x+Math.pow(y,2)-7,2);
	}
	
	@Override public String toString() {
		return "Himmelblau Function (J8)";
	}
}