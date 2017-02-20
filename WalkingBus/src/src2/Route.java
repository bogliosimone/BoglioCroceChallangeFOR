package src2;

public class Route {
	Node start;
	Node end;
	
	public Route(Node start, Node end) {
		super();
		this.start = start;
		this.end = end;
	}
	
	public Node getStart() {
		return start;
	}
	public Node getEnd() {
		return end;
	}
}
