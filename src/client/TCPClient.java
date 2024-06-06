package src.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

import src.shared.Request;

/**
 * This class will initiate a TCP connection to the server and pass an input string
 * to the server
 */
public class TCPClient extends AbstractClient {

  private Socket socket = null;
  private ObjectOutputStream oos;
  private BufferedReader reader;

  /**
   * Constructs a TCPClient with the specified hostname and port.
   *
   * @param hostname the hostname of the server
   * @param port the port number of the server
   * @throws IOException if an I/O error occurs while connecting to the server
   */
  public TCPClient(String hostname, int port) throws IOException {
    this.socket = new Socket(hostname, port);
    oos = new ObjectOutputStream(socket.getOutputStream());
    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    socket.setSoTimeout(3000);
  }

  @Override
  protected void close() throws IOException {
    socket.close();
  }

  @Override
  protected String receiveServerResponse() throws IOException {
    return reader.readLine();
  }

  @Override
  protected void sendClientRequest(Request request) throws IOException {
    oos.writeObject(request); // Sending the message with newline
  }

  @Override
  protected String getLoggerPrefix() {
    return "TCP:";
  }

}