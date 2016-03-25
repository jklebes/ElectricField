package main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import GUI.FieldFrame;


public class RunField {
	static String filename = "infile.txt";
	static double charge=500000;

	public static void main(String[] args) throws InterruptedException, IOException {
		int interval = 5;
		double threshold =.0000000001;
		int stepinterval=10;
		int type = 1;
		FileReader fr = new FileReader(filename);
		BufferedReader br = new BufferedReader(fr);
		String line;
		//int zlength = 60;
		Field f;
		
		if(type==1){
			f = new ElectricField(60,60,60);}
		else{
			f = new MagneticField(60,60,60);}
		
		while ((line = br.readLine()) != null){
			String[] lineelements = line.split(" ");
			String name = lineelements[0];
			String[] values = new String[4];
			for (int i=1; i < lineelements.length; i++){
			values[i-1] = lineelements[i];}
			if (name.equals("type")){
				type = Integer.parseInt(values[0]);
				if(type==1){
					f = new ElectricField(60,60,60);}
				else{
					f = new MagneticField(60,60,60);}
			}
			else if (name.equals("xlength")){
				f.setxlength(Integer.parseInt(values[0]));
				System.out.println("xlength set to" + values[0]);
			}
			else if (name.equals("ylength")){
				f.setylength(Integer.parseInt(values[0]));
				System.out.println("ylength set to" + values[0]);
			}
			else if (name.equals("zlength")){
				f.setzlength(Integer.parseInt(values[0]));
				System.out.println("zlength set to" + values[0]);
			}
			else if (name.equals("threshold")){
				threshold=Double.valueOf(values[0]);
			}
			else if (name.equals("interval")){
				interval=Integer.parseInt(values[0]);
			}
			else if (name.equals("stepinterval")){
				stepinterval=Integer.parseInt(values[0]);
			}
			else if (name.equals("charge")){
				if (type==1){
				f.setCharge(Integer.parseInt(values[0]),Integer.parseInt(values[1]),Integer.parseInt(values[2]),
						Double.parseDouble(values[3]));
				System.out.println("setting charge "+ values[0]+" "+ values[1] +" "+ values[2] + " "+values[3]);
				}
			}
			else if (name.equals("wire")){
				if (type==2){
				f.setWire(Integer.parseInt(values[0]),Integer.parseInt(values[1]),
						Double.parseDouble(values[2]));}
			}
		}
		br.close();
		FieldFrame frame = new FieldFrame(f);
		converge(f, frame, 1, interval, stepinterval, threshold);
		//overrelaxloop(f, interval, stepinterval, threshold);
	}

	/**
	 * runs until solution is found & displays
	 * @param f Field
	 * @param overrelaxation, set to 1 for normal case
	 * @return overrelaxation parameter and steps taken in double[2]
	 * @throws InterruptedException
	 * @throws IOException
	 */
	private static double[] converge(Field f, FieldFrame framehere, double overrelaxation, int interval, int stepinterval, double threshold) throws InterruptedException, IOException{
		//for (int t=0; t<1000000; t++){
		int stepcounter = 0;
		framehere.setTitle("running with overrelaxation "+overrelaxation);
		while (!f.checkConverged(interval, threshold) && stepcounter<999999){
			for (int i=0; i<stepinterval; i++){
				//System.out.println("converging with overralaxation " +overrelaxation);
				f.GaussSeidelUpdate(overrelaxation);
		//System.out.println(f.getPotential(f.potentialField,51,51,50));
				framehere.step();
				stepcounter+=1;
			}
			}
		
	//writeFile(f, f.getxlength(), f.getylength(), f.getzlength(), stepcounter);
	framehere.setTitle("finished after "+stepcounter+ " steps");
	double[] results = new double[2];
	results[0]=overrelaxation;
	results[1]=stepcounter;
	return results;
	}

	/**
	 * loops over converging at different overrelaxation multipliers
	 * @param f Field
	 * @param threshold 
	 * @param stepinterval 
	 * @param interval 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	private static void overrelaxloop(Field f, int interval, int stepinterval, double threshold) throws InterruptedException, IOException{
		int[] stepnumbers = new int[50];
		double[] overrelaxations = new double[50];
		for (int i=0; i<50; i++){
			FieldFrame framehere = new FieldFrame(f);
			System.out.println("in loop with overrelaxation " +(1+(i*.02)));
			ElectricField fhere = new ElectricField(60,60,60);
			fhere.setCharge(30,30,30,charge);
			double[] results = converge(fhere, framehere, 1+(i*.02), interval, stepinterval, threshold);
			stepnumbers[i]= (int)results[1];
			overrelaxations[i]=results[0];
			framehere.dispose();
		}
		System.out.println("out of loop");
		writeConvergeFile(overrelaxations, stepnumbers, interval, stepinterval);
	}
	
	/**
	 * writes file recording potential of radial field
	 * @param f
	 * @param xlength
	 * @param ylength
	 * @param zlength
	 * @param stepcounter
	 * @throws IOException
	 */
	private static void writeFile(Field f, int xlength, int ylength, int zlength,int stepcounter) throws IOException {
		File file = new File("xxx.txt");

		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		String line1 = "# run for "+stepcounter+" steps ";
		String line1b;
		line1b= "#r potential ";
		bw.write(line1);
		bw.newLine();
		bw.write(line1b);
		String line;
		//since radially symmetric only plotting 1/16th with z>0, x>0, y>x
		for (int k = zlength/2; k < zlength; k ++){
		for (int i = xlength/2; i < xlength; i ++){
			for (int j = i; j < ylength; j ++){
				line = f.getDistancefromCenter(i,j,k) + " " + f.getPotential(i,j,k);
				bw.write(line);
				bw.newLine();
			}
		}
	}
	bw.close();
	
}

	/**
	 * writes two arrays, representing overrelaxation vs step number, to file
	 * @param overrelaxations
	 * @param stepnumbers
	 * @throws IOException
	 */
private static void writeConvergeFile(double[] overrelaxations, int[] stepnumbers, int interval, int stepinterval) throws IOException {
	File file = new File("xx.txt");

	if (!file.exists()) {
		file.createNewFile();
	}
	FileWriter fw = new FileWriter(file.getAbsoluteFile());
	BufferedWriter bw = new BufferedWriter(fw);
	String line1 = "# charge "+charge+" steps "+ interval + "checked every  "+stepinterval +" steps";
	String line1b;
	line1b= "#r potential ";
	bw.write(line1);
	bw.newLine();
	bw.write(line1b);
	String line;
	//since radially symmetric only plotting 1/16th with z>0, x>0, y>x
	for (int k = 0; k < overrelaxations.length; k ++){
			line = overrelaxations[k] + " " + stepnumbers[k];
			bw.write(line);
			bw.newLine();
		
}
bw.close();
}
}

