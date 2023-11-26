package common.exception;;

/**
 * The MediaNotAvailableException wraps all unchecked exceptions You can use this
 * exception to inform
 * 
 */
public class MediaNotAvailableException extends RuntimeException {

	private static final long serialVersionUID = 0xf253557d8db88faL;

	public MediaNotAvailableException() {

	}

	public MediaNotAvailableException(String message) {
		super(message);
	}

}