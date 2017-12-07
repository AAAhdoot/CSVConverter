
 import java.io.*;
 import java.util.* ;
 import java.lang.Object;
 import java.io.BufferedReader;
 import java.nio.file.*;

 // Ariel Ahdoot, September 23rd 2017
 // Takes in name of csv file, uses newline as delimiter, and outputs to text file of same name
 // Used to parse Pluralsight notes, which are unfortunately only available in csv form
 // IMPORTANT: remember to delete all other columns before running through the CSVConverter
 // UPDATE ON September 24th 2017: should be able to ignore all other columns now
 // UPDATE ON December 2nd, 2017: modernized it, used StringBuilder, try-with-resources,Path(s) instead of FileReader/FileWriter
public class CSVConverter{

	public static String parseLine(String ptr){
		int i = 0;
		StringBuilder result = new StringBuilder(ptr.substring(0, ptr.lastIndexOf(",")));
		StringBuilder trimmed = new StringBuilder(ptr.substring(0, ptr.lastIndexOf(",")));
		while(i<4 && result.lastIndexOf(",") > -1){
			result.replace(0,result.length(),result.substring(0, result.lastIndexOf(",")));
			trimmed.replace(0,trimmed.length(),result.substring(result.indexOf("\"")+1, result.lastIndexOf("\"")).toString());
			if(trimmed.lastIndexOf(",") != trimmed.length()-1){
				i++;
			}
		}
		return trimmed.toString();
	}

	public static void outputToFile(String [] columns, BufferedWriter fw) throws IOException{ 
		fw.newLine();
		for(String ptr : columns){
				fw.write(ptr);
				fw.newLine();
		}
	}

	public static void main (String[] args){
		String[] columns;
		String row;
		String filename = args[0];
		String resultfile = filename.substring(0,filename.length() - 3) + "txt";
		Path read = Paths.get(filename);
		Path write = Paths.get(resultfile);

		try(BufferedReader reader = Files.newBufferedReader(read); BufferedWriter writer = Files.newBufferedWriter(write)){
		reader.readLine();
		while((row = reader.readLine())!=null){ 
				columns = parseLine(row).split("\\\\n");
				outputToFile(columns,writer);
		}

		}catch(Exception e){
			System.out.println(e.getClass().getSimpleName() + " - " + e.getMessage());
			return;
		}




}





















}