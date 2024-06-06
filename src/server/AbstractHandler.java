package src.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.function.Consumer;

import src.shared.Logger;
import src.shared.Request;


/**
 * AbstractHandler is an abstract class that provides a framework for handling
 * server requests.
 * Subclasses are responsible for implementing the specific details for handling
 * TCP and UDP connections.
 */
public abstract class AbstractHandler extends Thread {

  private static Logger logger = new Logger(System.out);
  private final int serverPort;
  private boolean running;
  private HashMap<String, String> store = new HashMap<>();
  private ObjectInputStream ois;

  /**
   * Constructs an AbstractHandler with the specified server port.
   *
   * @param serverPort the port number on which the server will listen for connections
   */
  protected AbstractHandler(int serverPort) {
    this.serverPort = serverPort;
    running = false;
  }

  /**
   * Logs a message with a prefix specific to the handler.
   *
   * @param message the message to be logged
   */
  protected void log(String message) {
    logger.log(getLoggerPrefix() + message);
  }

  /**
   * Performs the requested operation on the store based on the type of request.
   *
   * @param request the request to be processed
   * @return the result of the operation as a string
   */
  protected String performOperation(Request request) {
    String key = request.getKey();
    String value = request.getValue();
    switch (request.getType()) {
      case PUT:
        if (key == null || value == null) {
          return "Key and value should be valid strings";
        } else {
          store.put(key, value);
          return "Updated value for " + key + " successfully";
        }
      case GET:
        if (store.containsKey(key)) {
          return store.get(key);
        } else {
          return "Key " + key + " not present in store to get";
        }
      case DELETE:
        if (store.containsKey(key)) {
          store.remove(key);
          return "Deleted " + key + " successfully";
        } else {
          return "Key " + key + " not present in store to delete";
        }
      default:
        return "Invalid request type";
    }
  }


  /**
   * The main loop of the handler, which waits for connections, processes
   * requests, and sends responses. This method is run when the thread starts.
   */
  public void run() {
    try {
      System.out.println("Waiting for connections on port " + serverPort + " ....");
      createSocket(serverPort);
      running = true;
      ois = new ObjectInputStream(getInputStream());
      while (running) {
        try {
          Request request = getRequest();
          getClientDetails(clientDetails -> log("Received request from client - " + request.toString() + " from " + clientDetails));
          String result = performOperation(request);
          sendResponse(result);
          getClientDetails(client -> log("Sent response to client " + client + " - " + result));
        } catch (IOException | ClassNotFoundException e) {
          logError(e);
        }
      }
    } catch (IOException e) {
      logError(e);
    } finally {
      try {
        terminateHandler();
      } catch (IOException e) {
        logError(e);
      }
    }
  }

  /**
   * Terminates the handler, closing any open resources.
   *
   * @throws IOException if an I/O error occurs
   */
  protected abstract void terminateHandler() throws IOException;

  /**
   * Creates the socket on the specified port.
   *
   * @param serverPort the port number on which the server will listen for connections
   * @throws IOException if an I/O error occurs when opening the socket
   */
  protected abstract void createSocket(int serverPort) throws IOException;

  /**
   * Sends the response to the client.
   *
   * @param result the result to be sent as a response
   * @throws IOException if an I/O error occurs when sending the response
   */
  protected abstract void sendResponse(String result) throws IOException;

  /**
   * Gets the input stream from which requests are read.
   *
   * @return the input stream for reading requests
   * @throws IOException if an I/O error occurs when getting the input stream
   */
  protected abstract InputStream getInputStream() throws IOException;

  /**
   * Reads the request from the input stream.
   *
   * @return the request read from the input stream
   * @throws IOException            if an I/O error occurs when reading the request
   * @throws ClassNotFoundException if the class of the serialized object cannot be found
   */
  private Request getRequest() throws IOException, ClassNotFoundException {
    Request request;
    try {
      request = (Request) ois.readObject();
    } catch (IOException e) {
      ois = new ObjectInputStream(getInputStream());
      return getRequest();
    }
    return request;
  }

  /**
   * Gets the prefix for logging messages specific to the handler.
   *
   * @return the prefix for logging messages
   */
  protected abstract String getLoggerPrefix();

  /**
   * Retrieves client details for logging purposes.
   *
   * @param logger a consumer that logs the client details
   */
  protected abstract void getClientDetails(Consumer<String> logger);

  /**
   * Logs an error message.
   *
   * @param e the exception to be logged
   */
  private void logError(Exception e) {
    log("Error encountered - " + e.getMessage());
  }
}
