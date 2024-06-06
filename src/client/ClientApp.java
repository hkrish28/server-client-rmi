package src.client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * ClientApp is the main entry point for the client application.
 * It initializes a client based on the protocol specified (TCP or UDP)
 * and sends requests to the server using input from a file or standard input.
 */
public class ClientApp {

  /**
   * The main method to start the client application.
   *
   * @param args the command line arguments, expecting three arguments:
   *             <hostname> <port-number> <protocol>
   * @throws FileNotFoundException if the input file is not found
   */
  public static void main(String[] args) throws FileNotFoundException {
    if (args.length != 3
            || !(args[2].equalsIgnoreCase("udp")
            || args[2].equalsIgnoreCase("tcp"))) {
      System.out.println("Error in input");
      return;
    }
    try {
      Client client = getClient(args);

      InputStream in = new FileInputStream("ClientInput.txt");
      client.sendRequests(in);

      client = getClient(args);
      in = System.in;
      client.sendRequests(in);
    } catch (IOException e) {
      System.out.println("Client not created - " + e.getMessage());
    }
  }

    /**
     * Creates and returns a client instance based on the protocol specified in the arguments.
     *
     * @param args the command line arguments
     * @return a Client instance (TCPClient or UDPClient)
     */
    private static Client getClient (String[]args) throws IOException {
      Client client;
      if (args[2].equalsIgnoreCase("tcp")) {
        client = new TCPClient(args[0], Integer.parseInt(args[1]));
      } else {
        client = new UDPClient(args[0], Integer.parseInt(args[1]));
      }

      return client;
    }
  }
