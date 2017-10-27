package de.pascalwagler.gso.vis;


public class MathFuncDataset extends Dataset {
	
	Function function;
	
	public MathFuncDataset(Function fun, int xSize, int ySize, 
			double xMin, double xMax, double yMin, double yMax) {
		
		this.function = fun;
		this.xSize = xSize;
		this.ySize = ySize;
		this.xMin = xMin;
		this.yMin = yMin;
		dx = (xMax - xMin) / (double)(xSize - 1);
		dy = (yMax - yMin) / (double)(ySize - 1);
		data = new double[xSize][ySize];
		
		fillDatasetWithFunctionValues();
		findDataMinMax();
	}
	
	public void fillDatasetWithFunctionValues() {
		for (int i = 0; i < xSize; i++) {
			double x = xMin + i * dx;
			for (int j = 0; j < ySize; j++) {
				double y = yMin + j * dy;				
				data[i][j] = function.getValueAt(x, y);
			}
		}
	}
}
