package up.mi.ald.root;

import java.security.SecureRandom;

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
	
	public static void saveRechargeSolution(String path, Agglomeration agg) {
		
	}
}
