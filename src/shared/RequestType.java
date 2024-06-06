package src.shared;

/**
 * RequestType represents the types of requests that can be created.
 * It includes PUT, GET, DELETE, and UNKNOWN request types.
 */
public enum RequestType {
  PUT("put"),
  GET("get"),
  DELETE("delete"),
  UNKNOWN("unknown");

  private String type;

  /**
   * Constructs a RequestType enum with the specified type string.
   *
   * @param type the string representation of the request type
   */
  RequestType(String type) {
    this.type = type;
  }

  /**
   * Converts a string representation of a request type into the corresponding RequestType enum.
   * If the string does not match any known request types, it returns UNKNOWN.
   *
   * @param requestType the string representation of the request type
   * @return the corresponding RequestType enum, or UNKNOWN if not found
   */
  public static RequestType fromType(String requestType) {
    for (RequestType type : values()) {
      if (type.getType().equalsIgnoreCase(requestType)) {
        return type;
      }
    }
    return UNKNOWN;
  }

  /**
   * Gets the string representation of the request type.
   *
   * @return the string representation of the request type
   */
  public String getType() {
    return type;
  }
}
