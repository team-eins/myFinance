package main.java.data.programs;

import main.java.data.handler.DataHandler;
import main.java.team1.myFinance.contracts.IDataHandler;

public class InitialUpload {

	public static void main(String[] args) {
		
		IDataHandler handler = new DataHandler(true);

		//transactions
		int transID = handler.createTransaction("Test1").getId();

		handler.closeDatabaseConnection();
		
	}

}
