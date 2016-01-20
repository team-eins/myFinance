package main.java.team1.myFinance.web.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Transaction {

	public int id;
	public String name;
	
    public static Transaction parse(main.java.data.model.Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        Transaction trans = new Transaction();

        trans.id = transaction.getId();
        trans.name = transaction.getName();
         
        return trans;
    }

    public static Collection<Transaction> parse(Collection<main.java.data.model.Transaction> transactions) {

        List<Transaction> transs = new ArrayList<>();

        if (transactions != null) {
            for (main.java.data.model.Transaction transaction : transactions) {
            	transs.add(Transaction.parse(transaction));
            }
        }
        return transs;
    }

}
