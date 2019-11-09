package se.itu.systemet.gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;
import javax.swing.event.*;

import se.itu.systemet.domain.Product;
import se.itu.systemet.rest.ApiAccess;
import se.itu.systemet.rest.ApiAccessFactory;
import se.itu.systemet.rest.Param;
import se.itu.systemet.rest.Query;
import se.itu.systemet.rest.QueryFactory;

/**
 * A class representing the GUI for an application for searching Systembolaget
 * products.
 */
public class SearchGUI {
  // Instance variables below - mostly Swing components for the UI
  private JFrame frame; // this is the actual window
  private JPanel panel; // a panel is a surface to put other components on
  private JPanel search;
  private JTable table; // A table which looks like a spread sheet, kind of
  // input fields for searching
  private JTextField minAlcoField;
  private JTextField maxAlcoField;
  private JTextField minPriceField;
  private JTextField maxPriceField;
  private List<Product> products;
  private ApiAccess api; // For talking to the REST API
  private JButton clearButton;

  public SearchGUI() {
    api = ApiAccessFactory.getHttpApiAccess();
    products = api.fetch(QueryFactory.getQuery());
    init(); // Initiate the components
    show(); // Show the frame
    System.out.println("GUI started!");
  }

  private void init() {
    // create window
    frame = new JFrame("Search products");
    frame.setLayout(new BorderLayout());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    panel = new JPanel(new GridLayout(1, 0));
    GridLayout formLayout = new GridLayout(2, 4);
    search = new JPanel(formLayout);
    formLayout.setVgap(2);
    formLayout.setHgap(4);
    table = new JTable(new ProductTableModel(products));
    table.setPreferredScrollableViewportSize(new Dimension(1600, 1600));
    table.setFillsViewportHeight(true);
    table.setRowHeight(30);
    table.setAutoCreateRowSorter(true);
    table.getColumnModel().getColumn(0).setPreferredWidth(400);
    JScrollPane scrollPane = new JScrollPane(table);
    panel.add(scrollPane);
    panel.setOpaque(true);
    frame.add(panel, BorderLayout.CENTER);
    minPriceField = new JTextField(6);
    maxPriceField = new JTextField(6);
    minAlcoField = new JTextField(3);
    maxAlcoField = new JTextField(3);
    GridLayout clearLayout = new GridLayout(1, 0);
    clearButton = new JButton("Clear");
    clearButton.setPreferredSize(new Dimension(100, 100));
    JPanel clearPanel = new JPanel(clearLayout);
    clearPanel.add(clearButton);
    search.add(new JLabel("Minimum alcolhol:"));
    search.add(minAlcoField);
    search.add(new JLabel("Maximum alcolhol:"));
    search.add(maxAlcoField);
    search.add(new JLabel("Minimum price:"));
    search.add(minPriceField);
    search.add(new JLabel("Maximum price:"));
    search.add(maxPriceField);
    frame.add(search, BorderLayout.SOUTH);
    addListeners();
  }

  private void show() {
    frame.pack();
    frame.setVisible(true);
  }

  private List<JTextField> textFields() {
    List<JTextField> textFields = Arrays.asList(minPriceField, maxPriceField, minAlcoField, maxAlcoField);
    return textFields;
  }

  private void addListeners() {
    for (JTextField textField : textFields()) {
      textField.getDocument().addDocumentListener(new DocumentListener() {
        public void changedUpdate(DocumentEvent e) {
          newFilter();
        }

        public void insertUpdate(DocumentEvent e) {
          newFilter();
        }

        public void removeUpdate(DocumentEvent e) {
          newFilter();
        }
      });
    }

    clearButton.addActionListener((e) -> textFields().forEach(event -> event.setText("")));

  }

  private List<Param> params() {
    List<Param> params = new ArrayList<>();
    if (!"".equals(minAlcoField.getText())) {
      params.add(new Param("AlcoholPercentageMin", minAlcoField.getText()));
    }
    if (!"".equals(maxAlcoField.getText())) {
      params.add(new Param("AlcoholPercentageMax", maxAlcoField.getText()));
    }
    if (!"".equals(minPriceField.getText())) {
      params.add(new Param("PriceMin", minPriceField.getText()));
    }
    if (!"".equals(maxPriceField.getText())) {
      params.add(new Param("PriceMax", maxPriceField.getText()));
    }
    return params;
  }

  private void newFilter() {
    Query query = QueryFactory.getQuery();
    for (Param param : params()) {
      query.addParam(param);
    }
    // System.out.println("Query: " + query.toQueryString());

    table.setModel(new ProductTableModel(api.fetch(query)));
  }
}
