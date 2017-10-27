package de.pascalwagler.gso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import de.pascalwagler.gso.vis.Function;

/**
 * Implements the glowworm swarm optimization (GSO) algorithm 
 * from K.N. Krishnanand and D. Ghose.
 * 
 * @author Pascal Wagler
 */
public class GSOAlgorithm {

	/**
	 * Constants
	 */
	private double rho = 0.4;
	private double gamma = 0.6;
	private double s = 0.03;
	private double beta = 0.08;
	private double n_t = 5;
	private double l_0 = 5;

	/**
	 * Parameters
	 */
	private int numGlowworms; 
	private int iterMax; 
	private double r_0;
	private double r_s;
	
	/**
	 * Live updating
	 */
	int t = 1;
	Glowworm[] glowworms;

	/**
	 * Functions & Callbacks
	 */
	private Function j;
	private IterationCallback iterationCallback;

	public GSOAlgorithm(GSOParameters params, Function j, IterationCallback iterationCallback) {
		
		this.numGlowworms = params.populationSize;
		this.iterMax = params.maxIterations;
		this.r_0 = params.initialSensorRange;
		this.r_s = params.maxSensorRange;

		this.j = j;
		this.iterationCallback = iterationCallback;

		this.glowworms = new Glowworm[numGlowworms];
	}

	/**
	 * Starts the glowworm swarm optimization and returns immediately.
	 * The result is given as a CompletableFuture which can be checked
	 * for completion with {@link java.util.concurrent.CompletableFuture#isDone()} (blocking) alternatively
	 * you can run code after completion with {@link java.util.concurrent.CompletableFuture#thenRun(Runnable)} (non blocking).
	 * 
	 * @return The CompletableFuture of this (potentially long running) operation.
	 */
	public CompletableFuture<Void> start() {

		return CompletableFuture.runAsync(() -> { internalStart(); } );
	}

	/**
	 * Performs the glowworm swarm optimization.
	 * Only used internally because it is a blocking operation.
	 */
	private void internalStart() {

		/**
		 * Initialization
		 */
		for(int g = 0; g < glowworms.length; g++) {

			double posX = randDoubleInRange(j.minX, j.maxX);
			double posY = randDoubleInRange(j.minY, j.maxY);

			glowworms[g] = new Glowworm(l_0, posX, posY, r_0);
		}

		iterationCallback.iterationFinished(this);

		for(; t<= iterMax; t++) {
			/**
			 * Luciferin Update
			 */
			for(Glowworm i: glowworms) {
				i.luciferin = (1-rho)*i.luciferin + gamma*j.getValueAt(i.posX, i.posY);
			}

			/**
			 * Movement
			 */
			for(Glowworm i: glowworms) {

				List<Glowworm> neighborhood = getNeighborhood(i, glowworms);
				List<Double> propabilities = new ArrayList<Double>(neighborhood.size());
				double liciferinSumOfNeighborhood = sumLuciferin(neighborhood);

				for(int c = 0; c < neighborhood.size(); c++) {
					Glowworm j = neighborhood.get(c);
					double propability = (j.luciferin - i.luciferin) / (liciferinSumOfNeighborhood - i.luciferin);
					propabilities.add(propability);
				}
				Glowworm j = selectGlowworm(neighborhood, propabilities);

				if(j != null) {
					/**
					 * Position
					 */
					double oldIPosX = i.posX;
					double oldIPosY = i.posY;

					double oldJPosX = j.posX;
					double oldJPosY = j.posY;

					i.posX = oldIPosX + s*((oldJPosX - oldIPosX) / (euclideanNorm(oldJPosX - oldIPosX, oldJPosY - oldIPosY)));
					i.posY = oldIPosY + s*((oldJPosY - oldIPosY) / (euclideanNorm(oldJPosX - oldIPosX, oldJPosY - oldIPosY)));
				}

				/**
				 * Range
				 */
				i.range = Math.min(r_s, Math.max(0, i.range + beta*(n_t - neighborhood.size())) );
			}
			
			iterationCallback.iterationFinished(this);
		}
	}

