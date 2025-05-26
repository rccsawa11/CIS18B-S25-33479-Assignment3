This program is the CIS-18B Lesson 3 Assignment. 

This program builds off the functionality from Lesson 1. 

It simulates a secure banking system with added exception handling, transaction logging using the Observer pattern, and a decorator to restrict large withdrawals. 

The entire program is written in a single Java file, BankAccountTest.java. It runs entirely in the console using user input through Scanner. 


FUNCTIONALITY – The user will be able to: 
-  Create a bank account with an initial deposit
-  Deposit funds (only positive amounts allowed)
-  Withdraw funds (cannot withdraw more than available balance, and cannot exceed $500 in one transaction)
-  Automatically log transactions to both the console and a file (transaction_log.txt) - Handle errors such as: depositing a negative number or withdrawing more than balance

Any errors that occur (such as trying to deposit a negative number or withdraw from a closed account) will be caught and printed to the screen using custom exceptions. 

EXTRA FEATURES: 
- A TransactionLogger class writes each transaction to transaction_log.txt

Sample Console Output: 

Please enter your initial balance: 100

Bank Account Created: #123456

Please enter the amount you want to deposit:
100

Transaction logged: Deposited: $100.0

Please enter the amount you want to withdraw:
100

Transaction logged: Withdrew: $100.0

Your current account balance is: $100.0

PS C:\Users\leenz\Documents\GitHub\CIS18B-S25-33479-Assignment3>

