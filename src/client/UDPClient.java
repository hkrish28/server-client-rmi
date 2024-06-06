package src.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import src.shared.Request;

/**
 * UDPClient initiates a UDP connection to the server and sends requests.
 */
public class UDPClient extends AbstractClient {

  DatagramSocket socket = null;
  private String hostname;
  private int port;
  private byte[] buffer = new byte[256];

  /**
   * Constructs a UDPClient with the specified hostname and port.
   *
   * @param hostname the hostname of the server
   * @param port the port number of the server
   * @throws SocketException if an error occurs while creating the DatagramSocket
   */
  public UDPClient(String hostname, int port) throws SocketException {
    this.socket = new DatagramSocket();
    socket.setSoTimeout(3000);
    this.hostname = hostname;
    this.port = port;
  }

  @Override
  protected void close() throws IOException {
    this.socket.close();
  }

  @Override
  protected String receiveServerResponse() throws IOException {
    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
    socket.receive(packet);
    return  new String(packet.getData(), 0, packet.getLength());
  }

  @Override
  protected void sendClientRequest(Request request) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(baos);
    oos.writeObject(request);
    oos.flush();
    byte[] data = baos.toByteArray();

    DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName(hostname), port);

    socket.send(packet);
  }

  @Override
  protected String getLoggerPrefix() {
    return "UDP:";
  }

}
