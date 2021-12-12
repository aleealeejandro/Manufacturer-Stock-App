package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.datamodel.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *
 * @author Alexander Padilla
 */

public class Controller implements Initializable {
    @FXML private BorderPane mainPanel;
    @FXML private Button exitButton;
    @FXML private TextField partSearchField;
    @FXML private TextField productSearchField;

    @FXML private TableView<Part> partsTable;
    @FXML private TableColumn<Part, String> partID;
    @FXML private TableColumn<Part, String> partName;
    @FXML private TableColumn<Part, String> partStock;
    @FXML private TableColumn<Part, String> partPrice;

    @FXML private TableView<Product> productsTable;
    @FXML private TableColumn<Product, String> productID;
    @FXML private TableColumn<Product, String> productName;
    @FXML private TableColumn<Product, String> productStock;
    @FXML private TableColumn<Product, String> productPrice;

    public static final ButtonType SAVE = new ButtonType("Save");
    private String errorMessage = "";

    /**
     * uses initialize to initialize partsTable and productsTable
     */
    public void initialize(URL location, ResourceBundle resources) {
        partID.setCellValueFactory(new PropertyValueFactory<Part, String>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partStock.setCellValueFactory(new PropertyValueFactory<Part, String>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<Part, String>("price"));
        partsTable.getItems().setAll(getPartList());


        productID.setCellValueFactory(new PropertyValueFactory<Product, String>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productStock.setCellValueFactory(new PropertyValueFactory<Product, String>("stock"));
        productPrice.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
        productsTable.getItems().setAll(getProductList());
    }

    /**
     * @return list of all parts from Inventory
     */
    private ObservableList<Part> getPartList(){
        return Inventory.getAllParts();
    }

    /**
     * @return list of all products from Inventory
     */
    private ObservableList<Product> getProductList(){
        return Inventory.getAllProducts();
    }

    /**
     * on action searches for part
     * if integer use lookup method for id
     *      if null alert user that part not found
     *      else if search bar is empty reset partsTable with allParts list from Inventory
     *      else its found and we set our table to show us the correct Part
     * else search for string using lookup method for string
     *      repeat same as if integer but for string except this one returns list of parts
     */
    @FXML
    public void searchForPart() {
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
     * on action searches for product
     * if integer use lookup method for id
     *      if null alert user that product not found
     *      else if search bar is empty reset partsTable with allProducts list from Inventory
     *      else its found and we set our table to show us the correct Product
     * else search for string using lookup method for string
     *      repeat same as if integer but for string except this one returns list of products
     */
    @FXML
    public void searchForProduct() {
        productSearchField.setOnAction(e -> {
            if(Inventory.isInteger(productSearchField.getText())) {
                int idSearched = Integer.parseInt(productSearchField.getText());

                if(Inventory.lookUpProduct(idSearched) == null) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("No product found.");
                    alert.setHeaderText(null);
                    alert.setContentText("No product with Product ID: " + idSearched + " found.");
                    alert.showAndWait();
                } else if(productSearchField.getText().equals("")) {
                    productsTable.getItems().setAll(getProductList());
                } else {
                    Inventory.lookUpProduct(idSearched);
                    System.out.println("product is integer");
                    productsTable.getItems().setAll(Inventory.lookUpProduct(idSearched));
                }
            } else {
                String nameSearched =  productSearchField.getText();

                if(Inventory.lookupProduct(nameSearched).isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("No product found.");
                    alert.setHeaderText(null);
                    alert.setContentText("No part with Product Name: " + nameSearched + " found.");
                    alert.showAndWait();
                } else if(productSearchField.getText().equals("")) {
                    productsTable.getItems().setAll(getProductList());
                } else {
                    Inventory.lookupProduct(nameSearched);
                    System.out.println("product is string");
                    productsTable.getItems().setAll(Inventory.lookupProduct(nameSearched));
                }

            }
        });
    }

    /**
     * if exit button clicked get confirmation that user wants to actually exit program
     * if ok then exit program if cancel then continue program
     */
    @FXML
    public void exitButtonClicked() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Program");
        alert.setHeaderText("Exit Program.");
        alert.setContentText("Are you sure you want to exit this program?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
        }

    }

    /**
     * shows addPartDialog that is handled by PartController
     * reset parts table at the end so that we update when dialog pane closes
     */
    @FXML
    public void showAddPartDialog() {
        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Add New Part");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("partdialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch(IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.showAndWait();

        PartController partController = fxmlLoader.getController();
        partsTable.getItems().setAll(getPartList());
    }

    /**
     * shows addProductDialog that is handled by ProductController
     * reset product table at the end so that we update when dialog pane closes
     */
    @FXML
    public void showAddProductDialog() {
        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Add New Product");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("productdialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch(IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.showAndWait();

        ProductController productController = fxmlLoader.getController();
        productsTable.getItems().setAll(getProductList());
    }

    /**
     * shows editPartDialog that is handled by PartController
     * if selectedPart is null alert user to choose a part to edit
     * initiate all fields with partController.editPart
     * if instance of InHouse edit InHouse
     * else edit OutSourced
     * if we want to change from InHouse to OutSourced when editing or vice versa we must delete the selected part and add the new part
     * reset part table at the end so that we update when dialog pane closes
     */
    @FXML
    public void showEditPartDialog() {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();

        if(selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Part Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the part you want to edit.");
            alert.showAndWait();
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Edit Part");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("partediteddialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch(IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        PartController partController = fxmlLoader.getController();

        //validates what Type the selected part is and to set its own Types data
        if(selectedPart instanceof InHouse) {
            //makes sure that when edit button selected the InHouse button will be toggled and InHouse data will be set
            partController.sourceButtonChosen(true);
            partController.editInHousePart((InHouse) selectedPart);

        } else {
            //makes sure that when edit button selected the OutSourced button will be toggled and OutSourced data will be set
            partController.sourceButtonChosen(false);
            partController.editOutSourcedPart((OutSourced) selectedPart);
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.showAndWait();

        if(selectedPart instanceof InHouse) {
            if(!partController.isInHouse) {
                OutSourced newPart = partController.getNewOutSourcedPart();
                partController.updateOutSourcedPart(newPart);

                Inventory.deletePart(selectedPart);
                Inventory.addNewPart(newPart);
            }
        } else {
            if(partController.isInHouse) {
                InHouse newPart = partController.getNewInHousePart();
                partController.updateInHousePart(newPart);

                Inventory.deletePart(selectedPart);
                Inventory.addNewPart(newPart);
            }
        }

        partsTable.getItems().setAll(getPartList());

    }

    /**
     * shows editProductDialog that is handled by ProductController
     * if selectedProduct is null alert user to choose a product to edit
     * initiate all fields with productController.editProduct
     * reset product table at the end so that we update when dialog pane closes
     */
    @FXML
    public void showEditProductDialog() {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();

        if(selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Products Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the product you want to edit.");
            alert.showAndWait();
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Edit Product");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("producteditdialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch(IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        ProductController productController = fxmlLoader.getController();
        productController.editProduct(selectedProduct);

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.showAndWait();

        productsTable.getItems().setAll(getProductList());

    }

    /**
     * when delete part is clicked check that selectedPart is not null
     * if null alert user that they must select something to delete
     * else show confirmation dialog making sure if they really want to delete
     * if ok then delete and update the table without the deleted part
     */
    @FXML
    public void deletePart() {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        if(selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Part Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the part you want to delete.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Part");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete the selected part?" +
                "\nPart: " + selectedPart.getName() + "\nID:    " + selectedPart.getId()
        );

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deletePart(selectedPart);
            partsTable.getItems().setAll(getPartList());
        }

    }

    /**
     * when delete product is clicked check that selectedProduct is not null
     * if null alert user that they must select something to delete
     * else show confirmation dialog making sure if they really want to delete
     * if ok then delete and reset table to table without the deleted product
     */
    @FXML
    public void deleteProduct() {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        if(selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Products Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the product you want to delete.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Product");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete the selected product?" +
               "\nPart: " + selectedProduct.getName() + "\nID:    " + selectedProduct.getId()
        );

        Alert deletionAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deletionAlert.setTitle("Delete Product");
        deletionAlert.setHeaderText(null);
        deletionAlert.setContentText("The product you want to delete still has linked parts.\nIf you wish to delete this product remove any linked parts" +
                "\nProduct: " + selectedProduct.getName() + "\nID:    " + selectedProduct.getId()
        );

        if(selectedProduct.getAssociatedParts().isEmpty()) {
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deleteProduct(selectedProduct);
                productsTable.getItems().setAll(getProductList());
            }
        } else {
            Optional<ButtonType> deletionResult = deletionAlert.showAndWait();

            if(deletionResult.isPresent() && deletionResult.get() == ButtonType.OK) {
                productsTable.getItems().setAll(getProductList());
            }
        }

    }


}
