package up.mi.ald.root;

import java.time.temporal.ValueRange;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Iterator;
//TODO move all scanner code to Scanner class
// Recharge is a utility class
public class Recharge {
	private static Scanner scanner = new Scanner(System.in);
	
	public static void addRechargePoint(Agglomeration agg) {
		int indexOfCity = -1;
		System.out.println("Please enter the index of the city where the charging post needs to be installed");
		agg.printCities();
		indexOfCity = scanCityIndex(agg, indexOfCity);
		if(indexOfCity == -1) return;
		if(!agg.rechargeZones.add(indexOfCity)) {
			// will only be added in the set if it doesn't exist already
			System.out.println("A charging point already exists in this city.");
		}
		agg.printRechargeZones();
	}
	
	public static void deleteRechargePoint(Agglomeration agg) {
		int indexOfCity = -1;
		System.out.println("Please enter the index of the city you want to remove the charging point from.");
		agg.printCities();
		indexOfCity = scanCityIndex(agg, indexOfCity); 
		if(indexOfCity == -1) return; // if the city index is invalid the deletion operation is not performed
		agg.rechargeZones.remove(indexOfCity);
		if(checkAccessibility(agg) == false) {
			// if nonCoveredZones is not empty aka there are cities that are not covered
			agg.rechargeZones.add(indexOfCity);
			System.out.println("Charging point can't be removed from this city.");
		} else {
			System.out.println("Charging point removed from city C" + indexOfCity);
		}
		agg.printRechargeZones();
	}
	
	//TODO move this method to Scanner class
	private static int scanCityIndex(Agglomeration agg, int index) {
		try {
			index = Integer.parseInt(scanner.nextLine());
			if(ValueRange.of(0, agg.getNumberOfCities()-1).isValidIntValue(index)) {
				return index; // if index is correct we return it
			}
			else {
				System.out.println("*****ERROR*****\nInvalid city index.\nIt doesn't correspond to an index of a city that exists in the list of cities.\n***************");
				return -1; // if index is invalid we return -1
			}
		} catch (Exception e) {
			System.out.println("*****ERROR*****\nIndex of a city must be a positive integer\n***************");
		}
		return index;
	}
}
