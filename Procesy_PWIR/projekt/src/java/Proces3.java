import javax.swing.JLabel;

class Proces3 extends Thread {
	private JLabel labelStatusProces3;
	private JLabel labelZleconoProces3;
	private JLabel labelWykonanoProces3;
	private JLabel labelAnulowanoProces3;
	private JLabel labelZasobyNaStanieA;
	private JLabel labelZasobyNaStanieB;
	private boolean bezpiecznik = false;

	public Proces3(JLabel labelStatusProces3, JLabel labelZleconoProces3, JLabel labelWykonanoProces3,
			JLabel labelAnulowanoProces3, JLabel labelZasobyNaStanieA, JLabel labelZasobyNaStanieB) {
		this.labelStatusProces3 = labelStatusProces3;
		this.labelWykonanoProces3 = labelWykonanoProces3;
		this.labelZleconoProces3 = labelZleconoProces3;
		this.labelAnulowanoProces3 = labelAnulowanoProces3;
		this.labelZasobyNaStanieA = labelZasobyNaStanieA;
		this.labelZasobyNaStanieB = labelZasobyNaStanieB;
	}

	public void run() {
		try {
			Procesy.zleconoProces3++;
			labelZleconoProces3.setText("zlecono: " + Procesy.zleconoProces3);
			Procesy.SEMAPHORE_PROCES_3.acquire();
			labelStatusProces3.setText(Procesy.STATUS_OCZEKUJE);
			while (Procesy.zasobyNaStanieA == 0 && bezpiecznik == false) {
				if (Procesy.zasobyNaStanieB > 0) {
					Panel.wspXProces3 = 850;
					Procesy.zasobyNaStanieB--;
					Procesy.pracujeProces3B = true;
					labelZasobyNaStanieB
							.setText("Zasobów B na stanie: " + Procesy.zasobyNaStanieB + "/" + Procesy.MAGAZYN_MAX_B);
					labelStatusProces3.setText(Procesy.STATUS_PRACUJE);
					Procesy.pracujeProces3 = true;
					while (Procesy.pracujeProces3 == true) {
						sleep(100);
					}

					Procesy.wykonanoProces3++;
					labelWykonanoProces3.setText("wykonano: " + Procesy.wykonanoProces3);
					labelStatusProces3.setText(Procesy.STATUS_NIE_PRACUJE);
					bezpiecznik = true;
					Procesy.SEMAPHORE_PROCES_3.release();

				} else {
					Procesy.anulowanoProces3++;
					labelAnulowanoProces3.setText("anulowano: " + Procesy.anulowanoProces3);
					bezpiecznik = true;
					Procesy.SEMAPHORE_PROCES_3.release();
					labelStatusProces3.setText(Procesy.STATUS_NIE_PRACUJE);
				}

			}
			if (Procesy.zasobyNaStanieA > 0 && bezpiecznik == false) {
				Panel.wspXProces3 = 200;
				Procesy.zasobyNaStanieA--;
				Procesy.pracujeProces3A = true;
				labelZasobyNaStanieA
						.setText("Zasobów A na stanie: " + Procesy.zasobyNaStanieA + "/" + Procesy.MAGAZYN_MAX_A);
				labelStatusProces3.setText(Procesy.STATUS_PRACUJE);
				Procesy.pracujeProces3 = true;
				while (Procesy.pracujeProces3 == true) {
					sleep(100);
				}

				Procesy.wykonanoProces3++;
				labelWykonanoProces3.setText("wykonano: " + Procesy.wykonanoProces3);

				labelStatusProces3.setText(Procesy.STATUS_NIE_PRACUJE);
				Procesy.SEMAPHORE_PROCES_3.release();
			}

		} catch (InterruptedException e) {
		}
	}
}