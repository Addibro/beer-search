package se.itu.systemet.rest;

import se.itu.systemet.domain.Product;

import java.util.ArrayList;
import java.util.List;

public class SystemetRestApiAccess implements ApiAccess {

    private static final String END_POINT = "http://localhost:8080/search/products/all?";

    private List<Product> products;

    @Override
    public List<Product> fetch(Query query) {
        products = new ArrayList<>();
        Client client = new Client(END_POINT + query.toQueryString());
        products.addAll(new ProductParser().parse(client.getJson()));
        return products;
    }
}
