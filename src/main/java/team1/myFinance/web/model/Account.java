package team1.myFinance.web.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Account {

	public int id;
	public int ownerID;
	public Double balance;
	public AccountType type;
	
	public static Account parse(team1.myFinance.data.model.Account account) {

		if (account == null) {
			return null;
		}

		Account acc = new Account();

		// mapping
		acc.id      = account.getId();
		acc.ownerID = account.getOwner().getId();
		acc.balance = account.getBalance();
		
		switch(account.getType()){
		case 0:
			acc.type = AccountType.SAVINGS;
			break;
		case 1:
			acc.type = AccountType.CREDITCARD;
			break;
		}

		return acc;
	}

	public static Collection<Account> parse(Collection<team1.myFinance.data.model.Account> accounts) {

		List<Account> result = new ArrayList<>();

		if (accounts != null) {
            for (team1.myFinance.data.model.Account acc : accounts) {
                result.add(Account.parse(acc));
            }
		}

		return result;
	}
	
}
