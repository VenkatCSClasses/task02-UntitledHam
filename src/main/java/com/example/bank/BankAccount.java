package com.example.bank;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * Represents a bank account with an owner email and monetary balance.
 * <p>
 * Monetary operations avoid floating-point math by internally using {@link BigDecimal}.
 */
public class BankAccount {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^(?=.{1,254}$)(?=.{1,64}@)"
                    + "[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*"
                    + "@"
                    + "(?:[A-Za-z0-9](?:[A-Za-z0-9-]{0,61}[A-Za-z0-9])?\\.)+[A-Za-z]{2,63}$"
    );

    private final String email;
    private BigDecimal balance;

    /**
     * Creates a new bank account with a validated email and starting balance.
     *
     * @param email           email associated with the account
     * @param startingBalance starting balance; zero is allowed as a special case
     * @throws IllegalArgumentException if the email or starting balance is invalid
     */
    public BankAccount(String email, double startingBalance) {
        if (!isEmailValid(email)) {
            throw new IllegalArgumentException("Invalid email address.");
        }

        if (startingBalance != 0.0d && !isAmountValid(startingBalance)) {
            throw new IllegalArgumentException("Invalid starting balance.");
        }

        this.email = email;
        this.balance = BigDecimal.valueOf(startingBalance);
    }

    /**
     * Returns the current account balance.
     *
     * @return current balance as a double
     */
    public double getBalance() {
        return balance.doubleValue();
    }

    /**
     * Returns the account email.
     *
     * @return account email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Deposits money into this account.
     *
     * @param amount amount to deposit
     * @throws IllegalArgumentException if the amount is invalid
     */
    public void deposit(double amount) {
        if (!isAmountValid(amount)) {
            throw new IllegalArgumentException("Invalid deposit amount.");
        }
        balance = balance.add(BigDecimal.valueOf(amount));
    }

    /**
     * Withdraws money from this account.
     *
     * @param amount amount to withdraw
     * @throws IllegalArgumentException    if the amount is invalid
     * @throws InsufficientFundsException if this account does not have enough funds
     */
    public void withdraw(double amount) {
        if (!isAmountValid(amount)) {
            throw new IllegalArgumentException("Invalid withdrawal amount.");
        }

        BigDecimal decimalAmount = BigDecimal.valueOf(amount);
        if (balance.compareTo(decimalAmount) < 0) {
            throw new InsufficientFundsException("Insufficient funds.");
        }

        balance = balance.subtract(decimalAmount);
    }

    /**
     * Transfers money from this account to another account.
     *
     * @param amount       amount to transfer
     * @param otherAccount recipient account
     * @throws IllegalArgumentException    if amount is invalid or recipient account is null
     * @throws InsufficientFundsException if this account does not have enough funds
     */
    public void transfer(double amount, BankAccount otherAccount) {
        if (otherAccount == null) {
            throw new IllegalArgumentException("Other account cannot be null.");
        }
        if (!isAmountValid(amount)) {
            throw new IllegalArgumentException("Invalid transfer amount.");
        }

        withdraw(amount);
        otherAccount.deposit(amount);
    }

    /**
     * Validates an email using an RFC 5322 compatible pattern for common valid addresses.
     *
     * @param email email to validate
     * @return true when valid, otherwise false
     */
    public static boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Returns true for positive, finite monetary values with up to two decimal places.
     *
     * @param amount amount to validate
     * @return true if amount is positive, finite, and has at most two decimals
     */
    public static boolean isAmountValid(double amount) {
        if (!Double.isFinite(amount) || amount <= 0.0d) {
            return false;
        }

        BigDecimal decimalAmount = BigDecimal.valueOf(amount).stripTrailingZeros();
        int scale = Math.max(decimalAmount.scale(), 0);
        return scale <= 2;
    }
}
