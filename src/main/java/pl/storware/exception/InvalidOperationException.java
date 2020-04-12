package pl.storware.exception;

public class InvalidOperationException extends Exception {

    public InvalidOperationException() {
        super();
    }
    public InvalidOperationException(String cause) {
        super(cause);
    }
}
