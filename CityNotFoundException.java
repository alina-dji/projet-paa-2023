package up.mi.ald.root;

/**
*
* The CityNotFoundException class is a custom runtime exception that indicates a city was not found.
* This exception is thrown when attempting to perform operations related to cities, and the specified city is not present.
*
* @author Lina Djihane AZIZA, Suntanqing FU
* @version 1.0
*
*/
public class CityNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public CityNotFoundException() {
		
	}
	
	public CityNotFoundException(String message) {
		super(message);
	}
	
	public void printMessage() {
		String message = this.toString();
		System.out.println(message.substring(message.indexOf(":") + 2));
	}
}
