package de.pascalwagler.gso.functions;

/**
 * Schaffer N.2 Function
 */
public class SchafferN2Function extends ObjectiveFunction {

	public SchafferN2Function() {
		this.minX = -10;
		this.maxX = 10;

		this.minY = -10;
		this.maxY = 10;
	}

	public double getValueAt(double x, double y) {
		return 0.5+((Math.pow(Math.sin(x*x-y*y)-0.5,2)-0.5)/Math.pow(1+0.001*(x*x+y*y),2) );
	}
	
	@Override public String toString() {
		return "Schaffer N.2 Function";
	}
}