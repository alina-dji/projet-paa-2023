package up.mi.ald.root;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class AgglomerationFileParser {
	
	public static void readFile(String path) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
	        String line;
	        while ((line = reader.readLine()) != null) {
	        	parseLine(line);
	        }
	        reader.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
    }
	
	public static void parseLine(String line) {
		int endOfCities = 0;
		int endOfRoutes = 0;
		 
		if(line.matches("/ville\\(\\X+\\)./gm") && endOfCities != -1 ) {
			extractCity(line);
		}
		
		if(line.matches("/route\\(\\X+, \\X+\\)./gm") && endOfRoutes != -1) {
			extractRoute(line);
		}
		
		if(line.matches("/recharge\\(\\X+\\)./gm")) {
			extractRecharge(line);
		}
	}
	
	public static Set<String> extractCity(String line) {
		Set<String> cities = new HashSet<>();
		return cities;
	}
	
	public static Set<String> extractRoute(String line) {
		Set<String> routes = new HashSet<>();
		return routes;
	}
	
	public static Set<String> extractRecharge(String line) {
		Set<String> recharges = new HashSet<>();
		return recharges;
	}
}
