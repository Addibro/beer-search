package se.itu.systemet.rest;

import java.net.URI;
// import org.apache.http.HttpEntity;
// import org.apache.http.HttpResponse;
// import org.apache.http.client.HttpClient;
// import org.apache.http.client.methods.HttpGet;
// import org.apache.http.client.utils.URIBuilder;
// import org.apache.http.impl.client.HttpClients;
// import org.apache.http.util.EntityUtils;

import java.util.List;

import se.itu.systemet.domain.Product;
import se.itu.systemet.rest.Query;

public class HttpApiAccess implements ApiAccess {

  private final String END_POINT = "https://api-extern.systembolaget.se/product/v1/product/search?";

  public List<Product> fetch(Query query) {
    Client client = new Client(END_POINT + query.toQueryString());
    return HttpResponseProductParser.parse(client.getJson());
  }
}