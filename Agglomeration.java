package up.mi.ald.root;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Agglomeration {
	
	private Set<String> cities = new HashSet<>();
	private Set<String> routes = new HashSet<>();
	private Set<String> rechargeZones = new HashSet<>();
	
	private int numberOfCities; // !!! may remove if not useful
	private boolean[][] routesMatrix;
	private String[] citiesIndex;
	
	// this constructor is used when the user only gives the number of cities without names
	public Agglomeration(int numberOfCities) {
		this.numberOfCities = numberOfCities;
		for (int i = 0; i < numberOfCities; i++) {
			cities.add("C" + i);
		}
		this.routesMatrix = createRoutesMatrix();
		citiesIndex = createCitiesIndex();
		// naive approach: there is a charging point in every city
		for(int i = 0; i < numberOfCities; i++) {
			rechargeZones.addAll(cities);
		}	
	}
	
	public Agglomeration(Set<String> cities, Set<String> routes, Set<String> rechargeZones) {
		this.cities = cities;
		this.routes = routes;
		this.rechargeZones = rechargeZones;
		this.numberOfCities = cities.size();
		this.citiesIndex = createCitiesIndex();
		this.routesMatrix = createRoutesMatrix();
	}
	
	public void addCity(String city) {
		cities.add(city);
		numberOfCities++;
		citiesIndex = createCitiesIndex();
		routesMatrix = createRoutesMatrix();
	}
	
	public void addRoute(String route) throws IllegalDataFormattingException {
		if (route.matches("(\\X+, \\X+)")) {
			routes.add(route);
			this.routesMatrix = createRoutesMatrix();
		} else {
			throw new IllegalDataFormattingException("A route should be formatted like this: (city1, city2)");
		}
		
	}
	
	public void addRecharge(String rechargeZone) throws CityNotFoundException {
		if (cities.contains(rechargeZone)) {
			rechargeZones.add(rechargeZone);
		} else {
			throw new CityNotFoundException(rechargeZone + " does not exist");
		}	
	}
	
	private boolean[][] createRoutesMatrix() {
		boolean[][] routesMatrix = new boolean[numberOfCities][numberOfCities];
		Iterator<String> r = routes.iterator();
		while (r.hasNext()) {
			String route = r.next();
			int cityIndex1 = Arrays.binarySearch(citiesIndex, route.substring(route.indexOf('(') + 1, route.indexOf(',')));
			int cityIndex2 = Arrays.binarySearch(citiesIndex, route.substring(route.indexOf(',') + 2, route.indexOf(')')));
			routesMatrix[cityIndex1][cityIndex2] = true;
			routesMatrix[cityIndex2][cityIndex1] = true;
		}
		return routesMatrix;
	}
	
	private String[] createCitiesIndex() {
		String[] citiesIndex = Arrays.copyOf(cities.toArray(), numberOfCities, String[].class);
		return citiesIndex;
	}
	
	//TODO edit this method so that it doesn't print messages and returns list of nonCoveredZones
	public boolean checkAccessibility() {
		Set<String> coveredZones = new HashSet<>(rechargeZones); // Set of cities that have access to a charging point
		Iterator<String> rz = rechargeZones.iterator();
		int zoneIndex = -1;
		while (rz.hasNext()) {
			zoneIndex++;
			for(int i = 0; i < numberOfCities; i++) {
				if(routesMatrix[zoneIndex][i] == true) {
					coveredZones.add(citiesIndex[i]);
				}
			}
		}
		// creating a set that contains the indexes of non covered cities
		Set<String> nonCoveredZones = new HashSet<>(cities);
		nonCoveredZones.removeAll(coveredZones);
		System.out.println(coveredZones);
		System.out.println(nonCoveredZones);
		if(!nonCoveredZones.isEmpty()) {
			System.out.println("The following cities don't have access to a charging point");
			System.out.println(nonCoveredZones);
		}
		
		return nonCoveredZones.isEmpty();
	}

	public void printCities() {
		System.out.println("List of cities:");
		System.out.println(cities);
	}
	
	public void printRoutes() {
		System.out.println("List of routes:");
		System.out.println(routes);
	}
	
	public void printRechargeZones() {
		System.out.println("List of cities that have a charging point: ");
		System.out.println(rechargeZones);
	}
	
	public void printCitiesIndex() {
		for(int i = 0; i < citiesIndex.length; i++) {
			System.out.println(i + ":" + citiesIndex[i]);
		}
	}
	
	/*public void printRoutesMatrix() {
		for (boolean[] row : routesMatrix) {
			System.out.println(Arrays.toString(row));
		}       
	}
	
	public int getNumberOfCities() {
		return this.numberOfCities;
	}*/
}
