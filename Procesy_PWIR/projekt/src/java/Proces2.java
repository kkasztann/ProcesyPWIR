import javax.swing.JLabel;

class Proces2 extends Thread {
	private JLabel labelStatusProces2;
	private JLabel labelZleconoProces2;
	private JLabel labelWykonanoProces2;
	private JLabel labelZasobyNaStanieA;
	private JLabel labelZasobyNaStanieB;
	private boolean bezpiecznik = false;

	public Proces2(JLabel labelStatusProces2, JLabel labelZleconoProces2, JLabel labelWykonanoProces2,
			JLabel labelZasobyNaStanieA, JLabel labelZasobyNaStanieB) {
		this.labelStatusProces2 = labelStatusProces2;
		this.labelWykonanoProces2 = labelWykonanoProces2;
		this.labelZleconoProces2 = labelZleconoProces2;
		this.labelZasobyNaStanieA = labelZasobyNaStanieA;
		this.labelZasobyNaStanieB = labelZasobyNaStanieB;
	}

	public void run() {
		try {
			Procesy.zleconoProces2++;
			labelZleconoProces2.setText("zlecono: " + Procesy.zleconoProces2);
			Procesy.SEMAPHORE_PROCES_2.acquire();
			labelStatusProces2.setText(Procesy.STATUS_OCZEKUJE);
			while (Procesy.zasobyNaStanieA == 0 && bezpiecznik == false) {
				if (Procesy.zasobyNaStanieB > 0) {
					Panel.wspXProces2 = 850;
					Procesy.zasobyNaStanieB--;
					Procesy.pracujeProces2B = true;
					labelZasobyNaStanieB
							.setText("Zasobów B na stanie: " + Procesy.zasobyNaStanieB + "/" + Procesy.MAGAZYN_MAX_B);
					labelStatusProces2.setText(Procesy.STATUS_PRACUJE);
					Procesy.pracujeProces2 = true;
					while (Procesy.pracujeProces2 == true) {
						sleep(100);
					}

					Procesy.wykonanoProces2++;
					labelWykonanoProces2.setText("wykonano: " + Procesy.wykonanoProces2);
					labelStatusProces2.setText(Procesy.STATUS_NIE_PRACUJE);
					bezpiecznik = true;
					Procesy.SEMAPHORE_PROCES_2.release();

				} else
					sleep(100);
			}
			if (Procesy.zasobyNaStanieA > 0 && bezpiecznik == false) {
				Panel.wspXProces2 = 200;
				Procesy.zasobyNaStanieA--;
				Procesy.pracujeProces2A = true;
				labelZasobyNaStanieA
						.setText("Zasobów A na stanie: " + Procesy.zasobyNaStanieA + "/" + Procesy.MAGAZYN_MAX_A);
				labelStatusProces2.setText(Procesy.STATUS_PRACUJE);
				Procesy.pracujeProces2 = true;
				while (Procesy.pracujeProces2 == true) {
					sleep(100);
				}

				Procesy.wykonanoProces2++;
				labelWykonanoProces2.setText("wykonano: " + Procesy.wykonanoProces2);

				labelStatusProces2.setText(Procesy.STATUS_NIE_PRACUJE);
				Procesy.SEMAPHORE_PROCES_2.release();
			}

		} catch (InterruptedException e) {
		}
	}
}