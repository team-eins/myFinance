package team1.myFinance.data.model;

import java.util.Collection;
import java.util.LinkedList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "Account")
public class Account {

	@Id
	@GeneratedValue
	private int id;
	@ManyToOne
	private SavedUser owner;
	private Double balance;
	private int type;
	@OneToMany(mappedBy = "from")
	@Cascade({ CascadeType.DELETE })
	private Collection<Transaction> fromTransactions = new LinkedList<>();
	@OneToMany(mappedBy = "to")
	@Cascade({ CascadeType.DELETE })
	private Collection<Transaction> toTransactions = new LinkedList<>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public SavedUser getOwner() {
		return owner;
	}
	public void setOwner(SavedUser owner) {
		this.owner = owner;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Collection<Transaction> getFromTransactions() {
		return fromTransactions;
	}
	public void setFromTransactions(Collection<Transaction> fromTransactions) {
		this.fromTransactions = fromTransactions;
	}
	public Collection<Transaction> getToTransactions() {
		return toTransactions;
	}
	public void setToTransactions(Collection<Transaction> toTransactions) {
		this.toTransactions = toTransactions;
	}
	
}
