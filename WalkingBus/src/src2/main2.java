package src2;

import java.util.List;

import parser.Dataset;
import parser.InputParser;
import parser.OutputParser;
import src.Risultato;
import src.Solver;
import src.Strada;

public class main2 {

	public static void main(String[] args){
		Boolean debugMode = true;
		Solver solver;
		Risultato risultato;
		Dataset dataset;
		String pathDatFile; // for debug mode
		String debugPathFile = "test_instances/pedibus_10.dat";
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
		
		try {
			dataset = InputParser.initializeData(pathDatFile);
		} catch (Exception e) {
			System.out.println("Error parse dat file");
			System.out.println(e.toString());
			return;
		}
				
		Solver2 solver2 = new Solver2(dataset.getPoints(), dataset.getDangerMatrix(), dataset.getParamAlpha(), dataset.getParamN());
		risultato = solver2.compute();
		
		
		for(Strada s: risultato.getStrade()){
			System.out.println(s.getPartenza().getId() + " - " + s.getArrivo().getId());
		}
		System.out.println("");
		System.out.println(risultato.getNum_foglie() + " - " + risultato.getPericolo());
		
		//OutputParser.OutputFile(pathDatFile, risultato);

	}
}
