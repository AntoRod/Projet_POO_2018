package exceptions;

public class NoArgumentException extends Exception{
	
	private static final long serialVersionUID = 7341334188813219237L;
	public NoArgumentException() {
		this("");
	}
	public NoArgumentException(String text) {
		super(text);
	}
}
