package up.mi.ald.root;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Agglomeration {
	
	private Set<String> cities = new HashSet<>();
	private Set<String> routes = new HashSet<>();
	private Set<String> rechargeZones = new HashSet<>();
	private boolean[][] routesMatrix;
	private int numberOfCities; 
	private String[] citiesIndex;
	
	public Agglomeration() {
		this.numberOfCities = 0;
	}
	
	// this constructor is used when the user only gives the number of cities without names
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

	public void addCity(String cityName) {
		cities.add(cityName);
		numberOfCities++;
	}
	
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
	
	private int getCityIndex(String city) {
		int cityIndex = -1;
		for(int i = 0; i < numberOfCities; i++) {
	        if (citiesIndex[i].equals(city)) {
	            cityIndex = i;
	        }
	    }
		return cityIndex;
	}
	
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
	
	public boolean checkRechargeExists(String city) {
		if(rechargeZones.contains(city)) return true;
		else return false;
	}
	
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
