package up.mi.ald.root;

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
