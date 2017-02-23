package src2;

import java.util.ArrayList;
import java.util.List;

import src.Punto;

public class Node {
	private int id;
	private int x;
	private int y;
	private double distanceRoot;
	private double maxDistanceRoot;
	private List<Node> neighborsNode;
	private double angle; 
	
	public Node(int id, int x, int y, double distanceRoot, double alpha) {
		super();
		this.id = id;
		this.x = x;
		this.y = y;
		this.distanceRoot = distanceRoot;
		this.maxDistanceRoot = distanceRoot * alpha;
		neighborsNode = new ArrayList<Node>();
	}
	
	public double getDistanceRoot() {
		return distanceRoot;
	}

	public void setDistanceRoot(double distaceRoot) {
		this.distanceRoot = distaceRoot;
	}

	public double getMaxDistanceRoot() {
		return maxDistanceRoot;
	}

	public void setMaxDistanceRoot(double maxDistanceRoot) {
		this.maxDistanceRoot = maxDistanceRoot;
	}

	public List<Node> getNeighborsNode() {
		return neighborsNode;
	}

	public void setNeighborsNode(List<Node> neighborNode) {
		this.neighborsNode = neighborNode;
	}

	public int getId() {
		return id;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void addNeighborNode(Node n){
		neighborsNode.add(n);
	}

	public double calcolaAngolo(Node root) {
		double diffx=(double)this.x-(double)root.x;
		double diffy=(double)this.y-(double)root.y;
	    double angle = Math.toDegrees(Math.atan2(diffy,diffx));
	    if(angle < 0){
	        angle += 360;
	    }
	    this.angle= angle;
	    return angle;
	}
	
	public double getAngle() {
		return angle;
	}

	public static double distanceNodes(Node n1, Node n2){
		return Math.sqrt((((double)n2.x-(double)n1.x)*((double)n2.x-(double)n1.x) + ((double)n2.y-(double)n1.y)*((double)n2.y-(double)n1.y)));
	}

	@Override
	public String toString() {
		return "Node [id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public static void printNodes(List<Node> listNode){
		String s = new String();
		for(Node k: listNode)
			s += k.getId()+" - ";
		System.out.println(s);
	}
	
	
}
