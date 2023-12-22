package up.mi.ald.root;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
* The Agglomeration class represents an urban structure composed of cities, routes, and recharge zones.
* It provides methods to add and delete cities, routes, and recharge zones, as well as check the accessibility of recharge zones.
*
* @author Lina Djihane AZIZA, Suntanqing FU
* @version 1.0
*/
public class Agglomeration {
	
	private Set<String> cities = new HashSet<>();
	private Set<String> routes = new HashSet<>();
	private Set<String> rechargeZones = new HashSet<>();
	private boolean[][] routesMatrix;
	private int numberOfCities; 
	private String[] citiesIndex;
	
	/**
     * Constructs an empty Agglomeration with zero cities.
     */
	public Agglomeration() {
		this.numberOfCities = 0;
	}
	
	/**
     * Constructs an Agglomeration with a specified number of cities, each with a default name.
     * Charging points are added to every city by default.
     * It is used when the user only gives the number of cities without names.
     *
     * @param numberOfCities The number of cities in the agglomeration.
     */
	public Agglomeration(int numberOfCities) {
		this.numberOfCities = numberOfCities;
		for (int i = 0; i < numberOfCities; i++) {
			cities.add("C" + i);
		}
		this.routesMatrix = createRoutesMatrix();
		this.citiesIndex = createCitiesIndex();
		// naive approach: there is a charging point in every city
		for(int i = 0; i < numberOfCities; i++) {
			rechargeZones.addAll(cities);
		}	
	}
	
	/**
     * Constructs an Agglomeration with specified sets of cities, routes, and recharge zones.
     *
     * @param cities The set of city names within the agglomeration.
     * @param routes The set of route information connecting the cities.
     * @param rechargeZones The set of recharge zones within the cities.
     */
	public Agglomeration(Set<String> cities, Set<String> routes, Set<String> rechargeZones) {
		this.cities = cities;
		this.routes = routes;
		this.numberOfCities = cities.size();
		this.citiesIndex = createCitiesIndex();
		this.routesMatrix = createRoutesMatrix();
		this.rechargeZones = rechargeZones;
		// if the accessibility constraint is not respected, apply the naive approach
		if(this.checkAccessibility().isEmpty() != true) {
			for(int i = 0; i < numberOfCities; i++) {
				this.rechargeZones.addAll(cities);
			}
		}
	}
	
	/**
     * Adds a city to the agglomeration.
     *
     * @param cityName The name of the city to add.
     */
	public void addCity(String cityName) {
		cities.add(cityName);
		numberOfCities++;
	}
	
	/**
     * Adds a route to the agglomeration.
     *
     * @param route The route information to add.
     * @throws CityNotFoundException If one or both cities in the route do not exist.
     */
	public void addRoute(String route) throws CityNotFoundException {
		String city1 = route.substring(route.indexOf('(') + 1, route.indexOf(','));
		String city2 = route.substring(route.indexOf(',') + 2, route.indexOf(')'));
		if (checkCityExists(city1) && checkCityExists(city2)) {
			if (routesMatrix == null) routesMatrix = new boolean[numberOfCities][numberOfCities];
			int cityIndex1 = getCityIndex(city1);
			int cityIndex2 = getCityIndex(city2);
			routesMatrix[cityIndex1][cityIndex2] = true;
			routesMatrix[cityIndex2][cityIndex1] = true;
		}
	}
	
	/**
     * Adds a recharge zone to a city in the agglomeration.
     *
     * @param city The city where to add a recharge zone.
     * @return A message indicating the success or failure of adding the recharge zone.
     * @throws CityNotFoundException If the specified city does not exist.
     */
	public String addRecharge(String city) throws CityNotFoundException {
		String message = null;
		if(checkCityExists(city)) {
			if(rechargeZones.contains(city)) {
				message = "A charging point already exists in " + city;
			} else {
				message = "A charging point was added to " + city;
				rechargeZones.add(city);
			}
		}
		return message;
	}
	
