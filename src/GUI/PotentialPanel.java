package GUI;

import javax.swing.JPanel;

import main.Field;

import java.awt.Color;
import java.awt.Graphics;

public class PotentialPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Field f;
	private int height;
	private int width;
	int z;
	private int xboxsize;
	private int yboxsize;

	/**
	 * constructor sets up and displays panel of potential at initial z level
	 * @param f
	 * @param z
	 * @param xboxsize
	 * @param yboxsize
	 */
	public PotentialPanel(Field f, int z, int xboxsize, int yboxsize){
		this.f=f;
		this.width = f.getxlength();
		this.height=f.getylength();
		this.z=z;
		this.xboxsize=xboxsize;
		this.yboxsize=yboxsize;
		this.setSize(xboxsize*width,yboxsize*height);
	}

	/**
	 * overriding paint component method to display grid of potential strength
	 */
	public void paintComponent(Graphics g) {
		for (int i=0; i < width; i++){
			for (int j=0; j < height; j++){
				g.setColor(getColor(i,j,f));
				g.fillRect(5+i*xboxsize, 5+ j*yboxsize, xboxsize,yboxsize);
			}
		}
	}

	/**
	 * gets color value corresponding to field strength
	 * logarithmic color scale
	 * cyan-blue-black positive charge; yellow-red-magenta negative charge
	 * @param i x coordinates
	 * @param j y coordinate
	 * @param f field to look up color in
	 * @return
	 */
	private Color getColor(int i, int j, Field f) {
		double value = f.getValue(f.potentialField,i,j,this.z);
		Color color;
		if(value >=0){
		if (value <255){
		color = new Color(255 - (int)value,255,255);}
		else if (value < 2550 + 255){
		color = new Color(0,(int)(255 - ((value -255)/10)),255);
		}
		else if (value < 25500+ 2550 + 255){
			color = new Color(0,0,(int)(255 - ((value -2550-255)/100)));
		}
		else  {color =new Color(0,0,0);}
		}
		else{
			if (value > -255){
				color = new Color(255,255,255 - (int)-value);}
				else if (value > - (2550 + 255)){
				color = new Color(255,(int)(255 - ((-value -255)/10)),0);
				}
				else if (value > - (25500+ 2550 + 255)){
					color = new Color(255,0,(int)(((-value -2550-255)/100)));
				}
				else  {color =new Color(255,0,255);}
				}
		
		return color;
	}

	/**
	 * sets z and refreshes display to show this level
	 * @param z
	 */
	public void setz(int z) {
		this.z=z;
		repaint();
	}  




}
