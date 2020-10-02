import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class CountRMIImpl extends UnicastRemoteObject implements CountRMI{
	private int sum;
	public CountRMIImpl(String name) throws RemoteException{
		super();
		try{
			Naming.rebind(name,this);
			sum=0;
		}catch(Exception e){ System.out.println("Exception:"+e.getMessage());}
	}
	public int sum() throws RemoteException
	{
		return sum;
	}
	public int sum(int x1,int x2) throws RemoteException
	{
		sum=x1+x2;
		return sum;
	}
	public void sum(int val)throws RemoteException
	{
		sum=val;
	}
	public int increment() throws RemoteException
	{
		sum++;
		return sum;
	}
}