	/**
	 * Calculates the euclidean norm of a given vector.
	 * 
	 * @param x
	 * @param y
	 * @return The euclidean norm of the given vector
	 */
	private double euclideanNorm(double x, double y) {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	/**
	 * Selects one glowworm from a neighborhood based on selection propabilities.
	 * Internally a <i>fitness proportionate selection</i> also known as a <i>roulette wheel selection</i>
	 * is used.
	 */
	private Glowworm selectGlowworm(List<Glowworm> neighborhood, List<Double> propabilities) {

		double[] propArray = propabilities.stream().mapToDouble(i -> i).toArray();
		int index = rouletteSelect(propArray);

		if(neighborhood.size() > 0) {
			return neighborhood.get(index);
		}
		return null;
	}

	/**
	 * Implements the <i>fitness proportionate selection</i> also known as a <i>roulette wheel selection</i>
	 * 
	 * Returns the selected index based on the weights (probabilities).
	 * Source: https://en.wikipedia.org/wiki/Fitness_proportionate_selection#Java_-_linear_O.28n.29_version
	 * 
	 * @param weight The array of weights (propabilities)
	 * @return Returns the selected index based on the weights (probabilities)
	 */
	private int rouletteSelect(double[] weight) {
		
		// calculate the total weight
		double weight_sum = 0;
		for(int i=0; i<weight.length; i++) {
			weight_sum += weight[i];
		}
		
		// get a random value
		double value = randUniformPositive() * weight_sum;	
		// locate the random value based on the weights
		for(int i=0; i<weight.length; i++) {		
			value -= weight[i];		
			if(value <= 0) return i;
		}
		// only when rounding errors occur
		return weight.length - 1;
	}

	/**
	 * Returns a uniformly distributed double value between min and max.
	 * 
	 * @param min Lower bound
	 * @param max Upper bound
	 * @return A uniformly distributed double value between min and max.
	 */
	private double randDoubleInRange(double min, double max) {
		Random r = new Random(); // TODO: Make r a field and init only once
		return min + (max - min) * r.nextDouble();
	}
	
	/**
	 * Returns a uniformly distributed double value between 0.0 and 1.0
	 * 
	 * @return A uniformly distributed double value between 0.0 and 1.0
	 */
	private double randUniformPositive() {
		return new Random().nextDouble(); // TODO: Make r a field and init only once
	}
	
	/**
	 * Sums up the luciferin values of a given list of glowworm.
	 * 
	 * @param glowworms
	 * @return The sum of luciferin values
	 */
	private double sumLuciferin(List<Glowworm> glowworms) {
		double sum = 0;
		for(Glowworm i: glowworms) {
			sum = sum + i.luciferin;
		}
		return sum;
	}

	/**
	 * Returns the neighborhood of the glowworm i.
	 * 
	 * @param i	The glowworm whose neighborhood should be calculated
	 * @param glowworms The array of potential neighbors.
	 * @return A list of neighbors for the glowworm i
	 */
	private List<Glowworm> getNeighborhood(Glowworm i, Glowworm[] glowworms) {

		List<Glowworm> neighborhood = new ArrayList<Glowworm>();
		for(Glowworm j: glowworms) {
			if( (distance(i, j)) < i.range && (i.luciferin < j.luciferin) ) {
				neighborhood.add(j);
			}
		}
		return neighborhood;
	}

	/**
	 * Calculates the euclidean distance between the glowworms g1 and g2.
	 * 
	 * @param g1 The first glowworm
	 * @param g2 The second glowworm
	 * @return The euclidean distance between the glowworms g1 and g2.
	 */
	private double distance(Glowworm g1, Glowworm g2) {
		// TODO: It may be quicker to remove the square root
		return Math.sqrt(Math.pow((g2.posX - g1.posX), 2) + Math.pow((g2.posY - g1.posY), 2));
	}
}

class Glowworm {

	public double luciferin;
	public double posX;
	public double posY;
	public double range;

	public Glowworm(double luciferin, double positionX, double positionY, double range) {
		this.luciferin = luciferin;
		this.posX = positionX;
		this.posY = positionY;
		this.range = range;
	}

	@Override
	public String toString() {
		return "\"Glowworm\": {\n\t\"luciferin\": \"" + luciferin
				+ "\", \n\t\"posX\": \"" + posX + "\", \n\t\"posY\": \"" + posY
				+ "\", \n\t\"range\": \"" + range + "\"\n}";
	}
}

class GSOParameters {
	
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