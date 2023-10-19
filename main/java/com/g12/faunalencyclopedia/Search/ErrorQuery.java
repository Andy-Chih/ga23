package com.g12.faunalencyclopedia.Search;

/**
 * @author UID:u7574003 Name: Andy Chih
 */
 
public class ErrorQuery {
    private final String errorMessage;

    public ErrorQuery(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
