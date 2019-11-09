package se.itu.systemet.rest;

import org.json.JSONArray;
import org.json.JSONObject;
import se.itu.systemet.domain.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class HttpResponseProductParser {
  public static List<Product> parse(String json) {
    JSONObject outerObject = new JSONObject(json);
    JSONArray hits = outerObject.getJSONArray("Hits");
    return IntStream.range(0, hits.length()).mapToObj(i -> hits.getJSONObject(i))
        .map(HttpResponseProductParser::toProduct).collect(Collectors.toList());
  }

  private static Product toProduct(JSONObject jsonObject) {
    // get every property in the json object
    String name = jsonObject.getString("ProductNameBold");
    double price = jsonObject.getDouble("Price");
    double alcohol = jsonObject.getDouble("AlcoholPercentage");

    int nr = jsonObject.getInt("ProductNumberShort");
    String productGroup = jsonObject.getString("Category");
    // add the product to the list of products
    return new Product.Builder().name(name).price(price).alcohol(alcohol).nr(nr).productGroup(productGroup).build();
  }

}
