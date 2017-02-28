package com.kpit.util;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.type.StringType;

public class HibernateSessionFactory {

	protected static SessionFactory sessionFactory;
	private static final Logger LOGGER = Logger.getLogger(HibernateSessionFactory.class);

	/**
	 * Constructs a new Singleton SessionFactory
	 * 
	 * @author sachinc3
	 * @return session Factory
	 * @throws HibernateException
	 */
	public static SessionFactory buildSessionFactory() throws HibernateException {
		if (sessionFactory != null) {
			closeFactory();
		}
		return configureSessionFactory();
	}

	/**
	 * Builds a SessionFactory, if it hasn't been already.
	 * 
	 * @author sachinc3
	 * @return Session Factory
	 * @throws HibernateException
	 */
	public static SessionFactory buildIfNeeded() throws HibernateException {
		if (sessionFactory != null) {
			return sessionFactory;
		}
		try {
			return configureSessionFactory();
		} catch (HibernateException e) {
			LOGGER.error("Cannot create hibernate session factory", e);
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * @author sachinc3
	 * @return SessionFactory
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * 
	 * @return Session
	 * @throws HibernateException
	 */
	public static Session openSession() throws HibernateException {
		buildIfNeeded();
		return sessionFactory.openSession();
	}

	public static void closeFactory() {
		if (sessionFactory != null) {
			try {
				sessionFactory.close();
			} catch (HibernateException ignored) {
				LOGGER.log(Level.ERROR, ignored.getMessage());
			}
		}
	}

	public static void close(Session session) {
		if (session != null) {
			try {
				session.close();
			} catch (HibernateException ignored) {
				LOGGER.log(Level.ERROR, ignored.getMessage());
			}
		}
	}

	public static void rollback(Transaction trans) {
		try {
			if (trans != null) {
				trans.rollback();
			}
		} catch (HibernateException ignored) {
			LOGGER.log(Level.ERROR, ignored.getMessage());
		}
	}

	private static SessionFactory configureSessionFactory() throws HibernateException {

		// Configuration configuration = new Configuration();
		// configuration.configure(HibernateConfigXML);
		AnnotationConfiguration annoConfig = new AnnotationConfiguration();
		annoConfig.configure("hibernate.cfg.xml");
		annoConfig.addSqlFunction("group_concat",
				new org.hibernate.dialect.function.StandardSQLFunction("group_concat", new StringType()));
		// myConf.addSqlFunction("group_concat", new
		// StandardSQLFunction("group_concat", new StringType()));
		Properties p = annoConfig.getProperties();
		String url = p.getProperty("hibernate.connection.url");
		String user = p.getProperty("hibernate.connection.username");
		String password = p.getProperty("hibernate.connection.password");

		try {
			DriverManager.getConnection(url, user, password).close();
			LOGGER.info("*****************************************************************************");
			LOGGER.info("Database Connected Successfully");
			LOGGER.info("*****************************************************************************");
		} catch (SQLException e) {
			LOGGER.error("Database not connected --" + e);
			System.exit(1);
		}
		sessionFactory = annoConfig.buildSessionFactory();
		return sessionFactory;
	}

}
