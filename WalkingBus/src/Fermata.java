import java.util.List;

public class Fermata {
	private Punto punto_attuale;
	private Fermata fermata_precedente;
	private List<Fermata> fermate_successive;
	private float distanza_parziale;
	private float pericolo_parziale;
	
	public Fermata(Punto punto, Fermata fermata, float distanza){
		this.punto_attuale = punto;
		this.fermata_precedente = fermata;
		this.distanza_parziale = distanza;
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

	public float getDistanza_parziale() {
		return distanza_parziale;
	}

	public void setDistanza_parziale(float distanza_parziale) {
		this.distanza_parziale = distanza_parziale;
	}

	public float getPericolo_parziale() {
		return pericolo_parziale;
	}

	public void setPericolo_parziale(float pericolo_parziale) {
		this.pericolo_parziale = pericolo_parziale;
	}
	
	
}
