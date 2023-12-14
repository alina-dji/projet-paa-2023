package up.mi.ald.root;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class AgglomerationFileParser {
	
	private static Agglomeration agg = null;
	
	private static int citiesFlag = 0;
	private static int routesFlag = -1;
	private static int rechargesFlag = -1;
	
	private static Set<String> cities = new HashSet<>();
	private static Set<String> routes = new HashSet<>();
	private static Set<String> recharges = new HashSet<>();
	
	public static Agglomeration parseFile(String path) {
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
	        String line;
	        while ((line = reader.readLine()) != null) {
	        	parseLine(line);
	        	/*String[] data = parseLine(line);
	        	if(data[0] == "city") cities.add(data[1]);
	        	else if(data[0] == "route") routes.add(data[1]);
	        	else if(data[0] == "recharge") recharges.add(data[1]);*/
	        }
	        reader.close();
		} catch(IOException e) {
			e.printStackTrace();
			e.getMessage();
			e.toString();
		}
		System.out.println(cities);
		System.out.println(routes);
		System.out.println(recharges);
		return agg;
    }
	
	public static void parseLine(String line) {
		
		//String[] data = new String[2];
		
		if(line.matches("ville\\(\\X+\\).") && citiesFlag != -1 ) {
			//data[0] = "city";
			//data[1] = extractCity(line);
			//cities.add(data[1]);
			cities.add(extractCity(line));
			routesFlag = 0;
			rechargesFlag = 0;
		} else if(line.matches("route\\(\\X+, \\X+\\).") && routesFlag != -1) {
			citiesFlag = -1;
			//data[0] = "route";
			//data[1] = extractRoute(line, cities);
			routes.add(extractRoute(line, cities));
		} else if(line.matches("recharge\\(\\X+\\).") && rechargesFlag != -1) {
			routesFlag = -1;
			//data[0] = "recharge";
			//data[1] = extractRecharge(line, cities);
			recharges.add(extractRecharge(line, cities));
		} else {
			// throw IllegalDataFormattingException
			System.out.println("IllegalDataFormattingException");
		}
		
		//return data;
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
			System.out.println("CityNotFoundException");
			
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
			System.out.println("CityNotFoundException");
		}
		return recharge;
	}
}
