package com.example.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BankAccountTest {
    private static final double EPSILON = 1e-9;

    @Test
    void getBalance_positiveStartingBalance() {
        BankAccount account = new BankAccount("a@b.com", 200);
        assertEquals(200, account.getBalance(), EPSILON);
    }

    @Test
    void getBalance_positiveBalanceAfterWithdraw() {
        BankAccount account = new BankAccount("a@b.com", 200);
        account.withdraw(50);
        assertEquals(150, account.getBalance(), EPSILON);
    }

    @Test
    void getBalance_zeroStartingBalance() {
        BankAccount account = new BankAccount("zero@b.com", 0);
        assertEquals(0, account.getBalance(), EPSILON);
    }

    @Test
    void getBalance_drainedToZero() {
        BankAccount account = new BankAccount("drain@b.com", 75);
        account.withdraw(75);
        assertEquals(0, account.getBalance(), EPSILON);
    }

    @Test
    void withdraw_validWholeAmount() {
        BankAccount account = new BankAccount("a@b.cc", 100);
        account.withdraw(50);
        assertEquals(50, account.getBalance(), EPSILON);
    }

    @Test
    void withdraw_validTwoDecimalAmount() {
        BankAccount account = new BankAccount("a@b.cc", 100);
        account.withdraw(50.55);
        assertEquals(49.45, account.getBalance(), EPSILON);
    }

    @Test
    void withdraw_invalidThreeDecimalAmount() {
        BankAccount account = new BankAccount("a@b.cc", 100);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(50.555));
    }

    @Test
    void withdraw_invalidNegativeAmount() {
        BankAccount account = new BankAccount("a@b.cc", 100);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(-50));
    }

    @Test
    void withdraw_invalidZeroAmount() {
        BankAccount account = new BankAccount("zero@b.cc", 50);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(0));
    }

    @Test
    void withdraw_overdraft() {
        BankAccount account = new BankAccount("a@b.cc", 100);
        assertThrows(InsufficientFundsException.class, () -> account.withdraw(200));
        assertEquals(100, account.getBalance(), EPSILON);
    }

    @Test
    void withdraw_exactBalanceWithdraw() {
        BankAccount account = new BankAccount("exact@b.cc", 100);
        account.withdraw(100);
        assertEquals(0, account.getBalance(), EPSILON);
    }

    @Test
    void withdraw_nearOverdraftByOneCent() {
        BankAccount account = new BankAccount("near@b.cc", 100);
        assertThrows(InsufficientFundsException.class, () -> account.withdraw(100.01));
        assertEquals(100, account.getBalance(), EPSILON);
    }

    @Test
    void isEmailValid_basicValidEmail() {
        assertTrue(BankAccount.isEmailValid("a@b.com"));
    }

    @Test
    void isEmailValid_validLocalSeparator() {
        assertTrue(BankAccount.isEmailValid("abc-efg@mail.com"));
    }

    @Test
    void isEmailValid_validDashedDomain() {
        assertTrue(BankAccount.isEmailValid("abc.def@mail-archive.com"));
    }

    @Test
    void isEmailValid_emptyString() {
        assertFalse(BankAccount.isEmailValid(""));
    }

    @Test
    void isEmailValid_multipleAtSigns() {
        assertFalse(BankAccount.isEmailValid("test@em@ail.com"));
    }

    @Test
    void isEmailValid_missingDomainDot() {
        assertFalse(BankAccount.isEmailValid("test.email@com"));
    }

    @Test
    void isEmailValid_localStartsWithDot() {
        assertFalse(BankAccount.isEmailValid(".abc@mail.com"));
    }

    @Test
    void isEmailValid_localConsecutiveDots() {
        assertFalse(BankAccount.isEmailValid("abc..def@mail.com"));
    }

    @Test
    void isEmailValid_domainStartsWithDash() {
        assertFalse(BankAccount.isEmailValid("abc@-mail.com"));
    }

    @Test
    void isEmailValid_oneLetterTld() {
        assertFalse(BankAccount.isEmailValid("abc.def@mail.c"));
    }

    @Test
    void isAmountValid_positiveWholeNumber() {
        assertTrue(BankAccount.isAmountValid(100));
    }

    @Test
    void isAmountValid_positiveTwoDecimals() {
        assertTrue(BankAccount.isAmountValid(50.55));
    }

    @Test
    void isAmountValid_positiveThreeDecimals() {
        assertFalse(BankAccount.isAmountValid(50.505));
    }

    @Test
    void isAmountValid_negativeValue() {
        assertFalse(BankAccount.isAmountValid(-10.11));
    }

    @Test
    void isAmountValid_zero() {
        assertFalse(BankAccount.isAmountValid(0));
    }

    @Test
    void isAmountValid_smallestCent() {
        assertTrue(BankAccount.isAmountValid(0.01));
    }

    @Test
    void isAmountValid_doubleMax() {
        assertTrue(BankAccount.isAmountValid(Double.MAX_VALUE));
    }

    @Test
    void isAmountValid_doubleMin() {
        assertFalse(BankAccount.isAmountValid(Double.MIN_VALUE));
    }

    @Test
    void isAmountValid_positiveInfinity() {
        assertFalse(BankAccount.isAmountValid(Double.POSITIVE_INFINITY));
    }

    @Test
    void isAmountValid_nan() {
        assertFalse(BankAccount.isAmountValid(Double.NaN));
    }

    @Test
    void constructor_validWholeBalance() {
        BankAccount account = new BankAccount("a@b.com", 200);
        assertEquals("a@b.com", account.getEmail());
        assertEquals(200, account.getBalance(), EPSILON);
    }

    @Test
    void constructor_validTwoDecimalBalance() {
        BankAccount account = new BankAccount("a@b.com", 200.11);
        assertEquals(200.11, account.getBalance(), EPSILON);
    }

    @Test
    void constructor_invalidThreeDecimalBalance() {
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com", 200.111));
    }

    @Test
    void constructor_emptyEmail() {
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("", 100));
    }

    @Test
    void constructor_negativeStartingBalance() {
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com", -100));
    }

    @Test
    void deposit_validWholeAmount() {
        BankAccount account = new BankAccount("deposit@b.cc", 0);
        account.deposit(50);
        assertEquals(50, account.getBalance(), EPSILON);
    }

    @Test
    void deposit_validTwoDecimalAmount() {
        BankAccount account = new BankAccount("deposit@b.cc", 0);
        account.deposit(50.55);
        assertEquals(50.55, account.getBalance(), EPSILON);
    }

    @Test
    void deposit_invalidThreeDecimalAmount() {
        BankAccount account = new BankAccount("deposit@b.cc", 0);
        assertThrows(IllegalArgumentException.class, () -> account.deposit(50.555));
    }

    @Test
    void deposit_invalidNegativeAmount() {
        BankAccount account = new BankAccount("deposit@b.cc", 100);
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-50));
    }

    @Test
    void deposit_invalidZeroAmount() {
        BankAccount account = new BankAccount("deposit@b.cc", 25);
        assertThrows(IllegalArgumentException.class, () -> account.deposit(0));
    }

    @Test
    void transfer_validWholeTransfer() {
        BankAccount from = new BankAccount("transfer@b.cc", 200);
        BankAccount to = new BankAccount("receiver@b.cc", 50);
        from.transfer(50, to);
        assertEquals(150, from.getBalance(), EPSILON);
        assertEquals(100, to.getBalance(), EPSILON);
    }

    @Test
    void transfer_validTwoDecimalTransfer() {
        BankAccount from = new BankAccount("transfer@b.cc", 200);
        BankAccount to = new BankAccount("receiver@b.cc", 0);
        from.transfer(50.55, to);
        assertEquals(149.45, from.getBalance(), EPSILON);
        assertEquals(50.55, to.getBalance(), EPSILON);
    }

    @Test
    void transfer_invalidThreeDecimalTransfer() {
        BankAccount from = new BankAccount("transfer@b.cc", 200);
        BankAccount to = new BankAccount("receiver@b.cc", 0);
        assertThrows(IllegalArgumentException.class, () -> from.transfer(50.555, to));
        assertEquals(200, from.getBalance(), EPSILON);
        assertEquals(0, to.getBalance(), EPSILON);
    }

    @Test
    void transfer_invalidNegativeTransfer() {
        BankAccount from = new BankAccount("transfer@b.cc", 200);
        BankAccount to = new BankAccount("receiver@b.cc", 0);
        assertThrows(IllegalArgumentException.class, () -> from.transfer(-50, to));
        assertEquals(200, from.getBalance(), EPSILON);
        assertEquals(0, to.getBalance(), EPSILON);
    }

    @Test
    void transfer_overdraftTransfer() {
        BankAccount from = new BankAccount("transfer@b.cc", 100);
        BankAccount to = new BankAccount("receiver@b.cc", 0);
        assertThrows(InsufficientFundsException.class, () -> from.transfer(200, to));
        assertEquals(100, from.getBalance(), EPSILON);
        assertEquals(0, to.getBalance(), EPSILON);
    }

    @Test
    void transfer_nullOtherAccount() {
        BankAccount from = new BankAccount("transfer@b.cc", 100);
        assertThrows(IllegalArgumentException.class, () -> from.transfer(10, null));
        assertEquals(100, from.getBalance(), EPSILON);
    }
}
