package up.mi.ald.root;

import java.io.IOException;

// command line interface: all the messages destined to communicate with the user go here
public class CLI {
	
	public static void getfilePath() {
		System.out.println("Input the path to the file containning your agglomeration data:");
		String path = Scanner.scanFilePath();
		while (path.isEmpty()) {
			System.out.println("Your file path is invalid. try again:");
			path = Scanner.scanFilePath();
		}
	}
	
	public static void createAgglomeration(String path) {
		try {
			Agglomeration agg = AgglomerationFileParser.parseFile(path);
		} catch (IllegalDataFormattingException | CityNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void mainMenu() {
		
	}
	
	public static void manualSolutionMenu() {
		
	}
}
