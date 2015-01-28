package gyq.ssh.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.trilead.ssh2.ChannelCondition;
import com.trilead.ssh2.Connection;
import com.trilead.ssh2.Session;

public class SSHConnectAndSendCommand {

	private static final int TIME_OUT = 1000 * 5 * 60; 
	public static void main(String args[]){
		Connection conn = null;
		boolean flag = false;
		String ip = "";
		String username = "";
		String password = "";
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
				System.out.println("flag is "+flag);
			}else{
				flag = true;
			}
			
			Session session = conn.openSession();
			session.execCommand("ls -l"); 
			System.out.println("*************************1");
			InputStream in = session.getStdout();
			OutputStream out = session.getStdin();
			System.out.println("*************************2");
			session.waitForCondition(ChannelCondition.EXIT_STATUS, TIME_OUT); 
			byte[] buffer = new byte[1024];
			//int readcount = in.read(buffer);
			//String result = new String(buffer,0,readcount);
			System.out.println("*************************3");
			int ret = session.getExitStatus(); 
//			if(result!=null && !result.equals("")){
//				System.out.println(result);
//			}else{
//				System.out.println("result is null");
//			}
			//String command = "list";
			System.out.println("*************************4");
			//out.write(command.getBytes());
			//System.out.println("*************************5");
			//out.flush();
			//System.out.println("*************************6");
			byte[] buffer2 = new byte[1024];
			int readcount = -1;
			while((readcount=in.read(buffer2))!=-1){
				String result = new String(buffer2,0,readcount);
				if(result!=null && !result.equals("")){
					System.out.println(result);
				}else{
					System.out.println("result is null");
				}
			}
			System.out.println("*************************7");
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
