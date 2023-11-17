package up.mi.ald.root;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		System.out.println("----- Projet Bornes de Recharge Phrase1 -----");
		System.out.println("Lina Djihane AZIZA, Suntanqing FU");
		
		Scanner scanner = new Scanner(System.in);
		Agglomeration agg = null;
		int numberOfCities = -1;
		int choice = -1;
		
		System.out.println("Please enter a number representing the number of cities.");
		System.out.println("The name of the city will be represented as Ci (i is an increment index).");
		do {
			System.out.println("number of cities: n = ");
			try {
				numberOfCities = Integer.parseInt(scanner.nextLine());
				agg = new Agglomeration(numberOfCities);
			} catch (Exception e) {
				System.out.println("*****ERROR*****\nThe number of cities must be a positive integer.\n***************");
			}
		} while(numberOfCities <= 0);
		
		do {
			System.out.println("1. Add a route");
			System.out.println("2. Show routes");
			System.out.println("0. Exit");
			try {
				choice = Integer.parseInt(scanner.nextLine());
			} catch (Exception e) {
				choice = -1;
			}
			switch (choice) {
				case 1:
					agg.addRoutes();
					break;
				case 2:
					agg.showRoutes();
					break;
				case 0:
					break;
				default:
					System.out.println("*****ERROR*****\nEnter the correct number of the action you want to perform from the menu.\n***************");
			}	
		} while(choice != 0);
		
		do {
			System.out.println("1. Add a charging point");
			System.out.println("2. Remove a charging point");
			System.out.println("3. Show charging points");
			System.out.println("0. Exit");
			try {
				choice = Integer.parseInt(scanner.nextLine());
			} catch (Exception e) {
				choice = -1;
			}
			switch (choice) {
				case 1:
					Recharge.addRechargePoint(agg);
					break;
				case 2:
					Recharge.deleteRechargePoint(agg);
					break;
				case 3:
					agg.showRechargeZones();
					break;
				case 0:
					break;
				default:
					System.out.println("*****ERROR*****\nEnter the correct number of the action you want to perform from the menu.\n***************");
			}	
		} while(choice != 0);
		
		scanner.close();
	}
}
