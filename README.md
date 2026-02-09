# Bank Account Library

A deterministic Java 17 bank account library with validation rules for email and monetary amounts.

## Features

- `BankAccount(String email, double startingBalance)` constructor with input validation
- `getBalance()` and `getEmail()` accessors
- `deposit(double amount)`
- `withdraw(double amount)`
- `transfer(double amount, BankAccount otherAccount)`
- `isEmailValid(String email)` static validator
- `isAmountValid(double amount)` static validator
- `InsufficientFundsException` for insufficient-balance operations

## Usage

### Add as a dependency (local build)

```bash
mvn clean install
```

Then depend on `com.example:bank-account:1.0.0` from another Maven project.

### Example

```java
import com.example.bank.BankAccount;

BankAccount checking = new BankAccount("alice@example.com", 250.00);
BankAccount savings = new BankAccount("alice.savings@example.com", 1000.00);

checking.deposit(50.25);
checking.withdraw(25.00);
checking.transfer(100.00, savings);

double checkingBalance = checking.getBalance();
```

## Install Instructions

Install instructions are listed after usage documentation as requested.

### Linux (Arch)

```bash
sudo pacman -Syu
sudo pacman -S jdk17-openjdk maven
java -version
mvn -version
```

### Linux (Ubuntu)

```bash
sudo apt update
sudo apt install -y openjdk-17-jdk maven
java -version
mvn -version
```

### Linux (Debian)

```bash
sudo apt update
sudo apt install -y openjdk-17-jdk maven
java -version
mvn -version
```

### macOS (Homebrew)

```bash
brew update
brew install openjdk@17 maven
java -version
mvn -version
```

### Windows

1. Install Java 17 JDK (for example, Eclipse Temurin 17).
2. Install Apache Maven.
3. Ensure `JAVA_HOME` points to the JDK 17 directory.
4. Ensure `%JAVA_HOME%\\bin` and Maven `bin` are on `Path`.
5. Verify in PowerShell:

```powershell
java -version
mvn -version
```

## Testing

```bash
mvn test
```
