import java.rmi.*;
import java.rmi.server.*;

public class CountRMIServer
{
	public static void main(String args[])
	{
		System.setSecurityManager(new RMISecurityManager());
		try
		{
			CountRMIImpl myCount = new CountRMIImpl("myCountRMI");
			System.out.println("CountRMI Server ready");
		}catch(Exception e){
			System.out.println("Exception: "+e.getMessage());
		}
	}
}

//java RMI suitable to write a chatting application like the once we wrote in udp tcp? no