package src;
import java.util.ArrayList;
import java.util.List;

public class Fermata {
	private Punto punto_attuale;
	private Fermata fermata_precedente;
	private List<Fermata> fermate_successive;
	private double distanza_parziale;
	private double pericolo_parziale;
	
	public Fermata(Punto punto, Fermata fermata, double distanza, double pericolo){
		this.punto_attuale = punto;
		this.fermata_precedente = fermata;
		this.distanza_parziale = distanza;
		this.pericolo_parziale = pericolo;
		this.fermate_successive = new ArrayList<>();
	}

	public Punto getPunto_attuale() {
		return punto_attuale;
	}

	public void setPunto_attuale(Punto punto_attuale) {
		this.punto_attuale = punto_attuale;
	}

	public Fermata getFermata_precedente() {
		return fermata_precedente;
	}

	public void setFermata_precedente(Fermata fermata_precedente) {
		this.fermata_precedente = fermata_precedente;
	}

	public List<Fermata> getFermate_successive() {
		return fermate_successive;
	}

	public void aggiungiFermata(Fermata fermata){
		this.fermate_successive.add(fermata);
	}

	public double getDistanza_parziale() {
		return distanza_parziale;
	}

	public double getPericolo_parziale() {
		return pericolo_parziale;
	}

	public void setFermate_successive(List<Fermata> fermate_successive) {
		this.fermate_successive = fermate_successive;
	}

	public void setDistanza_parziale(double distanza_parziale) {
		this.distanza_parziale = distanza_parziale;
	}

	public void setPericolo_parziale(double pericolo_parziale) {
		this.pericolo_parziale = pericolo_parziale;
	}
	
	
}
