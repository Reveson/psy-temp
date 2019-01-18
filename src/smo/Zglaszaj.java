package smo;

import java.util.LinkedList;

import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimParameters.SimDateField;

/**
 * Description: Zdarzenie generatora zgłoszeń. Tworzy obiekt - zgłoszenie co losowy czas.
 * 
 * @author Dariusz Pierzchala

 */
public class Zglaszaj extends BasicSimEvent<Otoczenie, Object>
{
    private SimGenerator generator;
    private Otoczenie parent;
    public LinkedList <Zgloszenie> aktualna_paczka;
    
    
    public Zglaszaj(Otoczenie parent, double delay) throws SimControlException
    {
    	super(parent, delay);
    	generator = new SimGenerator();
    	
    }

    public Zglaszaj(Otoczenie parent) throws SimControlException
    {
    	super(parent);
    	generator = new SimGenerator();
    	aktualna_paczka = new LinkedList <Zgloszenie>();
    }
    
	@Override
	protected void onInterruption() throws SimControlException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onTermination() throws SimControlException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void stateChange() throws SimControlException {
		

		System.out.println("==== POCZĄTEK TWORZENIA PACZKI");
		 parent = getSimObj();
		
		 aktualna_paczka = new LinkedList <Zgloszenie>();
		 int rozmiarpaczki = 0;
		 if (parent.smo.wielkosc_paczki == 1){
			 rozmiarpaczki = 0;
		 }else{
			rozmiarpaczki = generator.uniformInt(1,parent.smo.wielkosc_paczki);
		 }
		 for(int i=0;i <= rozmiarpaczki;i++){
				Zgloszenie zgl = new Zgloszenie(simTime(), parent.smo);
				//System.out.println(zgl.tenNr);
				aktualna_paczka.add(zgl);
		 }
		 System.out.println("aktualna paczka size " + aktualna_paczka.size());
		 int miejsca_w_kolejce = parent.smo.dl_kolejki - parent.smo.liczbaZgl();
		 System.out.print("Paczka: ");
		 if (rozmiarpaczki <= miejsca_w_kolejce){
			 for (Zgloszenie z : aktualna_paczka){
				 System.out.print(z.tenNr+" ");
			 }
		 }
		 System.out.println("");
		 System.out.println("kolejka:");
	    	for(Zgloszenie zglosz : parent.smo.kolejka){
	    		System.out.print(zglosz.tenNr+" ");
	    	}
	    	System.out.println("");
		 System.out.println("Wolne miejsce w kolejce: "+miejsca_w_kolejce);
		 int rozmiarpaczkitext = rozmiarpaczki;
		 if (rozmiarpaczki == 0){
			 rozmiarpaczkitext=1;
		 }
		 System.out.println("Rozmiar paczki: "+rozmiarpaczkitext);
		 if (rozmiarpaczki <= miejsca_w_kolejce){
			 for (Zgloszenie z : aktualna_paczka){
				 parent.smo.dodaj(z);
				 /*if (parent.smo.liczbaZgl() == 1 && parent.smo.isWolne()) {
					 System.out.println("=======================AAAA "+parent.smo.wolne_kanaly.size());
					 int nowynumer = parent.smo.wolne_kanaly.getFirst();
					 
						parent.smo.rozpocznijObsluge = new RozpocznijObsluge(parent.smo);
					}
				 */
				 if (parent.smo.liczbaZgl() == 1 && (parent.smo.wolne_kanaly.size() > 0)) {
					 System.out.println("WOLNE KANAŁY (SIZE): "+parent.smo.wolne_kanaly.size());
					 System.out.print("WOLNE KANAŁY (LISTA): ");
					 for(int kanal : parent.smo.wolne_kanaly){
				    		System.out.print(kanal+ " ");
				    	}
					 System.out.println("");
					 int nowynumer = parent.smo.wolne_kanaly.getFirst();
					 parent.smo.wolne_kanaly.removeFirst();
					 parent.smo.rozpocznijObsluge = new RozpocznijObsluge(parent.smo,nowynumer);
					}
			 }
		 }else{
			 
			 for (Zgloszenie z : aktualna_paczka){
				parent.smo.usunZgloszenie(z);
				
			 }
		 }
		
		 for (int i=0;i<rozmiarpaczki;i++){
			 aktualna_paczka.removeLast();
		 }
		
		
		 System.out.println("==== KONIEC TWORZENIA PACZKI");
		 /* ---- ZADANIE 2 stare
		Zgloszenie zgl = new Zgloszenie(simTime(), parent.smo);
        
       
        if (parent.smo.dl_kolejki <= parent.smo.liczbaZgl() && parent.smo.dl_kolejki != 0){
        	System.out.println("kolejka pełna, "+zgl.tenNr+" nie wchodzi, do usunięcia.");
        	
        	parent.smo.usunZgloszenie(zgl);
        	
        }else{
        	//Zgloszenie zgl = new Zgloszenie(simTime(), parent.smo);
            parent.smo.dodaj(zgl);
            System.out.println(simTime()+" - "+simDate(SimDateField.HOUR24)+" - "+simDate(SimDateField.MINUTE)+" - "+simDate(SimDateField.SECOND)+" - "+simDate(SimDateField.MILLISECOND)+": Otoczenie- Dodano nowe zgl. nr: " + zgl.getTenNr());
            // Aktywuj obsługę, jeżeli kolejka była pusta (gniazdo "spało")
            if (parent.smo.liczbaZgl()==1 && parent.smo.isWolne()) {
            	parent.smo.rozpocznijObsluge = new RozpocznijObsluge(parent.smo);
            }//
        }
        
        ----koniec zadania 2
        */
		 
		 
        
		// Wygeneruj czas do kolejnego zgłoszenia
			
			double odstep;
			if (parent.smo.rozkladCzasu == 0) {
				odstep = generator.normal(5.0, 1.0);
				System.out.println("odstep: " + odstep);
			} else {
				//System.out.println("========================");
				odstep = parent.smo.rozkEmp(generator.normal(0, 1));
				if (odstep == 0) odstep = 1;
				System.out.println("odstep: " + odstep);
			}
			setRepetitionPeriod(odstep);
			
        
     
        // Wygeneruj czas do kolejnego zgłoszenia
        //double odstep = generator.normal(5.0, 1.0);
        //setRepetitionPeriod(odstep);
        //alternatywnie: parent.zglaszaj = new Zglaszaj(parent, odstep);
	}

	@Override
	public Object getEventParams() {
		// TODO Auto-generated method stub
		return null;
	}
}