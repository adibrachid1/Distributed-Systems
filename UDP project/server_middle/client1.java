import java.net.*;
import java.io.*;
import java.util.*;
public class client1{
  public static void main(String args[]){
    DatagramSocket aSocket = null;
    String user2="6701";
    Scanner X = new Scanner(System.in);
    try{
      DatagramPacket request=null;
      DatagramPacket rec=null;
      String text;
      String tmp;
      ReceiveThread1 receive =null;
      byte [] m=new byte[1024];
      byte [] msg_rec=new byte[1024];
      final int server_port = 6700;
      InetAddress aHost = InetAddress.getByName("localhost");    
      while(true){
        text=X.nextLine();
        m=text.getBytes();
        if(text.equals("connect")){
          aSocket = new DatagramSocket();//other is 6701 per default each time connect -> new socket..
          request = new DatagramPacket(m, m.length, aHost, server_port); 
          aSocket.send(request);
          m=new byte[1024];
          rec=new DatagramPacket(m,m.length);
          aSocket.receive(rec);//receive connected users
          tmp=new String(rec.getData()).replaceAll("-", "\n");
          System.out.println(tmp);
          receive = new ReceiveThread1(aSocket);//start receiving messages from others
        }
        else if(text.equals("connectto")){
        // which user to connect to
          text=X.next();//which user to connect to
          m=text.getBytes();
          request = new DatagramPacket(m, m.length, aHost, server_port); 
          aSocket.send(request);//send the id of the client2
        //send to own to remove the other waiting buffer because it was waiting first..
          request = new DatagramPacket(m, m.length, aHost, aSocket.getLocalPort()); 
          aSocket.send(request);

          msg_rec=new byte[1024];
          rec = new DatagramPacket(msg_rec,msg_rec.length);
          aSocket.receive(rec);//receive the new port of the client2
          if(Integer.parseInt((new String (rec.getData())).trim())!=-1)//if not connected do nothing
          user2=new String(rec.getData());
          //System.out.println("User s 2 port: "+new String(rec.getData()));
        }
        else if(text.equals("exit")){
          request = new DatagramPacket(m, m.length, aHost, server_port); 
          aSocket.send(request);//send exit to remove from database on server
          m=new byte[1024];
          rec=new DatagramPacket(m,m.length);
          aSocket.receive(rec);//receive connected users
          tmp=new String(rec.getData()).replaceAll("-", "\n");
          System.out.println(tmp);
          user2="6701";
        }
        else{
          request = new DatagramPacket(m, m.length, aHost, Integer.parseInt(user2.trim())); 
          aSocket.send(request);//send message to other port
        }
      }}
      catch (UnknownHostException e){System.out.println("Error host: " + e.getMessage()); // for InetAddress exception
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
    boolean cond=true;
    while(cond){
      m=new byte[1024];
      DatagramPacket rec=new DatagramPacket(m,m.length);
      aSocket.receive(rec);
      if(new String(rec.getData()).trim().equals("stop_task")){//if exit
        cond=false;
      }
      else if(((new String(rec.getData()).trim()).length()>=14)&&(new String(rec.getData()).trim()).substring(0,15).equals("Connected users")){
        String tmp=new String(rec.getData()).replaceAll("-", "\n");
        System.out.println(tmp);
      }
      else if(!(new String(rec.getData()).trim().equals(""))){//if empty.. to jump to another buffer
        System.out.println("Client #"+rec.getPort()+" : "+new String(rec.getData()));//receive message from client2
      }
    }      
      }catch (UnknownHostException e){System.out.println("Error host: " + e.getMessage()); // for InetAddress exception
    } catch (SocketException e){
     System.out.println(" Error Socket: " + e.getMessage());
   } catch (IOException e){System.out.println("Error IO: " + e.getMessage());
 } finally { if(aSocket != null) {
   aSocket.close();//close the socket if condition is false <=> client typed exit
 }
}
}}