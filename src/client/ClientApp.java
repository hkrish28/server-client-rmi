package client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import server.Server;

public class ClientApp {

  public static void main(String[] args) {


    String host = (args.length < 1) ? null : args[0];
    Registry registry = null;
    try {
      registry = LocateRegistry.getRegistry(host);
      Server stub = (Server) registry.lookup("Store");
      Client client = new ClientImpl(stub);
      InputStream in = null;
      try {
        in = new FileInputStream("ClientInput.txt");
        client.sendRequests(in);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      in = System.in;
      client.sendRequests(in);

    } catch (RemoteException | NotBoundException e) {
      throw new RuntimeException(e);
    }
  }
}
