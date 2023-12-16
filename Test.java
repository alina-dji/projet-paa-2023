package up.mi.ald.root;

public class Test {
	public static void main(String[] args) {
		Agglomeration agg;
		try {
			agg = AgglomerationFileParser.parseFile("C:\\Users\\dell\\eclipse-workspace\\projet-paa-2023\\src\\up\\mi\\ald\\root\\aggdata.txt");
			agg.printCities();
			agg.printRoutes();
			agg.printRechargeZones();
		} catch (IllegalDataFormattingException | CityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}