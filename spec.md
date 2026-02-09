# Bank Account Design Specification
## Overview
BankAccounts are objects that represent an actual bank account. They have an email associated with them and a balance. Certain actions like withdraws, deposits and transfers can be performed on them.

### Design Principles
- Programming is done in **Java 17**
- **Maven** is used for building and package management 
- **BankAccounts** are an object and use Object Oriented Programming 
- **Accuracy**: Operations should give accurate responses, no floating point rounding errors. 
- **Deterministic**: given the same inputs and outputs the methods should have the same outcomes. 
- **Well Documented**: Code should be throughly documented, but documentation should be meaningful and not just be comments for the sake of comments. Also each method should have a valid java doc explaining the method. Also avoid using emojis. 
- **Handle All Boundry Cases**: Any boundry cases not explicitly stated should still be handled for the safety and security of the program. 

### Output Structure
Generate the minimal files needed to use and test the library.
#### Do generate:
- Files for each class
- Test file(s)
- A **README.md** laying out the usage of the bank accounts, install instructions on Linux (Including Arch, Ubuntu, and Debian), MacOS, and Windows and information about the project. The install instructions should be located after the docs on the usage. 
  
#### Do not generate: 
- Any main method (should be just Tests and the Objects)
- Any CI/CD configuration, GitHub Actions, etc.

## Bank Account Class:
### Constructor
#### BankAccount(String email, double startingBalance)
- Takes a email address and a starting balance and creates a bank account object.
- Throws an IllegalArgumentException if the email is invalid according to the isEmailValid static method. 
- Throws an IllegalArgumentException if the starting balance is invalid according to the isAmountValid static method. However bank accounts with 0 starting balance should be a special case and be allowed. 

### Methods
#### getBalance() -> double:
- Returns the balance of the account

#### getEmail() -> String:
- Returns the email of the account

#### deposit(double amount) -> void:
- Adds the amount deposited into the balance of the account.
- Throws an IllegalArguementException if the amount is invalid according to the isAmountValid static method. 

#### withdraw(double amount) -> void: 
- Takes the given amount out of the bank account. 
- Throws an InsufficentFundsException if there is not enough money in the account to withdraw. The money in the account should not be altered in this case. 
- Throws an IllegalArgumentException if the amount is invalid according to the isAmountValid static method. 

#### transfer(double amount, BankAccount otherAccount) -> void:
- Withdraws the given amount from the current account and deposits it into the otherAccount
- Throws an InsufficientFundsException if there is not enough money in the current account to give to the other account. The money in either account should not be effected in this case.
- Throws an IllegalArgumentException if the otherAccount is null, the otherAccount must be valid.
- Throws an IllegalArgumentException if the amount is invalid according to the isAmountValid static method.  

#### isEmailValid(String email) -> boolean:
- Static method
- Returns true if the email passes the RFC 5322 email specification, false if otherwise.

#### isAmountValid(double amount) -> boolean:
- Static method
- Returns true if:
  - The amount is positive
  - The amount is a finite amount. 
  - The amount has less than or equal to 2 decimal places.
  - No decimal places are also allowed. 
- Otherwise return false. 

## InsufficientFundsException
- An exception to be thrown when an account does not have the proper funds to perform an action. 



