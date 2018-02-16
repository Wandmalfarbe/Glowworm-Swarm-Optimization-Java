package de.pascalwagler.gso.functions;

/**
 * Beale Function
 */
public class BealeFunction extends ObjectiveFunction {

	public BealeFunction() {
		this.minX = -4.5;
		this.maxX = 4.5;

		this.minY = -4.5;
		this.maxY = 4.5;
	}

	public double getValueAt(double x, double y) {
		return Math.pow(1.5-x+x*y, 2)+Math.pow(2.25-x+Math.pow(x*y, 2), 2)+Math.pow(2.625-x+Math.pow(x*y, 3), 2);
	}
	
	@Override public String toString() {
		return "Beale Function";
	}
}