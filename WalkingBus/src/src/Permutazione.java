package src;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Permutazione {
	
	ArrayList<Punto> original;
	List<Punto> cuttedListStart;
	List<Punto> cuttedListEnd;
	int n;
	List<List<Punto>> permutationsPoints = new ArrayList<List<Punto>>();
	int sizePermutations;
	
	
	/*public static void main(String[] args) throws Exception{
		Punto p1,p2,p3,p4,p5;
		p1 = new Punto(new Coordinate(0,0),1,0);
		p2 = new Punto(new Coordinate(0,0),2,0);
		p3 = new Punto(new Coordinate(0,0),3,0);
		p4 = new Punto(new Coordinate(0,0),4,0);
		p5 = new Punto(new Coordinate(0,0),5,0);
		List<Punto> list = new ArrayList<Punto>();
		list.add(p1);
		list.add(p2);
		list.add(p3);
		list.add(p4);
		list.add(p5);
		Permutazione p = new Permutazione((ArrayList<Punto>) list,3);
		list = p.getNextPermutation();
		while (list!=null){
			System.out.println(list);
			list = p.getNextPermutation();
		}
	}*/
	
	
	@SuppressWarnings("unchecked")
	Permutazione (ArrayList<Punto> points, int n){
		this.original = (ArrayList<Punto>) points.clone();
		this.original = (ArrayList<Punto>) mixStartList(this.original);
		this.n = n;
		cuttedListStart = this.original.subList(0, n);
		cuttedListEnd = this.original.subList(n, original.size());
		
		permutationsPoints = (List<List<Punto>>) permute(cuttedListStart);
		sizePermutations = permutationsPoints.size();
	}
	
	
	
	public ArrayList<Punto> getOriginal() {
		return original;
	}



	public void setOriginal(ArrayList<Punto> original) {
		this.original = original;
	}



	public List<Punto> getNextPermutation(){
		if(permutationsPoints.isEmpty())
			return null;
		List<Punto> newPermutation = permutationsPoints.remove(sizePermutations-1);
		sizePermutations --;
		newPermutation.addAll(cuttedListEnd);
		return newPermutation;
	}
	
	public List<Punto> mixStartList(List<Punto> list){
		int n = 8;
		double step = 360.0001/n;
		List<List<Punto>> listList = new ArrayList<List<Punto>>();
		for(int i=0; i<n; i++){
			listList.add(new ArrayList<Punto>());
		}
		for(Punto p: list){
			int pSector = (int) (p.getAngolo()/step);
			listList.get(pSector).add(p);
		}
		for(List<Punto> l: listList){
			Collections.sort(l, (p1, p2)->new Double(p1.getDistanza_origine()).compareTo(new Double(p2.getDistanza_origine())));
		}
		List<Punto> startList = new ArrayList<Punto>();
		List<Punto> endList = new ArrayList<Punto>();
		for(List<Punto> l: listList){
			for(int i=0;i<l.size();i++){
				if(i==0)
					startList.add(l.get(i));
				else
					endList.add(l.get(i));
			}
		}
		Collections.sort(endList, (p1, p2)->new Double(p1.getDistanza_origine()).compareTo(new Double(p2.getDistanza_origine())));
		startList.addAll(endList);
		return startList;
	}
	
	
	
	public List<Punto> mixEndList(List<Punto> list){
		int n = 1;
		double step = 360/n;
		List<List<Punto>> listList = new ArrayList<List<Punto>>();
		for(int i=0; i<n; i++){
			listList.add(new ArrayList<Punto>());
		}
		for(Punto p: list){
			int pSector = (int) (p.getAngolo()/step);
			listList.get(pSector).add(p);
		}
		for(List<Punto> l: listList){
			Collections.sort(l, (p1, p2)->new Double(p1.getDistanza_origine()).compareTo(new Double(p2.getDistanza_origine())));
		}
		List<Punto> endList = new ArrayList<Punto>();
		for(List<Punto> l: listList){
			endList.addAll(l);
		}
		return endList;
	}
	
	
	public <T> Collection<List<T>> permute(Collection<T> input) {
        Collection<List<T>> output = new ArrayList<List<T>>();
        if (input.isEmpty()) {
            output.add(new ArrayList<T>());
            return output;
        }
        List<T> list = new ArrayList<T>(input);
        T head = list.get(0);
        List<T> rest = list.subList(1, list.size());
        for (List<T> permutations : permute(rest)) {
            List<List<T>> subLists = new ArrayList<List<T>>();
            for (int i = 0; i <= permutations.size(); i++) {
                List<T> subList = new ArrayList<T>();
                subList.addAll(permutations);
                subList.add(i, head);
                subLists.add(subList);
            }
            output.addAll(subLists);
        }
        return output;
    }
}
