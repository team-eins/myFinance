package main.java.team1.myFinance.web;

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;

import main.java.data.model.Transaction;

@Path("/transactions")
public class TransactionService extends ServiceBase {

	//constructor
	public TransactionService(){
		super();
	}
	
	@Override
	public void initializeLogger() {
		this.setLogger(LogManager.getLogger(TransactionService.class));
	}

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<main.java.team1.myFinance.web.model.Transaction> getCategories(@Context HttpServletResponse response) {

        this.initialize();
        Collection<Transaction> transactions = dh.getAllTransactions();

        if (transactions == null) {
            http.cancelRequest(response, SC_INTERNAL_SERVER_ERROR);
            return null;
        }

        return main.java.team1.myFinance.web.model.Transaction.parse(transactions);

    }
	
}
