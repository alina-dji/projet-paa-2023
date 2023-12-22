package up.mi.ald.root;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.SecureRandom;
import java.util.Iterator;
import java.util.Set;

// Recharge is a utility class
public class Recharge {
	
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
				/*try {
					agg.deleteRecharge(city);
				} catch(AccessibilityNotRespectedException anre) {
					continue;
				}*/
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
	
	public static void solveAutomatically2(Agglomeration agg) {
		
	}
	
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
