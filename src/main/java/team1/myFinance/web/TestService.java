package team1.myFinance.web;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;

import team1.myFinance.data.model.Account;
import team1.myFinance.data.model.Transaction;
import team1.myFinance.web.helper.JsonParser;
import team1.myFinance.web.model.AccountType;

@Path("/test")
public class TestService extends ServiceBase {

	// constructor
	public TestService() {
		super();
	}

	@Override
	public void initializeLogger() {
		this.setLogger(LogManager.getLogger(TransactionService.class));
	}

	@GET
	public String test(@Context HttpServletResponse response) {
		
		this.initialize();
		
		int userID1 = dh.createUser("Pati", "admin", 1).getId();
		int userID2 = dh.createUser("Andi", "admin", 1).getId();
		
		int accID1 = dh.createAccount(userID1, AccountType.SAVINGS).getId();
		int accID2 = dh.createAccount(userID2, AccountType.CREDITCARD).getId();
		
		int transID1 = dh.createTransaction("Test 1", accID1, accID2, 500).getId();
		int transID2 = dh.createTransaction("Test 2", accID2, accID1, 200).getId();
		
		return "true";
	}



}
