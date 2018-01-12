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

  /**
   * Executable method for running a client application.
   * @param args Name of the server and its operating port.
   */
  public static void main(String[] args) {
    @SuppressWarnings("unused")
    Client clientApplication = new Client(args[0], Integer.valueOf(args[1]));
  }

}
