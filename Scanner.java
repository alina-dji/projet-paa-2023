package up.mi.ald.root;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

public class Scanner {
	
	private static java.util.Scanner scanner = new java.util.Scanner(System.in);
	
	public static String scanFilePath() {
		String path = scanner.nextLine();
		try {
            Paths.get(path);
            return path;
        } catch (InvalidPathException ipe) {
            return "";
        }
	}
	
	public static void scanCityName() {
		
	}
	
	public static void scanMenuChoice() {
		
	}
}
