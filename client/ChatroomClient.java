package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Client of chatroom program.
 * by DD JF TG
 * ver. 1.0
 */
public class ChatroomClient {

  // Socket for connecting with the server.
  private Socket serverSocket;

  // Thread for writing the users input to the servers output.
  private StreamSwapper serverReader;

  // Thread for writing the servers output to the users output.
  private StreamSwapper serverWriter;

  /**
   * Connect with the provided server details and start reading and writing to the server.
   * @param serverName Name of the server to connect to.
   * @param portNumber Port of the server to connect to.
   * @throws IOException
   */
  public ChatroomClient(String serverName, int portNumber) {

    // Try and connect to the server.
    try {
      serverSocket = new Socket(serverName, portNumber);

      // Start a thread to read output from the server.
      serverReader = new StreamSwapper(serverSocket.getInputStream(), System.out);
      serverReader.start();

      // Start a thread to write input to the server.
      serverWriter = new StreamSwapper(System.in, serverSocket.getOutputStream());
      serverWriter.start();

      // Keep the threads and socket active until we are finished.
      boolean finished = false;
      while(!finished);

      // Close the socket if we ever finish.
      serverSocket.close();

    } catch (UnknownHostException e) {
      System.err.println("Can't find the server.");
      System.exit(-1);

    } catch (IOException e) {
      System.err.println("Couldn't get I/O from the server.");
      System.exit(-1);
    }

  }

  private static void print_help(String program_name)
  {
    System.out.println(program_name + "should be used following by [Host address] (port number, default 8888)");
  }
  /**
   * Executable method for running a client application.
   * @param args Name of the server and its operating port.
   */
  public static void main(String[] args) {
    //@SuppressWarnings("unused")
    int port;
    if (args.length == 0 || args[0].equals("help") == true) 
    {
      print_help("SEN632 chat client");
      return;
    }
    //else if (args[0] == "help") print_help("Chat Client");
    if (args.length == 1) port = 8888;
      else port = Integer.valueOf(args[1]);
    Client clientApplication = new Client(args[0], port);
  }
}
