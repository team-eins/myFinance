package team1.myFinance.web.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Transaction {

	public int id;
	public String name;
	
    public static Transaction parse(team1.myFinance.data.model.Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        Transaction trans = new Transaction();

        trans.id = transaction.getId();
        trans.name = transaction.getName();
         
        return trans;
    }

    public static Collection<Transaction> parse(Collection<team1.myFinance.data.model.Transaction> transactions) {

        List<Transaction> transs = new ArrayList<>();

        if (transactions != null) {
            for (team1.myFinance.data.model.Transaction transaction : transactions) {
            	transs.add(Transaction.parse(transaction));
            }
        }
        return transs;
    }

}
