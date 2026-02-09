package com.example.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BankAccountTest {

    @Test
    void getBalancePositiveStartingBalance() {
        BankAccount account = new BankAccount("a@b.com", 200);
        assertEquals(200.0, account.getBalance(), 1e-9);
    }

    @Test
    void getBalancePositiveAfterWithdraw() {
        BankAccount account = new BankAccount("a@b.com", 200);
        account.withdraw(50);
        assertEquals(150.0, account.getBalance(), 1e-9);
    }

    @Test
    void getBalanceZeroStartingBalance() {
        BankAccount account = new BankAccount("zero@b.com", 0);
        assertEquals(0.0, account.getBalance(), 1e-9);
    }

    @Test
    void getBalanceDrainedToZero() {
        BankAccount account = new BankAccount("drain@b.com", 75);
        account.withdraw(75);
        assertEquals(0.0, account.getBalance(), 1e-9);
    }

    @Test
    void withdrawValidWholeAmount() {
        BankAccount account = new BankAccount("a@b.cc", 100);
        account.withdraw(50);
        assertEquals(50.0, account.getBalance(), 1e-9);
    }

    @Test
    void withdrawValidTwoDecimalAmount() {
        BankAccount account = new BankAccount("a@b.cc", 100);
        account.withdraw(50.55);
        assertEquals(49.45, account.getBalance(), 1e-9);
    }

    @Test
    void withdrawInvalidThreeDecimalAmount() {
        BankAccount account = new BankAccount("a@b.cc", 100);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(50.555));
    }

    @Test
    void withdrawInvalidNegativeAmount() {
        BankAccount account = new BankAccount("a@b.cc", 100);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(-50));
    }

    @Test
    void withdrawInvalidZeroAmount() {
        BankAccount account = new BankAccount("zero@b.cc", 50);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(0));
    }

    @Test
    void withdrawOverdraft() {
        BankAccount account = new BankAccount("a@b.cc", 100);
        assertThrows(InsufficientFundsException.class, () -> account.withdraw(200));
        assertEquals(100.0, account.getBalance(), 1e-9);
    }

    @Test
    void withdrawExactBalance() {
        BankAccount account = new BankAccount("exact@b.cc", 100);
        account.withdraw(100);
        assertEquals(0.0, account.getBalance(), 1e-9);
    }

    @Test
    void withdrawNearOverdraftByOneCent() {
        BankAccount account = new BankAccount("near@b.cc", 100);
        assertThrows(InsufficientFundsException.class, () -> account.withdraw(100.01));
        assertEquals(100.0, account.getBalance(), 1e-9);
    }

    @Test
    void isEmailValidCases() {
        assertTrue(BankAccount.isEmailValid("a@b.com"));
        assertTrue(BankAccount.isEmailValid("abc-efg@mail.com"));
        assertTrue(BankAccount.isEmailValid("abc.def@mail-archive.com"));

        assertFalse(BankAccount.isEmailValid(""));
        assertFalse(BankAccount.isEmailValid("test@em@ail.com"));
        assertFalse(BankAccount.isEmailValid("test.email@com"));
        assertFalse(BankAccount.isEmailValid(".abc@mail.com"));
        assertFalse(BankAccount.isEmailValid("abc..def@mail.com"));
        assertFalse(BankAccount.isEmailValid("abc@-mail.com"));
        assertFalse(BankAccount.isEmailValid("abc.def@mail.c"));
    }

    @Test
    void isAmountValidCases() {
        assertTrue(BankAccount.isAmountValid(100));
        assertTrue(BankAccount.isAmountValid(50.55));
        assertFalse(BankAccount.isAmountValid(50.505));
        assertFalse(BankAccount.isAmountValid(-10.11));
        assertFalse(BankAccount.isAmountValid(0));
        assertTrue(BankAccount.isAmountValid(0.01));
        assertTrue(BankAccount.isAmountValid(Double.MAX_VALUE));
        assertFalse(BankAccount.isAmountValid(Double.MIN_VALUE));
        assertFalse(BankAccount.isAmountValid(Double.POSITIVE_INFINITY));
        assertFalse(BankAccount.isAmountValid(Double.NaN));
    }

    @Test
    void constructorValidWholeBalance() {
        BankAccount account = new BankAccount("a@b.com", 200);
        assertEquals("a@b.com", account.getEmail());
        assertEquals(200.0, account.getBalance(), 1e-9);
    }

    @Test
    void constructorValidTwoDecimalBalance() {
        BankAccount account = new BankAccount("a@b.com", 200.11);
        assertEquals(200.11, account.getBalance(), 1e-9);
    }

    @Test
    void constructorInvalidThreeDecimalBalance() {
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com", 200.111));
    }

    @Test
    void constructorEmptyEmail() {
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("", 100));
    }

    @Test
    void constructorNegativeStartingBalance() {
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com", -100));
    }

    @Test
    void depositValidWholeAmount() {
        BankAccount account = new BankAccount("deposit@b.cc", 0);
        account.deposit(50);
        assertEquals(50.0, account.getBalance(), 1e-9);
    }

    @Test
    void depositValidTwoDecimalAmount() {
        BankAccount account = new BankAccount("deposit@b.cc", 0);
        account.deposit(50.55);
        assertEquals(50.55, account.getBalance(), 1e-9);
    }

    @Test
    void depositInvalidThreeDecimalAmount() {
        BankAccount account = new BankAccount("deposit@b.cc", 0);
        assertThrows(IllegalArgumentException.class, () -> account.deposit(50.555));
    }

    @Test
    void depositInvalidNegativeAmount() {
        BankAccount account = new BankAccount("deposit@b.cc", 100);
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-50));
    }

    @Test
    void depositInvalidZeroAmount() {
        BankAccount account = new BankAccount("deposit@b.cc", 25);
        assertThrows(IllegalArgumentException.class, () -> account.deposit(0));
    }

    @Test
    void transferValidWholeAmount() {
        BankAccount from = new BankAccount("transfer@b.cc", 200);
        BankAccount to = new BankAccount("receiver@b.cc", 50);

        from.transfer(50, to);

        assertEquals(150.0, from.getBalance(), 1e-9);
        assertEquals(100.0, to.getBalance(), 1e-9);
    }

    @Test
    void transferValidTwoDecimalAmount() {
        BankAccount from = new BankAccount("transfer@b.cc", 200);
        BankAccount to = new BankAccount("receiver@b.cc", 0);

        from.transfer(50.55, to);

        assertEquals(149.45, from.getBalance(), 1e-9);
        assertEquals(50.55, to.getBalance(), 1e-9);
    }

    @Test
    void transferInvalidThreeDecimalAmount() {
        BankAccount from = new BankAccount("transfer@b.cc", 200);
        BankAccount to = new BankAccount("receiver@b.cc", 0);

        assertThrows(IllegalArgumentException.class, () -> from.transfer(50.555, to));
        assertEquals(200.0, from.getBalance(), 1e-9);
        assertEquals(0.0, to.getBalance(), 1e-9);
    }

    @Test
    void transferInvalidNegativeAmount() {
        BankAccount from = new BankAccount("transfer@b.cc", 200);
        BankAccount to = new BankAccount("receiver@b.cc", 0);

        assertThrows(IllegalArgumentException.class, () -> from.transfer(-50, to));
        assertEquals(200.0, from.getBalance(), 1e-9);
        assertEquals(0.0, to.getBalance(), 1e-9);
    }

    @Test
    void transferOverdraft() {
        BankAccount from = new BankAccount("transfer@b.cc", 100);
        BankAccount to = new BankAccount("receiver@b.cc", 0);

        assertThrows(InsufficientFundsException.class, () -> from.transfer(200, to));
        assertEquals(100.0, from.getBalance(), 1e-9);
        assertEquals(0.0, to.getBalance(), 1e-9);
    }

    @Test
    void transferNullDestinationAccount() {
        BankAccount from = new BankAccount("transfer@b.cc", 100);

        assertThrows(IllegalArgumentException.class, () -> from.transfer(10, null));
        assertEquals(100.0, from.getBalance(), 1e-9);
    }
}
