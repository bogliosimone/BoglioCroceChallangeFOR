package src;

import java.util.List;

import parser.Dataset;
import parser.InputParser;
import parser.OutputParser;

public class main {

	public static void main(String[] args) throws Exception{
		Boolean debugMode = true;
		Solver solver;
		Risultato risultato;
		Dataset dataset;
		String pathDatFile = "test_instances/pedibus_20.dat"; // for debug mode
		if(args.length != 1 && !debugMode) {
			  System.err.println("Invalid command line, exactly one argument required");
			  System.exit(1);
			}
		else{
			pathDatFile = args[0];
		}
		dataset = InputParser.initializeData(pathDatFile);
		
		solver = new Solver(dataset.getPoints(), dataset.getDangerMatrix(), dataset.getParamAlpha());
		risultato = solver.calcola();
		
		for(Strada s: risultato.getStrade()){
			System.out.println(s.getPartenza().getId() + " - " + s.getArrivo().getId());
		}
		System.out.println("");
		System.out.println(risultato.getNum_foglie() + " - " + risultato.getPericolo());
		
		OutputParser.OutputFile(pathDatFile, risultato);

	}
}
