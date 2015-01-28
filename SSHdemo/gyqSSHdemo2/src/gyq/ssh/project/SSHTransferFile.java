package gyq.ssh.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.trilead.ssh2.ChannelCondition;
import com.trilead.ssh2.Connection;
import com.trilead.ssh2.Session;

public class SSHTransferFile {

	private static final int TIME_OUT = 1000 * 5 * 60; 
	private static Session session = null;
	private static Connection conn = null;
	public static void main(String args[]) throws Exception{
		boolean flag = false;
		String ip = "";
		String username = "";
		String password = "";
		int execState = -1;
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("sshserver.conf");
		InputStreamReader isr = new InputStreamReader(is); 
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		try {
			while((line=br.readLine())!=null){
				if(line.startsWith("server")){
					ip=line.substring(7).trim();
				}else if(line.startsWith("username")){
					username=line.substring(9).trim();
				}else if(line.startsWith("password")){
					password=line.substring(9).trim();
				}
			}
			if(conn == null){
				conn = new Connection(ip);
				conn.connect();
				flag = conn.authenticateWithPassword(username, password);
			}
			if(flag==true){
				session = conn.openSession();
				InputStream in = session.getStdout();
				//OutputStream out = session.getStdin();
				String path = new File("").getCanonicalPath();
				String content = "'zzzz sewe'";
				execState = execCommand("sh "+path+"/copyMsgToFile.sh "+content);
				if(execState!=0){
					throw new Exception("执行命令出错。");
				}
				String responseMsg = getResponse(in);
				if(responseMsg!=null && !responseMsg.equals("")){
					System.out.println(responseMsg);
				}else{
					System.out.println("responseMsg is null");
				}
				Closeconnet();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static int execCommand(String command){
		int runstate = -1;
		try {
			session.execCommand(command);
			session.waitForCondition(ChannelCondition.EXIT_STATUS, TIME_OUT); 
			runstate = session.getExitStatus(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return runstate;
	}
	
	public static String getResponse(InputStream in){
		byte[] buffer = new byte[1024];
		int readcount = -1;
		StringBuffer strbuf = new StringBuffer();
		try {
			while((readcount=in.read(buffer))!=-1){
				String result = new String(buffer,0,readcount);
				strbuf.append(result);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strbuf.toString();
	}
	
	public static void Closeconnet(){
		session.close();
		conn.close();
	}
	
	public static void sendMsg(OutputStream out,String msg){
		try {
			out.write(msg.getBytes());
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
