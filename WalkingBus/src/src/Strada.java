package src;

public class Strada {
	private Punto partenza;
	private Punto arrivo;
	
	public Strada(Punto partenza, Punto arrivo){
		this.partenza = partenza;
		this.arrivo = arrivo;
	}

	public Punto getPartenza() {
		return partenza;
	}

	public void setPartenza(Punto partenza) {
		this.partenza = partenza;
	}

	public Punto getArrivo() {
		return arrivo;
	}

	public void setArrivo(Punto arrivo) {
		this.arrivo = arrivo;
	}
	
}
