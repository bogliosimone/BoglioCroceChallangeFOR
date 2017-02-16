package src;

import java.util.List;

import parser.Dataset;
import parser.InputParser;
import parser.OutputParser;

public class main {

	public static void main(String[] args) throws Exception{
		Solver solver;
		Risultato risultato;
		Dataset dataset;
		String cazzodifile = "test_instances/pedibus_50.dat";
		
		dataset = InputParser.initializeData(cazzodifile);
		
		solver = new Solver(dataset.getPoints(), dataset.getDangerMatrix(), dataset.getParamAlpha());
		risultato = solver.calcola();
		
		for(Strada s: risultato.getStrade()){
			System.out.println(s.getPartenza().getId() + " - " + s.getArrivo().getId());
		}
		System.out.println("");
		System.out.println(risultato.getNum_foglie() + " - " + risultato.getPericolo());
		
		OutputParser.OutputFile(cazzodifile, risultato);

	}

}
