package src.client;

import java.io.IOException;
import java.io.InputStream;

import java.net.SocketTimeoutException;
import java.util.Scanner;

import src.shared.Logger;
import src.shared.Request;
import src.shared.RequestType;

/**
 * AbstractClient provides common functionality for client classes.
 * It handles sending requests to the server and receiving responses.
 */
public abstract class AbstractClient implements Client {

  private static Logger logger = new Logger(System.out);

  /**
   * Sends requests to the server and handles responses.
   *
   * @param in the input stream from which to read requests
   */
  public void sendRequests(InputStream in) {
    try {
      Scanner inputScanner = new Scanner(in);
      while (inputScanner.hasNext()) {
        String inputText = inputScanner.nextLine();
        if(inputText.equalsIgnoreCase("exit")){
          System.out.println("Exiting...");
          return;
        }
        Request request = parseRequest(inputText.split(" "));
        if (request == null) continue;
        sendClientRequest(request);
        log("Request sent to server - " + request);
        try {
          String response = receiveServerResponse();
          log("Response from server: " + response);
        } catch (SocketTimeoutException e) {
          log("No response from server");
        } finally {
          System.out.println("Please enter your command (put, get, delete)");
        }
      }
    } catch (IOException e) {
      log("Error encountered - " + e.getMessage());
    }

    try {
      close();
    } catch (IOException e) {
      log("Error encountered while closing connection - " + e.getMessage());
    }
  }

  /**
   * Closes the client connection.
   *
   * @throws IOException if an I/O error occurs while closing the connection
   */

  protected abstract void close() throws IOException;

  /**
   * Receives the server response.
   *
   * @return the response received from the server
   * @throws IOException if an I/O error occurs while receiving the response
   */
  protected abstract String receiveServerResponse() throws IOException;

  /**
   * Sends a request to the server.
   *
   * @param request the request to send to the server
   * @throws IOException if an I/O error occurs while sending the request
   */
  protected abstract void sendClientRequest(Request request) throws IOException;

  /**
   * Parses a command into a Request object based on the command arguments.
   *
   * @param command the array of command arguments
   * @return the Request object parsed from the command, or null if the command is invalid
   */
  private Request parseRequest(String[] command) {
    RequestType type = RequestType.fromType(command[0]);
    Request request = null;
    switch (type) {
      case PUT:
        if (command.length != 3) {
          log("Invalid command. Put command requires 2 arguments");
        } else {
          request = new Request(type, command[1], command[2]);
        }
        break;
      case GET:
      case DELETE:
        if (command.length != 2) {
          log("Invalid command. Get and Delete command require 1 argument");
        } else {
          request = new Request(type, command[1]);
        }
        break;
      default:
        log("Invalid command provided");
    }
    return request;
  }

  /**
   * Logs a message using the logger.
   *
   * @param message the message to log
   */
  protected void log(String message) {
    logger.log(getLoggerPrefix() + message);
  }

  /**
   * Gets the prefix to use in logging messages.
   *
   * @return the logger prefix
   */
  protected abstract String getLoggerPrefix();
}
