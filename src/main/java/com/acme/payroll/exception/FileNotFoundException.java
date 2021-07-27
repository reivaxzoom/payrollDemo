package com.acme.payroll.exception;

/**
 *
 * @author xavier
 */
public class FileNotFoundException extends Exception{

    public FileNotFoundException() {
    }

    public FileNotFoundException(String message) {
        super(message);
    }

    public FileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    
    
    
}