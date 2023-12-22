package up.mi.ald.root;

import java.io.IOException;

// command line interface: all the messages destined to communicate with the user go here
public class CLI {
	
	public static void inputMenu() {
		
	}
	
	public static void mainMenu(String path) {
		Agglomeration agg = createAgglomeration(path);
		System.out.println("List of cities:");
		System.out.println(agg.getCities());
		System.out.println("List of routes:");
		System.out.println(agg.getRoutes());
		System.out.println("List of recharge zones:");
		System.out.println(agg.getRechargeZones());
		int menuChoice = -1;
		do {
			System.out.println("1. Solve manually");
			System.out.println("2. Solve automatically (naive algorithm)");
			System.out.println("3. Solve automatically (optimised algorithm)");
			System.out.println("4. Save");
			System.out.println("0. Exit");
			menuChoice = Scanner.scanMenuChoice();
			switch(menuChoice) {
				case 1:
					manualSolutionMenu(agg);
					break;
				case 2:
					Recharge.solveAutomatically(agg);
					System.out.println(agg.getRechargeZones());
					break;
				case 3:
					Recharge.solveAutomatically2(agg);
					System.out.println(agg.getRechargeZones());
					break;
				case 4:
					path = getFilePath("Input the path to the file where you want to save the solution");
					try {
						Recharge.saveRechargeSolution(path, agg);
						System.out.println("The solution has been successfully saved.");
					} catch (IOException e) {
						System.out.println("An error occured when trying to access your file path. Please, check that the location you specified is correct and that there are no other issues preventing the writing operation.");
					}
					
					break;
				case 0:
					endProgram();
				default:
					System.out.println("Invalid menu choice. The menu choice must be a number from the menu list. Please, try again.");
			}
			menuChoice = -1;
		} while(menuChoice == -1);
	}
	
	public static void manualSolutionMenu(Agglomeration agg) {
		int menuChoice = -1;
		String city = null;
		do {
			System.out.println("1. Add a charging point");
			System.out.println("2. Remove a charging point");
			System.out.println("3. Show charging points");
			System.out.println("0. Exit");
			menuChoice = Scanner.scanMenuChoice();
			switch (menuChoice) {
				case 1:
					System.out.println("Enter the name of the city where you want to add a charging point");
					try {
						city = Scanner.scanCityName(agg);
						String message = agg.addRecharge(city);
						System.out.println(message);
					} catch(CityNotFoundException cnfe) {
						cnfe.printMessage();
						System.out.println("Please, enter a valid city name.");
					}
					break;
				case 2:
					System.out.println("Enter the name of the city from where you want to delete a charging point");
					try {
						city = Scanner.scanCityName(agg);
						String message = agg.deleteRecharge(city);
						System.out.println(message);
					} catch(CityNotFoundException cnfe) {
						cnfe.printMessage();
						System.out.println("Please, enter a valid city name.");
					}
					break;
				case 3:
					System.out.println("List of cities that have a charging point: ");
					System.out.println(agg.getRechargeZones());
					break;
				case 0:
					endProgram();
				default:
					System.out.println("Invalid menu choice. The menu choice must be a number from the menu list. Please, try again.");
			}
			menuChoice = -1;
		} while(menuChoice == -1);
	}
	
	private static Agglomeration createAgglomeration(String path) {
		if(path.isEmpty()) path = getFilePath("Input the path to the file containning your agglomeration data:");
		Agglomeration agg = null;
		try {
			agg = AgglomerationFileParser.parseFile(path);
		} catch (IOException ioe) {
			System.out.println("An error occured when trying to access your data file. Please, check that the location you specified is correct and that there are no other issues preventing the reading operation.");
			endProgram();
		} catch (CityNotFoundException cnfe) {
			cnfe.printMessage();
			endProgram();
		} catch (IllegalDataFormattingException idfe) {
			idfe.printMessage();
			endProgram();
		}
		return agg;
	}
	
	private static String getFilePath(String message) {
		System.out.println(message);
		String path = Scanner.scanFilePath();
		while (path.isEmpty()) {
			System.out.println("Invalid file path. Please, try again:");
			path = Scanner.scanFilePath();
		}
		return path;
	}
	
	public static void endProgram() {
		System.out.println("Program ended");
		System.exit(0);
	}
}
