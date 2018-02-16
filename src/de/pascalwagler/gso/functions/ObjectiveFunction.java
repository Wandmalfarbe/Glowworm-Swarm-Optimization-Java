package de.pascalwagler.gso.functions;

import de.pascalwagler.gso.vis.Function;

public abstract class ObjectiveFunction extends Function {
	public String name;
	
	public double getValueAt(double[] position) {
		return getValueAt(position[0], position[1]);
	}
}