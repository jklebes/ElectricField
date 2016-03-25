package main;

/**
 * call for magnetic field specific potential and vector field calculations
 * only handles magnetic field z-direction current-carrying wires
 * @author s1203908
 *
 */
public class MagneticField extends Field{
private double[][] wires;
	
	/**
	 * 
	 * @param xlength
	 * @param ylength
	 * @param zlength
	 */
	public MagneticField(int xlength, int ylength, int zlength) {
		super(xlength, ylength, zlength);
		this.wires = new double[xlength][ylength];
		setAllWires(0);
	}

	/**
	 * sets (z direction only) wire at x,y
	 * @param value
	 */
	private void setAllWires(double value) {
		for (int i =0; i < xlength; i ++){
			for (int j =0; j < ylength; j ++){
					setWire(i,j,value);
			}
		}
	}
	
	/**
	 * sets z-direction wire with given current at x,y location
	 * @param i
	 * @param j
	 * @param value
	 */
	public void setWire(int i, int j, double value) {
		this.wires[i][j] = value;
	}
	
	/**
	 * updates magnetic vector potential (z component) from distribution of wires in x,y planes
	 * slower version makes past copy
	 * @param overrelaxation 
	 */
	@Override
	public void JacobiUpdate(double overrelaxation){
		double[][][] copy = this.potentialField;
		for (int i =0; i < xlength; i ++){
			for (int j =0; j < ylength; j ++){
				for (int k =0; k < zlength; k ++){
					double oldvalue = getValue(copy,i,j,k);
					double newvalue = ((double)1/(double)6)*(
							getValue(copy,i-1,j,k)+ getValue(copy,i+1,j,k)+
							getValue(copy,i,j-1,k)+ getValue(copy,i,j+1,k)+
							//getValue(copy,i,j,k-1) + getValue(copy,i,j,k+1)+
							wires[i][j]);
					setPotential(i,j,k,newvalue);
					newvalue = oldvalue + (newvalue-oldvalue)*overrelaxation;
				}
			}
		}
	}

	/**
	 * sets and returns magnetic field vectors
	 * @return
	 */
	@Override
	public double[][][][] getFieldVectors(){
		double[][][][] electricfield = new double[xlength][ylength][zlength][3];
		double xcomponent;
		double ycomponent;
		double zcomponent;
		for (int i =0; i < xlength; i ++){
			for (int j =0; j < ylength; j ++){
				for (int k =0; k < zlength; k ++){
					ycomponent = -(getValue(potentialField,i-1,j,k)-getValue(potentialField,i+1,j,k))/(2*dx);
					xcomponent = (getValue(potentialField,i,j-1,k)-getValue(potentialField,i,j+1,k))/(2*dy);
					zcomponent = 0;
					electricfield[i][j][k][0]=xcomponent;
					electricfield[i][j][k][1]=ycomponent;
					electricfield[i][j][k][2]=zcomponent;

				}
			}
		}
		return electricfield;
	}

	/**
	 * updates magnetic vector potential (z component) from distribution of wires in x,y planes
	 * fast version referencing self
	 */
void GaussSeidelUpdate(double overrelaxation){
	for (int i =0; i < xlength; i ++){
		for (int j =0; j < ylength; j ++){
			for (int k =0; k < zlength; k ++){
				double oldvalue = getPotential(i,j,k);
				double newvalue = ((double)1/(double)6)*(
						getPotential(i-1,j,k)+ getPotential(i+1,j,k)+
						getPotential(i,j-1,k)+ getPotential(i,j+1,k)+
						//getPotential(i,j,k-1) + getPotential(i,j,k+1)+
						wires[i][j]);
				newvalue = oldvalue + (newvalue-oldvalue)*overrelaxation;
				setPotential(i,j,k,newvalue);
				}
			}
	}
}

public void setxlength(int x) {
	super.setxlength(x);
	this.wires = new double[xlength][ylength];
	setAllWires(0);
}
public void setylength(int x) {
	super.setylength(x);
	this.wires = new double[xlength][ylength];
	setAllWires(0);
}
public void setzlength(int x) {
	super.setylength(x);
	this.wires = new double[xlength][ylength];
	setAllWires(0);
}
}
