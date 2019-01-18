package smo;
/**
 * @author Dariusz Pierzchala
 * 
 * Description: Description: Klasa gniazda obsługi obiektów - zgłoszeń 
 */

import java.util.LinkedList;

import smo.RozpocznijObsluge;
import smo.ZakonczObsluge;
import smo.Zgloszenie;
import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.monitors.MonitoredVar;
import dissimlab.simcore.BasicSimObj;
import dissimlab.simcore.SimEventSemaphore;
import dissimlab.simcore.SimControlException;



public class Smo extends BasicSimObj
{
    public LinkedList <Zgloszenie> kolejka;
	private boolean wolne = true;
    public RozpocznijObsluge rozpocznijObsluge;
    public LinkedList <RozpocznijObsluge> listaOkienek;
    public ZakonczObsluge zakonczObsluge;
    public int Lifo =0;
    public int dl_kolejki =0;
    public LinkedList <Zgloszenie> wyrzucone;
    public int dodane_zdarzenia = 0;
    public int wielkosc_paczki =1;
    public int rozkladCzasu = 0;
    public LinkedList <Zgloszenie> aktualna_paczka;
    public int[] wartosc = { 1, 2, 3 };
    public double[] prawd = { 0.3, 0.5, 0.2 };
    
    //blic LinkedList <int> wolneObslugi;
    public int limitK;
    public LinkedList<Integer> wolne_kanaly;
    
	
    /** Creates a new instance of Smo 
     * @throws SimControlException */
    public Smo() throws SimControlException
    {
        // Utworzenie wewnętrznej listy w kolejce
        kolejka = new LinkedList <Zgloszenie>();
        wyrzucone = new LinkedList <Zgloszenie>();
        wolne_kanaly = new  LinkedList<Integer>();
       
       
    }

    public void utworzKanaly(){
    	 
         for (int i =0; i<this.limitK;i++){
         	///System.out.println("2???????????"+wolne_kanaly.size() + "  " + i);
         	wolne_kanaly.add((i+1));
         }
         ///System.out.println("3??????????"+wolne_kanaly.size());
    }
    // Wstawienie zgłoszenia do kolejki
    
    public int dodaj(Zgloszenie zgl)
    {
    	dodane_zdarzenia++;
        kolejka.add(zgl);
        return kolejka.size();
    }

    // Pobranie zgłoszenia z kolejki
    public Zgloszenie usun()
    {
    	/*System.out.println("kolejka:");
    	for(Zgloszenie zglosz : kolejka){
    		System.out.print(zglosz.tenNr+" ");
    	}*/
    	System.out.println("");
    	if (Lifo == 1){
    		/* ZADANIE 1 LIFO / FIFO*/
    		System.out.println("Usuwanie ostatniego");
    		Zgloszenie zgl = (Zgloszenie) kolejka.removeLast();
    		 return zgl;
    	}else{
    		System.out.println("Usuwanie pierwszego");
    	
    	Zgloszenie zgl = (Zgloszenie) kolejka.removeFirst();
    	 return zgl;
    	}
       
    }

 // Metoda rozkEMP
 	public int rozkEmp(double losowy) {
 		double suma = 0;
 		//System.out.println("==========="+losowy);
 		for (int i = 0; i < wartosc.length; i++) {
 			suma = suma + prawd[i];
 			//System.out.println("==========="+suma);
 			if (losowy <= suma){
 				return wartosc[i];
 			}
 		}
 		return 0;
 	}
    
    // Pobranie zgłoszenia z kolejki
    public boolean usunWskazany(Zgloszenie zgl)
    {
    	Boolean b= kolejka.remove(zgl);
        return b;
    }
    
    public boolean usunZgloszenie(Zgloszenie zgl)
    {
    	Boolean b= wyrzucone.add(zgl);
        return b;
    }
    
    public int liczbaZgl()
    {
        return kolejka.size();
    }

	public boolean isWolne() {
		return wolne;
	}

	public void setWolne(boolean wolne) {
		this.wolne = wolne;
	}
	
	@Override
	public void reflect(IPublisher publisher, INotificationEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean filter(IPublisher publisher, INotificationEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
}