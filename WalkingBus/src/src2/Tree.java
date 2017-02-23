package src2;

import java.util.ArrayList;
import java.util.List;

public class Tree {
	private List<Stop> firstStop;
	
	public Tree(){
		this.firstStop = new ArrayList<>();
	}
	
	public void addStop(Stop stop){
		this.firstStop.add(stop);
	}

	public List<Stop> getFirstStop() {
		return firstStop;
	}

	public void setFirstStop(List<Stop> firstStop) {
		this.firstStop = firstStop;
	}
}
