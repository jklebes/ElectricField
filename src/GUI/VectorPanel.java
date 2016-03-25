package GUI;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

import main.Field;

public class VectorPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Field field;
	private double[][][][] f;
	private int height;
	private int width;
	private int xboxsize;
	private int yboxsize;
	int z;

	public VectorPanel(Field field, int z, int xboxsize, int yboxsize){
		this.field=field;
		//this.f=field.getElectricField();
		this.width = field.getxlength();
		this.height=field.getylength();
		this.z=z;
		this.setSize(5*width,5*height);
		this.xboxsize=xboxsize;
		this.yboxsize=yboxsize;
		this.setSize(xboxsize*width,yboxsize*height);
	}


	public void paintComponent(Graphics g) {
		//System.out.println("field"+ field.getElectricField()[25][25][25][0]);
		f=field.getFieldVectors();
		//System.out.println("f"+ f[25][25][25][0]);
		g.setColor(this.getBackground ());
		g.fillRect(0, 0,xboxsize*(width+1),yboxsize*(height+1));
		g.setColor(Color.WHITE);
		g.fillRect(5, 5,xboxsize*(width),yboxsize*(height));
		for (int i=0; i < width; i++){
			for (int j=0; j < height; j++){
				//double[] vector = field.getElectricFieldVector(f,i,j,this.z);
				//System.out.println(vector[0]+", "+vector[1]);
				g.setColor(Color.BLACK);
				drawArrow(g,5+ i*xboxsize, 5+ j*yboxsize,xboxsize,yboxsize,i,j);
			}
		}
	}

	private void drawArrow(Graphics g,int squarexcoord, int squareycoord, int squarexsize, int squareysize,int i, int j) {
		double[] vector = new double[2];
		double[] mainvector = new double[2];
		double[] headleftvector = new double[2];
		double[] headrightvector = new double[2];
		vector[0]=f[i][j][this.z][0];
		vector[1]=f[i][j][this.z][1];
		mainvector=scale(normalize(rotate(vector,0)),squarexsize);
		headleftvector = scale(normalize(rotate(vector,(45))),3);
		headrightvector = scale(normalize(rotate(vector,(-45))),3);
		g.drawLine(squarexcoord, squareycoord, squarexcoord +(int)mainvector[0], squareycoord+(int)mainvector[1]);
		g.drawLine(squarexcoord , squareycoord,
				squarexcoord+(int)(headleftvector[0]),squareycoord+(int)(headleftvector[1]));
		g.drawLine(squarexcoord , squareycoord,
				squarexcoord+(int)(headrightvector[0]),squareycoord+(int)(headrightvector[1]));
		//}
	}

	private double[] normalize(double[] vector) {
		double[] newvector = new double[2];
		newvector[0]=vector[0]/Math.sqrt((vector[0])*(vector[0])+(vector[1])*(vector[1]));
		newvector[1]=vector[1]/Math.sqrt((vector[0])*(vector[0])+(vector[1])*(vector[1]));
		return newvector;
	}


	private double[] scale(double[] vector,double i) {
		double[] newvector = new double[2];
		newvector[0]=i*(vector[0]);
		newvector[1]=i*(vector[1]);
		return newvector;
	}
	
	private int getlength(double[] vector) {
		return (int)Math.sqrt((vector[0])*(vector[0])+(vector[1])*(vector[1]));
	}


	private double[] rotate(double[] vector, double i) {
		double[] newvector = new double[2];
		newvector[0]=(vector[0]*Math.cos(Math.toRadians(i))-vector[1]*Math.sin(Math.toRadians(i)));
		newvector[1]=(vector[0]*Math.sin(Math.toRadians(i))+vector[1]*Math.cos(Math.toRadians(i)));
		return newvector;
	}


	public void setz(int z) {
		this.z=z;
		repaint();
	}  
}
