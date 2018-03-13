package se.itu.systemet.rest;

import org.json.JSONArray;
import org.json.JSONObject;
import se.itu.systemet.domain.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductParser {
  public List<Product> parse(String json) {
    List<Product> products = new ArrayList<>();
    // create a JSONArray of the json string
    JSONArray jsonArray = new JSONArray(json);

    // for each element in the json array
    for (int i = 0; i < jsonArray.length(); i++) {
      // get each object (product)
      JSONObject jsonObject = jsonArray.getJSONObject(i);

      // get every property in the json object
      String name = jsonObject.getString("name");
      double price = jsonObject.getDouble("price");
      double alcohol = jsonObject.getDouble("alcohol");
      int volume = jsonObject.getInt("volume");
      int nr = jsonObject.getInt("nr");
      String productGroup = jsonObject.getString("product_group");
      String type = "";
      String hello;
      // add the product to the list of products
      products.add(new Product.Builder()
                   .name(name)
                   .price(price)
                   .alcohol(alcohol)
                   .volume(volume)
                   .nr(nr)
                   .productGroup(productGroup)
                   .type(type)
                   .build());
    }
    return products;
  }

}
