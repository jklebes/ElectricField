package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import main.Field;


public class FieldFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int xlength;
	private int ylength;
	private int zlength;
	private int xboxsize=10;
	private int yboxsize=10;
	PotentialPanel pan;
	VectorPanel el;
	SliderPanel slider;
	//ContentPanel pan ;

	/**
	 * constructor sets up frame displaying given potential & vector field of given field
	 * @param f
	 * @param z
	 */
	public FieldFrame(Field f){
		this.xlength = f.getxlength();
		this.ylength = f.getylength();
		this.zlength = f.getzlength();
		JPanel contentPane = new JPanel(new BorderLayout());
		this.setSize(2*xlength*xboxsize+10,ylength*yboxsize+30+50);
		this.el = new VectorPanel(f,zlength/2, xboxsize,yboxsize);
		this.pan = new PotentialPanel(f,zlength/2, xboxsize, yboxsize);
		el.setPreferredSize(new Dimension(xlength*xboxsize, ylength*yboxsize));
		pan.setPreferredSize(new Dimension(xlength*xboxsize, ylength*yboxsize));
		//pan.setMinimumSize(new Dimension(500,500));
		this.slider = new SliderPanel(f.getzlength(),pan,el);
		contentPane.add(pan, BorderLayout.LINE_START);
		contentPane.add(el,BorderLayout.LINE_END);
		contentPane.add(slider.zslider, BorderLayout.PAGE_END);
		this.setContentPane(contentPane);
		//this.pan = new ContentPanel(f,z);
		//this.add(pan);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	/**
	 * method to update panels while running or for slider change
	 */
	public void step(){
	  pan.repaint();
	  el.repaint();
	}
	
}


