package com.example.bank;

/**
 * Exception thrown when an account operation requires more funds than are available.
 */
public class InsufficientFundsException extends RuntimeException {

    /**
     * Creates a new insufficient funds exception with the provided message.
     *
     * @param message detail message describing the failure
     */
    public InsufficientFundsException(String message) {
        super(message);
    }
}
