# Bank: CS591 Final Project

### Team 10: 
### Priya Kumari: 
### Jorge Jimenez: U34800726
### Ziyu Shen:

========================================================================================

#### CLASS STRUCTURE TO-DO LIST

- [X] Empty Skeleton Class Files
- [ ] Add proper class specifications (class, abstract, interface) 
- [X] BankComponent
- [X] ID
- [ ] Bank
- [ ] BankUser
- [ ] Customer
- [ ] Manager
- [ ] Account
- [ ] CheckingAccount
- [ ] SavingsAccount
- [ ] SecuritiesAccount
- [ ] LoanAccount
- [X] Currency
- [X] Dollar
- [X] Euro
- [X] Yen
- [X] Transaction
- [X] Deposit
- [X] Withdrawl
- [X] Transfer
- [ ] Loan
- [ ] Stock
- [ ] Stock Market
- [ ] Report

========================================================================================

#### DESIGN PATTERNS TO INCORPORATE

1. Singleton Pattern - design so that there is only 1 bank, 1 stock market, and 1 bank manager created 
2. Factory Pattern - create an AccountFactory class with a getAccount() method that will return an Account object (can be checking, savings, securities, or loan) 
3. Facade Pattern - design the interface for the customer so that all the methods and other complex code is not visible
4. Observer Pattern - have the customers observe something (the Manager or the Stock Market could be the subject) so that they are notified of changes to their account or changes in the market 
