package src2;

import java.util.ArrayList;
import java.util.List;

public class Node {
	private int id;
	private int x;
	private int y;
	private double distanceRoot;
	private double maxDistanceRoot;
	private List<Node> neighborsNode;
	
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

	public static double distanceNodes(Node n1, Node n2){
		return Math.sqrt((n2.x-n1.x)*(n2.x-n1.x) + (n2.y-n1.y)*(n2.y-n1.y));
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
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	
	
	
}
