package se.itu.systemet.rest;

import javax.swing.JTextField;

/**
 * Represents a Key-Value pair of a Request, typically the constraints for a
 * request.
 */
public class Param {
  private final String key;
  private final String value;

  /**
   * Creates a new Param with the specified key and value.
   * 
   * @param key   The key for this Param
   * @param value The value for this Param
   */
  public Param(JTextField field) {
    key = field.getName();
    value = field.getText();
  }

  /**
   * Returns the key for this Param, as a String
   * 
   * @return This Param's key, as a String
   */
  public String key() {
    return key;
  }

  /**
   * Returns the value for this Param, as a String
   * 
   * @return This Param's value, as a String
   */
  public String value() {
    return value;
  }

  /**
   * Returns this Param as a String on the format
   * 
   * <pre>
   * somekey = somevalue
   * </pre>
   * 
   * @return This Param as a String
   */
  public String toString() {
    return key + "=" + value;
  }
}
