package parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import src.Punto;

public class InputParser {

	private static final String FILENAME = "test_instances/pedibus_10.dat";

	
	public static void main (String args[]){
		String allFile;
		try {
			allFile = readFile(FILENAME);
		} catch (IOException e) {
			System.out.println(e);
			return;
		}
		List<String> lf = splitFile(clearStringFile(allFile));
		try{
			int n = 0;
			float alpha = 0;
			parseFileList(lf,n,alpha,new ArrayList<Punto>());
		}catch(Exception e){
			e.printStackTrace();
			return;
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
	
	private static void parseFileList(List<String> list, int n, float alpha, List<Punto> coordList) throws Exception{
		try{
			String tmp;
			String[] array2;
			String[] array;
			Map<Integer , Integer> mapX= new HashMap<Integer,Integer>();
			Map<Integer , Integer> mapY= new HashMap<Integer,Integer>();
			Map<Key,Double> mapDanger = new HashMap<Key,Double>();
			boolean isNameNode = false;
			int tmpKey = -1;
			int i = 0;
			Iterator<String> iterator = list.iterator();
			tmp = iterator.next();
			if(!tmp.contains("data"))
				throw new Exception("error parse .dat file: data");
			array = iterator.next().split(":=");
			if(!array[0].contains("param n"))
				throw new Exception("error parse .dat file: expected param n");
			int paramN =  Integer.parseInt(array[1].replaceAll(" ", ""));
			array = iterator.next().split(":=");
			if(!array[0].contains("param alpha"))
				throw new Exception("error parse .dat file: expected param alpha");
			float paramAlpha =  Float.parseFloat(array[1].replaceAll(" ", ""));
			array = iterator.next().split(":=");
			if(!array[0].contains("param coordX"))
				throw new Exception("error parse .dat file: expected param coordX [*]");
			array = array[1].split(" ");
			isNameNode = true;
			tmpKey = -1;
			for (String s: array){
				if(!s.replaceAll(" ", "").equals(""))
					if(isNameNode){
						tmpKey = Integer.parseInt(s);
						isNameNode = false;
					}
					else{
						mapX.put(tmpKey, Integer.parseInt(s));
						isNameNode = true;
					}
			}
			array = iterator.next().split(":=");
			isNameNode = true;
			if(!array[0].contains("param coordY"))
				throw new Exception("error parse .dat file: expected param coordY [*]");
			array = array[1].split(" ");
			tmpKey = -1;
			for (String s: array){
				if(!s.replaceAll(" ", "").equals(""))
					if(isNameNode){
						tmpKey = Integer.parseInt(s);
						isNameNode = false;
					}
					else{
						mapY.put(tmpKey, Integer.parseInt(s));
						isNameNode = true;
					}
			}
			array = iterator.next().split(":=");
			array2 = array[0].split(":");
			if(!array2[0].contains("param d"))
				throw new Exception("error parse .dat file: expected param d [*,*]");			
			array2 = array2[1].split(" ");
			ArrayList<Integer> tmpIndexCol = new ArrayList<Integer>();
			for (String s: array2){
				if(!s.replaceAll(" ", "").equals("")){
					tmpIndexCol.add(Integer.parseInt(s));
				}
			}
			System.out.println(tmpIndexCol);
			array = array[1].split(" ");		
			i=-1;
			Integer currentX = new Integer(-1);
			for (String s: array){
				if(!s.replaceAll(" ","").equals(""))
						if(i == -1){
							currentX = Integer.parseInt(s);
							i++;
						}
						else{
							mapDanger.put(new Key(currentX, tmpIndexCol.get(i)),Double.parseDouble(s));
							i++;
							if(i>=paramN+1){
								i=-1;
							}
						}
			}
			
			System.out.println(paramN);
			System.out.println(paramAlpha);
			System.out.println(mapX);
			System.out.println(mapY);
			System.out.println(mapDanger);
		}catch(Exception e){
			throw e;
		}
	}	
	
}
	
