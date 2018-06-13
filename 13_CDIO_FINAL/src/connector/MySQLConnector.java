package connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import exception.DALException;


public class MySQLConnector {
	/**
	 * To connect to a MySQL-server
	 *
	 * @param url must have the form
	 *            "jdbc:mysql://<server>/<database>" for default port (3306)
	 *            OR
	 *            "jdbc:mysql://<server>:<port>/<database>" for specific port
	 *            more formally "jdbc:subprotocol:subname"
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws SQLException
	 */
	private static Connection connectToDatabase(String url, String username, String password)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		// call the driver class' no argument constructor
		Class.forName("com.mysql.jdbc.Driver").newInstance();

		// get Connection-object via DriverManager
		return (Connection) DriverManager.getConnection(url, username, password);
	}

	private static Connection conn;
	private static Statement stm;
	
	private MySQLConnector(String server, int port, String database,
			String username, String password)
					throws InstantiationException, IllegalAccessException,
					ClassNotFoundException, SQLException {
		conn = connectToDatabase("jdbc:mysql://" + server + ":" + port + "/" + database,
				username, password);
		stm = conn.createStatement();
	}

	private static Connection getConn() {
		return conn;
	}

	private MySQLConnector() throws InstantiationException, IllegalAccessException,
	ClassNotFoundException, SQLException {
		this(Constant.server, Constant.port, Constant.database,
				Constant.username, Constant.password);
	}
	private static Connection getInstance() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		if(conn == null) {
			new MySQLConnector();
			return conn;
		}else {
			return conn;
		}
	}
	public static ResultSet doQuery(String cmd) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		if(conn != null) {
			try {
				stm.executeQuery("select 1 = 1");
			}catch(SQLException e) {
				conn = null;
			}
		}
		try {		        	
			conn = getInstance();
			return stm.executeQuery(cmd);
		} catch (SQLException e) {
			throw new DALException(e);
		}
	}

	public static int doUpdate(String cmd) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		if(conn != null) {
			try {
				stm.executeQuery("select 1 = 1");
			}catch(SQLException e) {
				conn = null;
			}
		}
		try {
			conn = getInstance();
			return stm.executeUpdate(cmd);
		} catch (SQLException e) {
			throw new DALException(e);
		}
	}


}