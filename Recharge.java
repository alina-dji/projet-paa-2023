package up.mi.ald.root;

import java.time.temporal.ValueRange;
import java.util.Scanner;

// Recharge is a utility class
public class Recharge {
	private static Scanner scanner = new Scanner(System.in);
	
	public static void addRechargePoint(Agglomeration agg) {
		int indexOfCity = -1;
		System.out.println("Please enter the index of the city where the charging post needs to be installed");
		agg.showCities();
		indexOfCity = checkCityIndex(agg, indexOfCity);
		if(indexOfCity == -1) return;
		if(!agg.rechargeZones.add(indexOfCity)) {
			// will only be added in the set if it doesn't exist already
			System.out.println("A charging point already exists in this city.");
		}
		agg.showRechargeZones();
	}
	
	public static void deleteRechargePoint(Agglomeration agg) {
		int indexOfCity = -1;
		System.out.println("Please enter the index of the city you want to remove the charging point from.");
		agg.showCities();
		indexOfCity = checkCityIndex(agg, indexOfCity);
		if(indexOfCity == -1) return;
		boolean canBeRemoved = true;
		for(int i = 0; i < agg.getNumberOfCities(); i++) {
			if(checkAccessibility(agg, indexOfCity, i) == false) {
				canBeRemoved = false;
			}
		}
		if (canBeRemoved) {
			agg.rechargeZones.remove(indexOfCity);
			System.out.println("charging point removed from city C" + indexOfCity);
		} else {
			System.out.println("Charging point can't be removed from this city.1");
		}
		agg.showRechargeZones();
	}
	
	private static int checkCityIndex(Agglomeration agg, int index) {
		try {
			index = Integer.parseInt(scanner.nextLine());
			if(ValueRange.of(0, agg.getNumberOfCities()-1).isValidIntValue(index)) {
				return index; // if index is correct we return it
			}
			else {
				System.out.println("*****ERROR*****\nInvalid city index.\nIt doesn't correspond to an index of a city that exists in the list of cities.\n***************");
				return -1; // if index is incorrect we return -1
			}
		} catch (Exception e) {
			System.out.println("*****ERROR*****\nIndex of a city must be a positive integer\n***************");
		}
		return index;
	}
	
	private static boolean checkAccessibility(Agglomeration agg, int indexOfCity, int i) {
		// if a city x has a charging point 
		if(agg.rechargeZones.contains(indexOfCity)) {
			// we check whether the cities that are connected to city x have a charging point
			if(agg.routes[indexOfCity][i] == true && agg.rechargeZones.contains(i)) {
				// if they do have a charging point we can remove the charging point of city x
				return true;
			} else if(agg.routes[indexOfCity][i] == true && !agg.rechargeZones.contains(i)){
				System.out.println("C" + i + " won't have access to a charging point.");
				return false;
			}
		}
		return true;
	}

}
