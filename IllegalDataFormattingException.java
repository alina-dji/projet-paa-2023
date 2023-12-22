package up.mi.ald.root;

/**
 * Exception thrown to indicate that the formatting of data in an Agglomeration file is illegal or incorrect.
 * This exception is typically raised when parsing the file data to create an Agglomeration object.
 *
 * @author Lina Djihane AZIZA, Suntanqing FU
 * @version 1.0
 */
public class IllegalDataFormattingException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	/**
     * Constructs a new IllegalDataFormattingException with no detail message.
     */
	public IllegalDataFormattingException() {
		
	}
	
	 /**
     * Constructs a new IllegalDataFormattingException with the specified detail message.
     *
     * @param message The detail message indicating the reason for the exception.
     */
	public IllegalDataFormattingException(String message) {
		super(message);
	}
	
	/**
     * Prints the custom message of this this exception to the standard output stream.
     */
	public void printMessage() {
		String message = this.toString();
		System.out.println(message.substring(message.indexOf(":") + 2));
	}
}
