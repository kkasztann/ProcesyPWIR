import javax.swing.JLabel;

class Proces1 extends Thread {
	private JLabel labelStatusProces1;
	private JLabel labelZleconoProces1;
	private JLabel labelWykonanoProces1;
	private JLabel labelZasobyNaStanieA;

	public Proces1(JLabel labelStatusProces1, JLabel labelZleconoProces1, JLabel labelWykonanoProces1,
			JLabel labelZasobyNaStanieA) {
		this.labelStatusProces1 = labelStatusProces1;
		this.labelWykonanoProces1 = labelWykonanoProces1;
		this.labelZleconoProces1 = labelZleconoProces1;
		this.labelZasobyNaStanieA = labelZasobyNaStanieA;
	}

	public void run() {
		try {
			Procesy.zleconoProces1++;
			labelZleconoProces1.setText("zlecono: " + Procesy.zleconoProces1);
			Procesy.SEMAPHORE_PROCES_1.acquire();
			labelStatusProces1.setText(Procesy.STATUS_OCZEKUJE);
			while (Procesy.zasobyNaStanieA == 0) {
				sleep(100);
			}
			if (Procesy.zasobyNaStanieA > 0) {
				Procesy.zasobyNaStanieA--;
				Panel.wspXProces1 = 200;
				labelZasobyNaStanieA
						.setText("Zasobów A na stanie: " + Procesy.zasobyNaStanieA + "/" + Procesy.MAGAZYN_MAX_A);
				labelStatusProces1.setText(Procesy.STATUS_PRACUJE);
				Procesy.pracujeProces1 = true;
				while (Procesy.pracujeProces1 == true) {
					sleep(100);
				}

				Procesy.wykonanoProces1++;
				labelWykonanoProces1.setText("wykonano: " + Procesy.wykonanoProces1);
			}

			labelStatusProces1.setText(Procesy.STATUS_NIE_PRACUJE);
			Procesy.SEMAPHORE_PROCES_1.release();

		} catch (InterruptedException e) {
		}
	}
}