package src;

import java.util.List;

import parser.Dataset;
import parser.InputParser;
import parser.OutputParser;

public class main {

	public static void main(String[] args){
		Boolean debugMode = false;
		Dataset dataset;
		String pathDatFile; // for debug mode
		String debugPathFile = "test_instances/pedibus_250.dat";
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
			//System.out.println("Error parse dat file");
			//System.out.println(e.toString());
			return;
		}
				
		Solver solver4 = new Solver(dataset.getPoints(), dataset.getDangerMatrix(), dataset.getParamAlpha(), dataset.getParamN());
		List<Route> solution = solver4.compute();
		OutputParser.OutputFile(pathDatFile, solution);

	}
}

