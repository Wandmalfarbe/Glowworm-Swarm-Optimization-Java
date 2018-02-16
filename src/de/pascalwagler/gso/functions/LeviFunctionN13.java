package de.pascalwagler.gso.functions;

/**
 * Lévi Function N.13
 */
public class LeviFunctionN13 extends ObjectiveFunction {

	public LeviFunctionN13() {
		this.minX = -10;
		this.maxX = 10;

		this.minY = -10;
		this.maxY = 10;
	}

	public double getValueAt(double x, double y) {
		return Math.pow(Math.sin(3*Math.PI*x), 2)+Math.pow(x-1, 2)*(1+Math.pow(Math.sin(3*Math.PI*y),2))+Math.pow(y-1, 2)*(1+Math.pow(Math.sin(2*Math.PI*y),2));
	}
	
	@Override public String toString() {
		return "Lévi Function N.13";
	}
}