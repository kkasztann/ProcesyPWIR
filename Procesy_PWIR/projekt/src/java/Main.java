import java.awt.EventQueue;

public class Main {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Ramka ramka = new Ramka();
				ramka.setResizable(false);
			}
		});
	}
}