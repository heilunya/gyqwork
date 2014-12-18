package com.hdwiki;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.hdwiki.Account;



public class HdwikiInit {
	
	//将初始数据导入wiki_user中
    public void init() throws Exception {
        List<Account> accounts = getAllBOSSAccount();
        if (accounts != null && accounts.size() != 0) {
            Connection mysqlconn = DatabaseConnection.getMySqlConn();
            boolean flag ;
            String sql = "insert into wiki_user(username,password,email,gender,image,postcode,location,regip"
            				+",lastip,groupid,truename,telephone,qq,msn,authstr) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            if(accounts!=null && accounts.size()>0){
            	PreparedStatement pst =  mysqlconn.prepareStatement(sql);
            	for(Iterator<Account> its = accounts.iterator();its.hasNext();){
            		Account account = its.next();
            		
            		
            		pst.setString(1, account.getUsername());
            		pst.setString(2, account.getPassword());
            		pst.setString(3, account.getEmail());
            		pst.setString(4, account.getGender());
            		pst.setString(5, account.getImage());
            		pst.setString(6, account.getPostcode());
            		pst.setString(7, account.getLocation());
            		pst.setString(8, account.getRegip());
            		pst.setString(9, account.getLastip());
            		pst.setString(10, account.getGroupid());
            		pst.setString(11, account.getTruename());
            		pst.setString(12, account.getTelephone());
            		pst.setString(13, account.getQq());
            		pst.setString(14, account.getMsn());
            		pst.setString(15, account.getAuthstr());
            		flag = pst.execute();
            		System.out.println(flag);
            	}
            	if (pst != null) {
            		pst.close();
                }
            }
            if (mysqlconn != null) {
            	mysqlconn.close();
            }
        }
    }
	
    
	
    
    //insert into hdwiki_sa(username,password,email,gender,image,postcode,location,regip,lastip,groupid,truename,telephone,qq,msn,authstr) 
	//values('testgyq','s','guoyaqiu@jobcn.com',0,'http://192.168.60.118/Upload/PER_PHOTO/JCNERP579043.png','','','',0,2,'name','8080','','','');
	/**
     * 获取当前BOSS系统中的所有非虚拟帐号信息
     */
    public List<Account> getAllBOSSAccount() throws Exception {
        Connection sqlserverConn = null;
        List<Account> Accounts = new ArrayList<Account>();
        try {
            sqlserverConn = DatabaseConnection.getSqlServerConn();
            String querySQL = ""
            		+ " select p.ID as username"
            		+ "     , p.Password as password"
            		+ "     , p.email as email"
            		+ "     , case when p.sex='女' then 0 else 1 end as gender"
            		+ "     , 'http://192.168.60.118/Upload/PER_PHOTO/'+p.PhotoFile as image"
            		+ "     , d.Name as location"
            		+ "     , p.name as truename"
            		+ "     , p.Extension as telephone"
            		+ " from jobcn_boss_hr.dbo.Person p with(nolock)"
            		+ "     , jobcn_boss_hr.dbo.Department d with(nolock)"
            		+ " where p.DeptID = d.ID"
            		+ "     and p.SaleType <> '3308'"
            		//+ "     and p.id > 'JCNEP6568'"
            		//+ "     and p.RegisterDate>'2014-10-30 16:28:21.193'"
            		+ " order by p.ID";

            ResultSet rs = sqlserverConn.prepareStatement(querySQL).executeQuery();
            while (rs.next()) {
            	//员工号
                String username = rs.getString("username");
                //密码
                String password = rs.getString("password");
                //员工email
                String email = rs.getString("email");
                //性别
                String gender = rs.getString("gender");
                //照片
                String image = rs.getString("image");
                //部门
                String location = rs.getString("location");
                //真实姓名
                String truename = rs.getString("truename");
                //工作电话
                String telephone = rs.getString("telephone");
                

                // 现在135上的上线的jar是之判断email属性是否为空的
                if (!isEmptyString(username)) {
                	Account account = new Account();
                    account.setUsername(username);
                    account.setPassword(password);
                    account.setEmail(isEmptyString(email) ? telephone + "@jobcn.com" : email);
                    account.setGender(gender);
                    account.setImage(image);
                    account.setLocation(location);
                    account.setTruename(truename);
                    account.setTelephone(telephone);
                    Accounts.add(account);
                    System.out.println(account.getUsername()+account.getImage());
                }
                System.out.println(querySQL);
                }
        } finally {
            if (sqlserverConn != null) {
                sqlserverConn.close();
            }
        }
        return Accounts;
    }
    
    private static boolean isEmptyString(String str) {
        return str == null || str.length() < 1;
    }
    
	

    public static void main(String agrs[]) {
    	try {
			new HdwikiInit().init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    
}

