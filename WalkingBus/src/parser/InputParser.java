package parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import src.Node;

public class InputParser {

	private static final String FILENAME = "test_instances/pedibus_50.dat";

	
	public static void main (String args[]){
		try {
			System.out.println(initializeData(FILENAME));
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static Dataset initializeData(String FilePath) throws Exception{
		String allFile;
		int paramN;
		double paramAlpha;
		ArrayList<Node> points = new ArrayList<Node>();
		try {
			allFile = readFile(FilePath);
			allFile = clearStringFile(allFile);
			List<String> lf = splitFile(allFile);
			paramN = parseParamN(lf);
			paramAlpha = parseParamAlpha(lf);
			Map<Integer,Integer> mapX = parseCoordX(lf);
			Map<Integer,Integer> mapY = parseCoordY(lf);
			Map<Key,Double> mapD = parseDangerMatrix(lf);
			double[][] dangerMatrix = new double[paramN+1][paramN+1];
			int xRoot = mapX.get(0);
			int yRoot = mapY.get(0);
			for(int i=0; i<= paramN; i++){
				int tmpX= mapX.get(i);
				int tmpY= mapY.get(i);
				double distanceFromRoot;
				if (i==0)
					distanceFromRoot = 0;
				else
					distanceFromRoot = Math.sqrt((((double)tmpX-(double)xRoot)*((double)tmpX-(double)xRoot))+(((double)tmpY-(double)yRoot)*((double)tmpY-(double)yRoot)));
				points.add(new Node(i,tmpX,tmpY,distanceFromRoot,paramAlpha));
				for(int j=0; j<= paramN; j++){
					dangerMatrix[i][j] = mapD.get(new Key(i,j));
				}
			}
			return new Dataset(paramN,paramAlpha, points, dangerMatrix);
		} catch (Exception e) {
			throw e;
		}
	}
	
	private static String readFile(String filePath) throws FileNotFoundException, IOException {
		try(FileInputStream inputStream = new FileInputStream(filePath)) {     
		    return IOUtils.toString(inputStream, "UTF-8");
		}
	}
	
	private static String clearStringFile(String s){
		s = s.replaceAll("\\n", " ");
		s = s.replaceAll("\\r", " ");
		s = s.replaceAll("\\t", " ");
		s = s.replaceAll("\\s{2,}", " ");
		return s;
	}
	
	private static List<String> splitFile(String file){
		String[] array = file.split(";");
		List<String> list = new ArrayList<String>();
		for(String s: array){
			list.add(s);
		}
		return list; 
	}
	
	private static int parseParamN(List<String> listFile) throws Exception{
		int paramN = 0;
		boolean find = false;
		try{
			for(String line: listFile){
				String[] array = line.split(":=");
				if(array[0].contains("param n")){
					paramN =  Integer.parseInt(array[1].replaceAll(" ", ""));
					find = true;
					break;
				}
			}
		}catch(Exception e){
			throw new Exception("error parse .dat file: param n not integer");
		}
		if(!find)
			throw new Exception("error .dat: param n not find");
		return paramN;
	}
	
	private static double parseParamAlpha(List<String> listFile) throws Exception{
		double paramAlpha = 0;
		boolean find = false;
		try{
			for(String line: listFile){
				String[] array = line.split(":=");
				if(array[0].contains("param alpha")){
					paramAlpha =  Double.parseDouble(array[1].replaceAll(" ", ""));
					find = true;
					break;
				}
			}
		}catch(Exception e){
			throw new Exception("error parse .dat file: param alpha not double");
		}
		if(!find)
			throw new Exception("error .dat: param alpha not find");
		return paramAlpha;
	}
	
	private static Map<Integer, Integer> parseCoordX(List<String> listFile) throws Exception{
		Map<Integer , Integer> mapX= new HashMap<Integer,Integer>();
		boolean find = false;
		try{
			for(String line: listFile){
				String[] array = line.split(":=");
				if(array[0].contains("param coordX")){	
					array = array[1].split(" ");
					boolean isNameNode = true;
					int tmpKey = -1;
					for (String s: array){
						if(!s.replaceAll(" ", "").equals("")){
							if(isNameNode){
								tmpKey = Integer.parseInt(s);
								isNameNode = false;
							}								else{
								mapX.put(tmpKey, Integer.parseInt(s));
								isNameNode = true;
							}
						}
					}
					find = true;
					break;
				}
			}
		}catch(Exception e){
			throw new Exception("error parse .dat file: param coordX [*] not correct format");
		}
		if(!find){
			throw new Exception("error .dat: param coordX [*] not find");
		}
		return mapX;
	}
	
	private static Map<Integer, Integer> parseCoordY(List<String> listFile) throws Exception{
		Map<Integer , Integer> mapY= new HashMap<Integer,Integer>();
		boolean find = false;
		try{
			for(String line: listFile){
				String[] array = line.split(":=");
				if(array[0].contains("param coordY")){	
					array = array[1].split(" ");
					boolean isNameNode = true;
					int tmpKey = -1;
					for (String s: array){
						if(!s.replaceAll(" ", "").equals("")){
							if(isNameNode){
								tmpKey = Integer.parseInt(s);
								isNameNode = false;
							}								else{
								mapY.put(tmpKey, Integer.parseInt(s));
								isNameNode = true;
							}
						}
					}
					find = true;
					break;
				}
			}
		}catch(Exception e){
			throw new Exception("error parse .dat file: param coordY [*] not correct format");
		}
		if(!find){
			throw new Exception("error .dat: param coordY [*] not find");
		}
		return mapY;
	}
	
	private static Map<Key, Double> parseDangerMatrix(List<String> listFile) throws Exception{
		Map<Key, Double> mapD= new HashMap<Key,Double>();
		boolean find = false;
		try{
			for(String line: listFile){
				String[] array = line.split(":=");
				if(array[0].contains("param d")){
					find = true;
					String[] array2 = array[0].split(":");
					array2 = array2[1].split(" ");
					ArrayList<Integer> tmpIndexCol = new ArrayList<Integer>();
					for (String s: array2){
						if(!s.replaceAll(" ", "").equals("")){
							tmpIndexCol.add(Integer.parseInt(s));
						}
					}
					array = array[1].split(" ");
					int n = tmpIndexCol.size();
					int i=-1;
					Integer currentX = new Integer(-1);
					for (String s: array){
						if(!s.replaceAll(" ","").equals(""))
								if(i == -1){
									currentX = Integer.parseInt(s);
									i++;
								}
								else{
									mapD.put(new Key(currentX, tmpIndexCol.get(i)), Double.parseDouble(s));
									i++;
									if(i>=n){
										i=-1;
									}
								}
					}
				}
			}
		}catch(Exception e){
			throw new Exception("error parse .dat file: param d [*,*] not correct format");
		}
		if(!find){
			throw new Exception("error .dat: param d [*,*] not find");
		}
		return mapD;
	}
}
	
