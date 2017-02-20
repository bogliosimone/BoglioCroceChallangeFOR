package src2;

import java.util.ArrayList;

public class Branch {
	private ArrayList<Node> branch;
	private double currentDistance;
	
	Branch(Node n){
		this.branch = new ArrayList<Node>();
		this.branch.add(n);
		this.currentDistance = n.getDistanceRoot();
	}
	
	// private constructor used to clone the branch (shallow copy)
	private Branch(ArrayList<Node> branch, double currentDistance){
		this.branch = branch;
		this.currentDistance = currentDistance;
	}
	
	public Branch addNode(Node newNode){
		Node lastNode = branch.get(branch.size()-1);
		double d_newNode_lastNode = Node.distanceNodes(newNode, lastNode); 
		branch.add(newNode);
		currentDistance += d_newNode_lastNode;
		return this;
	}

	public boolean canAddNode(Node newNode){
		Node lastNode = branch.get(branch.size()-1);
		double d_newNode_lastNode = Node.distanceNodes(newNode, lastNode); 
		double newTotalDistance = currentDistance + d_newNode_lastNode;
		if(newTotalDistance <= newNode.getMaxDistanceRoot())
			return true; //alpha*distance is respected, add the new node 
		else
			return false; //alpha*distance root is not respected
	}
	
	//clone the branch (shallow copy)
	@SuppressWarnings("unchecked")
	public Branch cloneBranch(){	
		return new Branch((ArrayList<Node>) this.branch.clone(),this.currentDistance);
	}
	
	public ArrayList<Node> getBranch() {
		return branch;
	}

	public double getCurrentDistance() {
		return currentDistance;
	}

	@Override
	public String toString() {
		return "Branch [branch=" + branch + ", currentDistance=" + currentDistance + "]";
	}
	
	
	
}
