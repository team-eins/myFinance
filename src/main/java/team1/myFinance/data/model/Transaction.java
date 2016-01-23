package team1.myFinance.data.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@Table(name = "Transaction")
public class Transaction {

	@Id
	@GeneratedValue
	private int id;
	private String name;
	@ManyToOne
	private Account from;
	@ManyToOne
	private Account to;
	private Double amount;
	@Temporal(TemporalType.TIMESTAMP)
	private Date postingdate = new Date();
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Account getFrom() {
		return from;
	}

	public void setFrom(Account from) {
		this.from = from;
	}

	public Account getTo() {
		return to;
	}

	public void setTo(Account to) {
		this.to = to;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getPostingdate() {
		return postingdate;
	}

	public void setPostingdate(Date postingdate) {
		this.postingdate = postingdate;
	}
	
	
}
