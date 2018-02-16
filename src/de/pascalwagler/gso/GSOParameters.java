package de.pascalwagler.gso;

public class GSOParameters {
	
	public int populationSize;
	public int maxIterations;
	public double initialSensorRange;
	public double maxSensorRange;
	
	public GSOParameters(int populationSize, int maxIterations, double initialSensorRange, double maxSensorRange) {
		this.populationSize = populationSize;
		this.maxIterations = maxIterations;
		this.initialSensorRange = initialSensorRange;
		this.maxSensorRange = maxSensorRange;
	}
}