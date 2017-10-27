
package de.pascalwagler.gso.vis;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ColorMapper {

	private Dataset	dataset;
	private ColorMap colorTable;
	
	private int width;
	private int height;

	public ColorMapper(Function func, int width, int height) {
		
		this.width = width;
		this.height = height;
		
		dataset = new MathFuncDataset(func, 200, 200, func.minX, func.maxX, func.minY, func.maxY);

		colorTable = ColorMap.PARULA;
	}

	public BufferedImage getImage() {

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		int xSize = dataset.getXSize();
		int ySize = dataset.getYSize();
		
		double xMin = dataset.getXMin();
		double yMin = dataset.getYMin();
		
		double dx = dataset.getDx();
		double dy = dataset.getDy();
		
		double dxImage = dx * (xSize - 1) / (double) width;
		double dyImage = dy * (ySize - 1) / (double) height;

		for (int i = 0; i < width; i++) {
			double x = xMin + i * dxImage;
			for (int j = 0; j < height; j++) {

				// mirror data in y direction (image coordinates, origin in upper left corner)
				double y = yMin + j/*(height - 1 - j)*/ * dyImage;
				
				double dataValue = dataset.getInterpolatedData(x, y);
				double dataValNormalized = (dataValue - dataset.fMin)/(dataset.fMax-dataset.fMin);
				Color color = colorTable.get(dataValNormalized); 
				image.setRGB(i, j, color.getRGB());
			}
		}
		return image;
	}

}
