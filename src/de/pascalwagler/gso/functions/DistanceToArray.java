package de.pascalwagler.gso.functions;

/**
 * DistanceToArray
 */
public class DistanceToArray extends ObjectiveFunction {

	private double[] arr = new double[]{0.0, 0.0, 2.0, 3.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 4.0, 2.0, 2.0, 4.0};

	public DistanceToArray() {
		this.minX = -5;
		this.maxX = 5;

		this.minY = -5;
		this.maxY = 5;
	}

	public double getValueAt(double[] position) {

		double sumOfSquares = 0;
		for(int i = 0; i < arr.length; i++) {
			sumOfSquares += (arr[i] - position[i]) * (arr[i] - position[i]);
		}

		return Math.sqrt(sumOfSquares);
	}

	@Override 
	public String toString() {
		return "Distance To Array";
	}

	@Override
	public double getValueAt(double x, double y) {
		return 0;
	}
}