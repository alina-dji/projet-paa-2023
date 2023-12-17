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
	
	public static Agglomeration parseFile(String path) throws IllegalDataFormattingException, CityNotFoundException, IOException {
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String line;
		int lineNumber = 0;
		while ((line = reader.readLine()) != null) {
			lineNumber++;
			parseLine(line, lineNumber);
		}
		agg = new Agglomeration(cities, routes, recharges);
		reader.close();
		return agg;
    }
	
	public static void parseLine(String line, int lineNumber) throws IllegalDataFormattingException, CityNotFoundException {
		// TODO: edit regex to only accept names of cities that contain letters and the - character
		if(line.matches("ville\\(\\X+\\).") && citiesFlag != -1 ) {
			cities.add(extractCity(line));
			routesFlag = 0;
			rechargesFlag = 0;
		} else if(line.matches("route\\(\\X+, \\X+\\).") && routesFlag != -1) {
			citiesFlag = -1;
			routes.add(extractRoute(line, cities));
		} else if(line.matches("recharge\\(\\X+\\).") && rechargesFlag != -1) {
			routesFlag = -1;
			recharges.add(extractRecharge(line, cities));
		} else {
			throw new IllegalDataFormattingException("Your data is incorrectly formatted at line " + lineNumber);
		}
	}
	
	public static String extractCity(String line) {
		String city = line.substring(line.indexOf('(') + 1, line.indexOf(')'));
		return city;
	}
	
	public static String extractRoute(String line, Set<String> cities) throws CityNotFoundException {
		String route = null;
		boolean cityOneExists = cities.contains(line.substring(line.indexOf('(') + 1, line.indexOf(',')));
		boolean cityTwoExists = cities.contains(line.substring(line.indexOf(',') + 2, line.indexOf(')')));
		if (cityOneExists && cityTwoExists) {
			route = line.substring(line.indexOf('('), line.indexOf(')') + 1);
		} else {
			throw new CityNotFoundException(line + " can't be added because one or both cities of your route don't exist");
		}
		return route;
	}
	
	public static String extractRecharge(String line, Set<String> cities) throws CityNotFoundException {
		String recharge = null;
		boolean cityExists = cities.contains(line.substring(line.indexOf('(') + 1, line.indexOf(')')));
		if(cityExists) {
			recharge = line.substring(line.indexOf('(') + 1, line.indexOf(')'));
		} else {
			throw new CityNotFoundException(line + " can't be added to a city that does not exist");
		}
		return recharge;
	}
}
