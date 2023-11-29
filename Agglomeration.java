package up.mi.ald.root;

import java.time.temporal.ValueRange;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Agglomeration {
	protected int[] cities;
	protected boolean[][] routes; 
	protected Set<Integer> rechargeZones = new HashSet<>(); // a set that contains the indexes of cities that have a charging point
	Scanner scanner = new Scanner(System.in); // private???
	
	public Agglomeration(int numberOfCities) {
		this.cities = new int[numberOfCities];
		for (int i = 0; i < numberOfCities; i++) {
			cities[i] = i; // a better way to do this?
		}
		this.routes = new boolean[numberOfCities][numberOfCities]; // array is automatically Initialized to false
		// naive approach: there is a charging point in every city
		for(int i = 0; i < cities.length; i++) {
			rechargeZones.add(i);
		}
	}
	
	public void addRoutes() {
		int indexOfCity1 = -1, indexOfCity2 = -1;
		System.out.println("Enter the indexes of the two cities you want to connect with a route.");
		System.out.println("Make sure the city indexes are correct and do exist in the following list of cities:");
		showCities();
		try {
			System.out.print("Index of the first city = ");
			indexOfCity1 = Integer.parseInt(scanner.nextLine()); // check that the value entered by the user is a valid int
			System.out.print("Index of the second city = ");
			indexOfCity2 = Integer.parseInt(scanner.nextLine()); // check that the value entered by the user is a valid int
			if(!ValueRange.of(0, cities.length-1).isValidIntValue(indexOfCity1) || !ValueRange.of(0, cities.length-1).isValidIntValue(indexOfCity2)) {
				System.out.println("*****ERROR*****\nInvalid city index.\nIt doesn't correspond to an index of a city that exists in the list of cities.\n***************");
			} else if (indexOfCity1 == indexOfCity2) {
				System.out.println("*****ERROR*****\nYou can't have a route that connects a city to itself.\n***************");
			} else {
				routes[indexOfCity1][indexOfCity2] = true;
				routes[indexOfCity2][indexOfCity1] = true;
			}
		} catch (Exception e) {
			System.out.println("*****ERROR*****\nIndex of a city must be a positive integer\n***************");
		}
	}

	public void showCities() {
		System.out.println("List of cities:");
		for (int i = 0; i < cities.length; i++)
			System.out.print("C" + cities[i] + " "); // a better way to do it?
		System.out.println("");
	}
	
	public void showRoutes() {
		System.out.println("List of routes:");
		for (int i = 0; i < cities.length; i++) {
			for (int j = i + 1; j < cities.length; j++) {
				if ((routes[i][j] == true)) System.out.println("C" + i + ", C" + j);
			}
		}
	}
	
	public void showRechargeZones() {
		System.out.println("List of cities that have a charging point: ");
		System.out.println(rechargeZones);
	}
	
	public int getNumberOfCities() {
		return this.cities.length;
	}
}
