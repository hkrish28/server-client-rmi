package src.server;

/**
 * ServerApp is the entry point for starting the server.
 * It initializes and starts both TCP and UDP handlers on specified ports.
 */
public class ServerApp {

  /**
   * The main method starts the server by initializing TCP and UDP handlers.
   *
   * @param args command-line arguments specifying the TCP and UDP port numbers
   */
  public static void main(String[] args) {
    if (args.length != 2) {
      System.out.println("Two port numbers must be provided");
    }
    try {
      int tcpPort = Integer.parseInt(args[0]);
      int udpPort = Integer.parseInt(args[1]);
      new TCPHandler(tcpPort).start();
      new UDPHandler(udpPort).start();
    } catch (NumberFormatException e) {
      System.out.println("Port numbers should be integers");
    }
  }
}
