
package de.pascalwagler.gso.vis;

public abstract class Dataset {

	protected int    xSize;
	protected int    ySize;
	protected double xMin;
	protected double yMin;
	protected double dx;
	protected double dy;
	protected double fMin;
	protected double fMax;
	protected double[][] data;
	
	public int getXSize() {
		return xSize;
	}

	public int getYSize() {
		return ySize;
	}

	public double getXMin() {
		return xMin;
	}

	public double getYMin() {
		return yMin;
	}

	public double getDx() {
		return dx;
	}

	public double getDy() {
		return dy;
	}

	public double getFMin() {
		return fMin;
	}

	public double getFMax() {
		return fMax;
	}
	
	/**
	 * Interpolate attribute value f(x,y)
	 */
	public double getInterpolatedData(double x, double y) {
		
		// (x,y) coordinates of value to be interpolated
		// indices of closest sample points in x direction, x0 <= x <= x1
		int i0 = (int)((x - xMin) / dx);
		i0 = Math.min(Math.max(i0, 0), xSize - 1);	// ensure 0 <= i0 < xSize 
		int i1 = Math.min(i0 + 1, xSize - 1);
		
		// indices of closest sample points in y direction, y0 <= y <= y1
		int j0 = (int)((y - yMin) / dy);
		j0 = Math.min(Math.max(j0, 0), ySize - 1);	// ensure 0 <= j0 < ySize 
		int j1 = Math.min(j0 + 1, ySize - 1);
		
		// bilinear interpolation of f(x,y)
		double r = (x - (xMin + i0 * dx)) / dx; 
		double s = (y - (yMin + j0 * dy)) / dy; 
		return (1 - r) * (1 - s) * data[i0][j0] + r * (1 - s) * data[i1][j0] +
				r * s * data[i1][j1] + (1 - r) * s * data[i0][j1];
	}

	/**
	 * Find minimum/maximum attribute values, to be called by constructor
	 */
	protected void findDataMinMax() {
		if (xSize > 0 && ySize > 0) {
			fMin = fMax = data[0][0];
			for (int i = 0; i < xSize; i++) {
				for (int j = 0; j < ySize; j++) {
					fMin = Math.min(fMin, data[i][j]);
					fMax = Math.max(fMax, data[i][j]);
				}
			}
		}
	}

}
