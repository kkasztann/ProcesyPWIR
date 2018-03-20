import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

class Ramka extends JFrame {

	public Ramka() {
		super("Program zaliczeniowy - Programowanie wspó³bie¿ne i równoleg³e - Daniel S³owik - 194704 - KrDZIs3011Io");
		JPanel panel = new Panel();
		add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		Dimension wymiarEkranu = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(wymiarEkranu.width / 2 - this.getSize().width / 2,
				wymiarEkranu.height / 2 - this.getSize().height / 2);
		setVisible(true);
	}
}