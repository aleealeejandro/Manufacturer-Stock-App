package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.datamodel.*;
import java.net.URL;
import java.util.*;

/**
 *
 * @author Alexander Padilla
 */

public class ProductController implements Initializable {

    @FXML private TextField partSearchField;
    @FXML private TextField nameField;
    @FXML private TextField idField;
    @FXML private TextField stockField;
    @FXML private TextField minField;
    @FXML private TextField maxField;
    @FXML private TextField priceField;

    @FXML public Button closeButton;
    @FXML public Button saveButton;

    @FXML private TableView<Part> partsTable;
    @FXML private TableColumn<Part, Integer> partID;
    @FXML private TableColumn<Part, String> partName;
    @FXML private TableColumn<Part, Integer> partStock;
    @FXML private TableColumn<Part, Double> partPrice;

    @FXML private TableView<Part> associatedPartsTable;
    @FXML private TableColumn<Part, Integer> associatedPartID;
    @FXML private TableColumn<Part, String> associatedPartName;
    @FXML private TableColumn<Part, Integer> associatedPartStock;
    @FXML private TableColumn<Part, Double> associatedPartPrice;

    String errorMessage = "";

    private final ObservableList<Part> partsAddedList = FXCollections.observableArrayList();

    /**
     * uses initialize to initialize partsTable, associatedPartsTable and our idField
     * @param location the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resources the resources used to localize the root object, or null if the root object was not localized
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        partID.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partStock.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        partsTable.getItems().setAll(getPartList());

        associatedPartID.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        associatedPartStock.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        associatedPartPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        associatedPartsTable.setItems(partsAddedList);

        int productID = Inventory.getUniqueProductID();
        idField.setText(String.valueOf(productID));
    }

//    public ObservableList<Part> getProductAssociatedParts(Product product) {
//        ObservableList<Part> associatedProductParts = FXCollections.observableArrayList();
//        associatedProductParts.addAll(product.getAssociatedParts());
//        return associatedProductParts;
//    }

    /**
     *onAction search for part
     * if integer check if null
     *      if null when id is looked up then show alert
     *      else if partsField is empty then reset partsTable by setting it to all parts list again
     *      else part is found and set table to show found id
     * else is a string
     *      if string is empty when searched than there is no match
     *      else if partsField is empty then reset partsTable by setting it to all parts list again
     *      else else part is found and set table to show found partsList
     * @param event the ActionEvent that needs to happen to search for part
     */
    @FXML
    public void searchForPart(ActionEvent event) {
        partSearchField.setOnAction(e -> {
            if(Inventory.isInteger(partSearchField.getText())) {
                int idSearched = Integer.parseInt(partSearchField.getText());

                if(Inventory.lookUpPart(idSearched) == null) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("No part found.");
                    alert.setHeaderText(null);
                    alert.setContentText("No part with Part ID: " + idSearched + " found.");
                    alert.showAndWait();
                } else if(partSearchField.getText().equals("")) {
                    partsTable.getItems().setAll(getPartList());
                } else {
                    Inventory.lookUpPart(idSearched);
                    System.out.println("part is integer");
                    partsTable.getItems().setAll(Inventory.lookUpPart(idSearched));
                }
            } else {
                String nameSearched =  partSearchField.getText();

                if(Inventory.lookupPart(nameSearched).isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("No part found.");
                    alert.setHeaderText(null);
                    alert.setContentText("No part with Part Name: " + nameSearched + " found.");
                    alert.showAndWait();
                    partSearchField.setText("");
                } else if(partSearchField.getText().equals("")) {
                    partsTable.getItems().setAll(getPartList());
                } else {
                    Inventory.lookupPart(nameSearched);
                    System.out.println("part is string");
                    partsTable.getItems().setAll(Inventory.lookupPart(nameSearched));
                }

            }
        });
    }

    /**
     * when save product is clicked
     * try loading a new Product
     *      if errorMessage.length == 0 when its validated then close dialog pane
     *          then set associated parts
     *          get every other input
     *          add product with Inventory add product method
     * @param event the ActionEvent that needs to happen to save a product
     * @return the saveProduct Product instance
     *      else alert the user with errorMessages and return null
     * @throws NumberFormatException catches whenever numerical input is a non-numerical value
     * @throws NullPointerException catches whenever input is null
     * set errorMessage to empty after every catch and fail so that we can actually add our product after
     * not saving successfully if this is not implemented then if product doesn't validate once then we will be in a
     * endless loop of trying to save but not being able to
     */
    @FXML
    public Product saveProduct(ActionEvent event) throws NumberFormatException, NullPointerException {
        try {
            Product saveProduct = getNewProduct();
//            updateProduct(saveProduct);
            errorMessage = productIsValid(saveProduct);
            if(errorMessage.length() == 0) {
                Stage stage = (Stage) saveButton.getScene().getWindow();
                stage.close();
                saveProduct.setAssociatedParts(partsAddedList);
                updateProduct(saveProduct);
                Inventory.addNewProduct(saveProduct);
                System.out.println("Product saved");
                return saveProduct;
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding Product.");
                alert.setContentText(errorMessage);
                alert.showAndWait();
                errorMessage = "";
//                return getNewProduct();
                return null;
            }
        } catch(NumberFormatException err) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Adding Product.");
            alert.setContentText("Numeric input field is empty or \ndoes not have a numeric value.");
            alert.showAndWait();
            errorMessage = "";
//            return getNewProduct();
            return null;

        } catch(NullPointerException f) {
            System.out.println("Null Pointer exception in saveProduct() catch statement @line 121");
            errorMessage = "";
//            return getNewProduct();
            return null;
        }

    }


    /**
     * when save product is clicked
     * try loading a new Product
     *      if errorMessage.length == 0 when its validated then close dialog pane
     *          then set associated parts
     *          get every other input
     *          update product with Inventory update product method
     * @param event the ActionEvent that needs to happen to save an edited product
     * @return the saveProduct Product instance
     *      else alert the user with errorMessages and return a new Product instance
     * @throws NumberFormatException catches whenever numerical input is a non-numerical value and return null
     * @throws NullPointerException catches whenever input is null and return oldProduct object
     *                              to fix the program from not allowing the product to save with new values
     *                              when clicking cancel
     * set errorMessage to empty after every catch and fail so that we can actually update our product after
     * not saving successfully if this is not implemented then if product doesn't validate once then we will be in a
     * endless loop of trying to save but not being able to
     */

    @FXML
    public Product saveEditedProduct(ActionEvent event) throws NumberFormatException, NullPointerException {
//            updateProduct(saveProduct);
        try {
            Product saveProduct = getNewProduct();
            errorMessage = modifiedProductIsValid(saveProduct);
            if(errorMessage.length() == 0) {
                Stage stage = (Stage) saveButton.getScene().getWindow();
                stage.close();
                saveProduct.setAssociatedParts(partsAddedList);
                updateProduct(saveProduct);
                Inventory.updateProduct(oldProduct, saveProduct);
                System.out.println("Product saved");
                return saveProduct;
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding Product.");
                alert.setContentText(errorMessage);
                alert.showAndWait();
                errorMessage = "";
                return getNewProduct();
//                return null;
            }
        } catch(NumberFormatException err) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Adding Product.");
            alert.setContentText("Numeric input field is empty or \ndoes not have a numeric value.");
            alert.showAndWait();
            errorMessage = "";
            System.out.println("NUMBERFORMATEXCEPTION in saveEditProduct() @line 160");
//            return getNewProduct();
            return null;

        } catch(NullPointerException f) {
            System.out.println("Null Pointer exception in SaveProduct() catch statement @line 121");
            errorMessage = "";
            return oldProduct;
//            return null;
        }

    }

    /**
     * when remove associated product button is clicked
     *  check if selected associated part is instance of InHouse
     *      if so check if selectedPart is null
     *          if so alert user that nothing is selected
     *          else confirm if user wants to actually remove associated part
     *              if ok pressed then remove associated part
     * repeat the same logic but for instanceof OutSourced
     * @param event the ActionEvent that needs to happen to remove associatedParts
     */

    @FXML
    public void removeAssociatedPart(ActionEvent event) {

        if(associatedPartsTable.getSelectionModel().getSelectedItem() instanceof InHouse) {
            InHouse selectedPart = (InHouse) associatedPartsTable.getSelectionModel().getSelectedItem();

            if(selectedPart == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Parts Associated to delete.");
                alert.setHeaderText(null);
                alert.setContentText("No items in associated parts list. Cannot delete empty item.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Remove Associated Part");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to remove the selected associated part?" +
                        "\nPart: " + selectedPart.getName() + "\nID:    " + selectedPart.getId()
                );

                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK) {
                    partsAddedList.remove(selectedPart);
                    associatedPartsTable.setItems(partsAddedList);
                }
//                partsAddedList.remove(selectedPart);
//                associatedPartsTable.setItems(partsAddedList);
            }

        } else {
            OutSourced selectedPart = (OutSourced) associatedPartsTable.getSelectionModel().getSelectedItem();

            if(selectedPart == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Parts Associated to delete.");
                alert.setHeaderText(null);
                alert.setContentText("No items in associated parts list. Cannot delete empty item.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Remove Associated Part");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to remove the selected associated part?" +
                        "\nPart: " + selectedPart.getName() + "\nID:    " + selectedPart.getId()
                );

                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK) {
                    partsAddedList.remove(selectedPart);
                    associatedPartsTable.setItems(partsAddedList);
                }
