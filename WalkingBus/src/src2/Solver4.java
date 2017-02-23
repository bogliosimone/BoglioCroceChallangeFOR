package src2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import src.Coordinate;
import src.Punto;
import src.Risultato;
import src.Strada;
import view.DrawGraph;

public class Solver4 {
	private ArrayList<Node> nodes;
	private Node root;
	private double[][] distances;
	private double[][] dangers;
	private boolean[][] arcs;
	private double alpha;
	private int n;
	private Map<Integer,Node> mapNodes;
	private ArrayList<Route> routes;
	private int bestBound;
	private StarTree bestTree;
	double ntree=1;
	
	public Solver4(List<Punto> points, double[][]dangers, double alpha, int n){
		this.dangers = dangers;
		this.alpha = alpha;
		this.n = n + 1; //nodes + root
		//convert Punto in Node
		this.nodes = initNodes(points,alpha);
		//do these before remove root
		this.mapNodes = initMapNodes(this.nodes);
		this.routes = initRoutes(this.nodes);
		this.distances = initDistances(this.nodes);
		this.root = this.nodes.get(0);
	}
	
	public Risultato compute(){
		List<Route> routesSolution = start();
		nodes.remove(0);
		DrawGraph.drawGraph(nodes, routesSolution, root);;
		Risultato r = new Risultato();
		DangerSolver ds = new DangerSolver(this.distances,this.dangers,routesSolution,1);
		ds.solve();
		List<Strada> strade = convertRouteToStrada(routesSolution);
		for(Strada s: strade)
			r.aggiungiStrade(s);
		return r;
	}
	
	private List<Strada> convertRouteToStrada(List<Route> routesSolution) {
		List<Strada> strade= new ArrayList<Strada>();
		for(Route r: routesSolution){
			strade.add(new Strada(new Punto(new Coordinate(0,0),r.getEnd().getId(),0),new Punto(new Coordinate(0,0),r.getStart().getId(),0)));
		}
		return strade;
	}

	@SuppressWarnings("null")
	private List<Route> start(){
		ArrayList<Route> allRoutes = this.routes;
		Collections.sort(allRoutes, (r1, r2)->new Double(r1.getDistance()).compareTo(new Double(r2.getDistance())));
		ArrayList<Route> bestRoutes = new ArrayList<Route>();
		Map<Integer,List<Route>> mapNodeRoutes = initMapNodeRoutes(this.nodes);
		Map<Node,Double> distLeafRoot = new HashMap<Node,Double>();
		Map<Integer,Node> unvisited = initMapUnvisited(this.nodes);
		Map<Integer,Node> visited = new HashMap<Integer,Node>(); 
		Map<Integer,Node> currentLeaf = new HashMap<Integer,Node>(); 
		int count =0;
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
									if(!n2.equals(root))
										n1 = checkBetterNode2(n1,n2,mapNodeRoutes.get(id2),root,this.alpha,unvisited,currDist);
									if(id1!=n1.getId())
										System.out.println("swap ->" + mapNodes.get(id1) + " con "+n1);
									id1 = n1.getId();
									totalDist = currDist + Node.distanceNodes(n1, n2);
									bestRoutes.add(new Route(n1,n2,0));
									currentLeaf.remove(id2);
									currentLeaf.put(id1,n1);
									distLeafRoot.put(n1, totalDist);
									unvisited.remove(id1);
									visited.put(id1, n1);
									count ++;
									//if(count ==50)
										//return bestRoutes;
									check = true;
									break;
								}
						}
					}
					if(check==false)
						currentLeaf.put(root.getId(), root);
			}
			System.out.println(currentLeaf.size());
		return bestRoutes;
	}
	
	
	private Node checkBetterNode2(Node actualNode, Node leaf, List<Route> routes, Node root, double alpha
			, Map<Integer,Node> unvisited,double currentDistanceFromRoot){
		Node bestNode = actualNode;
		double alphaNormUp = (alpha-1);
		double alphaNormDown = (alpha-1);
		double alphaUp = 1 + alphaNormUp;
		double alphaDown = 1+ alphaNormDown;
		double diffAngle = calculateDifferenceBetweenAngles(leaf.calcolaAngolo(root),actualNode.calcolaAngolo(root));
		diffAngle = 1-(diffAngle/360);
		double distanceBestNode = Node.distanceNodes(actualNode, leaf);
		distanceBestNode = 1- (distanceBestNode/(Node.distanceNodes(actualNode, root)*alpha));
		double distanceSearchMax =Node.distanceNodes(actualNode, leaf)* alphaUp;
		for(Route r: routes){
			Node n = r.getEnd();
			if(currentDistanceFromRoot+ Node.distanceNodes(n, leaf) > n.getMaxDistanceRoot())
				continue;
			if(unvisited.containsKey(n.getId())&&Node.distanceNodes(leaf, root)<=Node.distanceNodes(n, leaf)*alpha){
				double newDiffAngle = calculateDifferenceBetweenAngles(leaf.calcolaAngolo(root),n.calcolaAngolo(root));
				double newDistanceNode = Node.distanceNodes(n, leaf);
				newDistanceNode= 1-(newDistanceNode/(Node.distanceNodes(n, root)*alpha));
				newDiffAngle = 1-(newDiffAngle/360);
				System.out.println("ciao");
				if((newDiffAngle > diffAngle )){
				//if((newDiffAngle * newDistanceNode) > (diffAngle * distanceBestNode)){
					bestNode = n;
					diffAngle = newDiffAngle;
					distanceBestNode = newDistanceNode;
				}
			}
		}
		return bestNode;
	}
	
	private double calculateDifferenceBetweenAngles(double alpha, double beta) {
	        double phi = Math.abs(beta - alpha) % 360;       // This is either the distance or 360 - distance
	        double distance = phi > 180 ? 360 - phi : phi;
	        return distance;
	 }
	
	private ArrayList<Node> initNodes(List<Punto> points, double alpha){
		ArrayList<Node> nodes = new ArrayList<Node>();
		for(Punto p: points)
			nodes.add(new Node(p.getId(),p.getCoord().getX(),p.getCoord().getY(),p.getDistanza_origine(),alpha));
		return nodes;
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
	
	private Map<Integer,Node> initMapNodes(List<Node> nodes){
		Map<Integer,Node> mapNodes = new HashMap<Integer,Node>();
		for(Node n: nodes)
			mapNodes.put(n.getId(), n);
		return mapNodes;
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
	
	private double[][] initDistances(List<Node> nodes){
		double[][] distances = new double[n][n];
		for(Node n1: nodes)
			for(Node n2: nodes)
					distances[n1.getId()][n2.getId()] = Node.distanceNodes(n1, n2);
		return distances;
	}

}
