package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import server.chatbot.Chatbot;

public class ConnectionThread extends Thread {

  // Connections
  private String connectionName;
  private static List<ConnectionThread> allConnections = new LinkedList<ConnectionThread>();

  // Socket information
  private Socket clientSocket;
  private ServerSocket serverSocket;

  // Stream information
  private PrintStream socketPrintStream = null;
  private BufferedReader socketInputStream = null;

  /**
   * Creates a new thread associated with a provided socket.
   * @param s
   */
  public ConnectionThread(ServerSocket server, Socket client) {
    super("ConnectionThread");

    this.connectionName = String.valueOf(allConnections.size());
    allConnections.add(this);

    this.serverSocket = server;
    this.clientSocket = client;
  }

  /**
   * Get the connections output stream and greet them.
   */
  public void run() {

    // Get the streams the connection is going to use to read and write from/to the client.
    try {
      socketPrintStream = new PrintStream(clientSocket.getOutputStream());
      socketInputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    } catch (IOException e) {
      e.printStackTrace();
    }

    // Inform all the other connections the connection joined.
    messageAllSockets(" ", "joined this room.");

    // Relay output from from this connection to all the other connections.
    String inputLine;
      try {
      while ((inputLine = socketInputStream.readLine()) != null) {
        if( inputLine.startsWith("@") ) {
          System.out.println(connectionName + "use bot: " + inputLine);
          messageAllSockets(": ", Chatbot.singleStr(this, inputLine));
        }
        else messageAllSockets(": ", inputLine);
      }
    } catch (IOException e) { }
  }

  /**
   * Inform the connection about who sent the message and what the message is.
   * Can be used by the server to send a specific message to the client.
   * @param message Message to send.
   */
  public void messageSocket(String name, String connector, String message) {
    this.socketPrintStream.println(name + connector + message);
  }

  /**
   * Broadcast a message to all of the clients.
   * @param message Message to send.
   */
  public void messageAllSockets(String connector, String message) {
    for(ConnectionThread ct : allConnections) {
      ct.messageSocket("User " + this.connectionName, connector, message);
    }
  }

}
