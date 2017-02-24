package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import view.DrawGraph;

public class Solver {
	private ArrayList<Node> nodes;
	private Node root;
	private double[][] dangers;
	private double alpha;
	private ArrayList<Route> routes;
	double ntree=1;
	
	public Solver(List<Node> points, double[][]dangers, double alpha, int n){
		this.dangers = dangers;
		this.alpha = alpha;
		//convert Punto in Node
		this.nodes = (ArrayList<Node>) points;
		//do these before remove root
		this.routes = initRoutes(this.nodes);
		this.root = this.nodes.get(0);
	}
	
	public List<Route> compute(){
		int nLeaf1,nLeaf2;
		List<Route>routesSolution1 = new ArrayList<Route>();
		List<Route>routesSolution2 = new ArrayList<Route>();
		List<Route> bestRoutesSolution;
		nLeaf1 = start(false,routesSolution1); //normal
		nLeaf2 = start(true,routesSolution2); //with check better node function
		double danger1 = computeDanger(routesSolution1);
		double danger2 = computeDanger(routesSolution2);
		//System.out.println(nLeaf1 + " " + nLeaf2);
		//System.out.println(danger1 + " " + danger2);
		if(nLeaf1 < nLeaf2)
			bestRoutesSolution = routesSolution1;
		else if(nLeaf1> nLeaf2)
			bestRoutesSolution = routesSolution2;
		else if(danger1<=danger2)
			bestRoutesSolution = routesSolution1;
		else
			bestRoutesSolution = routesSolution2;
		//uncomment this two lines to draw the graph
		//nodes.remove(0);
		//DrawGraph.drawGraph(nodes, bestRoutesSolution, root);
		return bestRoutesSolution;
	}

	private double computeDanger(List<Route> routesSolution) {
		double tot=0;
		for(Route r:routesSolution){
			tot+= this.dangers[r.getStart().getId()][r.getEnd().getId()];
		}
		return tot;
	}

	private int start(boolean checkBetterNode, List<Route> bestRoutes){
		ArrayList<Route> allRoutes = this.routes;
		Collections.sort(allRoutes, (r1, r2)->new Double(r1.getDistance()).compareTo(new Double(r2.getDistance())));
		Map<Integer,List<Route>> mapNodeRoutes = initMapNodeRoutes(this.nodes);
		Map<Node,Double> distLeafRoot = new HashMap<Node,Double>();
		Map<Integer,Node> unvisited = initMapUnvisited(this.nodes);
		Map<Integer,Node> visited = new HashMap<Integer,Node>(); 
		Map<Integer,Node> currentLeaf = new HashMap<Integer,Node>(); 
		//start from the root
		Node root = unvisited.remove(0);
		visited.put(root.getId(), root);
		distLeafRoot.put(root, new Double(0));
		currentLeaf.put(root.getId(), root);
			while(!unvisited.isEmpty()){
					boolean check = false;
					for(Route r: allRoutes){
						Node n1 = r.getStart();
						Node n2 = r.getEnd();
						Integer id1 = n1.getId();
						Integer id2 = n2.getId();
						if(!(currentLeaf.containsKey(id1)||currentLeaf.containsKey(id2))){
							continue;
						}
						if(visited.containsKey(id1)&&unvisited.containsKey(id2)){
							Node swapNode = n1;
							int swapId = id1;
							n1=n2;
							id1 = id2;
							n2=swapNode;
							id2 = swapId;
						}
						if(visited.containsKey(id2)&&unvisited.containsKey(id1)){
								double currDist = distLeafRoot.get(n2);
								double totalDist = currDist + r.getDistance();
								if(totalDist <= n1.getMaxDistanceRoot()){
									if(checkBetterNode==true&&!n2.equals(root))
										n1 = checkBetterNode(n1,n2,mapNodeRoutes.get(id2),root,this.alpha,unvisited,currDist);
									id1 = n1.getId();
									totalDist = currDist + Node.distanceNodes(n1, n2);
									bestRoutes.add(new Route(n1,n2,0));
									currentLeaf.remove(id2);
									currentLeaf.put(id1,n1);
									distLeafRoot.put(n1, totalDist);
									unvisited.remove(id1);
									visited.put(id1, n1);
									check = true;
									break;
								}
						}
					}
					if(check==false)
						currentLeaf.put(root.getId(), root);
			}
			//System.out.println(currentLeaf.size());
		return currentLeaf.size();
	}
	
	
	private Node checkBetterNode(Node actualNode, Node leaf, List<Route> routes, Node root, double alpha
			, Map<Integer,Node> unvisited,double currentDistanceFromRoot){
		Node bestNode = actualNode;
		double diffAngle = calculateDifferenceBetweenAngles(actualNode.calcolaAngolo(leaf),leaf.calcolaAngolo(root));
		diffAngle = 1-(diffAngle/180);
		double distanceBestNode = Node.distanceNodes(actualNode, leaf);
		for(Route r: routes){
			Node n = r.getEnd();
			if(currentDistanceFromRoot+Node.distanceNodes(n, leaf) > n.getMaxDistanceRoot() || n.equals(bestNode))
				continue;
			if(unvisited.containsKey(n.getId())&&Node.distanceNodes(leaf, root)<=Node.distanceNodes(n, leaf)*alpha){
				double newDiffAngle = calculateDifferenceBetweenAngles(n.calcolaAngolo(leaf),leaf.calcolaAngolo(root));
				double newDistanceNode = Node.distanceNodes(n, leaf);
				newDiffAngle = 1-(newDiffAngle/180);
				if((newDiffAngle / newDistanceNode) > (diffAngle / distanceBestNode)){
					bestNode = n;
					diffAngle = newDiffAngle;
					distanceBestNode = newDistanceNode;
				}
			}
		}
		return bestNode;
	}
	
	private double calculateDifferenceBetweenAngles(double alpha, double beta) {
	        double phi = Math.abs(beta - alpha) % 360;
	        double distance = phi > 180 ? 360 - phi : phi;
	        return distance;
	 }

	private ArrayList<Route> initRoutes(List<Node> nodes){
		ArrayList<Route> routes = new ArrayList<Route>();
		for(Node n1: nodes){
			for(Node n2: nodes){
				if(!n1.equals(n2) && !routes.contains(new Route(n2,n1,0)))
					routes.add(new Route(n1,n2,Node.distanceNodes(n1, n2)));		
			}
		}
		return routes;
	}
	
	private Map<Integer,Node> initMapUnvisited(List<Node> nodes){
		Map<Integer,Node> mapUnvisited = new HashMap<Integer,Node>();
		for(Node n: nodes)
			mapUnvisited.put(n.getId(), n);
		return mapUnvisited;
	}
	
	private Map<Integer, List<Route>> initMapNodeRoutes(List<Node> nodes){
		Map<Integer,List<Route>> mapNodeRoutes = new HashMap<Integer,List<Route>>();
		for(Node n1: nodes){
			List<Route> l = new ArrayList<Route>();
			for(Node n2: nodes)
				if(!n1.equals(n2))
					l.add(new Route(n1,n2,Node.distanceNodes(n1, n2)));
			mapNodeRoutes.put(n1.getId(), l);
		}
		return mapNodeRoutes;
	}

}
