package src;

public class Punto {
	private Coordinate coord;
	private int id;
	private double distanza_origine;
	private double angolo;
	
	public Punto(Coordinate coord, int id, double distanza_origine){
		this.coord = coord;
		this.distanza_origine = distanza_origine;
		this.id = id;
	}

	public void calcolaAngolo(Punto p){
		double ac;
		double ab;
		ac = Math.sqrt(((double)p.getCoord().getX()-(double)this.coord.getX())*((double)p.getCoord().getX()-(double)this.coord.getX()) + ((double)p.getCoord().getY()-(double)this.coord.getY())*((double)p.getCoord().getY()-(double)this.coord.getY()));  
		ab = this.coord.getX() - p.getCoord().getX();
		this.angolo = Math.acos(ab/ac);
		if(this.getCoord().getY()-p.getCoord().getY()<=0){
			this.angolo = this.angolo + Math.PI;
		}
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

	public double getAngolo() {
		return angolo;
	}

	public void setAngolo(double angolo) {
		this.angolo = angolo;
	}
	
	
}
