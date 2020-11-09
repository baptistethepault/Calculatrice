package error;

public class MyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -245881077703937202L;
	private final String message;
	public static String PREFIX_MESSAGE = "Erreur : ";

	public MyException(String message) {
		this.message = message;
	}

	public String getMyMessage() {
		return PREFIX_MESSAGE + message;
	}

}
