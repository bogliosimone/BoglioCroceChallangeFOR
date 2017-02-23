package src2;

import java.util.ArrayList;
import java.util.List;

public class Stop {
	private Node node;
	private Node previousNode;
	private Node followingNode;
	private Stop previousStop;
	private double partialDistance;
	private double distance;
	private double danger;
	private List<Stop> followingStop;
	
	public Stop(Node node, Node followingNode){
		this.node = node;
		this.partialDistance = 0;
		this.followingNode = followingNode;
		this.followingStop = new ArrayList<>();
	}
	
	public Stop getFirstFollowing(){
		return this.followingStop.get(0);
	}
	
	public Node getNode() {
		return node;
	}
	
	public void addStop(Stop stop){
		this.followingStop.add(stop);
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public Stop getPreviousStop() {
		return previousStop;
	}

	public void setPreviousStop(Stop previousStop) {
		this.previousStop = previousStop;
	}

	public List<Stop> getFollowingStop() {
		return followingStop;
	}

	public void setFollowingStop(List<Stop> followingStop) {
		this.followingStop = followingStop;
	}

	public Node getPreviousNode() {
		return previousNode;
	}

	public void setPreviousNode(Node previousNode) {
		this.previousNode = previousNode;
	}

	public Node getFollowingNode() {
		return followingNode;
	}

	public void setFollowingNode(Node followingNode) {
		this.followingNode = followingNode;
	}

	public double getPartialDistance() {
		return partialDistance;
	}

	public void setPartialDistance(double partialDistance) {
		this.partialDistance = partialDistance;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getDanger() {
		return danger;
	}

	public void setDanger(double danger) {
		this.danger = danger;
	}
}
