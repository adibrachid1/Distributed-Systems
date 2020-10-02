public interface CountRMI extends java.rmi.Remote{
	int sum(int x1,int x2) throws java.rmi.RemoteException;
	int sum() throws java.rmi.RemoteException;
	void sum(int _cal) throws java.rmi.RemoteException;
	public int increment() throws java.rmi.RemoteException;
}