package GUI;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * class for slider element which changes z-level displayed
 * @author s1203908
 *
 */
class SliderPanel extends JPanel {
	private static final long serialVersionUID = 1L;
JSlider zslider;
static final int z_MIN = 0;
int z;
PotentialPanel potentialpanel;
VectorPanel elpanel;

/**
 * construcot creates and displays slider to vary z-level diplayed
 * @param z_MAX
 * @param potentialpanel
 * @param vpanel
 */
public SliderPanel(int z_MAX, final PotentialPanel potentialpanel, final VectorPanel vpanel){
	this.potentialpanel = potentialpanel;
this.zslider = new JSlider(JSlider.HORIZONTAL,
                                      z_MIN, z_MAX, z_MAX/2);
zslider.addChangeListener(new ChangeListener(){public void stateChanged(ChangeEvent e) {
	JSlider source = (JSlider)e.getSource();
	if (!source.getValueIsAdjusting()) {
		z = (int)source.getValue()-1;
		potentialpanel.setz(z);
		vpanel.setz(z);
	}   }});
zslider.setMajorTickSpacing(10);
zslider.setMinorTickSpacing(1);
zslider.setPaintTicks(true);
zslider.setPaintLabels(true);

}
}
