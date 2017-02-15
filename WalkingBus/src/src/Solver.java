package src;
import java.util.ArrayList;
import java.util.List;

public class Solver {
	private List<Punto> punti;
	private List<Ramo> tronco;
	private Punto scuola; //da inizializzare
	
	private static final float ALPHA = 1;
	
	public Solver(){
		this.punti = new ArrayList<>();
		this.tronco = new ArrayList<>();
	}
	
	public void calcola(){
		Risultato risultato;
		risultato = new Risultato();
		for(Punto p: this.punti){
			if(this.tronco.isEmpty()){
				iniziaNuovoRamo(p);
				risultato.aggiungiStrade(new Strada(this.scuola, p));
				risultato.setNum_foglie(risultato.getNum_foglie()+1);
			}
			else{
				if(!inserisciPuntoSuFoglieEsistenti(p, risultato)){
					inserisciPuntoComeNuovaFoglia(p, risultato);
				}
			}
		}
	}
	
	private void iniziaNuovoRamo(Punto punto){
		Fermata fermata = new Fermata(punto, null, punto.getDistanza_origine());
		Ramo ramo = new Ramo(fermata);
		ramo.aggiungiFoglia(fermata);
		this.tronco.add(ramo);
	}
	
	private boolean inserisciPuntoSuFoglieEsistenti(Punto punto, Risultato risultato){
		for(Ramo r: this.tronco){
			for(Fermata f: r.getFoglie()){
				float distanza_parziale;
				float distanza_punto;
				distanza_parziale = f.getDistanza_parziale();
				distanza_punto = distanza_parziale + 10000; ////bisogna capire come salvare le distanze tra i vari nodi
				if(distanza_punto <= punto.getDistanza_origine()*ALPHA){
					Fermata fermata = new Fermata(punto, f, distanza_punto);
					f.aggiungiFermata(fermata);
					r.sostituisciFoglia(f, fermata);
					risultato.aggiungiStrade(new Strada(f.getPunto_attuale(), punto));
					return true;
				}
			}
		}
		return false;
	}
	
	//miglioramento: escludere nodi gi� visitati
	private void inserisciPuntoComeNuovaFoglia(Punto punto, Risultato risultato){
		for(Ramo r: this.tronco){
			for(Fermata f: r.getFoglie()){
				float distanza_parziale;
				float distanza_punto;
				Fermata fermata_temp;
				fermata_temp = f.getFermata_precedente();
				while(fermata_temp != null){
					distanza_parziale = fermata_temp.getDistanza_parziale();
					distanza_punto = distanza_parziale + 10000; ////bisogna capire come salvare le distanze tra i vari nodi
					if(distanza_punto <= punto.getDistanza_origine()*ALPHA){
						Fermata fermata = new Fermata(punto, fermata_temp, distanza_punto);
						fermata_temp.aggiungiFermata(fermata);
						r.aggiungiFoglia(fermata);
						risultato.aggiungiStrade(new Strada(fermata_temp.getPunto_attuale(), punto));
						return;
					}
					fermata_temp = fermata_temp.getFermata_precedente();
				}
			}
		}
		iniziaNuovoRamo(punto);
	}
	
}
