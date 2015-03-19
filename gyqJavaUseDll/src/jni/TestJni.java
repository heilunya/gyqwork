package jni;

public class TestJni

{

       public native void display(String strName);

       static {

              System.loadLibrary("Test");

       }

       public static void main(String[] args){

    	   
          new TestJni().display("Christmas");

       }
}