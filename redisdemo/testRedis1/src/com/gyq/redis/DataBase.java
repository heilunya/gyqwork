package com.gyq.redis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * 用于连接数据源，并提供基本的数据读取与操作方法
 */

public class DataBase {
	public static String DRIVER = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
	public static String USER = "$user";
	public static String PASSWORD = "$passwd";
	public static String ConnectionURL = "jdbc:microsoft:sqlserver://$ip:1433;DatabaseName=";
	//public static String ConnectionURL = "jdbc:microsoft:sqlserver://192.168.60.116:1433;DatabaseName=";
	 
	public Connection connectjdbc(String database) throws SQLException {

		try {
			Class.forName(DRIVER);
			return DriverManager.getConnection(ConnectionURL+database,USER, PASSWORD);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		 
	}

	public void close(ResultSet rs, Statement stmt, Connection conn) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (Exception e3) {
			e3.printStackTrace();
		}
	}


}