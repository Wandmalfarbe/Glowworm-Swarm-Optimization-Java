package de.pascalwagler.gso.functions;

/**
 * Eggholder Function
 */
public class EggholderFunction extends ObjectiveFunction {

	public EggholderFunction() {
		this.minX = -512;
		this.maxX = 512;

		this.minY = -512;
		this.maxY = 512;
	}

	public double getValueAt(double x, double y) {
		return -(y+47)*Math.sin(Math.sqrt(Math.abs((x/2)+(y+47))))-x*Math.sin(Math.sqrt(Math.abs(x-(y+47))));
	}
	
	@Override public String toString() {
		return "Eggholder Function";
	}
}