package com.example.bank;

/**
 * Backward-compatible misspelled alias of {@link InsufficientFundsException}.
 */
public class InsufficentFundsException extends InsufficientFundsException {
    /**
     * Creates a new insufficient funds exception with a message.
     *
     * @param message details about the failure
     */
    public InsufficentFundsException(String message) {
        super(message);
    }
}
