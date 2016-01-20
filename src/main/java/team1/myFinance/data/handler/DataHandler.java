package team1.myFinance.data.handler;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import team1.myFinance.contracts.IDataHandler;
import team1.myFinance.data.model.*;
import team1.myFinance.data.model.Transaction;

public class DataHandler implements IDataHandler {

	private SessionFactory sessionFactory;
	private ServiceRegistry serviceRegistry;
	private Connection connection;

	// private constructor
	public DataHandler(boolean test) throws IllegalStateException {

		try {
			// connect to db
			// connection = connectToDatabase();

			// create session factory
			Configuration configuration = new Configuration();
			// productive DB
			if (test) {
				configuration.configure("hibernate.cfg.test.xml");
			} else {
				configuration.configure();
			}
			serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);

		} catch (HibernateException e) {
			System.out.println("Hibernate problems");
			throw new IllegalStateException("Hibernate problems");
		} catch (IllegalStateException e) {
			System.out.println("no connection to database");
			throw new IllegalStateException("no connection to database");
		} catch (Exception e) {
			System.out.println("some connection error");
		}
	}

	// private constructor
	public DataHandler() throws IllegalStateException {

		this(false);
	}

	/**
	 * connect to the database
	 * 
	 * @return the Connection to the database
	 * @throws URISyntaxException
	 *             throw this exception when the URI to the database is wrong
	 * @throws IllegalStateException
	 *             throw this exception when it is not possible to connect
	 */
	private Connection connectToDatabase() throws URISyntaxException, IllegalStateException {

		URI dbUri = new URI(
				"postgres://ubtajozptkphzu:aSKaHnj7xrv7_qR6Bd3RG8Rd9S@ec2-54-197-224-155.compute-1.amazonaws.com:5432/dbvr819hfm9lsd");

		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath()
				+ "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";

		Connection conn;
		try {
			conn = DriverManager.getConnection(dbUrl, username, password);
		} catch (Exception e) {
			System.out.println("closing connection not possible");
			throw new IllegalStateException("closing connection not possible");
		}

		return conn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see team1.myshop.contracts.IDataHandler#closeDatabaseConnection()
	 */
	@Override
	public void closeDatabaseConnection() throws IllegalStateException {
		// sessionFactory.close();
		// try {
		// connection.close();
		// } catch (Exception e) {
		// System.out.println("closing connection not possible");
		// throw new IllegalStateException("closing connection not possible");
		// }
	}

	/**
	 * save an object to the database, when it is an entity
	 * 
	 * @param obj
	 *            the object of an entity
	 * @return the ID of the entity
	 * @throws IllegalStateException
	 *             commit failed by saving from object
	 */
	private Integer saveObjectToDb(Object obj) throws IllegalStateException {

		Session session = openSession();

		try {

			// begin transaction
			session.beginTransaction();

			// save an object
			int id = (int) session.save(obj);

			// commit
			session.getTransaction().commit();

			return id;

		} catch (Exception e) {
			// Exception -> rollback
			session.getTransaction().rollback();
			System.out.println("saving from object not possible");
			throw new IllegalStateException("saving from object not possible", e);
		} finally {
			// close session
			session.close();
		}

	}

	/**
	 * deletes an object from the database, when it is an entity
	 * 
	 * @param obj
	 *            the object of an entity
	 * @throws IllegalArgumentException
	 *             deletion of object failed
	 */
	private void deleteObjectFromDb(Object obj) throws IllegalArgumentException {

		Session session = openSession();

		try {

			// begin transaction
			session.beginTransaction();

			// save an object
			session.delete(obj);

			// commit
			session.getTransaction().commit();

		} catch (Exception e) {
			// Exception -> rollback
			session.getTransaction().rollback();

			// deletion failed
			System.out.println("deletion of object failed");
			throw new IllegalArgumentException("deletion of object failed", e);
		} finally {
			// close session
			session.close();
		}
	}

	/**
	 * open a new Session
	 * 
	 * @return the session
	 */
	private Session openSession() {

		return sessionFactory.openSession();
	}

	// change user
	/*
	 * (non-Javadoc)
	 * 
	 * @see team1.myshop.contracts.IDataHandler#changeUser(int,
	 * java.lang.String, int)
	 */
	@Override
	public SavedUser changeUser(int userID, int role)
			throws IllegalArgumentException, IllegalStateException {

		Session session = openSession();

		if (role < 1 || role > 3) {
			throw new IllegalArgumentException("invalid role");
		}

		try {

			// begin transaction
			session.beginTransaction();

			// get user
			Criteria cr = session.createCriteria(SavedUser.class);
			cr.add(Restrictions.eq("id", userID));
			List<SavedUser> results = cr.list();

			if (results.size() == 0)
				throw new IllegalArgumentException("userID");

			SavedUser user = results.get(0);

			// change user team1.myFinance.data
			user.setRole(role);

			// update user
			session.update(user);

			// commit
			session.getTransaction().commit();

			return user;

		} catch (IllegalArgumentException e) {
			// Exception -> rollback
			session.getTransaction().rollback();
			System.out.println("no user with this ID in the database");
			throw new IllegalArgumentException(e.getMessage());
		} catch (Exception e) {
			// Exception -> rollback
			session.getTransaction().rollback();
			throw new IllegalStateException("user change");
		} finally {
			// close session
			session.close();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see team1.myshop.contracts.IDataHandler#createUser(java.lang.String,
	 * java.lang.String, int)
	 */
	@Override
	public SavedUser createUser(String alias, String password, int role) throws IllegalStateException {

		// create user instance
		SavedUser user = new SavedUser();
		user.setAlias(alias);

		if (role < 1 || role > 3) {
			throw new IllegalStateException("invalid user role");
		}

		user.setRole(role);

		try {
			user.setPassword(PasswordHash.getSaltedHash(password));
		} catch (Exception e) {
			System.out.println("Creation of Hash failed");
			throw new IllegalStateException("Creation of Hash failed", e);
		}

		// save user in database
		saveObjectToDb(user);
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see team1.myshop.contracts.IDataHandler#createCategory(java.lang.String)
	 */
	@Override
	public Transaction createTransaction(String name) throws IllegalStateException {

		// create category instance
		Transaction transaction = new Transaction();
		transaction.setName(name);

		saveObjectToDb(transaction);
		return transaction;

	}

	@Override
	public void deleteUser(int userID) throws IllegalArgumentException {
		try {
			// SavedUser
			SavedUser user = getUserByID(userID);
			// delete comment from database
			deleteObjectFromDb(user);
		} catch (IllegalArgumentException e) {
			System.out.println("deletion or getting user from ID failed");
			throw new IllegalArgumentException("deletion or getting user from ID failed", e);
		}
	}

	// delete category
	/*
	 * (non-Javadoc)
	 * 
	 * @see team1.myshop.contracts.IDataHandler#deleteCategory(int)
	 */
	@Override
	public void deleteTransaction(int transactionID) throws IllegalArgumentException {
		try {
			// get transaction
			Transaction transaction = getTransactionByID(transactionID);
			// delete transaction from database
			deleteObjectFromDb(transaction);
		} catch (IllegalArgumentException e) {
			System.out.println("deletion or getting transaction from ID failed");
			throw new IllegalArgumentException("deletion or getting transaction from ID failed", e);
		}
	}

	// get all transactioins
	/*
	 * (non-Javadoc)
	 * 
	 * @see team1.myshop.contracts.IDataHandler#getAllTransactions()
	 */
	@Override
	public Collection<Transaction> getAllTransactions() throws IllegalStateException {

		try {

			return (Collection<Transaction>) getTableData(Transaction.class);

		} catch (Exception e) {
			// Exception
			throw new IllegalStateException("something went wrong by getting the transaction list");
		}
	}

	// get all users
	/*
	 * (non-Javadoc)
	 * 
	 * @see team1.myshop.contracts.IDataHandler#getAllUsers()
	 */
	@Override
	public Collection<SavedUser> getAllUsers() throws IllegalStateException {

		try {

			return (Collection<SavedUser>) getTableData(SavedUser.class);

		} catch (Exception e) {
			// Exception
			throw new IllegalStateException("something went wrong by getting the user list");
		}
	}

	// search for user by ID
	/*
	 * (non-Javadoc)
	 * 
	 * @see team1.myshop.contracts.IDataHandler#getUserByID(int)
	 */
	@Override
	public SavedUser getUserByID(int id) throws IllegalArgumentException {
		return this.<SavedUser> searchForID(id, SavedUser.class);
	}

	// search for user by name
	/*
	 * (non-Javadoc)
	 * 
	 * @see team1.myshop.contracts.IDataHandler#getUserByID(int)
	 */
	@Override
	public SavedUser getUserByName(String alias) throws IllegalArgumentException {

		Session session = openSession();

		try {

			// begin transaction
			session.beginTransaction();

			Criteria cr = session.createCriteria(SavedUser.class);
			cr.add(Restrictions.eq("alias", alias));
			List<SavedUser> results = cr.list();

			// commit
			session.getTransaction().commit();

			//user not available
			if(results.size() < 1){
				return null;
			}
			
			// only one element in the list because the id is unique
			return results.get(0);

		} catch (IndexOutOfBoundsException e) {
			// Exception -> rollback
			session.getTransaction().rollback();
			throw new IllegalArgumentException("object with this ID is not in the database", e);
		} finally {
			// close session
			session.close();
		}

		
	}
	
	// search for category by ID
	/*
	 * (non-Javadoc)
	 * 
	 * @see team1.myshop.contracts.IDataHandler#getCategoryByID(int)
	 */
	@Override
	public Transaction getTransactionByID(int id) throws IllegalArgumentException {
		return this.<Transaction> searchForID(id, Transaction.class);
	}

	// select all team1.myFinance.data from table
	private Collection<?> getTableData(Class cls) throws IllegalStateException {

		Session session = openSession();

		try {

			// begin transaction
			session.beginTransaction();

			// get all categories
			Criteria cr = session.createCriteria(cls);
			List<?> results = cr.list();

			// commit
			session.getTransaction().commit();

			return results;

		} catch (Exception e) {
			// Exception -> rollback
			session.getTransaction().rollback();
			throw new IllegalStateException("something went wrong by getting the data");
		} finally {
			// close session
			session.close();
		}

	}

	// search for a single dataset by ID
	private <T> T searchForID(int id, Class<T> typeParameterClass) throws IllegalArgumentException {

		Session session = openSession();

		try {

			// begin transaction
			session.beginTransaction();

			Criteria cr = session.createCriteria(typeParameterClass);
			cr.add(Restrictions.eq("id", id));
			List<T> results = cr.list();

			// commit
			session.getTransaction().commit();

			// only one element in the list because the id is unique
			return results.get(0);

		} catch (IndexOutOfBoundsException e) {
			// Exception -> rollback
			session.getTransaction().rollback();
			throw new IllegalArgumentException("object with this ID is not in the database", e);
		} finally {
			// close session
			session.close();
		}
	}

	// login user
	/*
	 * (non-Javadoc)
	 * 
	 * @see team1.myshop.contracts.IDataHandler#getUserLogin(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public SavedUser getUserLogin(String alias, String password) throws IllegalStateException {
		Session session = openSession();

		try {

			// begin transaction
			session.beginTransaction();

			// System.out.println("Transaction started");

			Criteria cr = session.createCriteria(SavedUser.class);
			cr.add(Restrictions.eq("alias", alias));
			List<SavedUser> results = cr.list();

			// commit
			session.getTransaction().commit();

			// only one element in the list because the id is unique
			for (SavedUser user : results) {
				try {
					// System.out.println("PW check started");
					if (PasswordHash.check(password, user.getPassword()))
						// System.out.println("Return user");
						return user;
				} catch (Exception e) {
					// System.out.println("PW check failed");
					throw new IllegalStateException("Fail by checking the user password");
				}
			}
			// System.out.println("no users found");
		} catch (HibernateException e) {
			// Exception -> rollback
			// System.out.println("Error!!");
			session.getTransaction().rollback();
			throw new IllegalStateException("something went wrong by getting the user");
		} finally {
			// close session
			session.close();
		}
		// no appropriate user found in database
		return null;
	}

}