//                partsAddedList.remove(selectedPart);
//                associatedPartsTable.setItems(partsAddedList);
            }
        }
//        associatedPartsTable.getItems();

    }

    /**
     * when add associated product button is clicked
     *  check if selected associated part is instance of InHouse
     *      if so check if selectedPart is null
     *          if so alert user that nothing is selected
     *          if selected part is already in associatedParts list alert that
     *          user cant add that part because it already exists in associated parts table
     *          else add associated part and update associated parts table
     * repeat the same logic but for instanceof OutSourced
     * @param event the ActionEvent that needs to happen to add associatedParts
     */

    @FXML
    public void addAssociatedPart(ActionEvent event) {

        if(partsTable.getSelectionModel().getSelectedItem() instanceof InHouse) {
            InHouse selectedPart = (InHouse) partsTable.getSelectionModel().getSelectedItem();

            if(selectedPart == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Part Selected");
                alert.setHeaderText(null);
                alert.setContentText("Please select the part you want to add to associated product parts list.");
                alert.showAndWait();
            } else if(partsAddedList.contains(selectedPart)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Part Already Selected");
                alert.setHeaderText(null);
                alert.setContentText("Please select a part that isn't already in the Parts Associated Table.");
                alert.showAndWait();
            } else {
                partsAddedList.add(selectedPart);
                associatedPartsTable.setItems(partsAddedList);
            }
        } else {
            OutSourced selectedPart = (OutSourced) partsTable.getSelectionModel().getSelectedItem();

            if(selectedPart == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Part Selected");
                alert.setHeaderText(null);
                alert.setContentText("Please select the part you want to add to associated product parts list.");
                alert.showAndWait();
            } else if(partsAddedList.contains(selectedPart)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Part Already Selected");
                alert.setHeaderText(null);
                alert.setContentText("Please select a part that isn't already in the Parts Associated Table.");
                alert.showAndWait();
            } else {
                partsAddedList.add(selectedPart);
                associatedPartsTable.setItems(partsAddedList);
            }
        }

    }

    /**
     * @return allParts list from Inventory class
     */
    private ObservableList<Part> getPartList() {
        return Inventory.getAllParts();
    }

    /**
     * returns new Product constructor that's initialized
     * @return new Product with empty fields
     */
    public Product getNewProduct() {
        String name = nameField.getText();
        int id = Inventory.getUniquePartID();
        int stock = Integer.parseInt(stockField.getText());
        int min = Integer.parseInt(minField.getText());
        int max = Integer.parseInt(maxField.getText());
        double price = Double.parseDouble(priceField.getText());

        return new Product(name, id, stock, min, max, price, partsAddedList);
    }

    Product oldProduct;

    /**
     * sets every field so that when edit product button is pressed
     * every field is set including associatedPartsTable
     * @param product the product object to edit
     */
    public void editProduct(Product product) throws NullPointerException {

        nameField.setText(product.getName());
        idField.setText(String.valueOf(product.getId()));
        stockField.setText(String.valueOf(product.getStock()));
        minField.setText(String.valueOf(product.getMin()));
        maxField.setText(String.valueOf(product.getMax()));
        priceField.setText(String.valueOf(product.getPrice()));

        try {
            partsAddedList.addAll(product.getAssociatedParts());
            associatedPartsTable.setItems(partsAddedList);
            updateProduct(product);
            oldProduct = product;
        } catch(NullPointerException f) {
            System.out.println("empty associated parts table");
        }
    }

    /**
     * gets every field and sets the product values accordingly
     * @param product the product object to update
     */
    public void updateProduct(Product product) {
        product.setName(nameField.getText());
        product.setId(Integer.parseInt(idField.getText()));
        product.setStock(Integer.parseInt(stockField.getText()));
        product.setMin(Integer.parseInt(minField.getText()));
        product.setMax(Integer.parseInt(maxField.getText()));
        product.setPrice(Double.parseDouble(priceField.getText()));
//        product.setAssociatedParts(partsAddedList);
    }

    /**
     * validation method that returns errorMessage to be used with alerts
     * @param product the product object to check for validation
     * @return errorMessage
     */
    public String productIsValid(Product product) {
        double partsTotal = 0.00;

        for (Part part : product.getAssociatedParts()) {
            partsTotal += part.getPrice();
        }

        if (product.getName().equals("")) {
            errorMessage += ("Product name is blank.\n");
        }

        if (product.getMin() < 0) {
            errorMessage += ("Product Inventory cannot be less than 0.\n");
        }

        if (product.getPrice() < 0.01) {
            errorMessage += ("Product price must be more than $0.00\n");
        }

        if (product.getMin() > product.getMax()) {
            errorMessage += ("Inventory minimum cannot be more than the maximum.\n");
        }

        if (product.getStock() < product.getMin() || product.getStock() > product.getMax()) {
            errorMessage += ("In stock amount cannot be less than the minimum\nor greater than the maximum.\n");
        }

        if (product.getAssociatedParts().isEmpty()) {
            errorMessage += ("Product must contain at least 1 associated part.\n");
        }

        if (partsTotal > product.getPrice()) {
            errorMessage += ("Product price must be greater than cost of the included associated parts.\n");
        }

        return errorMessage;
    }

    /**
     * validation method that returns errorMessage to be used with alerts
     * @param product the modified product object to check for validation
     * @return errorMessage
     */
    public String modifiedProductIsValid(Product product) {
        double partsTotal = 0.00;

        for (Part part : product.getAssociatedParts()) {
            partsTotal += part.getPrice();
        }

        if (product.getName().equals("")) {
            errorMessage += ("Product name is blank.\n");
        }

        if (product.getMin() < 0) {
            errorMessage += ("Product Inventory cannot be less than 0.\n");
        }

        if (product.getPrice() < 0.01) {
            errorMessage += ("Product price must be more than $0.00\n");
        }

        if (product.getMin() > product.getMax()) {
            errorMessage += ("Inventory minimum cannot be more than the maximum.\n");
        }

        if (product.getStock() < product.getMin() || product.getStock() > product.getMax()) {
            errorMessage += ("In stock amount cannot be less than the minimum\nor greater than the maximum.\n");
        }

//        if (product.getAssociatedParts().) {
//            errorMessage += ("Product must contain at least 1 associated part.\n");
//        }

        if (partsTotal > product.getPrice()) {
            errorMessage += ("Product price must be greater than cost of the included associated parts.\n");
        }

        return errorMessage;
    }
}
