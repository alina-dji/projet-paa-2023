package up.mi.ald.root;

import java.security.SecureRandom;

// Recharge is a utility class
public class Recharge {
	
	public static void solveAutomatically(Agglomeration agg) {
		
		int numberOfCities = agg.getNumberOfCities();
		int k = numberOfCities + 100;
		int score = agg.getRechargeZones().size();
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
		}
	}
	
	public static void saveRechargeSolution(String path, Agglomeration agg) {
		
	}
}
