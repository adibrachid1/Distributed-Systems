import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;

public class CountRMIClient
{
	public static void main(String args[])
	{
		System.setSecurityManager(new RMISecurityManager());

		try{
			CountRMI myCount= (CountRMI)Naming.lookup("rmi://"+args[0]+"/"+"myCountRMI");
			System.out.println("Setting sum to 0");
			myCount.sum(Integer.parseInt(args[1]),Integer.parseInt(args[2]));

			System.out.println("Sum = "+myCount.sum());
		}catch(Exception e){
			System.err.println("System Exception"+e);
		}
	}
}