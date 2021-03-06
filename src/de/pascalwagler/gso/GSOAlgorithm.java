package de.pascalwagler.gso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.pascalwagler.gso.vis.Function;

/**
 * Implements the glowworm swarm optimization (GSO) algorithm 
 * from Krishnanand N. Kaipa and Debasish Ghose.
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
	public Glowworm[] glowworms;

	/**
	 * Functions & Callbacks
	 */
	private Function j;
	private IterationCallback iterationCallback;

	Random rand = new Random();
	
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
	 * Initialization
	 */
	public void initialize() {
		
		for(int g = 0; g < glowworms.length; g++) {

			double posX = randDoubleInRange(j.minX, j.maxX);
			double posY = randDoubleInRange(j.minY, j.maxY);

			glowworms[g] = new Glowworm(l_0, posX, posY, r_0);
		}
	}

	/**
	 * Performs one discrete time step of the glowworm swarm optimization.
	 */
	public void step() {

		//for(; t<= iterMax; t++) {
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
				List<Double> probabilities = new ArrayList<Double>(neighborhood.size());
				double liciferinSumOfNeighborhood = sumLuciferin(neighborhood);

				for(int c = 0; c < neighborhood.size(); c++) {
					Glowworm j = neighborhood.get(c);
					double probability = (j.luciferin - i.luciferin) / (liciferinSumOfNeighborhood - i.luciferin);
					probabilities.add(probability);
				}
				Glowworm j = selectGlowworm(neighborhood, probabilities);

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
		//}
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
	 * Selects one glowworm from a neighborhood based on selection probabilities.
	 * Internally a <i>fitness proportionate selection</i> also known as a <i>roulette wheel selection</i>
	 * is used.
	 */
	private Glowworm selectGlowworm(List<Glowworm> neighborhood, List<Double> probabilities) {

		int index = rouletteSelect(probabilities);

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
	 * @param weight The list of weights (probabilities)
	 * @return Returns the selected index based on the weights (probabilities)
	 */
	private int rouletteSelect(List<Double> weight) {
		
		// calculate the total weight
		double weight_sum = 0;
		for(int i=0; i<weight.size(); i++) {
			weight_sum += weight.get(i);
		}
		
		// get a random value
		double value = randUniformPositive() * weight_sum;	
		// locate the random value based on the weights
		for(int i=0; i<weight.size(); i++) {		
			value -= weight.get(i);		
			if(value <= 0) return i;
		}
		// only when rounding errors occur
		return weight.size() - 1;
	}

	/**
	 * Returns a uniformly distributed double value between min and max.
	 * 
	 * @param min Lower bound
	 * @param max Upper bound
	 * @return A uniformly distributed double value between min and max.
	 */
	private double randDoubleInRange(double min, double max) {
		return min + (max - min) * rand.nextDouble();
	}
	
	/**
	 * Returns a uniformly distributed double value between 0.0 and 1.0
	 * 
	 * @return A uniformly distributed double value between 0.0 and 1.0
	 */
	private double randUniformPositive() {
		return rand.nextDouble();
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