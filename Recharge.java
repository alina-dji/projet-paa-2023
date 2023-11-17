package up.mi.ald.root;

import java.time.temporal.ValueRange;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Iterator;

// Recharge is a utility class
public class Recharge {
	private static Scanner scanner = new Scanner(System.in);
	
	public static void addRechargePoint(Agglomeration agg) {
		int indexOfCity = -1;
		System.out.println("Please enter the index of the city where the charging post needs to be installed");
		agg.showCities();
		indexOfCity = getCityIndex(agg, indexOfCity);
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
		indexOfCity = getCityIndex(agg, indexOfCity); 
		if(indexOfCity == -1) return;
		agg.rechargeZones.remove(indexOfCity);
		if(checkAccessibility(agg) == false) {
			System.out.println("Charging point can't be removed from this city.");
			agg.rechargeZones.add(indexOfCity);
		} else {
			System.out.println("Charging point removed from city C" + indexOfCity);
		}
		agg.showRechargeZones();
	}
	
	private static boolean checkAccessibility(Agglomeration agg) {
		Set<Integer> coveredZones = new HashSet<>(); // Set of cities that have access to a charging point directly or indirectly
		coveredZones.addAll(agg.rechargeZones);
		Iterator<Integer> cz = coveredZones.iterator();
		while (cz.hasNext()) {
			Integer zoneIndex = cz.next();
			for(int i = 0; i < agg.getNumberOfCities(); i++) {
				if(agg.routes[zoneIndex][i] == true) {
					coveredZones.add(i);
				}
			}
			cz = coveredZones.iterator();
		}
		Set<Integer> cityIndexes = new HashSet<>();
		for(int i = 0; i < agg.getNumberOfCities(); i++) {
			cityIndexes.add(i);
		}
		Set<Integer> nonCoveredZones = new HashSet<>(cityIndexes);
		nonCoveredZones.removeAll(coveredZones);
		if(!nonCoveredZones.isEmpty()) {
			System.out.println("The following cities don't have access to a charging zone");
			System.out.println(nonCoveredZones);
		}
		return coveredZones.equals(cityIndexes);
	}
	
	private static int getCityIndex(Agglomeration agg, int index) {
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
