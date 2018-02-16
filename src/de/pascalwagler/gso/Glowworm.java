package de.pascalwagler.gso;

public class Glowworm {

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