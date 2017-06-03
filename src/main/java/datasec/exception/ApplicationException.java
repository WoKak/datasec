package datasec.exception;

/**
 * Created by Michał on 2017-06-01.
 */
public class ApplicationException extends RuntimeException{

    private String message;

    public ApplicationException(String message) {

        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}