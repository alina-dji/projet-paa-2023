package up.mi.ald.root;

/**
* The Main class serves as the entry point for the "Projet Bornes de Recharge" project.
* It contains the main method that initializes and prints project information and delegates control to the Command Line Interface (CLI) based on user input.
*
* @author Lina Djihane AZIZA, Suntanqing FU
* @version 1.0
*/
public class Main {
	/**
     * The main method of the "Projet Bornes de Recharge" project.
     * It prints project information, including the project title and authors, and delegates control to the Command Line Interface (CLI) based on user input.
     *
     * @param args Command-line arguments. The first argument (if provided) is treated as the file path for input data.
     */
	public static void main(String[] args) {
		
		System.out.println("----- Projet Bornes de Recharge -----");
		System.out.println("Lina Djihane AZIZA, Suntanqing FU");
		
		if(args.length != 0) CLI.mainMenu(args[0]);
		else CLI.mainMenu("");
    }
}