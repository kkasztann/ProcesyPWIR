import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

class Panel extends JPanel {

	private BufferedImage tloPanelu;
	private BufferedImage zasob;
	private ImageIcon ikonka = new ImageIcon("src/resources/images/zasob.png");

	private JLabel labelDostawaA, labelDostawaB;
	private JLabel labelZasobyNaStanieA, labelZasobyNaStanieB;
	private JLabel labelStatusProces1, labelStatusProces2, labelStatusProces3;

	private JButton buttonDodajProces1;
	private JLabel labelProces1, labelZleconoProces1, labelWykonanoProces1;

	private JButton buttonDodajProces2;
	private JLabel labelProces2, labelZleconoProces2, labelWykonanoProces2;

	private JButton buttonDodajProces3;
	private JLabel labelProces3, labelZleconoProces3, labelWykonanoProces3, labelAnulowanoProces3;

	private JButton buttonZasady;

	private JProgressBar progressBarZasobyNaStanieA;
	private JProgressBar progressBarZasobyNaStanieB;

	private static final Color NIEBIESKI = new Color(80, 130, 200);
	private static final Color ZIELONY = new Color(115, 200, 75);
	private static final Color CZERWONY = new Color(245, 60, 60);
	private static final Color SZARAWY = new Color(119, 102, 95);
	private static final Color CZARNY = new Color(0, 0, 0);
	private final Border CZARNE_OBRAMOWANIE = BorderFactory.createLineBorder(CZARNY);

	public static int wspXProces1 = 1200;
	public int wspYProces1 = 125;
	public static int wspXProces2 = 1200;
	public int wspYProces2 = 325;
	public static int wspXProces3 = 1200;
	public int wspYProces3 = 525;

	private int dostawaSekundyA = Procesy.DOSTAWA_A_ZA;
	private int dostawaSekundyB = Procesy.DOSTAWA_B_ZA;

