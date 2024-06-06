package src.shared;

import java.io.Serializable;

/**
 * Request represents a request object sent between client and server.
 * It contains information such as request type, key, and optional value.
 */
public class Request implements Serializable {

  private final RequestType type;

  private final String key;

  private final String value;

  /**
   * Constructs a Request object with the given type and key.
   * The value is set to null.
   *
   * @param type the type of the request
   * @param key  the key associated with the request
   */
  public Request(RequestType type, String key) {
    this.type = type;
    this.key = key;
    this.value = null;
  }

  /**
   * Constructs a Request object with the given type, key, and value.
   *
   * @param type  the type of the request
   * @param key   the key associated with the request
   * @param value the value associated with the request
   */
  public Request(RequestType type, String key, String value) {
    this.type = type;
    this.key = key;
    this.value = value;
  }

  /**
   * Gets the value associated with the request.
   *
   * @return the value associated with the request, or null if not present
   */
  public String getValue() {
    return value;
  }

  /**
   * Gets the type of the request.
   *
   * @return the type of the request
   */
  public RequestType getType() {
    return type;
  }

  /**
   * Gets the key associated with the request.
   *
   * @return the key associated with the request
   */
  public String getKey() {
    return key;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("(Type: " + type.getType());
    sb.append(this.key == null ? "" : ", Key: " + key);
    sb.append(this.value == null ? "" : ", Value: " + value);
    sb.append(")");
    return sb.toString();
  }
}
