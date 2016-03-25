package main;

/**
 * class for generic wire/charge distribution, potential field, vector field
 * @author s1203908
 *
 */
public class Field {
public double[][][] potentialField;
double[][][] convcopy;
protected int xlength;
protected int ylength;
protected int zlength;
protected int dx=1;
protected int dy = 1;
protected int dz=1;

/**
 * constructor sets up arrays of correct sizes
 * @param xlength
 * @param ylength
 * @param zlength
 */
public Field(int xlength, int ylength, int zlength){
	this.xlength= xlength;
	this.ylength= ylength;
	this.zlength= zlength;
	this.potentialField = new double[xlength][ylength][zlength];
	this.convcopy = new double[xlength][ylength][zlength];
	setAllPotential(0);
	fillconvcopy(1000);
}

/**
 * sets potential field to value
 * @param value
 */
private void setAllPotential(double value) {
	for (int i =0; i < xlength; i ++){
		for (int j =0; j < ylength; j ++){
			for (int k =0; k < zlength; k ++){
				setPotential(i,j,k,value);
			}
		}
	}
}

/**
 * initiates array that holds copy of past state
 * @param value
 */
void fillconvcopy(double value) {
	for (int i =0; i < xlength; i ++){
		for (int j =0; j < ylength; j ++){
			for (int k =0; k < zlength; k ++){
				convcopy[i][j][k]=value;
			}
		}
	}
}

/**
 * fills past copy to initial value for first comparison
 * @param field
 */
private void fillconvcopy(double[][][] field) {
	for (int i =0; i < xlength; i ++){
		for (int j =0; j < ylength; j ++){
			for (int k =0; k < zlength; k ++){
				convcopy[i][j][k]=field[i][j][k];
			}
		}
	}
}

/**
 * gets value from any 3D array,
 * returning 0 for values on boundary at -1, length
 * @param field
 * @param i
 * @param j
 * @param k
 * @return
 */
public double getValue(double[][][] field, int i, int j, int k) {
	//boundary condition
	if (i==-1 || i==xlength ||j==-1 || j==ylength ||k==-1 || k==zlength ){return 0;}
	else{return field[i][j][k];}
}

/**
 * gets value from potential array
 * returning 0 for values on boundary at -1, length
 * @param i
 * @param j
 * @param k
 * @return
 */
public double getPotential(int i, int j, int k) {
	//boundary condition
	if (i==-1 || i==xlength ||j==-1 || j==ylength ||k==-1 || k==zlength ){return 0;}
	else{return this.potentialField[i][j][k];}
}

/**
 * sets single value in potential array
 * @param i
 * @param j
 * @param k
 * @param value
 */
protected void setPotential(int i, int j, int k, double value) {
	this.potentialField[i][j][k] = value;
}

/**
 * checks if converged by comparing present and last check's values of potential field
 * sampling at 3D grid of points at intervals specified, use 1 to sample all points
 * @param interval
 * @return
 */
public boolean checkConverged(int interval, double threshold) {
	boolean converged = true;
	for (int i =0; i < xlength/interval; i ++){
		for (int j =0; j < ylength/interval; j ++){
			for (int k =0; k < zlength/interval; k ++){
				//System.out.println(getValue(this.potentialField,i*interval,j*interval,k*interval)+"-"+ getValue(convcopy,i*interval,j*interval,k*interval));
				if (Math.abs(getValue(this.potentialField,i*interval,j*interval,k*interval)- getValue(convcopy,i*interval,j*interval,k*interval)) > threshold){
					converged = converged && false;
				}
				else {converged = converged && true;}
			}
		}
	}
	fillconvcopy(this.potentialField);
	return converged;
}

/**
 * gets x-direction size of space
 * @return xlength
 */
public int getxlength() {
	return xlength;
}

/**
 * gets y-direction size of space
 * @return ylength
 */
public int getylength() {
	return ylength;
}

/**
 * gets z-direction size of space
 * @return zlength
 */
public int getzlength() {
	return zlength;
}

/**
 * gets field vector at location
 * @param field
 * @param i
 * @param j
 * @param k
 * @return
 */
public double[] getFieldVector(double[][][][] field, int i,
		int j, int k) {
	if (k==50){
		System.out.println("getting vector with "+ field[i][j][k][0]+" "+field[i][j][k][0]);
	}
	return field[i][j][k];
}

/**
 * gets distance from center of a point
 * @param x
 * @param y
 * @param z
 * @return
 */
public double getDistancefromCenter(int x, int y, int z) {
	return Math.sqrt((xlength-x)*(xlength-x)+(ylength-y)*(ylength-y)+(zlength-z)*(zlength-z));
}

/**
 * method to create field vectors
 * blank, override in specific fields
 * @return
 */
public double[][][][] getFieldVectors() {
	return null;
}

/**
 * Jacobi update
 * blank, override in specific fields
 * @return
 */
public void JacobiUpdate(double overrelaxation) {
	// TODO Auto-generated method stub
	
}

void GaussSeidelUpdate(double overrelaxation) {
	// TODO Auto-generated method stub
	
}

public void setCharge(int x, int y, int z, double value) {
	// TODO Auto-generated method stub
	
}

public void setWire(int x, int y, double value) {
	// TODO Auto-generated method stub
	
}

public void setxlength(int x) {
	this.xlength = x;
	this.potentialField = new double[xlength][ylength][zlength];
	this.convcopy = new double[xlength][ylength][zlength];
	setAllPotential(0);
	fillconvcopy(1000);
}
public void setylength(int x) {
	this.ylength = x;
	this.potentialField = new double[xlength][ylength][zlength];
	this.convcopy = new double[xlength][ylength][zlength];
	setAllPotential(0);
	fillconvcopy(1000);
}
public void setzlength(int x) {
	this.zlength = x;
	this.potentialField = new double[xlength][ylength][zlength];
	this.convcopy = new double[xlength][ylength][zlength];
	setAllPotential(0);
	fillconvcopy(1000);
}


}
