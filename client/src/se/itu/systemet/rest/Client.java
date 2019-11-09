package se.itu.systemet.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class Client {

  private String url;
  private HttpURLConnection connection;

  public Client(String url) {
    this.url = url;
  }

  public String getJson() {

    StringBuilder response = new StringBuilder();
    if (!isHttpOk()) {
      return "[]";
    }

    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      for (String line : reader.lines().collect(Collectors.toList())) {
        response.append(line);
      }
      reader.close();
    } catch (IOException e) {
      System.err.println("Error reading Json: " + e.getMessage());
    }
    return response.toString();
  }

  private boolean isHttpOk() {
    try {
      connection = (HttpURLConnection) new URL(url).openConnection();
      connection.setRequestMethod("GET");
      connection.setRequestProperty("Ocp-Apim-Subscription-Key", "076f48c27fec405bbc8f62586787130e");
      connection.connect();
      int code = connection.getResponseCode();
      return code == 200;
    } catch (IOException e) {
      System.err.println("Error testing http status: " + e.getMessage());
    }
    return false;
  }
}
