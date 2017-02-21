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
		String pathDatFile; // for debug mode
		String debugPathFile = "test_instances/pedibus_300.dat";
		if(args.length != 1) {
			if(!debugMode){
				System.err.println("Invalid command line, exactly one argument required");
				return;
			}else{
				pathDatFile = debugPathFile;
			}
		}else{
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
