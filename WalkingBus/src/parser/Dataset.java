package parser;

import java.util.ArrayList;
import java.util.Arrays;

import src.Node;

public class Dataset {
	int paramN = -1;
	double paramAlpha = -1;
	ArrayList<Node> points;
	double[][] dangerMatrix;
	
	public Dataset(int paramN, double paramAlpha, ArrayList<Node> points, double[][] dangerMatrix) {
		super();
		this.paramN = paramN;
		this.paramAlpha = paramAlpha;
		this.points = points;
		this.dangerMatrix = dangerMatrix;
	}
	public int getParamN() {
		return paramN;
	}
	public double getParamAlpha() {
		return paramAlpha;
	}
	public ArrayList<Node> getPoints() {
		return points;
	}
	public double[][] getDangerMatrix() {
		return dangerMatrix;
	}
	@Override
	public String toString() {
		return "Dataset [paramN=" + paramN + ", paramAlpha=" + paramAlpha + ", points=" + points + ", dangerMatrix="
				+ Arrays.toString(dangerMatrix) + "]";
	}
	
	
	
}
