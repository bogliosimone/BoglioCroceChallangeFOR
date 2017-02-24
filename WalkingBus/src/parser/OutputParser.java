package parser;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import src.Route;

public class OutputParser {
	public static void OutputFile(String filePath, List<Route> routes){
		String fileString = new String();
		for(Route r: routes){
			fileString += r.getStart().getId()+ " " + r.getEnd().getId()+ "\n";
		}
		String fileName =  FilenameUtils.getBaseName(filePath)+".sol";
		File file = new File(fileName);
		try {
			FileUtils.writeStringToFile(file,fileString, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
