package smo;

import java.util.Scanner;

/**
 * @author Dariusz Pierzchala
 * 
 * Description: Klasa główna. Tworzy dwa SMO, inicjalizuje je.Startuje symulację. Wyświetla statystyki.
 * 
 * Wersja testowa.
 */

import dissimlab.simcore.SimControlEvent;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimManager;
import dissimlab.simcore.SimParameters.SimControlStatus;

public class AppSMO {
	public static void main(String[] args) {
		try {
			System.out.println("Start");
			Scanner reader = new Scanner(System.in);
			Config konfiguracja = new Config();
			System.out.println("1= FIFO, 2= LIFO");
			konfiguracja.lifo = reader.nextInt();
			System.out.println("Podaj długość kolejki (0 = nieskończoność):");
			konfiguracja.dlugosz_kolejki = reader.nextInt();
			System.out.println("1= pakiety pojedyncze, P= pakiety o rozkladzie");
			konfiguracja.multipakiety = reader.nextInt();
			System.out.println("Rozkład odstępu między zgłoszeniami: 0 - normalny, 1 - empiryczny ");
			konfiguracja.rozkladCzasu = reader.nextInt();
			System.out.println("Wybrano: " + konfiguracja.rozkladCzasu);
			
			System.out.println("Podaj limit SMO - K:");
			konfiguracja.limitSMO = reader.nextInt();
			System.out.println("Wybrano: " + konfiguracja.limitSMO);
			
			
			
			reader.close();
			
			
			
			SimManager model = SimManager.getInstance();
			// Powołanie Smo 
			Smo smo = new Smo();
			
			smo.limitK = konfiguracja.limitSMO;
			smo.utworzKanaly();
			smo.rozkladCzasu = konfiguracja.rozkladCzasu;
			
			if(konfiguracja.lifo == 2){
			smo.Lifo = 1;
			}else{
				smo.Lifo = 0;
			}
			smo.dl_kolejki = konfiguracja.dlugosz_kolejki;
			smo.wielkosc_paczki = konfiguracja.multipakiety;
			// Utworzenie otoczenia
			Otoczenie generatorZgl = new Otoczenie(smo);
			// Dwa sposoby zaplanowanego końca symulacji
			//model.setEndSimTime(10000);
			// lub
			SimControlEvent stopEvent = new SimControlEvent(1000.0, SimControlStatus.STOPSIMULATION);
			// Uruchomienie symulacji za pośrednictwem metody "startSimulation" 
			model.startSimulation();
			
			System.out.println("Liczba zdarzeń które weszły do kolejki: "+ smo.dodane_zdarzenia);
			System.out.println("Liczba zdarzeń odrzuconych: "+smo.wyrzucone.size());
			System.out.println("Wyliczone prawdopodobieństwo odrzufcenia z kolejki: " + ((double)smo.wyrzucone.size()/(double)smo.dodane_zdarzenia));

		} catch (SimControlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
