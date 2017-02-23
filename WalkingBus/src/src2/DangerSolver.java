package src2;

import java.util.ArrayList;
import java.util.List;

public class DangerSolver {
	private double[] deltaDistancePoints;
	private double[][] distance;
	private double[][] danger;
	private List<Route> routes;
	private List<Stop> stops;
	private Tree tree;
	
	
	public DangerSolver(double[][] distance, double[][] danger, List<Route> routes, int n){
		this.danger = danger;
		this.routes = routes;
		this.deltaDistancePoints = new double[n];
		this.tree = new Tree();
		this.stops = new ArrayList<>();
		this.distance = distance;
	}
	
	public void buildTree(){
		Stop stop;
		List <Stop> followingStops;
		for(Route r: this.routes){
			stop = new Stop(r.getStart(), r.getEnd());
			this.stops.add(stop);	
		}
		
		for(Stop s: this.stops){
			stop = getPrevious(s);
			s.setPreviousStop(stop);
		}
		
		for(Stop s: this.stops){
			followingStops = getFollowing(s);
			s.setFollowingStop(followingStops);
		}
		
		for(Stop s: this.stops){
			if(s.getPreviousStop() == null){
				this.tree.addStop(s);
			}
		}
		
		for(Stop s: this.tree.getFirstStop()){
			stop = s;
			while(stop!=null){
				if(stop.getPreviousStop()==null){
					stop.setDistance(0);
					stop.setDanger(this.danger[stop.getNode().getId()][0]);
				}
				else{
					stop.setDistance(this.distance[stop.getNode().getId()][stop.getPreviousStop().getNode().getId()]);
					stop.setDanger(this.danger[stop.getNode().getId()][stop.getPreviousStop().getNode().getId()]);
				}
				stop = stop.getFirstFollowing();
			}
		}
		
		double partialDistance;
		
		for(Stop s: this.tree.getFirstStop()){
			stop = s;
			partialDistance = 0;
			while(stop!=null){
				partialDistance = partialDistance + stop.getDistance();
				stop.setPartialDistance(partialDistance);
				stop = stop.getFirstFollowing();
			}
		}
	}
	
	private List<Stop> getFollowing(Stop stop){
		List <Stop>followingStops;
		followingStops = new ArrayList<>();
		for(Stop s: this.stops){
			if(s!=stop && s.getPreviousStop() == stop)
				followingStops.add(s);
		}
		return followingStops;
	}
	
	private Stop getPrevious(Stop stop){
		for(Stop s: this.stops){
			if(s!=stop && s.getFollowingNode() == stop.getNode()){
				return s;
			}
		}
		return null;
	}
	
	public void solve(){
		buildTree();
	}
}
