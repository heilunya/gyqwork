package com.hdwiki;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;


public class DatabaseConnection {

	/**
     * 获取远程SQLServer数据库连接信息
     *
     * @return
     */
    public static Connection getSqlServerConn() throws Exception {
        Connection conn = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "";
        String username = "";
        String password = "";
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("properties.conf");
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line = "";
        while ((line = br.readLine()) != null) {
        	if(line.startsWith("boss_url")){
				url=line.substring(9);
			}else if(line.startsWith("boss_username")){
				username=line.substring(14);
			}else if(line.startsWith("boss_passwd")){
				password=line.substring(12);
			}
        }
        conn = DriverManager.getConnection(url, username, password);
        return conn;
    }

   
    
    /**
     * 获取远程Mysql数据库连接信息
     *
     * @return
     */
    public static Connection getMySqlConn() throws Exception {
        Connection conn = null;
        Class.forName("com.mysql.jdbc.Driver");
        //Class.forName("org.gjt.mm.mysql.Driver");
        String url = "";
        String username = "";
        String password = "";
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("properties.conf");
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line = "";
        while ((line = br.readLine()) != null) {
        	if(line.startsWith("mysql_url")){
				url=line.substring(10);
			}else if(line.startsWith("mysql_username")){
				username=line.substring(15);
			}else if(line.startsWith("mysql_passwd")){
				password=line.substring(13);
			}
        }
        conn = DriverManager.getConnection(url, username, password);
        return conn;
    }

	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		new DatabaseConnection();
	}

}
