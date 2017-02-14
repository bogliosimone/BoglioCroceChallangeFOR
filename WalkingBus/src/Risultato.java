import java.util.ArrayList;
import java.util.List;

public class Risultato {
	private List<Strada> strade;
	private int num_foglie;
	private float pericolo;
	
	public Risultato(){
		this.strade = new ArrayList<>();
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
	
	public float getPericolo() {
		return pericolo;
	}
	
	public void setPericolo(float pericolo) {
		this.pericolo = pericolo;
	}
	
	
}
