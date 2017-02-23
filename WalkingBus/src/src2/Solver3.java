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

public class Solver3 {
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
	
	public Solver3(List<Punto> points, double[][]dangers, double alpha, int n){
		this.dangers = dangers;
		this.alpha = alpha;
		this.n = n + 1; //nodes + root
		//convert Punto in Node
		this.nodes = initNodes(points,alpha);
		//do these before remove root
		this.mapNodes = initMapNodes();
		this.routes = initRoutes(this.nodes);
		//now remove root from list
		this.root = this.nodes.get(0);
	}
	
	public Risultato compute(){
		List<Route> routesSolution = start();
		nodes.remove(0);
		DrawGraph.drawGraph(nodes, routesSolution, root);;
		Risultato r = new Risultato();
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
		ArrayList<Node> orderedNodes =  (ArrayList<Node>) this.nodes.clone();
		orderedNodes.remove(0);
		Collections.sort(orderedNodes, (r1, r2)->new Double(r1.getDistanceRoot()).compareTo(new Double(r2.getDistanceRoot())));
		ArrayList<Node> unvisited =  (ArrayList<Node>) this.nodes.clone();
		ArrayList<Node> visited = new ArrayList<Node>();
		ArrayList<Route> allRoutes = (ArrayList<Route>) this.routes.clone();
		Collections.sort(allRoutes, (r1, r2)->new Double(r1.getDistance()).compareTo(new Double(r2.getDistance())));
		ArrayList<Route> bestRoutes = new ArrayList<Route>();
		Map<Node,Double> distLeafRoot = new HashMap<Node,Double>();
		ArrayList<Node> currentLeaf = new ArrayList<Node>();
		//start from the root
		Node root = unvisited.remove(0);
		visited.add(root);
		distLeafRoot.put(root, new Double(0));
		currentLeaf.add(root);
		int count=0;
			while(!unvisited.isEmpty()){
				//for(Node nearestNode: orderedNodes){
					for(Route r: allRoutes){
						Node n1 = r.getStart();
						Node n2 = r.getEnd();
						if(!(currentLeaf.contains(n1)||currentLeaf.contains(n2))){
							continue;
						}
						if(visited.contains(n1)&&unvisited.contains(n2)){
							if(!currentLeaf.contains(n1))
								continue;
							double currDist = distLeafRoot.get(n1);
							double totalDist = currDist + Node.distanceNodes(n1, n2);
							if(totalDist <= n2.getMaxDistanceRoot()){
								bestRoutes.add(new Route(n2,n1,0));
								if(!n1.equals(root))
									currentLeaf.remove(n1);
								currentLeaf.add(n2);
								distLeafRoot.put(n2, totalDist);
								int index = unvisited.indexOf(n2);
								visited.add(unvisited.remove(index));
								count++;
								if(count==6)
									return bestRoutes;
								break;
							}
						}
						else{
							if(visited.contains(n2)&&unvisited.contains(n1)){
								double currDist = distLeafRoot.get(n2);
								double totalDist = currDist + Node.distanceNodes(n1, n2);
								if(!currentLeaf.contains(n2))
									continue;
								if(totalDist <= n1.getMaxDistanceRoot()){
									bestRoutes.add(new Route(n1,n2,0));
									if(!n2.equals(root))
										currentLeaf.remove(n2);
									currentLeaf.add(n1);
									distLeafRoot.put(n1, totalDist);
									int index = unvisited.indexOf(n1);
									visited.add(unvisited.remove(index));
									count++;
									if(count==6)
										return bestRoutes;
									break;
								}
							}
						}
					}
				//}
			}
			System.out.println(currentLeaf.size()-1);
		return bestRoutes;
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
	
	private Map<Integer,Node> initMapNodes(){
		Map<Integer,Node> mapNodes = new HashMap<Integer,Node>();
		for(Node n: nodes)
			mapNodes.put(n.getId(), n);
		return mapNodes;
	}
	
}