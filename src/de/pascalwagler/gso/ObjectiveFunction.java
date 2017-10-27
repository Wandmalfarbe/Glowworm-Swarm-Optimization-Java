package de.pascalwagler.gso;

import de.pascalwagler.gso.vis.Function;

public abstract class ObjectiveFunction extends Function {
	public String name;
}


/**
 * Peaks Function (J1)
 */
class FunctionJ1 extends ObjectiveFunction {

	public FunctionJ1() {
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

/**
 * Rastrigin Function (J2)
 */
class FunctionJ2 extends ObjectiveFunction {

	public FunctionJ2() {
		this.minX = -5;
		this.maxX = 5;

		this.minY = -5;
		this.maxY = 5;
	}

	public double getValueAt(double x, double y) {
		return 20+(Math.pow(x,2)-10*Math.cos(2*Math.PI*x)+Math.pow(y,2)-10*Math.cos(2*Math.PI*y));
	}
	
	@Override public String toString() {
		return "Rastrigin Function (J2)";
	}
}

/**
 * Circles Function (J3)
 */
class FunctionJ3 extends ObjectiveFunction {

	public FunctionJ3() {
		this.minX = -10;
		this.maxX = 10;

		this.minY = -10;
		this.maxY = 10;
	}

	public double getValueAt(double x, double y) {
		return Math.pow(x*x + y*y, 0.25) * ((Math.pow(Math.sin(50*Math.pow(x*x+y*y, 0.1)), 2))+0.1);
	}
	
	@Override public String toString() {
		return "Circles Function (J3)";
	}
}

/**
 * Staircase Function (J4)
 */
class FunctionJ4 extends ObjectiveFunction {

	public FunctionJ4() {
		this.minX = -3;
		this.maxX = 3;

		this.minY = -3;
		this.maxY = 3;
	}

	public double getValueAt(double x, double y) {
		return 25 - Math.floor(x) - Math.floor(y);
	}
	
	@Override public String toString() {
		return "Staircase Function (J4)";
	}
}

/**
 * Plateaus Function (J5)
 */
class FunctionJ5 extends ObjectiveFunction {

	public FunctionJ5() {
		this.minX = -(4*Math.PI);
		this.maxX = 4*Math.PI;

		this.minY = -(4*Math.PI);
		this.maxY = 4*Math.PI;
	}

	public double getValueAt(double x, double y) {
		return Math.signum(Math.cos(x)+Math.cos(y));
	}
	
	@Override public String toString() {
		return "Plateaus Function (J5)";
	}
}

/**
 * Himmelblau Function (J8)
 */
class FunctionJ8 extends ObjectiveFunction {

	public FunctionJ8() {
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

/**
 * Equal Peaks Function (J9)
 */
class FunctionJ9 extends ObjectiveFunction {

	public FunctionJ9() {
		this.minX = -5;
		this.maxX = 5;

		this.minY = -5;
		this.maxY = 5;
	}

	public double getValueAt(double x, double y) {
		return Math.pow(Math.cos(x), 2) + Math.pow(Math.sin(y), 2);
	}
	
	@Override public String toString() {
		return "Equal Peaks Function (J9)";
	}
}