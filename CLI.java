package up.mi.ald.root;

import java.io.IOException;

// command line interface: all the messages destined to communicate with the user go here
public class CLI {
	
	public static String getfilePath() {
		System.out.println("Input the path to the file containning your agglomeration data:");
		String path = Scanner.scanFilePath();
		while (path.isEmpty()) {
			System.out.println("Your file path is invalid. try again:");
			path = Scanner.scanFilePath();
		}
		return path;
	}
	
	public static Agglomeration createAgglomeration(String path) {
		Agglomeration agg = null;
		try {
			agg = AgglomerationFileParser.parseFile(path);
		} catch (IOException ioe) {
			System.out.println("An error occured when trying to access your data file. Please check that the location you specified is correct and that there are no other issues preventing the reading operation.");
			endProgram();
		} catch (CityNotFoundException cnfe) {
			String message = cnfe.toString();
			System.out.println(message.substring(message.indexOf(":") + 2));
			endProgram();
		} catch (IllegalDataFormattingException idfe) {
			String message = idfe.toString();
			System.out.println(message.substring(message.indexOf(":") + 2));
			endProgram();
		}
		return agg;
	}
	
	public static void mainMenu() {
		
	}
	
	public static void manualSolutionMenu() {
		
	}
	
	public static void endProgram() {
		System.out.println("Program ended");
	}
}
