package de.pascalwagler.gso.functions;

/**
 * Hölder Table Function
 */
public class HoelderTableFunction extends ObjectiveFunction {

	public HoelderTableFunction() {
		this.minX = -10;
		this.maxX = 10;

		this.minY = -10;
		this.maxY = 10;
	}

	public double getValueAt(double x, double y) {
		return -Math.abs(Math.sin(x)*Math.cos(y)*Math.exp(Math.abs(1-(Math.sqrt(x*x+y*y)/Math.PI))));
	}
	
	@Override public String toString() {
		return "Hölder Table Function";
	}
}