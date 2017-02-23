package src2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import src.Punto;
import src.Risultato;
import view.DrawGraph;

public class Solver2 {
	private ArrayList<Node> nodes;
	private Node root;
	private double[][] distances;
	private double[][] dangers;
	private boolean[][] arcs;
	private double alpha;
	private int n;
	private Map<Integer,Node> mapNodes;
	private int bestBound;
	private StarTree bestTree;
	double ntree=1;
	
	public Solver2(List<Punto> points, double[][]dangers, double alpha, int n){
		this.dangers = dangers;
		this.alpha = alpha;
		this.n = n + 1; //nodes + root
		//convert Punto in Node
		this.nodes = initNodes(points,alpha);
		//do these before remove root
		this.mapNodes = initMapNodes();
		this.distances = initDistances();
		//now remove root from list
		this.root = this.nodes.get(0);
		this.nodes.remove(0);
		//do these after remove root
		this.arcs = initPossibleArcs();
		initNeighborsNode();
	}
	
	private void start(){
		bestBound = Integer.MAX_VALUE;
		for(Node n: nodes){
			System.out.println("start tree from :"+ n);
			StarTree newTree = new StarTree();
			newTree.setRoot(root);
			Branch newBranch= new Branch(root);
			newBranch.addNode(n);
			ArrayList<Node> newListNodes = cloneListNode(nodes);
			newListNodes.remove(n);
			printNodes(newListNodes);
			expandBranch(n,newBranch,newListNodes,newTree);
		}
	}
	
	private void expandBranch(Node lastNode, Branch branch, ArrayList<Node> listNode, StarTree starTree) {
		List<Node> nearNodes = lastNode.getNeighborsNode();
		boolean endBranch = true;
		for(Node n:nearNodes){
			if(branch.canAddNode(n)==true && listNode.contains(n)){
				endBranch =false;
				Branch newBranch = branch.cloneBranch().addNode(n);
				ArrayList<Node> newListNodes = cloneListNode(listNode);
				newListNodes.remove(n);
				expandBranch(n,newBranch,newListNodes, starTree);
			}
		}
		if(endBranch == true){
			StarTree newTree = starTree.cloneStarTree();
			newTree.addBranch(branch);
			startNewBranch(listNode, newTree);
			ntree++;
		}
	}
		


	private void startNewBranch(ArrayList<Node> listNode, StarTree starTree) {
		if(starTree.numOfBranch()>= bestBound){
			return;
		}
		boolean endNode = true;
		for(Node n: listNode){
			endNode= false;
			Branch newBranch = new Branch(root);
			newBranch.addNode(n);
			ArrayList<Node> newListNodes = cloneListNode(listNode);
			newListNodes.remove(n);
			expandBranch(n,newBranch,newListNodes, starTree);
		}
		if(endNode== true){
			//possible best solution if remove the = 
			bestTree = starTree;
			bestBound = starTree.numOfBranch(); 
			System.out.println("new bound:" + bestBound);
		}
	}

	public Risultato compute(){
		drawFirstPhase();
		order();
		//start();
		List<Route> routeSolution = convertStartTreeToRoutes(bestTree);
		System.out.println(bestBound);
		System.out.println(ntree);
		DrawGraph.drawGraph(this.nodes,routeSolution, this.root);
		
		Risultato r = new Risultato();
		return r;
	}
	
	private void drawFirstPhase(){
		List<Route> routes = new ArrayList<Route>();
		//for(Node n: nodes)
			//routes.add(new Route(n,root));
		for(Node n1: nodes){
			for(Node n2: nodes){
				if(arcs[n1.getId()][n2.getId()] == true){
					Route route = new Route(n1,n2,0);
					routes.add(route);
				}
			}
		}
		DrawGraph.drawGraph(this.nodes,routes , this.root);
	}
	
	private boolean[][] initPossibleArcs() {
		boolean[][]arcs = new boolean[n][n];
		//p1->p2->root <= alpha*(p1->root)
		for(int i = 0; i < n; i++)
			arcs[i][0] = true;
		for(Node n1: nodes){
			for(Node n2: nodes){
				if(n1.getId()!=n2.getId()){
					double dMax_n1root = n1.getMaxDistanceRoot();
					double d_n2root = n2.getDistanceRoot();
					double d_n1n2 = distances[n1.getId()][n2.getId()];
					if((d_n1n2 + d_n2root) <= (dMax_n1root)){
						arcs[n1.getId()][n2.getId()] = true;
					}
					else{
						arcs[n1.getId()][n2.getId()] = false;
					}
				}
			}
		}
		return arcs;
	}

	private double[][] initDistances(){
		double[][] distances = new double[n][n];
		for(Node n1: this.nodes)
			for(Node n2: this.nodes)
					distances[n1.getId()][n2.getId()] = Node.distanceNodes(n1, n2);
		return distances;
	}
	
	private ArrayList<Node> initNodes(List<Punto> points, double alpha){
		ArrayList<Node> nodes = new ArrayList<Node>();
		for(Punto p: points)
			nodes.add(new Node(p.getId(),p.getCoord().getX(),p.getCoord().getY(),p.getDistanza_origine(),alpha));
		return nodes;
	}
	
	private void initNeighborsNode(){
		for(Node n1: nodes)
			for(Node n2: nodes)
				if(arcs[n1.getId()][n2.getId()] == true){
					n2.addNeighborNode(n1);
				}
	}

	private Map<Integer,Node> initMapNodes(){
		Map<Integer,Node> mapNodes = new HashMap<Integer,Node>();
		for(Node n: nodes)
			mapNodes.put(n.getId(), n);
		return mapNodes;
	}

	@SuppressWarnings("unchecked")
	private ArrayList<Node> cloneListNode(ArrayList<Node> nodes){
		return (ArrayList<Node>) nodes.clone();		
	}
	
	private List<Route> convertStartTreeToRoutes(StarTree tree){
		List<Branch> branches = tree.getTree();
		List<Route> routes = new ArrayList<Route>();
		for(Branch b: branches){
			List<Node> nodes = b.getBranch();
			int max= nodes.size();
			for(int i=0; i< max-1; i++){
				routes.add(new Route(nodes.get(i),nodes.get(i+1),0));
			}
		}
		return routes;	
	}

	private void printNodes(List<Node> listNode){
		String s = new String();
		for(Node k: listNode)
			s += k.getId()+" - ";
		System.out.println(s);
	}
	
	private void order(){
		Node punto, p1;
		int i;
		int j;
		for(i=0 ; i<this.nodes.size() -1 ; i++){
			punto = this.nodes.get(i);
			for(j=i+1 ; j<this.nodes.size() ; j++){
				p1 = this.nodes.get(j);
				if(p1.getDistanceRoot()<=punto.getDistanceRoot()){
					punto = p1;
				}
			}
			this.nodes.remove(punto);
			this.nodes.add(0, punto);
		}
		for(Node p: this.nodes){
			System.out.println(p);
		}
	}
}
