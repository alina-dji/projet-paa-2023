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
	
	 /**
     * Constructs a new CityNotFoundException with no detail message.
     */
	public CityNotFoundException() {
		
	}
	
	/**
     * Constructs a new CityNotFoundException with the specified detail message.
     *
     * @param message The detail message that provides more information about the exception.
     */
	public CityNotFoundException(String message) {
		super(message);
	}
	
	/**
     * Prints the custom message of this exception to the standard output stream.
     */
	public void printMessage() {
		String message = this.toString();
		System.out.println(message.substring(message.indexOf(":") + 2));
	}
}
