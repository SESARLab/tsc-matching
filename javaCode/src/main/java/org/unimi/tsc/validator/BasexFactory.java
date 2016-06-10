package org.unimi.tsc.validator;

public class BasexFactory {
			private static BaseXOntologyManager bx=null; 
			public static BaseXOntologyManager getBasex(){
				//if(bx==null){
					bx=new BaseXOntologyManager(BasexFactory.getHost(), 1984, "admin", "admin", "mechanism");
					//a962e5.ngrok.com
					//bx=new BaseXOntologyManager("0.tcp.ngrok.io", 48106, "admin", "admin", "mechanism");
					//bx=new BaseXOntologyManager("172.20.21.95", 1984, "admin", "admin", "mechanism");
				//}
					return bx;
			}
			 public static void main( String[] args )
			    {
				System.out.println("Looking for m");
				System.out.println(TocFactory.randomMechanism());
				 
			    }
			public static String getHost() {
				// TODO Auto-generated method stub
				//return "192.168.1.74";
				return "localhost";
				//return "172.25.27.17";
			}
}
