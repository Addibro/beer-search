package se.itu.systemet.storage;

import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.sql.*;

import se.itu.systemet.domain.Product;

/**
 * <p>An implementation of ProuctLine which reads products from the database.
 * </p>
 */
public class SQLBasedProductLine implements ProductLine {

  private List<Product> products;
  private DBHelper.ColumnId columnId;

  // Prevent instantiation from outside this package
  SQLBasedProductLine() { }
  
  public List<Product> getProductsFilteredBy(Predicate<Product> predicate) {
    if (products == null) {
      readProductsFromDatabase();
    }
    return products.stream()
            .filter(predicate)
            .collect(Collectors.toList());
  }
  
  public List<Product> getAllProducts() {
    if (products == null) {
      readProductsFromDatabase();
    }
    return products;
  }

  private void readProductsFromDatabase() {
    /* You will get code to get you started here in Lab 3 */
    /* Write and use the FakeProductLine for now! */
    products = new ArrayList<>();

    ResultSet rs = DBHelper.productsResultSet();
    try {
      while (rs.next()) {
        String name = rs.getString(DBHelper.ColumnId.NAME);
        double price = rs.getDouble(DBHelper.ColumnId.PRICE);// SEK
        double alcohol = rs.getDouble(DBHelper.ColumnId.ALCOHOL); // % alcohol by volume
        int volume = rs.getInt(DBHelper.ColumnId.VOLUME); // milliliters
        int nr = rs.getInt(DBHelper.ColumnId.PRODUCT_NR); // XML: <nr>nnn</nr> unique nr in the catalog
        String productGroup = rs.getString(DBHelper.ColumnId.PRODUCT_GROUP); // e.g. <Varugrupp>Okryddad sprit</Varugrupp>
        String type = rs.getString(DBHelper.ColumnId.TYPE); // e.g. <Typ>Syrlig öl</Typ>

        products.add(new Product.Builder()
                .name(name)
                .price(price)
                .alcohol(alcohol)
                .nr(nr)
                .productGroup(productGroup)
                .type(type)
                .build());
      }
    } catch (SQLException ex) {
      System.err.println("Problem with sql: " + ex.getSQLState());
    }

    products.add(new Product.Builder()
            .name("Test")
            .build());

  }  
}
