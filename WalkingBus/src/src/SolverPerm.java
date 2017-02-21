package src;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SolverPerm {
	private List<Punto> punti;
	private List<Ramo> tronco;
	private Punto scuola;
	private double[][] distanze;
	private double[][] pericolosita; 
	private Risultato risultatoMigliore = null;
	
	private double alpha; 
	
	public SolverPerm(List<Punto> punti, double[][]pericolosita, double alpha){
		this.punti = punti;
		this.pericolosita = pericolosita;
		this.scuola = this.punti.remove(0);
		this.alpha = alpha;
		calcolaDistanze();
		for(Punto p: this.punti){
			p.calcolaAngolo(this.scuola);
		}
	}
	
	private void ordinaDistanza(){
		Collections.sort(this.punti, (p1, p2)->new Double(p1.getDistanza_origine()).compareTo(new Double(p2.getDistanza_origine())));
	}
	
	private void ordinaAngolo(){
		Collections.sort(this.punti, (p1, p2)->new Double(p1.getAngolo()).compareTo(new Double(p2.getAngolo())));
		for(Punto p: this.punti){
			System.out.println(p.getId() + " " + p.getAngolo());
		}
	}
	
	public Risultato calcola(){
		long start = System.currentTimeMillis();
		
		Risultato risultato;
		Punto temp;
		int j, i;
		ordinaDistanza();
		Permutazione perm = new Permutazione((ArrayList<Punto>) this.punti,10);
		this.punti = perm.getNextPermutation(); 
		while(this.punti!=null){
			risultato = new Risultato();
			this.tronco = new ArrayList<>();
			for(Punto p: this.punti){
				if(this.tronco.isEmpty()){
					iniziaNuovoRamo(p, risultato);
				}
				else if(!inserisciPuntoSuFoglieEsistenti(p, risultato)){
						if(!inserisciPuntoComeNuovaFoglia(p, risultato)){
							break;
						}
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
			this.punti = perm.getNextPermutation();
		}
		long end = System.currentTimeMillis();
		System.out.println("Exe time: "+(end - start) + " ms");
		return risultatoMigliore;
	}
	
	private void calcolaDistanze(){
		int num;
		num = this.punti.size() + 1;
		this.distanze = new double[num][num];
		Coordinate coord1;
		Coordinate coord2;
		double x1, x2;
		double y1, y2;
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
	
	private boolean iniziaNuovoRamo(Punto punto, Risultato risultato){
		Fermata fermata = new Fermata(punto, null, punto.getDistanza_origine(), this.pericolosita[punto.getId()][0]);
		Ramo ramo = new Ramo(fermata);
		ramo.aggiungiFoglia(fermata);
		this.tronco.add(ramo);
		risultato.aggiungiStrade(new Strada(this.scuola, punto));
		risultato.setNum_foglie(risultato.getNum_foglie()+1);
		if(this.risultatoMigliore != null && risultato.getNum_foglie() > this.risultatoMigliore.getNum_foglie()){
			return false;
		}
		risultato.setPericolo(risultato.getPericolo() + this.pericolosita[punto.getId()][0]);
		return true;
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
	
	private boolean inserisciPuntoComeNuovaFoglia(Punto punto, Risultato risultato){
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
			if(this.risultatoMigliore != null && risultato.getNum_foglie() > this.risultatoMigliore.getNum_foglie()){
				return false;
			}
			risultato.setPericolo(risultato.getPericolo() + this.pericolosita[punto.getId()][fermata_precedente.getPunto_attuale().getId()]);
		}
		else{
			iniziaNuovoRamo(punto, risultato);
		}
		return true;
	}
	
	private void spostaFoglie(Risultato risultato){
		Combinazione combinazioneTemp = null;
		for(Ramo r: this.tronco){
			for(Fermata f: r.getFoglie()){
				Combinazione combinazione = null;
				for(Fermata f2: r.getFoglie()){
					if(f!=f2){
						double distanza2 = f2.getDistanza_parziale();
						double pericolo = f2.getPericolo_parziale();
						distanza2 = distanza2 + this.distanze[f.getPunto_attuale().getId()][f2.getPunto_attuale().getId()];
						if(distanza2 <= f.getPunto_attuale().getDistanza_origine()*alpha){
							pericolo = pericolo + this.pericolosita[f.getPunto_attuale().getId()][f2.getPunto_attuale().getId()];
							combinazioneTemp = new Combinazione(r, f2, pericolo, f.getPunto_attuale(), distanza2);
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
					Punto punto = combinazione.getPunto();
					Fermata fermata = new Fermata(punto, fermata_precedente, combinazione.getDistanza(), combinazione.getPericolo());
					fermata_precedente.aggiungiFermata(fermata);
					combinazione.getRamo().getFoglie().remove(f);
					combinazione.getRamo().sostituisciFoglia(fermata_precedente, fermata);
					Fermata nuova_fermata = f.getFermata_precedente();
					nuova_fermata.getFermate_successive().remove(f);
					risultato.rimuoviStrade(nuova_fermata.getPunto_attuale(), f.getPunto_attuale());
					risultato.aggiungiStrade(new Strada(fermata_precedente.getPunto_attuale(), punto));
					risultato.setPericolo(risultato.getPericolo() + this.pericolosita[punto.getId()][fermata_precedente.getPunto_attuale().getId()]);
				}
			}
		}
	}

}

