package up.mi.ald.root;

public class Test {
	public static void main(String[] args) {
        String line = "route(Noisy-le-Sec, Paris).";
        String route = line.substring(line.indexOf('('), line.indexOf(')') + 1);
        String city1 = line.substring(line.indexOf('(') + 1, line.indexOf(','));
        String city2 = line.substring(line.indexOf(',') + 2, line.indexOf(')'));
        System.out.println(route);
        System.out.println(city1);
        System.out.println(city2);
    }
}
