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
 * by DD JF TG
 * ver. 1.0
 */
public class ChatroomServer {

  // Socket used to create connections.
  private ServerSocket serverSocket;
  private List<ConnectionThread> serverConnections;
  private static String ProgramVer = "SEN632 project by DD JS TG Ver. 1.0 Loading module: ";
  
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
      printServerInformation();

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
  private void printServerInformation() {
     try{
    InetAddress addr = InetAddress.getLocalHost();
    
    String status = "Running " +
    //  String.valueOf(serverSocket.getInetAddress().getHostName()) +
      addr.getHostAddress().toString() +
      " on port " + String.valueOf(serverSocket.getLocalPort());
    System.out.println(status);
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  private static void print_env(String init_mes)
  {
    Properties props=System.getProperties();  
    System.out.print(init_mes);
    System.out.println(" Java ver. " + props.getProperty("java.version") + " by " + props.getProperty("java.vendor") + " with VM: " + props.getProperty("java.vm.name"));  
  }
  
  /**
   * Executable method for running a server application.
   * @param args List of arguments to start the server.
   */
  public static void main(String[] args) {
    int port;
    System.out.print(ProgramVer);
    Chatbot cb = new Chatbot();
    //cb.loading();
    System.out.println(".");
    print_env("Starting on");
    if (args.length == 0) port = 8888;
      else port = Integer.valueOf(args[0]);
    @SuppressWarnings("unused")
    Server server = new Server(port);
  }

}
