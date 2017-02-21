package src;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Permutazione {
	
	ArrayList<Punto> original;
	List<Punto> cuttedListStart;
	List<Punto> cuttedListEnd;
	int n;
	List<List<Punto>> permutationsPoints = new ArrayList<List<Punto>>();
	int sizePermutations;
	int count = 0;
	
	
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
		this.n = n;
		cuttedListStart = this.original.subList(0, n);
		cuttedListEnd = this.original.subList(n, original.size());
		permutationsPoints = (List<List<Punto>>) permute(cuttedListStart);
		sizePermutations = permutationsPoints.size();
		count = 0;
	}
	
	public List<Punto> getNextPermutation(){
		if(count >= sizePermutations)
			return null;
		List<Punto> newPermutation = permutationsPoints.get(count);
		newPermutation.addAll(cuttedListEnd);
		count ++;
		return newPermutation;
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
