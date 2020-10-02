import java.net.*;
import java.io.*;
import java.util.*;
public class Server{
  static LinkedList<User> users=new LinkedList<User>();  
  static int id=0;
  public static void main(String args[]){
    DatagramSocket aSocket = null;
    final int PORT_NUMER=6700;//server's port
    try{
      byte[] buffer = new byte[1024];
      aSocket = new DatagramSocket(PORT_NUMER);
      String message_received="";
      DatagramPacket reply ;
      byte buffersendd[]=new byte[1024];
      byte buffersend[]=new byte[1024];//multiple just to separate cases
      int port_requested;
      String tmp="";
      System.out.println("Adibz Server is running");
      while(true){
        buffer = new byte[1024];
        DatagramPacket request = new DatagramPacket(buffer, buffer.length);
        aSocket.receive(request);
        message_received=new String(request.getData());//order received from client

        if(message_received.trim().equals("connect")){
          id++;
          User a=new User(id,request.getPort());
          users.add(a);
          System.out.println("User"+id+" joined - port:"+request.getPort());
          tmp=get_connected_users();
          buffersend =tmp.getBytes();
          InetAddress aHost = InetAddress.getByName("localhost");  
          Iterator<User> itr=users.iterator();  
          while(itr.hasNext()){  
            a=itr.next();
            reply = new DatagramPacket(buffersend,buffersend.length,aHost, a.get_port());
            aSocket.send(reply);
          }
          //reply to all connected users with the current connected ones
        }
        else if(message_received.trim().equals("exit")){
          System.out.println("User "+get_id(request.getPort())+" disconnected");
          remove(request.getPort());
          User a;
          tmp=get_connected_users();
          buffersend =tmp.getBytes();
          InetAddress aHost = InetAddress.getByName("localhost");  
          Iterator<User> itr=users.iterator();  
          while(itr.hasNext()){  
            a=itr.next();
            reply = new DatagramPacket(buffersend,buffersend.length,aHost, a.get_port());
            aSocket.send(reply);
          }
          //reply to all connected users with the current connected ones
          tmp="stop_task";
          buffersendd =tmp.getBytes();
          reply = new DatagramPacket(buffersendd,  buffersendd.length, request.getAddress(), request.getPort());
          aSocket.send(reply);//stop the client socket
          tmp=get_connected_users();
          buffersendd =tmp.getBytes();
          reply = new DatagramPacket(buffersendd,  buffersendd.length, request.getAddress(), request.getPort());
          aSocket.send(reply);//also return all connected users id to the client who exited



        }else{
          //get port of other
          port_requested=get_port(Integer.parseInt(new String(request.getData()).trim()));
          buffer=Integer.toString(port_requested).getBytes();
          reply = new DatagramPacket(buffer,  buffer.length, request.getAddress(), request.getPort());
          aSocket.send(reply);//send the port number of the 2nd client
        }      
        System.out.println("Connected users: ");
        show_users();
      }
    } catch (SocketException e){System.out.println("Error Socket: " + e.getMessage()); 
		 //if the port is already in use or if it is a reserved port
      } catch (IOException e) {System.out.println("Error IO: " + e.getMessage()); 	// send and receive can throw this exception
    } finally {if (aSocket != null) aSocket.close();}
  }

  public static void show_users(){
    Iterator<User> itr=users.iterator();  
    while(itr.hasNext()){  
      System.out.println(itr.next().id);  
    } 
  }
  public static String get_connected_users(){
    String r="Connected users ";
    User a;
    Iterator<User> itr=users.iterator();  
    while(itr.hasNext()){  
      a=itr.next();
      r+="-"+Integer.toString(a.id);  
    } 
    return r;
  }
  public static void remove(int p){
    Iterator<User> itr=users.iterator();  
    while(itr.hasNext()){  
      if(itr.next().get_port()==p){
        itr.remove();
      }
    } 
  }
  public static int get_port(int i){
    Iterator<User> itr=users.iterator();  
    User a;
    while(itr.hasNext()){  
      a=itr.next();
      if(a.get_id()==i){
        return a.get_port();
      }
    } 
    return -1;
  }
  public static int get_id(int p){
    Iterator<User> itr=users.iterator();  
    User a;
    while(itr.hasNext()){  
      a=itr.next();
      if(a.get_port()==p){
        return a.get_id();
      }
    } 
    return -1;
  }
}

class User{
  int id;
  int port;
  public User(int i,int p){
    id=i;
    port=p;
  }
  public int get_port(){
    return port;
  }
  public int get_id(){
    return id;
  }
}
