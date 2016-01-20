package team1.myFinance.data.programs;


import team1.myFinance.contracts.IDataHandler;
import team1.myFinance.data.handler.DataHandler;

public class InitialUpload {

	public static void main(String[] args) {
		
		IDataHandler handler = new DataHandler(true);

		//transactions
		int transID = handler.createTransaction("Test1").getId();

		handler.closeDatabaseConnection();
		
	}

}
