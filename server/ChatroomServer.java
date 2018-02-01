package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import server.chatbot.Chatbot;

/**
 * Simple Java chatroom server.
 * by DD JF TG, base on works of J. Galilee
 * ver. 1.2
 */
public class ChatroomServer {

  // Socket used to create connections.
  private ServerSocket serverSocket;
  private List<ConnectionThread> serverConnections;
  private static String ProgramVer = "SEN632 project by DD JS TG Ver. 1.2 Loading module: ";
  private static String hostIP = "0.0.0.0";
  
  /**
   * Creates a socket to accept connections on the declared port.
   * Listens for connections, creating separate threads for each connection.
   * @param portNumber Port to accept connections on.
   */
  public ChatroomServer(Integer portNumber) {

    // Establish the server on a designated port and provide information about it.
    try {
      serverSocket = new ServerSocket(portNumber);
      serverConnections = new ArrayList<ConnectionThread>();
      printEnvironment();

    } catch (IOException e) {
      System.out.println("Server could not start on " + String.valueOf(portNumber));
      System.exit(-1);
    }

    // Start listening for connections on that port.
    boolean finishedListening = false;
    while(!finishedListening) {

      // Wait for connections and thread if successful connection made.
      try {
        Socket connection = serverSocket.accept();
        if(connection.isConnected()) {
          ConnectionThread connectionThread = new ConnectionThread(serverSocket, connection);
          connectionThread.start();
          serverConnections.add(connectionThread);
        }

      } catch (Exception e) {
        System.out.println("Accept failed: " + String.valueOf(portNumber));
        System.exit(-1);
      }

    }

  }

  /**
   * Display information about the server.
   */
  private void printEnvironment() {
    try{
      Properties props=System.getProperties();
      InetAddress addr = InetAddress.getLocalHost();
      
      hostIP = addr.getHostAddress().toString();   
 
      System.out.println(" Java ver. " + props.getProperty("java.version") 
        + " by " + props.getProperty("java.vendor") + " with VM: " 
        + props.getProperty("java.vm.name"));
      String status = "Running "
        + hostIP
        + " on port " + String.valueOf(serverSocket.getLocalPort());
      System.out.println(status);
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
  
   /**
   * Executable method for running a server application.
   * @param args List of arguments to start the server.
   */
  public static void main(String[] args) {
    int port;
    
    // Print out the welcome message
    System.out.print(ProgramVer);
    Chatbot cb = new Chatbot();
    System.out.println(".");
    
    // If no args, program will use 8888 as port to serve
    if (args.length == 0) port = 8888;
      else port = Integer.valueOf(args[0]);
    ChatroomServer server = new ChatroomServer(port);
  }

}
