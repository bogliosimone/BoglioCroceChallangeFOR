package src;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Solver {
	private List<Punto> punti;
	private List<Ramo> tronco;
	private Punto scuola; //da inizializzare
	private double[][] distanze;
	private double[][] pericolosita;  // da inizializzare
	
	private double alpha; //da inizializzare
	
	public Solver(List<Punto> punti, double[][]pericolosita, double alpha){
		this.punti = punti;
		this.pericolosita = pericolosita;
		this.scuola = this.punti.remove(0);
		this.alpha = alpha;
		calcolaDistanze();
		ordina2();
	}
	
	private void ordina(){
		Punto punto, p1, p2;
		int i;
		int j;
		for(i=0 ; i<this.punti.size()-1 ; i++){
			punto = this.punti.get(i);
			for(j=i+1 ; j<this.punti.size() ; j++){
				p1 = this.punti.get(j);
				if(p1.getDistanza_origine()<=punto.getDistanza_origine()){
					punto = p1;
				}
			}
			this.punti.remove(punto);
			this.punti.add(0, punto);
		}
		for(Punto p: this.punti){
			System.out.println(p);
		}
	}
	
	private void ordina2(){
		Collections.sort(this.punti, (p1, p2)->new Double(p1.getDistanza_origine()).compareTo(new Double(p2.getDistanza_origine())));
		
	}
	
	public Risultato calcola(){
		Risultato risultatoMigliore = null;
		Risultato risultato;
		Punto temp;
		int i;
		for(i=0 ; i<this.punti.size() ; i++){
			risultato = new Risultato();
			this.tronco = new ArrayList<>();
			for(Punto p: this.punti){
				if(this.tronco.isEmpty()){
					iniziaNuovoRamo(p, risultato);
				}
				else if(!inserisciPuntoSuFoglieEsistenti(p, risultato)){
						inserisciPuntoComeNuovaFoglia(p, risultato);
				}
			}
			if(risultatoMigliore == null){
				risultatoMigliore = risultato;
			}
			else{
				if(risultato.getNum_foglie() < risultatoMigliore.getNum_foglie()){
					risultatoMigliore = risultato;
				}
				else if(risultato.getNum_foglie() == risultatoMigliore.getNum_foglie() && risultato.getPericolo() < risultatoMigliore.getPericolo()){
					risultatoMigliore = risultato;
				}
			}
			temp = this.punti.remove(0);
			this.punti.add(temp);
		}	
		return risultatoMigliore;
	}
	
	private void calcolaDistanze(){
		int num;
		num = this.punti.size() + 1;
		this.distanze = new double[num][num];
		Coordinate coord1;
		Coordinate coord2;
		int x1, x2;
		int y1, y2;
		for(Punto p1: this.punti){
			for(Punto p2: this.punti){
				if(p1!=p2){
					coord1 = p1.getCoord();
					coord2 = p2.getCoord();
					x1 = coord1.getX();
					y1 = coord1.getY();
					x2 = coord2.getX();
					y2 = coord2.getY();
					this.distanze[p1.getId()][p2.getId()] = Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
				}
			}
		}
	}
	
	private void iniziaNuovoRamo(Punto punto, Risultato risultato){
		Fermata fermata = new Fermata(punto, null, punto.getDistanza_origine(), this.pericolosita[punto.getId()][0]);
		Ramo ramo = new Ramo(fermata);
		ramo.aggiungiFoglia(fermata);
		this.tronco.add(ramo);
		risultato.aggiungiStrade(new Strada(this.scuola, punto));
		risultato.setNum_foglie(risultato.getNum_foglie()+1);
		risultato.setPericolo(risultato.getPericolo() + this.pericolosita[punto.getId()][0]);
	}
	
	private boolean inserisciPuntoSuFoglieEsistenti(Punto punto, Risultato risultato){
		Combinazione combinazione = null;
		Combinazione combinazioneTemp = null;
		for(Ramo r: this.tronco){
			for(Fermata f: r.getFoglie()){
				double distanza_parziale;
				double distanza_punto;
				double pericolo;
				distanza_parziale = f.getDistanza_parziale();
				pericolo = f.getPericolo_parziale();
				distanza_punto = distanza_parziale + this.distanze[punto.getId()][f.getPunto_attuale().getId()];
				if(distanza_punto <= punto.getDistanza_origine()*alpha){
					pericolo = pericolo + this.pericolosita[punto.getId()][f.getPunto_attuale().getId()];
					combinazioneTemp = new Combinazione(r, f, pericolo, punto, distanza_punto);
				}
				if(combinazione == null){
					combinazione = combinazioneTemp;
				}
				else if(combinazione.controlloMigliore(combinazioneTemp)){
					combinazione = combinazioneTemp;
				}
			}
		}
		if(combinazione != null){
			Fermata fermata_precedente;
			fermata_precedente = combinazione.getFermata();
			Fermata fermata = new Fermata(punto, fermata_precedente, combinazione.getDistanza(), combinazione.getPericolo());
			fermata_precedente.aggiungiFermata(fermata);
			combinazione.getRamo().sostituisciFoglia(fermata_precedente, fermata);
			risultato.aggiungiStrade(new Strada(fermata_precedente.getPunto_attuale(), punto));
			risultato.setPericolo(risultato.getPericolo() + this.pericolosita[punto.getId()][fermata_precedente.getPunto_attuale().getId()]);
			return true;
		}
		return false;
	}
	
	private void inserisciPuntoComeNuovaFoglia(Punto punto, Risultato risultato){
		List<Fermata> visitate;
		Combinazione combinazione = null;
		Combinazione combinazioneTemp = null;
		visitate = new ArrayList<>();
		
		for(Ramo r: this.tronco){
			for(Fermata f: r.getFoglie()){
				double distanza_parziale;
				double distanza_punto;
				double pericolo;
				Fermata fermata_temp;
				fermata_temp = f.getFermata_precedente();
				while(fermata_temp != null){
					if(!visitate.contains(fermata_temp)){
						distanza_parziale = fermata_temp.getDistanza_parziale();
						pericolo = fermata_temp.getPericolo_parziale();
						distanza_punto = distanza_parziale + this.distanze[punto.getId()][fermata_temp.getPunto_attuale().getId()];
						if(distanza_punto <= punto.getDistanza_origine()*alpha){
							pericolo = pericolo + this.pericolosita[punto.getId()][fermata_temp.getPunto_attuale().getId()];
							combinazioneTemp = new Combinazione(r, fermata_temp, pericolo, punto, distanza_punto);
						}
						if(combinazione == null){
							combinazione = combinazioneTemp;
						}
						else if(combinazione.controlloMigliore(combinazioneTemp)){
							combinazione = combinazioneTemp;
						}
						
						visitate.add(fermata_temp);
					}
					fermata_temp = fermata_temp.getFermata_precedente();
				}
			}
		}
	
		if(combinazione != null){
			Fermata fermata_precedente;
			fermata_precedente = combinazione.getFermata();
			Fermata fermata = new Fermata(punto, fermata_precedente, combinazione.getDistanza(), combinazione.getPericolo());
			fermata_precedente.aggiungiFermata(fermata);
			combinazione.getRamo().aggiungiFoglia(fermata);
			risultato.aggiungiStrade(new Strada(fermata_precedente.getPunto_attuale(), punto));
			risultato.setNum_foglie(risultato.getNum_foglie()+1);
			risultato.setPericolo(risultato.getPericolo() + this.pericolosita[punto.getId()][fermata_precedente.getPunto_attuale().getId()]);
		}
		else{
			iniziaNuovoRamo(punto, risultato);
		}
	}
	
}
