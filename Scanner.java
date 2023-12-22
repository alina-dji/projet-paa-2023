package up.mi.ald.root;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

/**
*
* The Scanner class is a utility class that contains all the methods needed to scan 
* the user's input from the keyboard.
*
* @author Lina Djihane AZIZA, Suntanqing FU
* @version 1.0
*
*/
public class Scanner {
	
	private static java.util.Scanner scanner = new java.util.Scanner(System.in);
	
	public static String scanFilePath() {
		String path = scanner.nextLine();
		try {
            Paths.get(path);
            return path;
        } catch (InvalidPathException ipe) {
            return "";
        }
	}
	
	public static String scanCityName(Agglomeration agg) throws CityNotFoundException {
		String cityName = scanner.nextLine();
		if(agg.checkCityExists(cityName) == true) return cityName;
		else return "";
	}
	
	public static int scanNumberOfCities() {
		int numberOfCities = -1;
		try {
			numberOfCities = Integer.parseInt(scanner.nextLine());
		} catch(NumberFormatException nfe) {
			numberOfCities = -1;
		}
		return numberOfCities;
	}

	public static int scanMenuChoice() {
		int menuChoice = -1;
		try {
			menuChoice = Integer.parseInt(scanner.nextLine());
		} catch (NumberFormatException nfe) {
			menuChoice = -1;	
		}
		return menuChoice;
	}
	
}
