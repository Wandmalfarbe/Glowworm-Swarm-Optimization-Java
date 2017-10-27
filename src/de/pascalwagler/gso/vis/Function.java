package de.pascalwagler.gso.vis;

public abstract class Function {

	public double minX = -1;
	public double maxX = 1;

	public double minY = -1;
	public double maxY = 1;

	public double getWidth() {
		return maxX - minX;
	}

	public double getHeight() {
		return maxY - minY;
	}

	/**
	 * Compute the mathematical function value
	 * at the position x and y.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public abstract double getValueAt(double x, double y);
}