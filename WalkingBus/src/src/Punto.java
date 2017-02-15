package src;

public class Punto {
	private Coordinate coord;
	private int id;
	private float distanza_origine;
	private float[] distanza_punti;
	
	public Punto(Coordinate coord, int id, float distanza_origine, float[] distanza_punti){
		this.coord = coord;
		this.distanza_origine = distanza_origine;
		this.distanza_punti = distanza_punti;
		this.id = id;
	}

	public Coordinate getCoord() {
		return coord;
	}

	public void setCoord(Coordinate coord) {
		this.coord = coord;
	}

	public float getDistanza_origine() {
		return distanza_origine;
	}

	public void setDistanza_origine(float distanza_origine) {
		this.distanza_origine = distanza_origine;
	}

	public float[] getDistanza_punti() {
		return distanza_punti;
	}

	public void setDistanza_punti(float[] distanza_punti) {
		this.distanza_punti = distanza_punti;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
