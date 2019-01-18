package smo;
/**
 * @author Dariusz Pierzchala
 * 
 * Description: Zdarzenie końcowe aktywności gniazda obsługi. Kończy obsługę przez losowy czas obiektów - zgłoszeń.
 */

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimEventSemaphore;
import dissimlab.simcore.SimParameters.SimDateField;

public class ZakonczObsluge extends BasicSimEvent<Smo, Zgloszenie>
{
    private Smo smoParent;
    public int numerObslugi;

    public ZakonczObsluge(Smo parent, double delay, Zgloszenie zgl) throws SimControlException
    {
    	super(parent, delay, zgl);
        this.smoParent = parent;
    }
    public ZakonczObsluge(Smo parent, double delay, Zgloszenie zgl,int numer) throws SimControlException
    {
    	super(parent, delay, zgl);
        this.smoParent = parent;
        this.numerObslugi = numer;
    }

    public ZakonczObsluge(Smo parent, SimEventSemaphore semafor, Zgloszenie zgl) throws SimControlException
    {
    	super(parent, semafor, zgl);
        this.smoParent = parent;
    }
    
	@Override
	protected void onInterruption() throws SimControlException {
		// TODO
	}

	@Override
	protected void onTermination() throws SimControlException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void stateChange() throws SimControlException {
  		smoParent.setWolne(true);
        //System.out.println(simTime()+" - "+simDate(SimDateField.HOUR24)+" - "+simDate(SimDateField.MINUTE)+" - "+simDate(SimDateField.SECOND)+" - "+simDate(SimDateField.MILLISECOND)+": SMO- (Kanał: "+this.numerObslugi+")Koniec obsługi zgl. nr: " + transitionParams.getTenNr());
        System.out.println(simTime()+" - "+simDate(SimDateField.HOUR24)+" - "+simDate(SimDateField.MINUTE)+" - "+simDate(SimDateField.SECOND)+" - "+simDate(SimDateField.MILLISECOND)+": SMO- (Kanał: "+this.numerObslugi+")Koniec obsługi zgl. nr: " + transitionParams.getTenNr());
        smoParent.wolne_kanaly.add(this.numerObslugi);
      	// Zaplanuj dalsza obsługe w tym gnieździe
      	if (smoParent.liczbaZgl() > 0)
       	{
      		int nowynumer = smoParent.wolne_kanaly.getFirst();
			 smoParent.wolne_kanaly.removeFirst();
      		smoParent.rozpocznijObsluge = new RozpocznijObsluge(smoParent,nowynumer);        	
       	}	
	}

	@Override
	public Object getEventParams() {
		// TODO Auto-generated method stub
		return null;
	}
}