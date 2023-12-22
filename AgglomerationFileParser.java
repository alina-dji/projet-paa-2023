package up.mi.ald.root;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
* The AgglomerationFileParser class parses a file containing information about cities, routes, and recharges to create an Agglomeration object. 
* It reads the file line by line, extracts relevant information, and constructs the Agglomeration.
*
* @author Lina Djihane AZIZA, Suntanqing FU
* @version 1.0
*/
public class AgglomerationFileParser {
	
	private static int citiesFlag = 0;
	private static int routesFlag = -1;
	private static int rechargesFlag = -1;
	
	private static Set<String> cities = new HashSet<>();
	private static Set<String> routes = new HashSet<>();
	private static Set<String> recharges = new HashSet<>();
	
	/**
     * Parses the specified file and constructs an Agglomeration object.
     *
     * @param path The path of the file to be parsed.
     * @return An Agglomeration object representing the urban structure.
     * @throws IllegalDataFormattingException If the data in the file is incorrectly formatted.
     * @throws CityNotFoundException If a city specified in the file is not found.
     * @throws IOException If an I/O error occurs while reading the file.
     */
	public static Agglomeration parseFile(String path) throws IllegalDataFormattingException, CityNotFoundException, IOException {
		Agglomeration agg;
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
	
	 /**
     * Parses a line of data from the file and updates the sets of cities, routes, and recharges.
     *
     * @param line The line of data from the file that needs to be parsed.
     * @param lineNumber The line number of the file, for error reporting.
     * @throws IllegalDataFormattingException If the data in the line is incorrectly formatted.
     * @throws CityNotFoundException If a city specified in the line is not found.
     */
	public static void parseLine(String line, int lineNumber) throws IllegalDataFormattingException, CityNotFoundException {
		//TODO edit regex to only accept names of cities that contain letters and the - character
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
	
	/**
     * Extracts the city name from a "ville()." file line.
     *
     * @param line The "ville()." line to extract the city name from.
     * @return The name of the city extracted from the file line.
     */
	public static String extractCity(String line) {
		String city = line.substring(line.indexOf('(') + 1, line.indexOf(')'));
		return city;
	}
	
	
	/**
     * Extracts the route information from a "route()." file line and ensures that the cities in the route exist.
     *
     * @param line The "route()." line to extract the route information from.
     * @param cities The set of cities to check for existence.
     * @return The route information.
     * @throws CityNotFoundException If one or both cities in the route do not exist.
     */
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
	
	/**
     * Extracts the recharge information from a "recharge()." line and ensures that the specified city exists.
     *
     * @param line The "recharge()." line to extract the recharge information from.
     * @param cities The set of cities to check for existence.
     * @return The recharge information.
     * @throws CityNotFoundException If the specified city does not exist.
     */
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