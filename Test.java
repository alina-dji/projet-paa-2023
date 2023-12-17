package up.mi.ald.root;

import java.io.IOException;

public class Test {
	public static void main(String[] args) {
		Agglomeration agg;
		try {
			agg = AgglomerationFileParser.parseFile("C:\\Users\\dell\\eclipse-workspace\\projet-paa-2023\\src\\up\\mi\\ald\\root\\aggdata.txt");
			agg.printCities();
			agg.printRoutes();
			agg.printRechargeZones();
		} catch (IllegalDataFormattingException idfe) {
			idfe.printStackTrace();
		} catch (CityNotFoundException cnfe) {
			cnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
    }
}