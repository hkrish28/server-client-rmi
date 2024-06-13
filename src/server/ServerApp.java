package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

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
    try {

      Server obj = new ServerImpl();
      Server stub = (Server) UnicastRemoteObject.exportObject(obj, 0);
      // Bind the remote object's stub in the registry
      Registry registry = LocateRegistry.getRegistry();
      registry.rebind("Store", stub);

      System.err.println("Server ready");
    } catch (Exception e) {
      System.err.println("Server exception: " + e.toString());
      e.printStackTrace();

    }
  }
}
