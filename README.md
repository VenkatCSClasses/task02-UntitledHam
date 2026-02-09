# Bank Account Library

Java 17 library that models bank accounts with deterministic money operations and strong validation.

## Features

- `BankAccount` with email identity
- Deposit, withdraw, and transfer operations
- Positive, finite, max-two-decimal amount validation
- RFC 5322-style email validation for common valid formats
- `InsufficientFundsException` for overdraft attempts
- JUnit test coverage for all scenarios defined in `tests.yaml`

## Usage

### Add to your project

Build and install locally:

```bash
mvn clean install
```

### API

Package: `com.example.bank`

```java
import com.example.bank.BankAccount;
import com.example.bank.InsufficientFundsException;

BankAccount checking = new BankAccount("alice@example.com", 250.00);
BankAccount savings = new BankAccount("alice.savings@example.com", 0.00);

checking.deposit(25.50);            // balance: 275.50
checking.withdraw(50.00);           // balance: 225.50
checking.transfer(25.50, savings);  // checking: 200.00, savings: 25.50

double balance = checking.getBalance();
String email = checking.getEmail();

boolean validEmail = BankAccount.isEmailValid("user@mail.com");
boolean validAmount = BankAccount.isAmountValid(19.99);
```

### Error behavior

- `IllegalArgumentException` for invalid email, invalid amount, invalid non-zero starting balance, or `null` transfer target
- `InsufficientFundsException` when withdraw/transfer amount exceeds current balance

## Install Instructions

Install Java 17 and Maven, then run:

```bash
mvn test
```

### Linux

#### Arch Linux

```bash
sudo pacman -Syu
sudo pacman -S jdk17-openjdk maven
java -version
mvn -version
```

#### Ubuntu

```bash
sudo apt update
sudo apt install -y openjdk-17-jdk maven
java -version
mvn -version
```

#### Debian

```bash
sudo apt update
sudo apt install -y openjdk-17-jdk maven
java -version
mvn -version
```

### macOS

With Homebrew:

```bash
brew update
brew install openjdk@17 maven
java -version
mvn -version
```

If Java is not linked automatically:

```bash
sudo ln -sfn /opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-17.jdk
```

### Windows

Install:

- OpenJDK 17 (for example Temurin 17)
- Apache Maven

Then verify in PowerShell:

```powershell
java -version
mvn -version
```

If needed, set `JAVA_HOME` to your JDK 17 installation path and add Maven `bin` to `PATH`.

## Development

- Run tests:

```bash
mvn test
```

- Build artifact:

```bash
mvn clean package
```
