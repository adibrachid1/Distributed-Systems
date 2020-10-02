import java.rmi.*;
import java.rmi.server.*;
import java.util.concurrent.TimeUnit;

public class CountRMIServer
{
	public static void main(String args[])
	{
		System.setSecurityManager(new RMISecurityManager());
		try
		{
			CountRMIImpl myCount = new CountRMIImpl("myCountRMI");
			System.out.println("Christian RMI Server ready");
		}catch(Exception e){
			System.out.println("Exception: "+e.getMessage());
		}
	}
}

//java RMI suitable to write a chatting application like the once we wrote in udp tcp? no