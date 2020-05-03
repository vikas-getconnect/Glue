package com.atf.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBAccess {
	
	protected static Logger logger = LoggerFactory.getLogger(DBAccess.class);
	private static DBAccess instance;

	private static Connection connection;

	private ConfigReader apiConfig;

	private String mySQLDatabaseIP;
	private String mySQLDatabasePort;
	private String mySQLDBUser;
	private String mySQLDBPwd;
	private String mySQLDB;
	private String databaseURL;

	class Criterion {
		private String key;
		private String operator;
		private String value;
		private String criteriaOp;

		public Criterion(String key, String operator, String value,
				String criteriaOp) {
			this.key = key;
			this.value = value;
			this.operator = operator;
			this.criteriaOp = criteriaOp;
		}

		@Override
		public String toString() {
			return key + operator + value + ' ' + criteriaOp;
		}
	}

	class KeyValue {
		private String key;
		private String value;

		public KeyValue(String key, String value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public String toString() {
			return key + "=" + value;
		}
	}
	private void setUpConnection() throws SQLException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		databaseURL = "jdbc:mysql://" + mySQLDatabaseIP + ":"
				+ mySQLDatabasePort + '/' + mySQLDB;

		Class.forName("com.mysql.jdbc.Driver").newInstance();
		connection = DriverManager.getConnection(databaseURL, mySQLDBUser,
				mySQLDBPwd);
		connection.setAutoCommit(true);
	}

	private DBAccess() {
		try {

			apiConfig = new ConfigReader();
			
			mySQLDatabaseIP = apiConfig.getValue("mySQLDatabaseIP");
			mySQLDatabasePort = apiConfig.getValue("mySQLDatabasePort");
			mySQLDBUser = apiConfig.getValue("mySQLDBUser");
			mySQLDBPwd = apiConfig.getValue("mySQLDBPwd");
			mySQLDB = apiConfig.getValue("mySQLDB");

			logger.info("\n\nINFO: DBConnection is being constructed by THREAD="
							+ Thread.currentThread().getName() + "\n\n");
			setUpConnection();

		} catch (ClassNotFoundException ex) {
			logger.info("Error in connecting DB");
			ex.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private DBAccess(Map <String,String> dbDeatils) {
		try {
			 mySQLDatabaseIP = dbDeatils.get("dbIp");
			 mySQLDatabasePort = dbDeatils.get("dbPort");
			 mySQLDBUser = dbDeatils.get("dbUser");
			 mySQLDBPwd = dbDeatils.get("dbPwd");
			 mySQLDB = dbDeatils.get("dbName");
			System.out
					.println("\n\nINFO: DBConnection is being constructed by THREAD="
							+ Thread.currentThread().getName() + "\n\n");
			setUpConnection();

		} catch (ClassNotFoundException ex) {
			logger.info("Error in connecting DB");
			ex.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static DBAccess getInstance() {
		instance = new DBAccess();
		return instance;
	}
	
	public static DBAccess getInstance(Map <String,String> dbDetails) {
		instance = new DBAccess(dbDetails);
		return instance;
	}

	public static Connection getConnection() throws SQLException {
		if (connection == null || connection.isClosed()) {
			System.out
					.println("\n\nINFO: DBConnection is being (re)constructed by THREAD="
							+ Thread.currentThread().getName() + "\n\n");
			try {
				getInstance().setUpConnection();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}

	public void closeConnection() {
		try {
			logger.info("\n\nINFO: DBConnection being closed by THREAD="
					+ Thread.currentThread().getName() + "\n\n");
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Crude method. Returns raw access to ResultSet
	 * 
	 * @param query
	 * @return
	 */
	public ResultSet executeSQLQuery(String query) {
		ResultSet resultSet = null;

		try {
			
			Statement statement = getConnection().createStatement();
			resultSet = statement.executeQuery(query);

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return resultSet;
	}

	/**
	 * This returns a more structured data as List of <K,V> pairs where
	 * K=columnName & V=value
	 * 
	 * @param rs
	 * @return
	 */
	public List<HashMap<Object, Object>> getStructuredData(ResultSet rs) {
		HashMap<Object, Object> row;
		List<HashMap<Object, Object>> completeData = new ArrayList<HashMap<Object, Object>>();

		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int columns = rsmd.getColumnCount();
			while (rs.next()) {
				row = new HashMap<Object, Object>();
				for (int i = 1; i <= columns; i++) {
					row.put(rsmd.getColumnName(i), rs.getObject(i));
				}
				completeData.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return completeData;
	}

	public List<HashMap<Object, Object>> selectQuery(String sqlQuery) {

		Statement statement;
		List<HashMap<Object, Object>> result = Collections.emptyList();
		try {
			statement = getConnection().createStatement();
			ResultSet rs = statement.executeQuery(sqlQuery);
			result = getStructuredData(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * enter queryParams as emptyList in case all params are required enter
	 * criteria list as empty if there is no criteria enter criteria entries
	 * like <Country, =, 'India', AND>, <Age, =, 10, >
	 * 
	 * @param tableName
	 * @param queryParams
	 * @param criteriaList
	 * @return
	 */
	public List<HashMap<Object, Object>> selectQuery(String tableName,
			ArrayList<String> queryParams, ArrayList<Criterion> criteriaList) {
		StringBuffer sqlQuery = new StringBuffer();

		String params = "";
		if (queryParams.size() == 0) {
			params = "*";
		} else {
			for (int i = 0; i < queryParams.size(); i++) {
				params += queryParams.get(i);
				if (i != (queryParams.size() - 1)) {
					params += ',';
				}
			}
		}

		String criteria = "";
		if (criteriaList.size() > 0) {
			criteria += " where ";
			for (int i = 0; i < criteriaList.size(); i++) {
				criteria += criteriaList.get(i);
				criteria += ' ';
			}
		}

		String finalQuery = sqlQuery.append("Select ").append(params)
				.append(" from ").append(tableName).append(' ')
				.append(criteria).append(';').toString();
		logger.info("Built SELECT Query: " + finalQuery);

		return selectQuery(finalQuery);
	}

	public void updateQuery(String sqlQuery) {
		try {
			PreparedStatement ps = getConnection().prepareStatement(sqlQuery);
			ps.executeUpdate();
			logger.info("Update performed successfully.:"+sqlQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * enter update values as <paramName, paramValue> list enter criteria list
	 * as empty if there is no criteria enter criteria entries like <Country, =,
	 * 'India', AND>, <Age, =, 10, >
	 * 
	 * @param tableName
	 * @param updateValues
	 * @param criteriaList
	 */
	public void updateQuery(String tableName, ArrayList<KeyValue> updateValues,
			ArrayList<Criterion> criteriaList) {
		StringBuffer sqlQuery = new StringBuffer();

		String updateParams = "";
		if (updateValues.size() == 0) {
			logger.info("Nothing tp update.");
			return;
		} else {
			for (int i = 0; i < updateValues.size(); i++) {
				updateParams += updateValues.get(i).toString();
				if (i != (updateValues.size() - 1)) {
					updateParams += ',';
				}
			}
		}

		String criteria = "";
		if (criteriaList.size() > 0) {
			criteria += " where ";
			for (int i = 0; i < criteriaList.size(); i++) {
				criteria += criteriaList.get(i);
			}
		}

		String finalQuery = sqlQuery.append("Update ").append(tableName)
				.append(" set ").append(updateParams).append(' ')
				.append(criteria).append(';').toString();

		logger.info("Built UPDATE Query: " + finalQuery);

		updateQuery(finalQuery);
	}

	public void insertQuery(String sqlQuery) {
		try {
			PreparedStatement ps = getConnection().prepareStatement(sqlQuery);
			ps.executeUpdate();
			logger.info("Inserted record successfully.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * enter update values as <paramName, paramValue> list enter criteria list
	 * as empty if there is no criteria enter criteria entries like <Country, =,
	 * 'India', AND>, <Age, =, 10, >
	 * 
	 * @param tableName
	 * @param insertPairs
	 */
	public void insertQuery(String tableName, ArrayList<KeyValue> insertPairs) {
		StringBuffer sqlQuery = new StringBuffer();

		if (insertPairs.size() == 0) {
			logger.info("Nothing to insert.");
		}
		String keyList = "(";
		String valueList = "(";

		for (int i = 0; i < insertPairs.size(); i++) {
			keyList += insertPairs.get(i).key;
			valueList += insertPairs.get(i).value;
			if (i != (insertPairs.size() - 1)) {
				keyList += ',';
				valueList += ',';
			} else {
				keyList += ')';
				valueList += ')';
			}
		}

		String finalQuery = sqlQuery.append("Insert into ").append(tableName)
				.append(keyList).append(" values").append(valueList)
				.append(';').toString();

		logger.info("Built INSERT Query: " + finalQuery);

		insertQuery(finalQuery);
	}

	public void deleteQuery(String sqlQuery) {
		try {
			PreparedStatement ps = getConnection().prepareStatement(sqlQuery);
			ps.executeUpdate();
			logger.info("Deleted record successfully.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * enter criteria list as empty if there is no criteria enter criteria
	 * entries like <Country, =, 'India', AND>, <Age, =, 10, >
	 * 
	 * @param tableName
	 * @param criteriaList
	 */
	public void deleteQuery(String tableName, ArrayList<Criterion> criteriaList) {
		StringBuffer sqlQuery = new StringBuffer();

		String criteria = "";
		if (criteriaList.size() > 0) {
			criteria += " where ";
			for (int i = 0; i < criteriaList.size(); i++) {
				criteria += criteriaList.get(i);
			}
		}

		String finalQuery = sqlQuery.append("Delete from ").append(tableName)
				.append(criteria).append(';').toString();

		logger.info("Built DELETE Query: " + finalQuery);

		deleteQuery(finalQuery);
	}
	
	
	public String getvalue(String query){
		Statement statement;
		String result=null;
		try {
			statement = getConnection().createStatement();
			ResultSet rs = statement.executeQuery(query);
			rs.next();
			result=rs.getString(1);
			logger.info("query excuted:"+query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeConnection();
		return result;

	}
	

	
	public ArrayList<String> getList(String query){
		Statement statement;
		ArrayList<String> result=new ArrayList<String>();
		try {
			statement = getConnection().createStatement();
			ResultSet rs = statement.executeQuery(query);
			while(rs.next()){
			result.add(rs.getString(1));
			}
			logger.info("query excuted:"+query);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		closeConnection();
		return result;
	}
	
	public ArrayList<ArrayList<String>> getTable(String query){
		Statement statement;
		ArrayList<ArrayList<String>> result=new ArrayList<ArrayList<String>>();
		try {
			statement = getConnection().createStatement();
			ResultSet rs = statement.executeQuery(query);
			while(rs.next()){
			 ArrayList<String> row=new ArrayList<String>();
			 for(int i=0;i<rs.getMetaData().getColumnCount();i++)
			 {
				 row.add(rs.getString(i+1));
				 
			 }
			 	result.add(row);
			}
			logger.info("query excuted:"+query);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		closeConnection();
		return result;
	}

	public static void main(String[] args) {

		DBAccess db = DBAccess.getInstance();

		logger.info("\n #1 SELECT USAGE \n");
		// Usage #1
		db.selectQuery("Select * from Persons");

		logger.info("\n #2 SELECT USAGE \n");
		// Usage #2
		ArrayList<String> params = new ArrayList<String>();
		params.add("LastName");
		params.add("FirstName");
		ArrayList<Criterion> criteria = new ArrayList<Criterion>();
		criteria.add(db.new Criterion("Address", "=", "'KCHalli'", "AND "));
		criteria.add(db.new Criterion("FirstName", "=", "'Vidit'", ""));
		db.selectQuery("Persons", params, criteria);

		logger.info("\n #3 UPDATE USAGE \n");
		// Usage #3
		db.updateQuery("Update Persons set Address='Whitefield' where FirstName='Gopal'");

		logger.info("\n #4 UPDATE USAGE \n");
		// Usage #4
		ArrayList<KeyValue> updateParams = new ArrayList<KeyValue>();
		updateParams.add(db.new KeyValue("Address", "'KCHalli'"));
		ArrayList<Criterion> updateCriteria = new ArrayList<Criterion>();
		updateCriteria.add(db.new Criterion("FirstName", "=", "'Vidit'", ""));
		db.updateQuery("Persons", updateParams, updateCriteria);

		logger.info("\n #5 INSERT USAGE \n");
		// Usage #5
		db.insertQuery("Insert into Persons values (3, 'testln', 'testfn', 'BTM1', 'Bangalore')");

		logger.info("\n #6 INSERT USAGE \n");
		// Usage #6
		ArrayList<KeyValue> insertParams = new ArrayList<KeyValue>();
		insertParams.add(db.new KeyValue("PersonId", "4"));
		insertParams.add(db.new KeyValue("FirstName", "'testfn1'"));
		insertParams.add(db.new KeyValue("LastName", "'testln1'"));
		insertParams.add(db.new KeyValue("Address", "'testAdd1'"));
		insertParams.add(db.new KeyValue("City", "'Bangalore'"));
		db.insertQuery("Persons", insertParams);

		logger.info("\n #7 DELETE USAGE \n");
		// Usage #7
		db.deleteQuery("Delete from Persons where PersonID=3");

		logger.info("\n #8 DELETE USAGE \n");
		// Usage #8
		ArrayList<Criterion> deleteCriteria = new ArrayList<Criterion>();
		deleteCriteria.add(db.new Criterion("PersonID", "=", "4", ""));
		db.deleteQuery("Persons", deleteCriteria);

		db.closeConnection();
	}
}