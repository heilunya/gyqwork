package gyq.project;

public class Test {
	

	public static void main(String args[]){
		System.out.println("********************************************");
		System.out.println(System.getProperty("java.library.path"));
		System.out.println("********************************************");
		outPutMessage();
		System.out.println("********************************************");
		System.out.println(Math.random());
//		String type="mx";
//		String[] arr = new String[]{type};
//		System.out.println(arr.length);
//		System.out.println(arr[arr.length-1]);
		
		
		
	}
	
	public static void outPutMessage(){
		String str = "123,456 .89,,iop,  ,,iwow;iwo";
		String array[] = str.split("[,; ]+");
		for(int i=0;i<array.length;i++){
			System.out.println(array[i]);
		}
		System.out.println("********************************************");
		String str2 = "10029 gyq.mail.com";
		System.out.println(str2.replaceFirst(".* ", ""));
		System.out.println(str2.replaceFirst(".*", ""));
		System.out.println(str2.replaceFirst(".? ", ""));
	}
}
/*
/usr/lib/jvm/java-6-openjdk-amd64/jre/lib/amd64/server:
/usr/lib/jvm/java-6-openjdk-amd64/jre/lib/amd64:
/usr/lib/jvm/java-6-openjdk-amd64/jre/../lib/amd64:
/usr/java/packages/lib/amd64:/usr/lib/x86_64-linux-gnu/jni:
/lib/x86_64-linux-gnu:
/usr/lib/x86_64-linux-gnu:
/usr/lib/jni:
/lib:
/usr/lib
*/

