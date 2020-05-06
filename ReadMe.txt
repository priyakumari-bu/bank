Bank: CS591 Final Project

Team 10: 
Priya Kumari: U21518661
Jorge Jimenez: U34800726
Ziyu Shen: U86763794

Instructions on Compilation and Execution of the Program: 
The CompileMe.java file contains a concise main method to run our application. To compile and execute this program, use the following 2 commands:

javac CompileMe.java
java CompileMe


Information on the Usability of Each Class: 

ABSTRACT CLASSES

Account.java
This is an abstract class that represents an account in the bank. It implements the BankComponent and Exchangeable interfaces since it a component of the bank and it can be represented in different currencies. It contains methods that allow customers to perform deposits, withdrawls, and transfers. 

Currency.java
This is an abstract class that represents a currency that can be used in the bank. It implements BankComponent and contains abstract methods implemented separately by each of the subclasses that extend it. 

Transaction.java
This is an abstract class that represents any kind of transaction that takes place in the bank. It is extended by transactions like Deposit, Withdrawl, and Transfer, and it implements BankComponent and Exchangeable. 





INTERFACES

BankComponent.java
This is an interface implemented by all of the components of the bank to enforce the common feature they share (an ID). 

Exchangeable.java
This is an interface that represents the ability of certain objects to be exchanged to different currencies. It contains an exchangeTo() method that must be defined by all the classes that implement it. 

InterestTaxable.java
This is an interface that represents the ability of certain objects to have interest and be taxed. It contains getInterestRate() and accumulateInterestRate() methods that every class that implements it must define. 

Tradeable.java
This is an interface that represnts the ability of certain objects like stocks to be traded. It could be extended to other derivates like bonds or options in the future.





REGULAR CLASSES 

CompileMe.java
This class is for compilation purposes. It contains the main method which creates an instance of Bank and an instance of PersistanceHandler, and then loads the state of the PersistanceHandler object to maintain persistence. 

AccountFactory.java
This is a class used to enforce the Factory pattern that produces different kinds of accounts. 

Bank.java
This is a class that represents the Bank. It follows the Singleton pattern with the use of a private constructor and a public getInstance() method so that only one bank is created in the entire application. The Bank has a manager, a stock market, and customers.

BankUser.java
This is a class that represents a user of the bank, which can either be a customer or the manager. All users have a username and password with which they log in to their account.

CheckingAccount.java
This account represents a checking account of the bank. It extends Account. 

Customer.java
This is a class that represents a customer in the bank. It extends BankUser as customers are a type of bank user. It contains methods that allow customers to open accounts, close accounts, request loans, and perform other functions. 

Deposit.java
This is a class that represents a deposit. It extends Transaction as it is a type of transaction. 

Dollar.java
This is a class that represents an amount of money in the form of a Dollar. It extends Currency as it is a type of currency. 

Euro.java
This is a class that represents an amount of money in the form of a Euro. It extends Currency as it is a type of currency. 

ID.java
This is a class that represents an ID, or an identification in the form of a string that is unique to every component of the bank. 

Loan.java
This is a front-end class that represents a loan. It implements BankComponent, Exchangeable, and InterestTaxable interfaces. 

LoanAccount.java
This is a class that represents a loan account. It contains loans and extends Account.  

Manager.java
This is a class that represents the manager of the bank. It extends BankUser as the manager a user of the bank. 

PersistanceHandler.java
This is a class that contains all of the methods necessary to maintain persistence in the application. It performs all of the file input and output needed to record information on the customers and the stock market. 

Report.java
This is a class that represents a report that the manager can generate to view information on transactions in the bank.

SavingsAccount.java
This is a class that represents a savings account. It extends account and implements InterestTaxable as savings accounts earn interest. 

SecuritiesAccount.java
This is a class that represents a securities account. It extends Account and contains stocks. 

Stock.java
This class represents a stock. It implements BankComponent, Exchangeable, and Tradeable as stocks can be traded (bought or sold). 

StockMarket.java
This class represents the stock market that contains the stocks available for customers to trade. 

Transfer.java
This is a class that represents a transfer of some money between two accounts. It extends Transaction as it is a type of transaction. 

Withdrawl.java
This is a class that represents a withdrawl from an account. It extends Transaction as it is a type of transaction. 

Yen.java
This is a class that represents an amount of money in the form of a Yen. It extends Currency as it is a type of currency. 





GUI CLASSES 

AddStocksFrame.java
This is a front-end class that produces a frame from which the manager can add stocks to the stock market. 

AdjustStocksFrame.java
This is a front-end class that produces a frame from which the manager can adjust the price of an existing stock in the stock market. 

CustomerAccountsFrame.java
This is a front-end class that produces a frame that allows users to perform different functions on their accounts (open them, close them, view their account transactions, etc.). 

CustomerFrame.java
This is a front-end class that produces a frame when the customer logs in to display all of the functions they can perform. 

CustomerInfoFrame.java
This is a front-end class that produces a frame when the manager wants to check up on a certain customer. 

CustomerLoansFrame.java
This is a front-end class that produces a frame that displays information regarding a customer's loans. 

CustomerRegistrationFrame.java
This is a front-end class that produces a frame from which a new user can be registered in the bank. 

DepositFrame.java
This is a front-end class that produces a frame from which the user can perform a deposit.

LoanFrame.java
This is a front-end class that produces a frame from which customers can request loans. 

Login.java
This is a front-end class that produces a frame from which bank users can log in with their username and password. 

ManagerFrame.java
This is a front-end class that produces a frame when the manager logs in to display all the functions they can perform. 

ManagerStockFrame.java
This is a front-end class that produces a frame from which the manager can perform different functions related to the stock market. 

PayLoanFrame.java
This is a front-end class that produces a frame from which customers can pay off their loans. 

StockMarketFrame.java
This is a front-end class that produces a frame from which bank users can view the stock market. 

TransferFrame.java
This is a front-end class that produces a frame from which the customer can perform a transfer. 


WithdrawlFrame.java
This is a front-end class that produces a frame from which the customer can perform a withdrawl. 





OTHER 

StoredData
This is a folder that contains the files that are written to and read from in order to maintain persistence in the application. 














