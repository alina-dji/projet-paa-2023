package up.mi.ald.root;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

/**
* The Scanner class is a utility class provides static methods for scanning input from the user using the standard input stream (System.in).
*
* @author Lina Djihane AZIZA, Suntanqing FU
* @version 1.0
*/

public class Scanner {
	
	private static java.util.Scanner scanner = new java.util.Scanner(System.in);
	
	/**
     * Scans and returns a valid file path entered by the user.
     *
     * @return A valid file path as a String.
     */
	public static String scanFilePath() {
		String path = scanner.nextLine();
		try {
            Paths.get(path);
            return path;
        } catch (InvalidPathException ipe) {
            return "";
        }
	}
	
	/**
     * Scans and returns a valid city name entered by the user.
     *
     * @param agg The Agglomeration object to check if the city exists.
     * @return A valid city name as a String.
     * @throws CityNotFoundException If the scanned city name does not exist in the provided Agglomeration.
     */
	public static String scanCityName(Agglomeration agg) throws CityNotFoundException {
		String cityName = scanner.nextLine();
		if(agg.checkCityExists(cityName) == true) return cityName;
		else return "";
	}
	
	/**
     * Scans and returns the number of cities entered by the user.
     *
     * @return The number of cities as an integer.
     */
	public static int scanNumberOfCities() {
		int numberOfCities = -1;
		try {
			numberOfCities = Integer.parseInt(scanner.nextLine());
		} catch(NumberFormatException nfe) {
			numberOfCities = -1;
		}
		return numberOfCities;
	}

	/**
     * Scans and returns the menu choice entered by the user.
     *
     * @return The menu choice as an integer.
     */
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
