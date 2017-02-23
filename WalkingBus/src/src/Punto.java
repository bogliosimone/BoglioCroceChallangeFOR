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

	
	public void calcolaAngolo(Punto root) {
		double diffx=(double)this.getCoord().getX()-(double)root.getCoord().getX();
		double diffy=(double)this.getCoord().getY()-(double)root.getCoord().getY();
	    double angle = Math.toDegrees(Math.atan2(diffy,diffx));
	    if(angle < 0){
	        angle += 360;
	    }
	    this.angolo= angle;
	}
	
	public double calcolaAngoloRelativo(Punto point) {
		double diffx=(double)this.getCoord().getX()-(double)point.getCoord().getX();
		double diffy=(double)this.getCoord().getY()-(double)point.getCoord().getY();
	    double angle = Math.toDegrees(Math.atan2(diffy,diffx));
	    if(angle < 0){
	        angle += 360;
	    }
	    return angle;
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
