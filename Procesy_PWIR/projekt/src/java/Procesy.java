import java.util.concurrent.Semaphore;

public class Procesy {
	public static final Semaphore SEMAPHORE_PROCES_1 = new Semaphore(1, true);
	public static final Semaphore SEMAPHORE_PROCES_2 = new Semaphore(1, true);
	public static final Semaphore SEMAPHORE_PROCES_3 = new Semaphore(1, true);
	public static final String STATUS_PRACUJE = "PRACUJE";
	public static final String STATUS_NIE_PRACUJE = "NIE PRACUJE";
	public static final String STATUS_OCZEKUJE = "OCZEKUJE";

	// KONFIGURACJA PROGRAMU - POCZĄTEK ==>
	public static final int MAGAZYN_MAX_A = 100;       // 100
	public static final int MAGAZYN_MAX_B = 100;       // 100
	public static final int ZASOBY_NA_STARCIE_A = 5;   // 5
	public static final int ZASOBY_NA_STARCIE_B = 10;  // 10
	public static final int DOSTAWA_A_ILOSC = 5;       // 5
	public static final int DOSTAWA_B_ILOSC = 10;      // 10
	public static final int DOSTAWA_A_ZA = 40;         // 40
	public static final int DOSTAWA_B_ZA = 30;         // 30
	public static final int PREDKOSC_PROCESU_1 = 20;   // 100-20=80
	public static final int PREDKOSC_PROCESU_2 = 15;   // 100-15=85
	public static final int PREDKOSC_PROCESU_3 = 35;   // 100-35=65
	// KONFIGURACJA PROGRAMU - KONIEC <==

	public static int zasobyNaStanieA = ZASOBY_NA_STARCIE_A;
	public static int zasobyNaStanieB = ZASOBY_NA_STARCIE_B;

	public static int zleconoProces1 = 0;
	public static int wykonanoProces1 = 0;
	public static int zleconoProces2 = 0;
	public static int wykonanoProces2 = 0;
	public static int zleconoProces3 = 0;
	public static int wykonanoProces3 = 0;
	public static int anulowanoProces3 = 0;

	public static boolean pracujeProces1 = false;
	public static boolean pracujeProces2 = false;
	public static boolean pracujeProces2A = false;
	public static boolean pracujeProces2B = false;
	public static boolean pracujeProces3 = false;
	public static boolean pracujeProces3A = false;
	public static boolean pracujeProces3B = false;
}