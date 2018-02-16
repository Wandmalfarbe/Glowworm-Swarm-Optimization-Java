package de.pascalwagler.gso.functions;

/**
 * Floored distance to x = 0 and y = 0
 */
public class DistanceToPoint extends ObjectiveFunction {

	private int pointX = 0;
	private int pointY = 0;
	
	public DistanceToPoint() {
		this.minX = -5;
		this.maxX = 5;

		this.minY = -5;
		this.maxY = 5;
	}

	public double getValueAt(double x, double y) {
		
		double sumOfSquares = 0;
		sumOfSquares += (pointX - Math.floor(x)) * (pointX - Math.floor(x));
		sumOfSquares += (pointY - Math.floor(y)) * (pointY - Math.floor(y));
		
		return -Math.sqrt(sumOfSquares);
	}
	
	@Override public String toString() {
		return "Floored distance to point (0,-0)";
	}
}