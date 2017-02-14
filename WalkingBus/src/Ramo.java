import java.util.List;

public class Ramo {
	private Fermata prima_fermata;
	private List<Punto> foglie;
	
	public Ramo(Fermata fermata){
		this.prima_fermata = fermata;
	}

	public Fermata getPrima_fermata() {
		return prima_fermata;
	}

	public void setPrima_fermata(Fermata prima_fermata) {
		this.prima_fermata = prima_fermata;
	}

	public List<Punto> getFoglie() {
		return foglie;
	}

	public void aggiungiFoglie(Punto foglia) {
		this.foglie.add(foglia);
	}
	
	
}
