import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// ============================
// DONE/TODO: Define Custom Exception Classes
// ============================
class NegativeDepositException extends Exception
{
    public NegativeDepositException(String errorResponse)
    {
        super(errorResponse);
    }
}

class OverdrawException extends Exception
{
    public OverdrawException(String errorResponse)
    {
        super(errorResponse); 
    }
}

class InvalidAccountOperationException extends Exception
{
    public InvalidAccountOperationException(String errorResponse)
    {
        super(errorResponse); 
    }
} 

// ============================
// DONE/Observer Pattern - Define Observer Interface
// ============================

interface Observer {
    void update(String message);
}

// DONE/TODO: Implement TransactionLogger class (Concrete Observer)

class TransactionLogger implements Observer
{
    public void update(String message) 
    {
        System.out.println("Transaction logged: " + message);
        try 
        {
            FileWriter writer = new FileWriter("transaction_log.txt", true); 
            writer.write(message + "\n");
            writer.close();
        } catch (IOException e) 
        {
            System.out.println("There has been an error reportin to file: " + e.getMessage()); 
        }
    }
}


// ============================
// BankAccount (Subject in Observer Pattern)
// ============================
class BankAccount 
{
    protected String accountNumber;
    protected double balance;
    protected boolean isActive;
    private List<Observer> observers = new ArrayList<>();

    public BankAccount(String accNum, double initialBalance) 
    {
        this.accountNumber = accNum;
        this.balance = initialBalance;
        this.isActive = true;
    }

    // DONE/Attach observer
    public void addObserver(Observer observer) 
    {
        observers.add(observer);
    }

    // DONE/Notify observers (TODO: Implement in methods)
    private void notifyObservers(String message) {
        // DONE/TODO: Notify all observers when a transaction occurs
        for (Observer obs : observers)
        {
            obs.update(message);
        }
    }

    public void deposit(double amount) throws Exception 
    {
        if(amount < 0)
        {
            throw new NegativeDepositException("Please do not enter negative numbers. Only enter positives.");
        }

        if (isActive == false)
        {
            throw new InvalidAccountOperationException("This account is not active.");
        }

        else 
        {
            balance = balance + amount;
            notifyObservers("Deposited: $" + amount);

        }


    }

    public void withdraw(double amount) throws Exception 
    {
        // DONE/TODO: Implement exception handling for overdrawing and closed accounts
        if (!isActive)
        {
            throw new InvalidAccountOperationException("This account is not currently active"); 
        }
        if (amount > balance)
        {
            throw new OverdrawException("You can not withdraw more than the balance of your account.");
        }

        else {
            balance = balance - amount;
            notifyObservers("Withdrew: $" + amount);
        }

    }

    public double getBalance() {
        return balance;
    }

    public void closeAccount() 
    {
        // DONE/TODO: Prevent further transactions when the account is closed
        isActive = false; 
    }
}

// ============================
// DONE/Decorator Pattern - Define SecureBankAccount Class
// ============================

// DONE/TODO: Implement SecureBankAccount (Concrete Decorator)

class SecureBankAccount extends BankAccountDecorator
{
    public SecureBankAccount(BankAccount account)
    {
        super(account);
    }

    public void deposit(double amount) throws Exception
    {
        decoratedAccount.deposit(amount);
    }

    public void withdraw(double amount) throws Exception
    {
        if (amount > 500)
        {
            throw new OverdrawException("You can not exceed the $500 daily limit.");
        }

        decoratedAccount.withdraw(amount);
    }
}

abstract class BankAccountDecorator extends BankAccount {
    protected BankAccount decoratedAccount;

    public BankAccountDecorator(BankAccount account) {
        super(account.accountNumber, account.getBalance());
        this.decoratedAccount = account;
    }
}


// ============================
// Main Program
// ============================

public class BankAccountTest 
{
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);

        try 
        {
            // DONE/TODO: Ask the user to enter an initial balance and create a BankAccount object
            System.out.print("Please enter your initial balance: ");
            double initialBalance = scanner.nextDouble();
            BankAccount account = new BankAccount("TEMPHOLD", initialBalance);
            System.out.println("Bank Account Created: #123456");

            // DONE/TODO: Create a TransactionLogger and attach it to the account
            TransactionLogger logger = new TransactionLogger();
            account.addObserver(logger);

            // DONE/TODO: Wrap account in SecureBankAccount decorator
            SecureBankAccount secureAccount = new SecureBankAccount(account);
            // DONE/TODO: Allow the user to enter deposit and withdrawal amounts
            System.out.println("Please enter the amount you want to deposit: ");
            double depositAmount = scanner.nextDouble();
            secureAccount.deposit(depositAmount);
            System.out.println("Please enter the amount you want to withdraw: ");
            double withdrawAmount = scanner.nextDouble();
            secureAccount.withdraw(withdrawAmount);

            // DONE/TODO: Display the final balance
            System.out.println("Your current account balance is: $" + secureAccount.getBalance());


        } 
        catch (Exception e) 
        {
            // DONE/TODO: Catch and handle exceptions properly
            System.out.println("Error: " + e.getMessage());
        } 
        finally 
        {
            scanner.close();
        }
    }
}