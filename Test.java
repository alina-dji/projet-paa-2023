package up.mi.ald.root;

public class Test {
	public static void main(String[] args) {
		
		System.out.println("----- Projet Bornes de Recharge -----");
		System.out.println("Lina Djihane AZIZA, Suntanqing FU");
		
		if(args.length != 0) CLI.mainMenu(args[0]);
		else CLI.mainMenu("");

    }
}