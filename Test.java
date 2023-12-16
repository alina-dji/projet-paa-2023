package up.mi.ald.root;

public class Test {
	public static void main(String[] args) {
		Agglomeration agg = AgglomerationFileParser.parseFile("C:\\Users\\dell\\eclipse-workspace\\projet-paa-2023\\src\\up\\mi\\ald\\root\\aggdata.txt");
		agg.printCities();
		agg.printRoutes();
		agg.printRechargeZones();
		
    }
}
