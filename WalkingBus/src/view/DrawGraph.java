package view;

import java.util.List;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import src2.Node;
import src2.Route;

public class DrawGraph {
	public static void drawGraph(List<Node> nodes, List<Route> routes, Node root) {
		Graph graph = new SingleGraph("WalkingBusGraph");
		//set points
		for(Node n: nodes){
			String id = Integer.toString(n.getId());
			int x = n.getX();
			int y = n.getY();
			graph.addNode(id).addAttribute("xyz",x,y,0);
			graph.getNode(id).setAttribute("ui.label", id);
		}
		//set root
		String idRoot = Integer.toString(root.getId());
		int x = root.getX();
		int y = root.getY();
		graph.addNode(idRoot).addAttribute("xyz",x,y,0);
		graph.getNode(idRoot).setAttribute("ui.style", "fill-color: red;");
		//set arc
		for(Route r: routes){
			String idStart = Integer.toString(r.getStart().getId());
			String idEnd = Integer.toString(r.getEnd().getId());
			graph.addEdge(idStart+"-"+idEnd,idStart,idEnd,true);
		}
		
		//draw
		graph.display(false);
	}
}
