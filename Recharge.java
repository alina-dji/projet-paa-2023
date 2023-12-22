package up.mi.ald.root;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.SecureRandom;
import java.util.Iterator;
import java.util.Set;

/**
 * The Recharge class provides methods for automatically solving the charging point placement problem
 * in an Agglomeration, as well as saving the solution to a file.
 *
 * @author Lina Djihane AZIZA, Suntanqing FU
 * @version 1.0
 */
public class Recharge {
	
	/**
     * Solves the charging point placement problem in an Agglomeration using a basic heuristic algorithm.
     *
     * @param agg The Agglomeration object representing the city network.
     */
	public static void solveAutomatically(Agglomeration agg) {
		int numberOfCities = agg.getNumberOfCities();
		int k = numberOfCities + 1000;
		int oldScore = agg.getNumberOfRechargeZones();
		int newScore;
		int i = 0;
		SecureRandom rand = new SecureRandom();
		int randomCityIndex; 
		String city;
		while(i < k) {
			randomCityIndex = rand.nextInt(numberOfCities);
			city = agg.getCityName(randomCityIndex);
			if(agg.checkRechargeExists(city)) {
				agg.deleteRecharge(city);
			} else {
				agg.addRecharge(city);
			}
			newScore = agg.getNumberOfRechargeZones();
			if(newScore < oldScore) {
				i = 0;
				oldScore = newScore;
			} else {
				i++;
			}
		}
	}
	
	/**
     * Solves the charging point placement problem in an Agglomeration using an optimized algorithm for small agglomerations.
     *
     * @param agg The Agglomeration object representing the city network.
     */
	public static void solveAutomatically2(Agglomeration agg) {
		
	}
	
	/**
     * Saves the charging point solution of an Agglomeration to a specified file path.
     *
     * @param filePath The file path where the solution will be saved.
     * @param aggcThe Agglomeration object containing the charging point solution.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
	public static void saveRechargeSolution(String filePath, Agglomeration agg) throws IOException {
		Path path = Path.of(filePath);
		Set<String> recharges = agg.getRechargeZones(); 
		Iterator<String> r = recharges.iterator();
		String line = "\nRecharge automatic solution:";
		byte[] textBytes = line.getBytes();
		do {
			if (Files.exists(path)) {
	            Files.write(path, textBytes, StandardOpenOption.APPEND);
	        } else {
	            Files.write(path, textBytes, StandardOpenOption.CREATE);
	        }
			if (r.hasNext()) {
				line = "\nrecharge(" + r.next() +").";
				textBytes = line.getBytes();
			}
			else line = null;
		} while(line != null);
	}
}
