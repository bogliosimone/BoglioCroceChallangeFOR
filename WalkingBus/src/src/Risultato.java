package src;
import java.util.ArrayList;
import java.util.List;

public class Risultato {
	private List<Strada> strade;
	private int num_foglie;
	private double pericolo;
	
	public Risultato(){
		this.strade = new ArrayList<>();
		this.num_foglie = 0;
		this.pericolo = 0;
	}
	
	public List<Strada> getStrade() {
		return strade;
	}
	
	public void aggiungiStrade(Strada strada) {
		this.strade.add(strada);
	}
	
	public int getNum_foglie() {
		return num_foglie;
	}
	
	public void setNum_foglie(int num_foglie) {
		this.num_foglie = num_foglie;
	}
	
	public double getPericolo() {
		return pericolo;
	}
	
	public void setPericolo(double pericolo) {
		this.pericolo = pericolo;
	}
	
	
}
