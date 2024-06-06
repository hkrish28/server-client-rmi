package src.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;


/**
 * TCPHandler is a concrete implementation of AbstractHandler for handling TCP connections.
 * It listens for incoming TCP connections, processes requests, and sends responses.
 */
public class TCPHandler extends AbstractHandler {

  private int serverPort;
  private Socket clientSocket;
  private ServerSocket socket;

  /**
   * Constructs a TCPHandler with the specified TCP port.
   *
   * @param tcpPort the port number on which the server will listen for TCP connections
   */
  public TCPHandler(int tcpPort) {
    super(tcpPort);
    serverPort = tcpPort;
  }

  @Override
  protected void createSocket(int portNumber) throws IOException {
    socket = new ServerSocket(serverPort);
  }

  @Override
  protected void terminateHandler() throws IOException {
    socket.close();
    socket = null;
  }


  @Override
  protected void sendResponse(String result) throws IOException {
    OutputStream output = clientSocket.getOutputStream();
    PrintWriter writer = new PrintWriter(output, true);
    writer.println(result);
  }

  @Override
  protected InputStream getInputStream() throws IOException {
    clientSocket = socket.accept();
    return clientSocket.getInputStream();
  }


  @Override
  protected String getLoggerPrefix() {
    return "TCP Server:";
  }

  @Override
  protected void getClientDetails(Consumer<String> logger) {
    logger.accept(clientSocket.getInetAddress() + ":" + clientSocket.getPort() );
  }

}
