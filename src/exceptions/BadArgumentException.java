package exceptions;

public class BadArgumentException extends Exception{
	
	private static final long serialVersionUID = -6470814263399629497L;
	public BadArgumentException() {
		this("");
	}
	public BadArgumentException(String text) {
		super(text);
	}

}