	/**
     * Deletes a recharge zone from a city in the agglomeration.
     *
     * @param city The city where the recharge zone needs to be removed.
     * @return A message indicating the success or failure of removing the recharge zone.
     * @throws CityNotFoundException If the specified city does not exist.
     */
	public String deleteRecharge(String city) throws CityNotFoundException {
		Set<String> nonCoveredZones = null;
		String message = null;
		if(checkCityExists(city)) {
			rechargeZones.remove(city);
			nonCoveredZones = checkAccessibility();
			if(!nonCoveredZones.isEmpty()) {
				message = "The charging point in " + city + "can't be removed. Because the following cities wouldn't have access to a charging point:" + nonCoveredZones;
				addRecharge(city);
			} else {
				message = "A charging point has been removed from " + city;
			}
		}
		return message;
	}
	
	/**
     * Retrieves the index of a city based on its name in the internal cities array.
     *
     * @param city The name of the city to find the index of.
     * @return The index of the city, or -1 if the city is not found.
     */
	public int getCityIndex(String city) {
		int cityIndex = -1;
		for(int i = 0; i < numberOfCities; i++) {
	        if (citiesIndex[i].equals(city)) {
	            cityIndex = i;
	        }
	    }
		return cityIndex;
	}
	
	/**
     * Retrieves the name of a city based on its index in the internal cities array.
     *
     * @param index The index of the city.
     * @return The name of the city.
     */
	public String getCityName(int index) {
		return citiesIndex[index];
	}
	
	private boolean[][] createRoutesMatrix() {
		boolean[][] routesMatrix = new boolean[numberOfCities][numberOfCities];
		Iterator<String> r = routes.iterator();
		while (r.hasNext()) {
			String route = r.next();
			int cityIndex1 = getCityIndex(route.substring(route.indexOf('(') + 1, route.indexOf(',')));
			int cityIndex2 = getCityIndex(route.substring(route.indexOf(',') + 2, route.indexOf(')')));
			routesMatrix[cityIndex1][cityIndex2] = true;
			routesMatrix[cityIndex2][cityIndex1] = true;
		}
		return routesMatrix;
	}
	
	private String[] createCitiesIndex() {
		String[] citiesIndex = Arrays.copyOf(cities.toArray(), numberOfCities, String[].class);
		return citiesIndex;
	}
	
	/**
     * Checks whether the accessibility constraint is respected in the agglomeration.
     *
     * @return A set containing the names of cities that do not have access to a charging point.
     */
	public Set<String> checkAccessibility() {
		Set<String> coveredZones = new HashSet<>(rechargeZones); // Set of cities that have access to a charging point
		Iterator<String> rz = rechargeZones.iterator();
		int zoneIndex;
		String zone;
		while (rz.hasNext()) {
			zone = rz.next();
			zoneIndex = getCityIndex(zone);
			for(int i = 0; i < numberOfCities; i++) {
				if(routesMatrix[zoneIndex][i] == true) {
					coveredZones.add(citiesIndex[i]);
				}
			}
		}
		// creating a set that contains the indexes of non covered cities
		Set<String> nonCoveredZones = new HashSet<>(cities);
		nonCoveredZones.removeAll(coveredZones);
		
		return nonCoveredZones;
	}
	
	/**
     * Checks if a recharge point exists in the specified city.
     *
     * @param city The name of the city to check for a recharge point.
     * @return true if a recharge point exists, false otherwise.
     */
	public boolean checkRechargeExists(String city) {
		if(rechargeZones.contains(city)) return true;
		else return false;
	}
	
	/**
     * Checks if a city exists in the agglomeration.
     *
     * @param cityName The name of the city to check.
     * @return true if the city exists, throws CityNotFoundException otherwise.
     * @throws CityNotFoundException If the specified city does not exist.
     */
	public boolean checkCityExists(String cityName) throws CityNotFoundException {
		if (cities.contains(cityName)) {
			return true;
		} else {
			throw new CityNotFoundException(cityName + " does not exist.");
		}	
	}
	
	public Set<String> getCities() {
		return cities;	
	}
	
	public Set<String> getRechargeZones() {
		return rechargeZones;
	}
	
	public Set<String> getRoutes() {
		return routes;
	}
	
	public int getNumberOfCities() {
		return this.numberOfCities;
	}
	
	public int getNumberOfRechargeZones() {
		return rechargeZones.size();
	}
}
