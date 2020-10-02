import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;

public class CountRMIClient
{
	public static void main(String args[])
	{
		System.setSecurityManager(new RMISecurityManager());

		try{
			CountRMI time= (CountRMI)Naming.lookup("rmi://"+args[0]+"/"+"myCountRMI");
			long time_sent=System.currentTimeMillis();
			long time_server=time.get_time();
			long time_received=System.currentTimeMillis();
			long coorect=time_server+(time_received-time_sent)/2;
			System.out.println("Current time:"+time_received);
			System.out.println("According to Christian:"+coorect);
			System.out.println("Tround= "+(time_received-time_sent));
		}catch(Exception e){
			System.err.println("System Exception"+e);
		}
	}
}