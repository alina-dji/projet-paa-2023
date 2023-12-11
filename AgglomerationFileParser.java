package up.mi.ald.root;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class AgglomerationFileParser {
	
	public static Agglomeration parseFile(String path) {
		Agglomeration agg = null;
		Set<String> cities = new HashSet<>();
		Set<String> routes = new HashSet<>();
		Set<String> recharges = new HashSet<>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
	        String line;
	        while ((line = reader.readLine()) != null) {
	        	String[] data = parseLine(line);
	        	if(data[0] == "city") cities.add(data[1]);
	        	else if(data[0] == "route") routes.add(data[1]);
	        	else if(data[0] == "recharge") recharges.add(data[1]);	
	        }
	        reader.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return agg;
    }
	
	public static String[] parseLine(String line) {
		int citiesFlag = 0;
		int routesFlag = -1;
		int rechargesFlag = -1;
		String[] data = new String[2];
		Set<String> cities = new HashSet<>();
		
		if(line.matches("/ville\\(\\X+\\)./gm") && citiesFlag != -1 ) {
			data[0] = "city";
			data[1] = extractCity(line);
			cities.add(data[1]);
			routesFlag = 0;
			rechargesFlag = 0;
		} else if(line.matches("/route\\(\\X+, \\X+\\)./gm") && routesFlag != -1) {
			citiesFlag = -1;
			data[0] = "route";
			data[1] = extractRoute(line, cities);
		} else if(line.matches("/recharge\\(\\X+\\)./gm") && rechargesFlag != -1) {
			routesFlag = -1;
			extractRecharge(line, cities);
		} else {
			// throw IllegalDataFormatting 
		}
		
		return data;
	}
	
	public static String extractCity(String line) {
		String city = line.substring(line.indexOf('(') + 1, line.indexOf(')'));
		return city;
	}
	
	public static String extractRoute(String line, Set<String> cities) {
		String route = null;
		boolean cityOneExists = cities.contains(line.substring(line.indexOf('(') + 1, line.indexOf(',')));
		boolean cityTwoExists = cities.contains(line.substring(line.indexOf(',') + 2, line.indexOf(')')));
		if (cityOneExists && cityTwoExists) {
			route = line.substring(line.indexOf('('), line.indexOf(')') + 1);
		} else {
			// throw CityNotFoundException
		}
		return route;
	}
	
	public static String extractRecharge(String line, Set<String> cities) {
		String recharge = null;
		boolean cityExists = cities.contains(line.substring(line.indexOf('(') + 1, line.indexOf(')')));
		if(cityExists) {
			recharge = line.substring(line.indexOf('(') + 1, line.indexOf(')'));
		} else {
			// throw CityNotFoundException
		}
		return recharge;
	}
}
