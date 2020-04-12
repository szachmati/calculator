package pl.storware.exception;

public class EmptyFileException extends Exception {
    public EmptyFileException() {
        super();
    }
    public EmptyFileException(String cause) {
        super(cause);
    }
}
