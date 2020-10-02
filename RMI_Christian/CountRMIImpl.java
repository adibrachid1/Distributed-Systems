import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
public class CountRMIImpl extends UnicastRemoteObject implements CountRMI{
	private long time;
	public CountRMIImpl(String name) throws RemoteException{
		super();
		try{
			Naming.rebind(name,this);
			time=0;
		}catch(Exception e){ System.out.println("Exception:"+e.getMessage());}
	}

	public long get_time() throws RemoteException
	{
		return System.currentTimeMillis();
	}
}