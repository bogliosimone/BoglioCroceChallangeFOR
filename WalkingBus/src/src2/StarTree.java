package src2;

import java.util.ArrayList;

public class StarTree {
	private ArrayList<Branch> tree;
	private Node root;
	
	public StarTree(){
		this.tree = new ArrayList<Branch>();
	}
	
	private StarTree(Node root, ArrayList<Branch> tree){
		this();
		this.root = root;
		for(Branch b: tree)
			this.tree.add(b.cloneBranch());
	}
	
	public void addBranch(Branch newBranch){
		tree.add(newBranch);
	}
	
	public ArrayList<Branch> getTree() {
		return tree;
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	public int numOfBranch(){
		return tree.size();
	}
	
	//clone the star tree (shallow copy)
	public StarTree cloneStarTree(){
		return new StarTree(this.root, this.tree);
	}
	
}
