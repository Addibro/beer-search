package servlets;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.Predicate;
import javax.servlet.*;
import javax.servlet.http.*;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.util.Locale;

import se.itu.systemet.domain.Product;
import se.itu.systemet.storage.ProductLine;
import se.itu.systemet.storage.ProductLineFactory;


public class SystemetWebAPI extends HttpServlet{

  @Override
  public void init() throws ServletException {
    System.setProperty("ProductLine", getServletContext().getInitParameter("ProductLine"));
    Locale.setDefault(Locale.ENGLISH);
  }
  /**
   * Used to print an json array filled with json objects, 
   * representing beer products
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
    // set the char encoding to UTF_8
    request.setCharacterEncoding(UTF_8.name());
    // set content type to application/json
    response.setContentType("application/json;charset=" + UTF_8.name());
    PrintWriter out =
      new PrintWriter(new OutputStreamWriter(response.getOutputStream(),
                                             UTF_8), true);
    // create a ParameterParser to parse the arguments in the query string
    ParameterParser paramParser = new ParameterParser(request.getQueryString().split("&"));

    // create a predicate with type of Products
    // and use the parameterParser to get the user-specified predicate
    Predicate<Product> filter = paramParser.filter();

    // get the product line with products
    ProductLine productLine = ProductLineFactory.getProductLine();

    // create a list of Products which is filteted by our newly created filter
    List<Product> products = productLine.getProductsFilteredBy(filter);

    // get a formatter (JsonFormatter)
    Formatter formatter = FormatterFactory.getFormatter();

    // format the products to a String
    String json = formatter.format(products);
    StringBuilder sb = new StringBuilder(json);
    
    // print the json string to the response stream
    out.println(sb.toString());
    out.close();
  }
}
