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

@Path("/accounts")
public class AccountService extends ServiceBase {

	// constructor
	public AccountService() {
		super();
	}

	@Override
	public void initializeLogger() {
		this.setLogger(LogManager.getLogger(TransactionService.class));
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<team1.myFinance.web.model.Account> getAccountsFromUser(@Context HttpServletResponse response) {

		int userID = 1;

		this.initialize();
		Collection<Account> accounts = dh.getAccountsFromUser(userID);

		if (accounts == null) {
			http.cancelRequest(response, SC_INTERNAL_SERVER_ERROR);
			return null;
		}

		return team1.myFinance.web.model.Account.parse(accounts);

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public team1.myFinance.web.model.Account createAccount(String accountString, @Context HttpServletResponse response) {

		this.initialize();
		
		// Parse body data
		team1.myFinance.web.model.Account acc = JsonParser.parse(accountString,
				team1.myFinance.web.model.Account.class);
		
		Account account = dh.createAccount(acc.id, acc.type);

		if (account == null) {
			http.cancelRequest(response, SC_INTERNAL_SERVER_ERROR);
			return null;
		}

		// Set response headers
		response.setHeader("Location", "api/accounts/" + account.getId() + "/transactions");
		response.setStatus(HttpServletResponse.SC_CREATED);

		return team1.myFinance.web.model.Account.parse(account);

	}

	@GET
	@Path("/{id}/transactions")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<team1.myFinance.web.model.Transaction> getTransactionsFromAccount(@PathParam("id") int id,
			@Context HttpServletResponse response) {

		this.initialize();
		Collection<Transaction> transactions = dh.getTransactionsFromAccount(id);

		if (transactions == null) {
			http.cancelRequest(response, SC_INTERNAL_SERVER_ERROR);
			return null;
		}

		return team1.myFinance.web.model.Transaction.parse(transactions);

	}

	@POST
	@Path("/{id}/transactions")
	@Produces(MediaType.APPLICATION_JSON)
	public team1.myFinance.web.model.Transaction createTransaction(@PathParam("id") int id, String transString,
			@Context HttpServletResponse response) {

		this.initialize();

		// Parse body data
		team1.myFinance.web.model.Transaction trans = JsonParser.parse(transString,
				team1.myFinance.web.model.Transaction.class);
		
		if (trans == null) {
			http.cancelRequest(response, SC_BAD_REQUEST);
		}
	
		assert trans != null;
		
		Transaction transaction = dh.createTransaction(trans.name, trans.from.id, trans.to.id, trans.amount);
		
		if (transaction == null) {
			http.cancelRequest(response, SC_INTERNAL_SERVER_ERROR);
		}

		assert transaction != null;
		response.setHeader("Location", "api/accounts/");
		response.setStatus(201);

		return team1.myFinance.web.model.Transaction.parse(transaction);

	}

}
