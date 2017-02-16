package src;

public class Punto {
	private Coordinate coord;
	private int id;
	private double distanza_origine;
	
	public Punto(Coordinate coord, int id, double distanza_origine){
		this.coord = coord;
		this.distanza_origine = distanza_origine;
		this.id = id;
	}

	public Coordinate getCoord() {
		return coord;
	}

	public int getId() {
		return id;
	}

	public double getDistanza_origine() {
		return distanza_origine;
	}

	@Override
	public String toString() {
		return "Punto [coord=" + coord + ", id=" + id + ", distanza_origine=" + distanza_origine + "]";
	}
	
	
}
