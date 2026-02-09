package com.example.bank;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Represents a bank account with an email owner identifier and a monetary balance.
 */
public class BankAccount {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^(?=.{1,254}$)(?=.{1,64}@)" +
            "[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*" +
            "@" +
            "(?:[A-Za-z0-9](?:[A-Za-z0-9-]{0,61}[A-Za-z0-9])?\\.)+[A-Za-z]{2,63}$"
    );

    private final String email;
    private BigDecimal balance;

    /**
     * Creates a bank account with the provided email and starting balance.
     *
     * @param email account email identifier
     * @param startingBalance initial account balance
     * @throws IllegalArgumentException when the email is invalid, or when the starting balance
     *                                  is invalid and not equal to zero
     */
    public BankAccount(String email, double startingBalance) {
        if (!isEmailValid(email)) {
            throw new IllegalArgumentException("Invalid email address.");
        }
        if (!isAmountValid(startingBalance) && Double.compare(startingBalance, 0.0d) != 0) {
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
     * Returns the email associated with the account.
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
     * @throws IllegalArgumentException when amount is invalid
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
     * @throws IllegalArgumentException when amount is invalid
     * @throws InsufficientFundsException when the account does not have enough funds
     */
    public void withdraw(double amount) {
        if (!isAmountValid(amount)) {
            throw new IllegalArgumentException("Invalid withdrawal amount.");
        }

        BigDecimal withdrawal = BigDecimal.valueOf(amount);
        if (balance.compareTo(withdrawal) < 0) {
            throw new InsufficientFundsException("Insufficient funds for withdrawal.");
        }

        balance = balance.subtract(withdrawal);
    }

    /**
     * Transfers money from this account into another account.
     *
     * @param amount amount to transfer
     * @param otherAccount destination account
     * @throws IllegalArgumentException when amount is invalid or otherAccount is null
     * @throws InsufficientFundsException when this account does not have enough funds
     */
    public void transfer(double amount, BankAccount otherAccount) {
        if (!isAmountValid(amount)) {
            throw new IllegalArgumentException("Invalid transfer amount.");
        }
        if (Objects.isNull(otherAccount)) {
            throw new IllegalArgumentException("Destination account cannot be null.");
        }

        BigDecimal transferAmount = BigDecimal.valueOf(amount);
        if (balance.compareTo(transferAmount) < 0) {
            throw new InsufficientFundsException("Insufficient funds for transfer.");
        }

        balance = balance.subtract(transferAmount);
        otherAccount.balance = otherAccount.balance.add(transferAmount);
    }

    /**
     * Returns whether the provided email address is valid.
     *
     * @param email email value to validate
     * @return true when email is valid, otherwise false
     */
    public static boolean isEmailValid(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Returns whether the provided monetary amount is valid.
     *
     * @param amount amount to validate
     * @return true when amount is positive, finite, and has at most 2 decimal places
     */
    public static boolean isAmountValid(double amount) {
        if (!Double.isFinite(amount) || amount <= 0.0d) {
            return false;
        }
        BigDecimal asDecimal = BigDecimal.valueOf(amount).stripTrailingZeros();
        return asDecimal.scale() <= 2;
    }
}
