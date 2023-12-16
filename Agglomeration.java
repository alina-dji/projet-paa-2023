package up.mi.ald.root;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Agglomeration {
	
	private Set<String> cities = new HashSet<>();
	private Set<String> routes = new HashSet<>();
	private Set<String> rechargeZones = new HashSet<>();
	
	int numberOfCities;
	private boolean[][] routesMatrix;
	private String[] citiesIndex;
	
	// this constructor is used when the user only gives the number of cities without names
	public Agglomeration(int numberOfCities) {
		this.numberOfCities = numberOfCities;
		for (int i = 0; i < numberOfCities; i++) {
			cities.add("C" + i);
		}
		//TODO: use createRoutesMatrix to create routesMatrix
		this.routesMatrix = new boolean[numberOfCities][numberOfCities]; // array is automatically Initialized to false
		// naive approach: there is a charging point in every city
		for(int i = 0; i < numberOfCities; i++) {
			rechargeZones.addAll(cities);
		}
		citiesIndex = createCitiesIndex();
	}
	
	public Agglomeration(Set<String> cities, Set<String> routes, Set<String> rechargeZones) {
		this.cities = cities;
		this.routes = routes;
		this.rechargeZones = rechargeZones;
		this.numberOfCities = cities.size();
		this.citiesIndex = createCitiesIndex();
		//TODO: create routesMatrix
	}
	
	public void addCity(String city) {
		cities.add(city);
		numberOfCities++;
		citiesIndex = createCitiesIndex();
		routesMatrix = createRoutesMatrix();
	}
	
	public void addRoute(String route) {
		routes.add(route);
		this.routesMatrix = createRoutesMatrix();
	}
	
	public void addRecharge(String rechargeZone) {
		rechargeZones.add(rechargeZone);
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
	
	//TODO: edit this method so that it doesn't print messages
	public boolean checkAccessibility() {
		Set<String> coveredZones = new HashSet<>(rechargeZones); // Set of cities that have access to a charging point
		Iterator<String> rz = rechargeZones.iterator();
		int zoneIndex = -1;
		while (rz.hasNext()) {
			zoneIndex++;
			for(int i = 0; i < numberOfCities; i++) {
				if(routesMatrix[zoneIndex][i] == true) {
					coveredZones.add((String)citiesIndex[i]);
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
	
	//TODO: delete this method
	/*public void addRoutes() {
		int indexOfCity1 = -1, indexOfCity2 = -1;
		System.out.println("Enter the indexes of the two cities you want to connect with a route.");
		System.out.println("Make sure the city indexes are correct and do exist in the following list of cities:");
		printCities();
		try {
			System.out.print("Index of the first city = ");
			indexOfCity1 = Integer.parseInt(scanner.nextLine()); // check that the value entered by the user is a valid int
			System.out.print("Index of the second city = ");
			indexOfCity2 = Integer.parseInt(scanner.nextLine()); // check that the value entered by the user is a valid int
			if(!ValueRange.of(0, cts.length-1).isValidIntValue(indexOfCity1) || !ValueRange.of(0, cts.length-1).isValidIntValue(indexOfCity2)) {
				System.out.println("*****ERROR*****\nInvalid city index.\nIt doesn't correspond to an index of a city that exists in the list of cities.\n***************");
			} else if (indexOfCity1 == indexOfCity2) {
				System.out.println("*****ERROR*****\nYou can't have a route that connects a city to itself.\n***************");
			} else {
				routesMatrix[indexOfCity1][indexOfCity2] = true;
				routesMatrix[indexOfCity2][indexOfCity1] = true;
			}
		} catch (Exception e) {
			System.out.println("*****ERROR*****\nIndex of a city must be a positive integer\n***************");
		}
	}*/

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
	
	public int getNumberOfCities() {
		return this.numberOfCities;
	}
}
