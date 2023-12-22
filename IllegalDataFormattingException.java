package up.mi.ald.root;

public class IllegalDataFormattingException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public IllegalDataFormattingException() {
		
	}
	
	public IllegalDataFormattingException(String message) {
		super(message);
	}
	
	public void printMessage() {
		String message = this.toString();
		System.out.println(message.substring(message.indexOf(":") + 2));
	}
}