	public Panel() {
		super();
		setLayout(null);
		File imageFileTloPanelu = new File("src/resources/images/tloPanelu.png");
		File imageFileZasob = new File("src/resources/images/zasob.png");
		try {
			tloPanelu = ImageIO.read(imageFileTloPanelu);
			zasob = ImageIO.read(imageFileZasob);
		} catch (IOException e) {
			System.err.println("B³¹d odczytu obrazka");
			e.printStackTrace();
		}

		Dimension wymiarPanelu = new Dimension(tloPanelu.getWidth(), tloPanelu.getHeight());
		setPreferredSize(wymiarPanelu);

		// STATUS Proces1
		labelStatusProces1 = new JLabel("NIE PRACUJE", SwingConstants.CENTER);
		labelStatusProces1.setBounds(500, 85, 100, 10);
		add(labelStatusProces1);

		// STATUS Proces2
		labelStatusProces2 = new JLabel("NIE PRACUJE", SwingConstants.CENTER);
		labelStatusProces2.setBounds(500, 285, 100, 10);
		add(labelStatusProces2);

		// STATUS Proces3
		labelStatusProces3 = new JLabel("NIE PRACUJE", SwingConstants.CENTER);
		labelStatusProces3.setBounds(500, 485, 100, 10);
		add(labelStatusProces3);

		// ZASOBY NA STANIE A
		labelZasobyNaStanieA = new JLabel(
				"Zasobów A na stanie: " + Procesy.zasobyNaStanieA + "/" + Procesy.MAGAZYN_MAX_A, SwingConstants.CENTER);
		labelZasobyNaStanieA.setBounds(0, 660, 195, 20);
		add(labelZasobyNaStanieA);

		// ZASOBY NA STANIE B
		labelZasobyNaStanieB = new JLabel(
				"Zasobów B na stanie: " + Procesy.zasobyNaStanieB + "/" + Procesy.MAGAZYN_MAX_B, SwingConstants.CENTER);
		labelZasobyNaStanieB.setBounds(905, 660, 195, 20);
		add(labelZasobyNaStanieB);

		// ZASOBY NA STANIE A - PROGRESSBAR
		progressBarZasobyNaStanieA = new JProgressBar(SwingConstants.VERTICAL, 0, Procesy.MAGAZYN_MAX_A);
		progressBarZasobyNaStanieA.setBounds(178, 655, 20, 40);
		progressBarZasobyNaStanieA.setStringPainted(true);
		progressBarZasobyNaStanieA.setBorder(new LineBorder(Color.BLACK, 1));
		progressBarZasobyNaStanieA.setForeground(null);
		progressBarZasobyNaStanieA.setValue(Procesy.zasobyNaStanieA);
		add(progressBarZasobyNaStanieA);

		// ZASOBY NA STANIE B - PROGRESSBAR
		progressBarZasobyNaStanieB = new JProgressBar(SwingConstants.VERTICAL, 0, Procesy.MAGAZYN_MAX_B);
		progressBarZasobyNaStanieB.setBounds(898, 657, 20, 40);
		progressBarZasobyNaStanieB.setStringPainted(true);
		progressBarZasobyNaStanieB.setBorder(new LineBorder(Color.BLACK, 1));
		progressBarZasobyNaStanieB.setForeground(null);
		progressBarZasobyNaStanieB.setValue(Procesy.zasobyNaStanieB);
		add(progressBarZasobyNaStanieB);

		// DOSTAWA A
		labelDostawaA = new JLabel("Dostawa A za XYZ sekund", SwingConstants.CENTER);
		labelDostawaA.setBounds(0, 675, 195, 20);
		add(labelDostawaA);

		ActionListener dostawaZaA = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (dostawaSekundyA > 0) {
					dostawaSekundyA--;
				}
				if (dostawaSekundyA <= 0) {
					dostawaSekundyA = Procesy.DOSTAWA_A_ZA;
					if (Procesy.zasobyNaStanieA <= Procesy.MAGAZYN_MAX_A - Procesy.DOSTAWA_A_ILOSC) {
						Procesy.zasobyNaStanieA += Procesy.DOSTAWA_A_ILOSC;
						labelZasobyNaStanieA.setText(
								"Zasobów A na stanie: " + Procesy.zasobyNaStanieA + "/" + Procesy.MAGAZYN_MAX_A);
					} else {
						Procesy.zasobyNaStanieA = Procesy.MAGAZYN_MAX_A;
						labelZasobyNaStanieA.setText(
								"Zasobów A na stanie: " + Procesy.zasobyNaStanieA + "/" + Procesy.MAGAZYN_MAX_A);
					}
				}

				labelDostawaA.setText("Dostawa A za " + dostawaSekundyA + " sekund");
			}
		};

		new Timer(1000, dostawaZaA).start();

		// DOSTAWA B
		labelDostawaB = new JLabel("Dostawa B za XYZ sekund", SwingConstants.CENTER);
		labelDostawaB.setBounds(905, 675, 195, 20);
		add(labelDostawaB);

		ActionListener dostawaZaB = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (dostawaSekundyB > 0) {
					dostawaSekundyB--;
				}
				if (dostawaSekundyB <= 0) {
					dostawaSekundyB = Procesy.DOSTAWA_B_ZA;
					if (Procesy.zasobyNaStanieB <= Procesy.MAGAZYN_MAX_B - Procesy.DOSTAWA_B_ILOSC) {
						Procesy.zasobyNaStanieB += Procesy.DOSTAWA_B_ILOSC;
						labelZasobyNaStanieB.setText(
								"Zasobów B na stanie: " + Procesy.zasobyNaStanieB + "/" + Procesy.MAGAZYN_MAX_B);
					} else {
						Procesy.zasobyNaStanieB = Procesy.MAGAZYN_MAX_B;
						labelZasobyNaStanieB.setText(
								"Zasobów B na stanie: " + Procesy.zasobyNaStanieB + "/" + Procesy.MAGAZYN_MAX_B);
					}
				}

				labelDostawaB.setText("Dostawa B za " + dostawaSekundyB + " sekund");
			}
		};

		new Timer(1000, dostawaZaB).start();

		// STATYSTYKI - PROCES1

		labelProces1 = new JLabel("Proces 1", SwingConstants.CENTER);
		labelProces1.setBounds(100, 740, 100, 120);
		labelProces1.setBorder(CZARNE_OBRAMOWANIE);
		labelProces1.setBackground(SZARAWY);
		labelProces1.setOpaque(true);
		add(labelProces1);

		labelZleconoProces1 = new JLabel("zlecono: " + Procesy.zleconoProces1, SwingConstants.CENTER);
		labelZleconoProces1.setBounds(200, 740, 100, 60);
		labelZleconoProces1.setBorder(CZARNE_OBRAMOWANIE);
		labelZleconoProces1.setBackground(NIEBIESKI);
		labelZleconoProces1.setOpaque(true);
		add(labelZleconoProces1);

		labelWykonanoProces1 = new JLabel("wykonano: " + Procesy.wykonanoProces1, SwingConstants.CENTER);
		labelWykonanoProces1.setBounds(200, 800, 100, 60);
		labelWykonanoProces1.setBorder(CZARNE_OBRAMOWANIE);
		labelWykonanoProces1.setBackground(ZIELONY);
		labelWykonanoProces1.setOpaque(true);
		add(labelWykonanoProces1);

		buttonDodajProces1 = new JButton("+");
		buttonDodajProces1.setBounds(300, 740, 50, 60);
		add(buttonDodajProces1);

		ActionListener P1start = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Proces1(labelStatusProces1, labelZleconoProces1, labelWykonanoProces1, labelZasobyNaStanieA)
						.start();
			}
		};
		buttonDodajProces1.addActionListener(P1start);

		final ActionListener pracujP1 = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (Procesy.pracujeProces1 == true) {
					wspXProces1 += 3;
					if (wspXProces1 >= 453) {
						Procesy.pracujeProces1 = false;
					}
				}
			}
		};

		new Timer(Procesy.PREDKOSC_PROCESU_1, pracujP1).start();

		// STATYSTYKI - PROCES2

		labelProces2 = new JLabel("Proces 2", SwingConstants.CENTER);
		labelProces2.setBounds(400, 740, 100, 120);
		labelProces2.setBorder(CZARNE_OBRAMOWANIE);
		labelProces2.setBackground(SZARAWY);
		labelProces2.setOpaque(true);
		add(labelProces2);

		labelZleconoProces2 = new JLabel("zlecono: " + Procesy.zleconoProces2, SwingConstants.CENTER);
		labelZleconoProces2.setBounds(500, 740, 100, 60);
		labelZleconoProces2.setBorder(CZARNE_OBRAMOWANIE);
		labelZleconoProces2.setBackground(NIEBIESKI);
		labelZleconoProces2.setOpaque(true);
		add(labelZleconoProces2);

		labelWykonanoProces2 = new JLabel("wykonano: " + Procesy.wykonanoProces2, SwingConstants.CENTER);
		labelWykonanoProces2.setBounds(500, 800, 100, 60);
		labelWykonanoProces2.setBorder(CZARNE_OBRAMOWANIE);
		labelWykonanoProces2.setBackground(ZIELONY);
		labelWykonanoProces2.setOpaque(true);
		add(labelWykonanoProces2);

		buttonDodajProces2 = new JButton("+");
		buttonDodajProces2.setBounds(600, 740, 50, 60);
		add(buttonDodajProces2);

		ActionListener P2start = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Proces2(labelStatusProces2, labelZleconoProces2, labelWykonanoProces2, labelZasobyNaStanieA,
						labelZasobyNaStanieB).start();
			}
		};
		buttonDodajProces2.addActionListener(P2start);

		final ActionListener pracujP2 = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (Procesy.pracujeProces2 == true) {
					if (Procesy.pracujeProces2A == true) {
						wspXProces2 += 3;
						if (wspXProces2 >= 453) {
							Procesy.pracujeProces2A = false;
							Procesy.pracujeProces2 = false;
						}
					} else if (Procesy.pracujeProces2B == true) {
						wspXProces2 -= 3;
						if (wspXProces2 <= 598) {
							Procesy.pracujeProces2B = false;
							Procesy.pracujeProces2 = false;
						}
					}
				}
			}
		};

		new Timer(Procesy.PREDKOSC_PROCESU_2, pracujP2).start();

		// STATYSTYKI - PROCES3

		labelProces3 = new JLabel("Proces 3", SwingConstants.CENTER);
		labelProces3.setBounds(700, 740, 100, 120);
		labelProces3.setBorder(CZARNE_OBRAMOWANIE);
		labelProces3.setBackground(SZARAWY);
		labelProces3.setOpaque(true);
		add(labelProces3);

		labelZleconoProces3 = new JLabel("zlecono: " + Procesy.zleconoProces3, SwingConstants.CENTER);
		labelZleconoProces3.setBounds(800, 740, 100, 40);
		labelZleconoProces3.setBorder(CZARNE_OBRAMOWANIE);
		labelZleconoProces3.setBackground(NIEBIESKI);
		labelZleconoProces3.setOpaque(true);
		add(labelZleconoProces3);

		labelWykonanoProces3 = new JLabel("wykonano: " + Procesy.wykonanoProces3, SwingConstants.CENTER);
		labelWykonanoProces3.setBounds(800, 780, 100, 40);
		labelWykonanoProces3.setBorder(CZARNE_OBRAMOWANIE);
		labelWykonanoProces3.setBackground(ZIELONY);
		labelWykonanoProces3.setOpaque(true);
		add(labelWykonanoProces3);

		labelAnulowanoProces3 = new JLabel("anulowano: " + Procesy.anulowanoProces3, SwingConstants.CENTER);
		labelAnulowanoProces3.setBounds(800, 820, 100, 40);
		labelAnulowanoProces3.setBorder(CZARNE_OBRAMOWANIE);
		labelAnulowanoProces3.setBackground(CZERWONY);
		labelAnulowanoProces3.setOpaque(true);
		add(labelAnulowanoProces3);

		buttonDodajProces3 = new JButton("+");
		buttonDodajProces3.setBounds(900, 740, 50, 40);
		add(buttonDodajProces3);

		ActionListener P3start = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Proces3(labelStatusProces3, labelZleconoProces3, labelWykonanoProces3, labelAnulowanoProces3,
						labelZasobyNaStanieA, labelZasobyNaStanieB).start();
			}
		};
		buttonDodajProces3.addActionListener(P3start);

		final ActionListener pracujP3 = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (Procesy.pracujeProces3 == true) {
					if (Procesy.pracujeProces3A == true) {
						wspXProces3 += 3;
						if (wspXProces3 >= 453) {
							Procesy.pracujeProces3A = false;
							Procesy.pracujeProces3 = false;
						}
					} else if (Procesy.pracujeProces3B == true) {
						wspXProces3 -= 3;
						if (wspXProces3 <= 598) {
							Procesy.pracujeProces3B = false;
							Procesy.pracujeProces3 = false;
						}
					}
				}
			}
		};

		new Timer(Procesy.PREDKOSC_PROCESU_3, pracujP3).start();

		// ODŒWIE¯ANIE PANELU
		final ActionListener odswiezaj = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				progressBarZasobyNaStanieA.setValue(Procesy.zasobyNaStanieA);
				progressBarZasobyNaStanieB.setValue(Procesy.zasobyNaStanieB);

				if (Procesy.zasobyNaStanieA == Procesy.MAGAZYN_MAX_A)
					progressBarZasobyNaStanieA.setForeground(ZIELONY);
				else
					progressBarZasobyNaStanieA.setForeground(null);

				if (Procesy.zasobyNaStanieB == Procesy.MAGAZYN_MAX_B)
					progressBarZasobyNaStanieB.setForeground(ZIELONY);
				else
					progressBarZasobyNaStanieB.setForeground(null);

				repaint();
			}
		};

		new Timer(30, odswiezaj).start();

		// ZASADY
		buttonZasady = new JButton("Zasady");
		buttonZasady.setBounds(1020, 870, 80, 20);
		add(buttonZasady);

		ActionListener zasady = new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(null, "ZASADY DZIA£ANIA PROGRAMU:\n"
						+ "        1. Procesy PIERWSZEJ grupy ¿¹daj¹ wy³¹cznie zasobu A i czekaj¹, a¿ bêdzie dostêpny\n"
						+ "        2. Procesy DRUGIEJ grupy ¿¹daj¹ zasobu A, lecz jeœli jest niedostêpny, to czekaj¹ na zasób dowolnego typu\n"
						+ "        3. Procesy TRZECIEJ grupy ¿¹daj¹ dowolnego zasobu, ale jeœli ¿aden nie jest dostêpny to s¹ anulowane\n\n\n"
						+ "PARAMETRY STARTOWE PROGRAMU:\n" + "        \u2699 Magazyn A mieœci " + Procesy.MAGAZYN_MAX_A
						+ " zasobów, na starcie w magazynie jest " + Procesy.ZASOBY_NA_STARCIE_A + " zasobów\n"
						+ "        \u2699 Magazyn B mieœci " + Procesy.MAGAZYN_MAX_B
						+ " zasobów, na starcie w magazynie jest " + Procesy.ZASOBY_NA_STARCIE_B + " zasobów\n"
						+ "        \u2699 Dostawa " + Procesy.DOSTAWA_A_ILOSC + " zasobów A odbywa siê co "
						+ Procesy.DOSTAWA_A_ZA + " sekund\n" + "        \u2699 Dostawa " + Procesy.DOSTAWA_B_ILOSC
						+ " zasobów B odbywa siê co " + Procesy.DOSTAWA_B_ZA + " sekund\n"
						+ "        \u2699 Prêdkoœæ procesów PIERWSZEJ grupy wynosi: "
						+ (100 - Procesy.PREDKOSC_PROCESU_1) + "\n"
						+ "        \u2699 Prêdkoœæ procesów DRUGIEJ grupy wynosi: " + (100 - Procesy.PREDKOSC_PROCESU_2)
						+ "\n" + "        \u2699 Prêdkoœæ procesów TRZECIEJ grupy wynosi: "
						+ (100 - Procesy.PREDKOSC_PROCESU_3) + "\n\n", "Zasady dzia³ania i parametry startowe programu",
						JOptionPane.INFORMATION_MESSAGE, ikonka);

			}
		};

		buttonZasady.addActionListener(zasady);

	}

	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(tloPanelu, 0, 0, this);
		g2d.drawImage(zasob, wspXProces1, wspYProces1, this);
		g2d.drawImage(zasob, wspXProces2, wspYProces2, this);
		g2d.drawImage(zasob, wspXProces3, wspYProces3, this);

	}
}