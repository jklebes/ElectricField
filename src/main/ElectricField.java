package main;

public class ElectricField extends Field{
	private double[][][] chargeField;

	/**
	 * class with methods for electric field specific potential and field vector calculations
	 * @param xlength
	 * @param ylength
	 * @param zlength
	 */
	public ElectricField(int xlength, int ylength, int zlength) {
		super(xlength, ylength, zlength);
		this.chargeField = new double[xlength][ylength][zlength];
		setAllCharge(0);
	}

	/**
	 * resets all charges
	 * @param value
	 */
	private void setAllCharge(double value) {
		for (int i =0; i < xlength; i ++){
			for (int j =0; j < ylength; j ++){
				for (int k =0; k < zlength; k ++){
					setCharge(i,j,k,value);
				}
			}
		}
	}

	/**
	 * sets point charge at location
	 * @param i
	 * @param j
	 * @param k
	 * @param value
	 */
	public void setCharge(int i, int j, int k, double value) {
		this.chargeField[i][j][k] = value;
	}
	
	/**
	 * performs step in solving for potential, referencing past copy
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
							getValue(copy,i,j,k-1) + getValue(copy,i,j,k+1)+
							chargeField[i][j][k]);
					newvalue = oldvalue + (newvalue-oldvalue)*overrelaxation;
					setPotential(i,j,k,newvalue);
				}
			}
		}
	}

	/**
	 * sets and returns 3d array of field vectors from potential
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
					xcomponent = (getValue(potentialField,i-1,j,k)-getValue(potentialField,i+1,j,k))/(2*dx);
					ycomponent = (getValue(potentialField,i,j-1,k)-getValue(potentialField,i,j+1,k))/(2*dy);
					zcomponent = (getValue(potentialField,i,j,k-1)-getValue(potentialField,i,j,k+1))/(2*dz);
					electricfield[i][j][k][0]=-xcomponent;
					electricfield[i][j][k][1]=-ycomponent;
					electricfield[i][j][k][2]=-zcomponent;

				}
			}
		}
		return electricfield;
	}

	/**
	 * performs step in solving for potential, referencing self
	 */
	@Override
	void GaussSeidelUpdate(double overrelaxation){
		for (int i =0; i < xlength; i ++){
			for (int j =0; j < ylength; j ++){
				for (int k =0; k < zlength; k ++){
					double oldvalue = getPotential(i,j,k);
					double newvalue = ((double)1/(double)6)*(
							getPotential(i-1,j,k)+ getPotential(i+1,j,k)+
							getPotential(i,j-1,k)+ getPotential(i,j+1,k)+
							getPotential(i,j,k-1) + getPotential(i,j,k+1)+
							chargeField[i][j][k]);
					newvalue = oldvalue + (newvalue-oldvalue)*overrelaxation;
					setPotential(i,j,k,newvalue);
				}
			}
		}
	}
	
	public void setxlength(int x) {
		super.setxlength(x);
		this.chargeField = new double[xlength][ylength][zlength];
		setAllCharge(0);
	}
	public void setylength(int x) {
		super.setylength(x);
		this.chargeField = new double[xlength][ylength][zlength];
		setAllCharge(0);
	}
	public void setzlength(int x) {
		super.setylength(x);
		this.chargeField = new double[xlength][ylength][zlength];
		setAllCharge(0);
	}
}