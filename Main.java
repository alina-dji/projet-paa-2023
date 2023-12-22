package up.mi.ald.root;

/**
*
* The Main class is the entry point to the program.
*
* @author Lina Djihane AZIZA, Suntanqing FU
* @version 1.0
*
*/
public class Main {
	public static void main(String[] args) {
		
		System.out.println("----- Projet Bornes de Recharge -----");
		System.out.println("Lina Djihane AZIZA, Suntanqing FU");
		
		if(args.length != 0) CLI.mainMenu(args[0]);
		else CLI.mainMenu("");
    }
}