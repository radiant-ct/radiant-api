package dev.pepecoral.radiant.modules.datasets.exceptions;

public class DatasetSetInImageCreationException extends RuntimeException {

    public DatasetSetInImageCreationException() {
        super();
    }

    public DatasetSetInImageCreationException(String message) {
        super(message);
    }

    public DatasetSetInImageCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}