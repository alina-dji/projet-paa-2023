package up.mi.ald.root;

public class Test {
	public static void main(String[] args) {
		String path = CLI.getfilePath();
		CLI.createAgglomeration(path);
		/*Agglomeration agg;
		try {
			agg = AgglomerationFileParser.parseFile("C:\\Users\\dell\\eclipse-workspace\\projet-paa-2023\\src\\up\\mi\\ald\\root\\aggdata.ca");
			agg.printCities();
			agg.printRoutes();
			agg.printRechargeZones();
			agg.printCitiesIndex();
		} catch (IllegalDataFormattingException idfe) {
			idfe.printStackTrace();
		} catch (CityNotFoundException cnfe) {
			cnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}*/
		//TODO add more .ca files for testing different scenarios
		
    }
}