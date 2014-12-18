package com.hdwiki;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Calendar;
import java.util.Date;



public class HdwikiUpdate {

	//private static Logger log = Logger.getLogger(HdwikiUpdate.class.getName());
	
	//将数据同步到到wiki_user表中
    public void update(String lastUpdateTime,String currentTime) throws Exception {
        List<Account> accounts = getBOSSAccountByUpdateTime(lastUpdateTime,currentTime);
    	if (accounts != null && accounts.size() != 0) {
            Connection mysqlconn = DatabaseConnection.getMySqlConn();
            boolean flag ;
            ResultSet rs ;
            String sql2 ="update wiki_user set username=?,password=?,email=?,gender=?,image=?,location=?,truename=?,telephone=? where uid=?";
            String sql = "insert into wiki_user(username,password,email,gender,image,postcode,location,regip"
            				+",lastip,groupid,truename,telephone,qq,msn,authstr) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            String sql3 = null;
            int uid ;
            PreparedStatement pst =  null;
            if(accounts!=null && accounts.size()>0){
            	for(Iterator<Account> its = accounts.iterator();its.hasNext();){
            		Account account = its.next();
            		sql3 = "select uid from wiki_user where username=?";
            		pst = mysqlconn.prepareStatement(sql3);
            		pst.setString(1, account.getUsername());
            		System.out.println(account.getUsername());
            		rs = pst.executeQuery();
            		if(rs.next()){
            			uid=rs.getInt("uid");
            			System.out.println("uid="+uid);
            			pst = mysqlconn.prepareStatement(sql2);
            			pst.setString(1, account.getUsername());
                		pst.setString(2, account.getPassword());
                		pst.setString(3, account.getEmail());
                		pst.setString(4, account.getGender());
                		pst.setString(5, account.getImage());
                		pst.setString(6, account.getLocation());
                		pst.setString(7, account.getTruename());
                		pst.setString(8, account.getTelephone());
                		pst.setInt(9, uid);
                		System.out.println("do sql2 update");
            		}else{
            			
            			pst = mysqlconn.prepareStatement(sql);
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
                		System.out.println("do sql insert");
            		}
            		flag = pst.execute();
                	
            		//System.out.println(flag);
            	}
            }
            if(pst != null){
            	pst.close();
            }
            if(mysqlconn != null){
            	mysqlconn.close();
            }
        }else{
        	System.out.println("do nothing!");
        }
    }
	/*
	 * 获取指定时间范围内更新的员工信息。
	 */
    private List<Account> getBOSSAccountByUpdateTime(String lastUpdateTime,String currentTime) throws Exception {
        Connection sqlserverConn = null;
        List<Account> Accounts = new ArrayList<Account>();
        try {
            sqlserverConn = DatabaseConnection.getSqlServerConn();
            //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
            		+ "     and p.updateDate between '" + lastUpdateTime + "' and '" + currentTime + "'"
            		+ " order by p.ID";
            //System.out.println(querySQL);
            //log.info(querySQL);
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
                    //System.out.println(account.getUsername()+account.getImage());
                }
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
    
    
    public void test() throws Exception{
    	List<Account> accounts = new HdwikiInit().getAllBOSSAccount();
        if (accounts != null && accounts.size() != 0) {
            Connection mysqlconn = DatabaseConnection.getMySqlConn();
            boolean flag ;
            ResultSet rs ;
            String sql = null;
            PreparedStatement pst =  null;
            if(accounts!=null && accounts.size()>0){
            	for(Iterator<Account> its = accounts.iterator();its.hasNext();){
            		Account account = its.next();
            		sql = "select uid from wiki_user where username='"+account.getUsername()+"'";
            		pst = mysqlconn.prepareStatement(sql);
            		System.out.println(sql);
            		rs = pst.executeQuery();
            		while(rs.next()){
            			System.out.println(rs.getInt("uid"));
            		}
            		//System.out.println(flag);
            	}
            }
            if(pst != null){
            	pst.close();
            }
            if(mysqlconn != null){
            	mysqlconn.close();
            }
        }
    }
    

    public static void main(String agrs[]) throws Exception{
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    	Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.MINUTE, -31);
    	String lastUpdateTime = sdf.format(cal.getTime());
    	String currentTime = sdf.format(new Date());
    	HdwikiUpdate hdwikiUpdate = new HdwikiUpdate();
    	lastUpdateTime="2014-10-30 16:28:21.193";
    	System.out.println("lastUpdateTime="+lastUpdateTime);
    	System.out.println("currentTime="+currentTime);
    	hdwikiUpdate.update(lastUpdateTime, currentTime);
    	//hdwikiUpdate.getBOSSAccountByUpdateTime(lastUpdateTime, currentTime);
    }

}
