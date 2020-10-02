import java.net.*;
import java.io.*;
import java.util.*;

public class client1{
  public static void main(String args[]){
    //args[0] gives message contents and args[1] gives server hostname
    DatagramSocket aSocket = null;
    Scanner X = new Scanner(System.in);
    try{
    	aSocket = new DatagramSocket(6700);//other is 6701
    	ReceiveThread1 receive = new ReceiveThread1(aSocket);
    	String text;
    	byte [] m=new byte[1024];
        final int client2_port = 6800;
        InetAddress aHost = InetAddress.getByName("localhost"); 
    while(true){
    	text=X.nextLine();
    	m=text.getBytes();
        DatagramPacket request = new DatagramPacket(m, m.length, aHost, client2_port); 
      	aSocket.send(request);
     }
      }catch (UnknownHostException e){System.out.println("Error host: " + e.getMessage()); // for InetAddress exception
      } catch (SocketException e){
			System.out.println(" Error Socket: " + e.getMessage());
       } catch (IOException e){System.out.println("Error IO: " + e.getMessage());
       } finally { if(aSocket != null) aSocket.close();}
    }
}

 class ReceiveThread1 extends Thread{
      byte [] m = new byte[1024];
      DatagramSocket aSocket=null;
     	public ReceiveThread1(DatagramSocket a){
     		aSocket=a;
      		this.start();
      	}
      public void run(){
      	try{
      	while(true){
      		byte m []=new byte[1024];
      		DatagramPacket rec=new DatagramPacket(m,m.length);
      		aSocket.receive(rec);
      		System.out.println("Client2:"+new String(rec.getData()));
      	}      
      }catch (UnknownHostException e){System.out.println("Error host: " + e.getMessage()); // for InetAddress exception
      } catch (SocketException e){
			System.out.println(" Error Socket: " + e.getMessage());
       } catch (IOException e){System.out.println("Error IO: " + e.getMessage());
       } finally { if(aSocket != null) aSocket.close();

      	}
      }
}