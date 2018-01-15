package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.crypto.SecretKey;

// The pakage use as crypt lib.
import client.textcrypt.TextCrypt;

/**
 * Swapping an input stream and an output stream.
 * @author DD JF TG, base on works of Jack Galilee
 * @version 1.2
 */
public class StreamSwapper extends Thread {

  // Stream we are going to read into the output stream.
  private InputStream inputStream;

  // Stream we are going to write the input stream into.
  private OutputStream outputStream;

  // Show the way of IN or OUT.
  private String whichWay;

  // The SecrectKey this process used.
  private SecretKey useKey;

  /**
   * Constructs the stream swapper.
   * @param input Stream to get data from.
   * @param output Stream to send data to.
   * @param oneWay shows the way from or to this computer.
   * @throws IOException
   */
  public StreamSwapper(InputStream input, OutputStream output, String oneWay) throws IOException {
    this.inputStream = input;
    this.outputStream = output;
    this.whichWay = oneWay;
    this.useKey = TextCrypt.getKey("DTJ^#@");
  }

  /**
   * Buffers the input stream and writes it to the output stream.
   */
  public void run() {

    // Create a reader for the input stream.
    BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream));

    // Create a writer for the output stream.
    PrintWriter outputWriter = new PrintWriter(outputStream, true);

    // Buffer each new line from the reader and write it to the output stream.
    try {
      String lineBuffer;
      while ((lineBuffer = inputReader.readLine()) != null) {
        
        // Find out the way, if this is from this computer, encrypt the message without the bot symbol (@)
        if (this.whichWay.equals("OUT")) {
          
          // Skip encrypt this message if the string starts with "@".
          if (!lineBuffer.startsWith("@")) {
            outputWriter.println("^_#" + TextCrypt.encrypt(lineBuffer, useKey));
          } else {
            outputWriter.println(lineBuffer);
          }
        }
        
        // If the message is ready to show on this computer, find out it is encrypted (with "^_#") or not.
        else {
          int startCifer;
          
          // Skip decrypt this message if the string does not start with "^_#".
          if ((startCifer = lineBuffer.indexOf("^_#")) != -1) {
            //int startCifer = lineBuffer.indexOf;
            String startText = lineBuffer.substring(0, startCifer);
            outputWriter.println(startText + TextCrypt.decrypt(lineBuffer.substring(startCifer + 3), useKey));
            //outputWriter.println(TextCrypt.decrypt(lineBuffer.substring(lineBuffer.indexOf("^_#") + 3), useKey));
          } else 
            outputWriter.println(lineBuffer);
          }
        }
      }

      // Close the input reader and output writer when we're finished.
      inputReader.close();
      outputWriter.close();

    // Handle any exceptions with the IO data streams.
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
