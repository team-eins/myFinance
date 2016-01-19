package main.java.team1.myFinance.contracts;

import java.util.Collection;

import main.java.data.model.SavedUser;
import main.java.data.model.Transaction;

public interface IDataHandler {

	void closeDatabaseConnection() throws IllegalStateException;

	// change user
	SavedUser changeUser(int userID, int role) throws IllegalArgumentException, IllegalStateException;

	SavedUser createUser(String alias, String password, int role) throws IllegalStateException;

	Transaction createTransaction(String name) throws IllegalStateException;

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

}