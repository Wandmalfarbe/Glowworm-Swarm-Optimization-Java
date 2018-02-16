package de.pascalwagler.gso.functions;

/**
 * Peaks Function (J1)
 */
public class PeaksFunction extends ObjectiveFunction {

	public PeaksFunction() {
		this.minX = -3;
		this.maxX = 3;

		this.minY = -3;
		this.maxY = 3;
	}

	public double getValueAt(double x, double y) {
		return 3*Math.pow(1-x,2) * Math.exp(-(Math.pow(x,2)+Math.pow(y+1,2)))-10*((x/5.0)-Math.pow(x,3)-Math.pow(y,5))*Math.exp(-(Math.pow(x,2)+Math.pow(y,2)))-(1/3.0)*Math.exp(-(Math.pow((x+1),2)+Math.pow(y,2)));
	}
	
	@Override public String toString() {
		return "Peaks Function (J1)";
	}
}