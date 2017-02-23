package src;

public class Combinazione {
	private Ramo ramo;
	private Fermata fermata;
	private double pericolo;
	private Punto punto;
	private double distanza;
	
	public Combinazione(Ramo ramo, Fermata fermata, double pericolo, Punto punto, double distanza){
		this.ramo = ramo;
		this.fermata = fermata;
		this.pericolo = pericolo;
		this.punto = punto;
		this.distanza = distanza;
	}
	
	public boolean controlloMigliore(Combinazione combinazione){
		double deltaOld=Math.abs(this.fermata.getPunto_attuale().getAngolo()-punto.getAngolo());
		double deltaNew=Math.abs(combinazione.fermata.getPunto_attuale().getAngolo()-punto.getAngolo());
		double disNew = combinazione.getDistanza();
		double disOld = this.distanza;
		double alphaOld = this.fermata.getPunto_attuale().getAngolo();
		double alphaNew = combinazione.fermata.getPunto_attuale().getAngolo();
		double gammaOld = punto.calcolaAngoloRelativo(this.fermata.getPunto_attuale())-alphaOld;
		double gammaNew = punto.calcolaAngoloRelativo(combinazione.fermata.getPunto_attuale()) - alphaNew;
		//deltaNew = Math.abs(gammaNew);
		//deltaOld = Math.abs(gammaOld);
		//if(combinazione.getDistanza() < this.distanza){
		if(deltaNew<deltaOld){
			return true;
		}
		if(combinazione.getDistanza() == this.distanza && combinazione.getPericolo() < this.pericolo){
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

	public double getPericolo() {
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

	public double getDistanza() {
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

	public void setPericolo(double pericolo) {
		this.pericolo = pericolo;
	}

	public void setDistanza(double distanza) {
		this.distanza = distanza;
	}
	
	
}
