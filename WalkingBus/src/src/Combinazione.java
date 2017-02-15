package src;

public class Combinazione {
	private Ramo ramo;
	private Fermata fermata;
	private float pericolo;
	private Punto punto;
	private float distanza;
	
	public Combinazione(Ramo ramo, Fermata fermata, float pericolo, Punto punto, float distanza){
		this.ramo = ramo;
		this.fermata = fermata;
		this.pericolo = pericolo;
		this.punto = punto;
		this.distanza = distanza;
	}
	
	public boolean controlloMigliore(Combinazione combinazione){
		if(combinazione.getPericolo() < this.pericolo){
			return true;
		}
		return false;
	}

	public Fermata getFermata() {
		return fermata;
	}

	public void setFermata(Fermata fermata) {
		this.fermata = fermata;
	}

	public float getPericolo() {
		return pericolo;
	}

	public void setPericolo(float pericolo) {
		this.pericolo = pericolo;
	}

	public Punto getPunto() {
		return punto;
	}

	public void setPunto(Punto punto) {
		this.punto = punto;
	}

	public float getDistanza() {
		return distanza;
	}

	public void setDistanza(float distanza) {
		this.distanza = distanza;
	}

	public Ramo getRamo() {
		return ramo;
	}

	public void setRamo(Ramo ramo) {
		this.ramo = ramo;
	}
	
	
}
