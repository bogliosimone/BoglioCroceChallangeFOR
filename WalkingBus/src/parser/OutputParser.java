package parser;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import src.Risultato;
import src.Strada;

public class OutputParser {
	public static void OutputFile(String filePath, Risultato risultato){
		List<Strada> list = risultato.getStrade();
		String fileString = new String();
		for(Strada s: list){
			fileString += s.getArrivo().getId() + " " + s.getPartenza().getId() + "\n";
		}
		String fileName =  FilenameUtils.getBaseName(filePath)+".sol";
		File file = new File(fileName);
		try {
			FileUtils.writeStringToFile(file,fileString, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		//:D uoooooooooh faccio anche il disegnino
		drawGraph(risultato);
	}
		
		public static void drawGraph(Risultato ris) {
			Graph graph = new SingleGraph("Tutorial 1");
			List<Strada> list = ris.getStrade();
			for(Strada s: list){
				try{
				graph.addNode(Integer.toString(s.getArrivo().getId()))
				.setAttribute("xyz",s.getArrivo().getCoord().getX(),
						s.getArrivo().getCoord().getY(),0);
				graph.getNode(Integer.toString(s.getArrivo().getId())).setAttribute("ui.label", Integer.toString(s.getArrivo().getId()));
				}catch(Exception e){}
				try{
				graph.addNode(Integer.toString(s.getPartenza().getId()))
				.setAttribute("xyz",s.getPartenza().getCoord().getX(),
						s.getPartenza().getCoord().getY(),0);
				graph.getNode(Integer.toString(s.getPartenza().getId())).setAttribute("ui.label", Integer.toString(s.getPartenza().getId()));
				}catch(Exception e){}
				try{
				graph.addEdge(Integer.toString(s.getArrivo().getId())+Integer.toString(s.getPartenza().getId()), Integer.toString(s.getArrivo().getId()), Integer.toString(s.getPartenza().getId()));
				}catch(Exception e){}
			}
			graph.getNode("0").setAttribute("ui.style", "fill-color: red;");
			graph.display(false);
		}
}
