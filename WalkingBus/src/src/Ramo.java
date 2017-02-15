package src;
import java.util.List;

public class Ramo {
	private Fermata prima_fermata;
	private List<Fermata> foglie;
	
	public Ramo(Fermata fermata){
		this.prima_fermata = fermata;
	}

	public Fermata getPrima_fermata() {
		return prima_fermata;
	}

	public void setPrima_fermata(Fermata prima_fermata) {
		this.prima_fermata = prima_fermata;
	}

	public List<Fermata> getFoglie() {
		return foglie;
	}

	public void aggiungiFoglia(Fermata foglia) {
		this.foglie.add(foglia);
	}
	
	public void sostituisciFoglia(Fermata vecchia, Fermata nuova){
		int pos;
		pos = this.foglie.indexOf(vecchia);
		this.foglie.remove(pos);
		this.foglie.add(pos, nuova);
	}
	
	
}
