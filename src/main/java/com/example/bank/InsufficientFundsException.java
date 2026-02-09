package com.example.bank;

/**
 * Exception thrown when an account does not have enough funds to perform an operation.
 */
public class InsufficientFundsException extends RuntimeException {
    /**
     * Creates a new insufficient funds exception with a message.
     *
     * @param message details about the failure
     */
    public InsufficientFundsException(String message) {
        super(message);
    }
}
