package servlets;

import java.util.List;

import se.itu.systemet.storage.JsonExporter;
import se.itu.systemet.domain.Product;

public class JsonFormatter implements Formatter {
  /**
   * Produces a String with json sturcture
   */
  @Override
  public String format(List<Product> products) {
    StringBuilder json = new StringBuilder("[\n");
    // JsonExporter is used to create a single json object
    JsonExporter jsonExp = new JsonExporter();
    int lastIndex = products.size() - 1;
    int index = 0;
    for (Product product : products) {
      product.export(jsonExp);
      json.append("  ");
      json.append(jsonExp.toJsonString());
      if (index != lastIndex) {
        json.append(","); // all but the last product gets a comma
      }
      index++;
      json.append("\n");
    }
    json.append("]\n");
    return json.toString();
  }
}
