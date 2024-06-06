package src.server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.function.Consumer;

/**
 * UDPHandler is a concrete implementation of AbstractHandler for handling UDP connections.
 * It listens for incoming UDP packets, processes requests, and sends responses.
 */
public class UDPHandler extends AbstractHandler{
  private DatagramSocket socket;

  private byte[] buffer = new byte[256];

  private InetAddress clientAddress;
  private int clientPort;

  /**
   * Constructs a UDPHandler with the specified UDP port.
   *
   * @param udpPort the port number on which the server will listen for UDP packets
   */
  public UDPHandler(int udpPort) {
    super(udpPort);
  }

  @Override
  protected void terminateHandler() {
    socket.close();
    socket = null;
  }

  @Override
  protected void createSocket(int serverPort) throws IOException {
    this.socket = new DatagramSocket(serverPort);
  }

  @Override
  protected void sendResponse(String result) throws IOException {
    byte[] responseData =  result.getBytes();
    DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, clientAddress, clientPort);
    socket.send(responsePacket);
  }

  @Override
  protected InputStream getInputStream() throws IOException {
    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
    socket.receive(packet);
    clientAddress = packet.getAddress();
    clientPort = packet.getPort();
    return new ByteArrayInputStream(packet.getData());
  }

  @Override
  protected String getLoggerPrefix() {
    return "UDP Server:";
  }

  @Override
  protected void getClientDetails(Consumer<String> logger) {
    logger.accept(clientAddress + ":" + clientPort );
  }

}
