package se.omegapoint.web.signencrypthashapp.exception;

public class GenericControlException extends RuntimeException{

    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public GenericControlException(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
