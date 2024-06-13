package client;

import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import server.Server;
import shared.Logger;

public class ClientImpl implements Client {

  private static Logger logger = new Logger(System.out);
  private Server server;

  public ClientImpl(Server server) {
    this.server = server;
  }


  @Override
  public void sendRequests(InputStream in) {
    Scanner inputScanner = new Scanner(in);
    while (inputScanner.hasNext()) {
      String inputText = inputScanner.nextLine();
      if (inputText.equalsIgnoreCase("exit")) {
        System.out.println("Exiting...");
        return;
      }
      log("Request sent to server - ");
      dispatchRequest(inputText.split(" "));
      System.out.println("Please enter your command (put, get, delete)");
    }

  }

  /**
   * Parses a command into a Request object based on the command arguments.
   *
   * @param command the array of command arguments
   */
  private void dispatchRequest(String[] command) {

    try {
      switch (command[0].toLowerCase()) {
        case "put":
          if (command.length != 3) {
            log("Invalid command. Put command requires 2 arguments");
          } else {
            logResponse(server.put(command[1], command[2]));
          }
          break;
        case "get":
          if (command.length != 2) {
            log("Invalid command. Get command require 1 argument");
          } else {
            logResponse(server.get(command[1]));
          }
          break;
        case "delete":
          if (command.length != 2) {
            log("Invalid command. Get and Delete command require 1 argument");
          } else {
            logResponse(server.delete(command[1]));
          }
          break;
        default:
          log("Invalid command provided");
      }
    } catch (RemoteException | ExecutionException | InterruptedException e) {
      log("Error encountered while performing operation on server : " + e.getMessage());
    }
  }

  private void log(String message) {
    logger.log(message);
  }

  private void logResponse(String message) {
    logger.log("Response from server : " + message);
  }
}
