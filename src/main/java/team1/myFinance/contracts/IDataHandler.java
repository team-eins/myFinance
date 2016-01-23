package team1.myFinance.contracts;

import team1.myFinance.data.model.Account;
import team1.myFinance.data.model.SavedUser;
import team1.myFinance.data.model.Transaction;
import team1.myFinance.web.model.AccountType;

import java.util.Collection;


public interface IDataHandler {

	void closeDatabaseConnection() throws IllegalStateException;

	// change user
	SavedUser changeUser(int userID, int role) throws IllegalArgumentException, IllegalStateException;

	SavedUser createUser(String alias, String password, int role) throws IllegalStateException;

	Transaction createTransaction(String name, int accFromID, int accToID, double amount) throws IllegalStateException;

	// get all transactions
	Collection<Transaction> getAllTransactions() throws IllegalStateException;

	// get all users
	Collection<SavedUser> getAllUsers() throws IllegalStateException;

	// search for user by ID
	SavedUser getUserByID(int id) throws IllegalArgumentException;

	// search for transaction by ID
	Transaction getTransactionByID(int id) throws IllegalArgumentException;

	//login user
	SavedUser getUserLogin(String alias, String password) throws IllegalStateException;

	//delete user
	void deleteUser(int userID) throws IllegalArgumentException;

	SavedUser getUserByName(String alias) throws IllegalArgumentException;

	void deleteTransaction(int transactionID) throws IllegalArgumentException;
	
	// get accounts from user
	Collection<Account> getAccountsFromUser(int userID) throws IllegalStateException;
	
	//create new account for user
	Account createAccount(int userID, AccountType type) throws IllegalStateException;
	
	//create new account for user
	Account getAccountByID(int accID) throws IllegalStateException;
	
	//get all transactions from account
	Collection<Transaction> getTransactionsFromAccount(int id) throws IllegalArgumentException;


}