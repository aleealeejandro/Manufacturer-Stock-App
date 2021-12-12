package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.datamodel.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author Alexander Padilla
 */

public class PartController implements Initializable {

    @FXML private RadioButton inHouseRadioButton;
    @FXML private RadioButton outSourcedRadioButton;
    @FXML private Button saveInHouseButton;
    @FXML private Button saveOutSourcedButton;

    @FXML private TextField nameField;
    @FXML private TextField idField;
    @FXML private TextField stockField;
    @FXML private TextField minField;
    @FXML private TextField maxField;
    @FXML private TextField priceField;

    @FXML private Label machineID;
    @FXML private TextField machineIDField;

    @FXML private Label companyName;
    @FXML private TextField companyNameField;

    public boolean isInHouse;
    private int id;
    String errorMessage = "";

    @FXML
    public void addPartInHouseInput(ActionEvent event) {
        sourceButtonChosen(true);
    }

    @FXML
    public void addPartOutSourcedInput(ActionEvent event) {
        sourceButtonChosen(false);
    }

    /**
     * changes dom according to which radio button is toggled
     * @param inHouseButtonSelected the boolean value to check if the inHouse button is toggled
     */
    public void sourceButtonChosen(boolean inHouseButtonSelected) {
        if(inHouseButtonSelected) {
            isInHouse = true;
            inHouseRadioButton.setSelected(true);
            outSourcedRadioButton.setSelected(false);
            machineID.setVisible(true);
            machineIDField.setVisible(true);
            companyName.setVisible(false);
            companyNameField.setVisible(false);
            saveInHouseButton.setVisible(true);
            saveOutSourcedButton.setVisible(false);
        }
        if(!inHouseButtonSelected) {
            isInHouse = false;
            outSourcedRadioButton.setSelected(true);
            inHouseRadioButton.setSelected(false);
            companyName.setVisible(true);
            companyNameField.setVisible(true);
            machineID.setVisible(false);
            machineIDField.setVisible(false);
            saveOutSourcedButton.setVisible(true);
            saveInHouseButton.setVisible(false);
        }
    }

    /**
     * returns new InHouse constructor that's initialized
     * @return new inHouse part
     */
    public InHouse getNewInHousePart() {
        String name = nameField.getText();
        int id = Inventory.getUniquePartID();
        int stock = Integer.parseInt(stockField.getText());
        int min = Integer.parseInt(minField.getText());
        int max = Integer.parseInt(maxField.getText());
        double price = Double.parseDouble(priceField.getText());
        int machineID = Integer.parseInt(machineIDField.getText());

        return new InHouse(name, id, stock, min, max, price, machineID);
    }

    /**
     * returns new OutSourced constructor that's initialized
     * @return new outSourced part
     */
    public OutSourced getNewOutSourcedPart() {
        String name = nameField.getText();
        int id = Inventory.getUniquePartID();
        int stock = Integer.parseInt(stockField.getText());
        int min = Integer.parseInt(minField.getText());
        int max = Integer.parseInt(maxField.getText());
        double price = Double.parseDouble(priceField.getText());
        String companyName = companyNameField.getText();

        return new OutSourced(name, id, stock, min, max, price, companyName);
    }


    /**
     * sets every field so that when edit button is pressed every field is filled with the existing info
     * @param part the inHouse part object to edit
     */
    public void editInHousePart(InHouse part) {
        sourceButtonChosen(true);

        nameField.setText(part.getName());
        idField.setText(String.valueOf(part.getId()));
        stockField.setText(String.valueOf(part.getStock()));
        minField.setText(String.valueOf(part.getMin()));
        maxField.setText(String.valueOf(part.getMax()));
        priceField.setText(String.valueOf(part.getPrice()));
        machineIDField.setText(String.valueOf(part.getMachineID()));
        oldInHousePart = part;

        updateInHousePart(part);
        oldInHousePart = part;
//        saveInHousePart();

//        try {
//            updateInHousePart(part);
//            oldInHousePart = part;
//        } catch(NullPointerException f) {
//            System.out.println("NullPointerException in editInHousePart() catch @line 119");
//        }
    }

    /**
     * sets every field so that when edit button is pressed every field is filled with the existing info
     * @param part the outSourced part object to edit
     */
    public void editOutSourcedPart(OutSourced part) {
        sourceButtonChosen(false);

        nameField.setText(part.getName());
        idField.setText(String.valueOf(part.getId()));
        stockField.setText(String.valueOf(part.getStock()));
        minField.setText(String.valueOf(part.getMin()));
        maxField.setText(String.valueOf(part.getMax()));
        priceField.setText(String.valueOf(part.getPrice()));
        companyNameField.setText(part.getCompanyName());

        updateOutSourcedPart(part);
        oldOutSourcedPart = part;
//        saveOutSourcedPart();

//        try {
//            updateOutSourcedPart(part);
//            oldOutSourcedPart = part;
//        } catch(NullPointerException f) {
//            System.out.println("NullPointerException in editOutSourcedPart() catch @line 132");
//        }
    }

    /**
     * updates every field and sets the InHouse values accordingly
     * @param part the inHouse part to update
     */
    public void updateInHousePart(InHouse part) {
        sourceButtonChosen(true);

        part.setName(nameField.getText());
        part.setId(Integer.parseInt(idField.getText()));
        part.setStock(Integer.parseInt(stockField.getText()));
        part.setMin(Integer.parseInt(minField.getText()));
        part.setMax(Integer.parseInt(maxField.getText()));
        part.setPrice(Double.parseDouble(priceField.getText()));
        part.setMachineID(Integer.parseInt(machineIDField.getText()));
    }

    /**
     * updates every field and sets the OutSourced values accordingly
     * @param part the outSourced part to update
     */
    public void updateOutSourcedPart(OutSourced part) {
        sourceButtonChosen(false);

        part.setName(nameField.getText());
        part.setId(Integer.parseInt(idField.getText()));
        part.setStock(Integer.parseInt(stockField.getText()));
        part.setMin(Integer.parseInt(minField.getText()));
        part.setMax(Integer.parseInt(maxField.getText()));
        part.setPrice(Double.parseDouble(priceField.getText()));
        part.setCompanyName(companyNameField.getText());
    }

    /**
     * initializes idField with a unique partID
     * @param url the location used to resolve relative paths for the root object, or null if the location is not known
     * @param rb the resources used to localize the root object, or null if the root object was not localized
     */
    public void initialize(URL url, ResourceBundle rb) {
        id = Inventory.getUniquePartID();
        idField.setText(String.valueOf(id));
    }

    private InHouse oldInHousePart;
    private OutSourced oldOutSourcedPart;

    /**
     * when save part is clicked
     * try loading a new part
     *      if errorMessage.length == 0 when its validated then close dialog pane
     *          then set part values
     *          get every other input
     *          update part with Inventory update part method
     * @param event the ActionEvent that needs to happen to save the edited outSourced part
     * @return the saveOutSourcedPart Part instance
     *      else if user toggles InHouse then delete oldOutSourcedPart add newInHousePart and return null
     *      else alert user and get new OutSourced Part instance
     * @throws NumberFormatException catches whenever numerical input is a non-numerical value and return null
     * @throws NullPointerException catches whenever input is null and return oldOutSourcedPart object
     *                              to fix the program from not allowing the product to save with new values
     *                              when clicking cancel
     * @throws IndexOutOfBoundsException when updating a different kind of part than selected which is easily fixed by returning null
     * set errorMessage to empty after every catch and fail so that we can actually update our part after
     * not saving successfully if this is not implemented then if part doesn't validate once then we will be in a
     * endless loop of trying to save but not being able to
     */
    @FXML
    Part saveEditedOutSourcedPart(ActionEvent event) throws NumberFormatException, NullPointerException {
        sourceButtonChosen(false);

        try {
            OutSourced savePart = getNewOutSourcedPart();
            errorMessage = outSourcedPartIsValid(savePart);
            if (errorMessage.length() == 0 && !isInHouse) {
                sourceButtonChosen(false);
                Stage stage = (Stage) saveOutSourcedButton.getScene().getWindow();
                stage.close();
                updateOutSourcedPart(savePart);
                Inventory.updatePart(oldOutSourcedPart, savePart);
                System.out.println("Part saved");
                return savePart;
            }
            else if(inHouseRadioButton.isSelected()) {
                sourceButtonChosen(true);
                InHouse saveInHousePart = getNewInHousePart();
                Stage stage = (Stage) saveOutSourcedButton.getScene().getWindow();
                stage.close();
                updateOutSourcedPart(savePart);
                Inventory.deletePart(oldOutSourcedPart);
                Inventory.addNewPart(saveInHousePart);
                System.out.println("Part saved");
//                return null;
                return saveInHousePart;
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding Part.");
                alert.setContentText(errorMessage);
                alert.showAndWait();
                errorMessage = "";
                return getNewOutSourcedPart();
            }
        } catch(NumberFormatException err) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Adding Part.");
            alert.setContentText("Numeric input field is empty or \ndoes not have a numeric value.\n");
            System.out.println("NumberFormatException Pointer in saveOutSourcedPart() catch statement @line 296");
            alert.showAndWait();
            errorMessage = "";
            return null;
        } catch (NullPointerException f) {
            System.out.println("Null Pointer exception in saveInHousePart() catch statement @line 301");
            errorMessage = "";
            return oldOutSourcedPart;
        } catch(IndexOutOfBoundsException f) {
            System.out.println("indexoutofboundsexception caught");
            return null;
        }
    }

    /**
     * when save part is clicked
     * try loading a new part
     *      if errorMessage.length == 0 when its validated then close dialog pane
     *          then set part values
     *          get every other input
     *          update part with Inventory update part method
     * @param event the ActionEvent that needs to happen to save the edited inHouse part
     * @return the saveInHousePart Part instance
     *      else if user toggles OutSourced then delete oldInHousePart add newOutSourcedPart and return null
     *      else alert user and get new InHouse Part instance
     * @throws NumberFormatException catches whenever numerical input is a non-numerical value and return null
     * @throws NullPointerException catches whenever input is null and return oldInHousePart object
     *                              to fix the program from not allowing the part to save with new values
     *                              when clicking cancel
     * @throws IndexOutOfBoundsException when updating a different kind of part than selected which is easily fixed by returning null
     * set errorMessage to empty after every catch and fail so that we can actually update our part after
     * not saving successfully if this is not implemented then if part doesn't validate once then we will be in a
     * endless loop of trying to save but not being able to
     */
    @FXML
    InHouse saveEditedInHousePart(ActionEvent event) throws NumberFormatException, NullPointerException {
        sourceButtonChosen(true);

        try {
            InHouse savePart = getNewInHousePart();
//            updateInHousePart(savePart);
            errorMessage = inHousePartIsValid(savePart);
            if (errorMessage.length() == 0) {
                Stage stage = (Stage) saveInHouseButton.getScene().getWindow();
                stage.close();
                updateInHousePart(savePart);
                Inventory.updatePart(oldInHousePart, savePart);
                System.out.println("Part saved");
                return savePart;
            }
            else if(outSourcedRadioButton.isSelected()) {
                sourceButtonChosen(false);
                OutSourced saveOutSourcedPart = getNewOutSourcedPart();
                Stage stage = (Stage) saveOutSourcedButton.getScene().getWindow();
                stage.close();
                updateOutSourcedPart(saveOutSourcedPart);
                Inventory.deletePart(oldOutSourcedPart);
                Inventory.addNewPart(saveOutSourcedPart);
                System.out.println("Part saved");
                return null;
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding Part.");
                alert.setContentText(errorMessage);
                alert.showAndWait();
                errorMessage = "";
                return getNewInHousePart();
            }
        } catch (NumberFormatException err) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Adding Part.");
            alert.setContentText("Numeric input field is empty or \ndoes not have a numeric value.\n");
            System.out.println("NumberFormatException Pointer in saveOutSourcedPart() catch statement @line 296");
            alert.showAndWait();
            errorMessage = "";
            return null;

        } catch (NullPointerException f) {
            System.out.println("Null Pointer exception in saveInHousePart() catch statement @line 301");
            errorMessage = "";
            return oldInHousePart;
        } catch(IndexOutOfBoundsException f) {
            System.out.println("indexoutofboundsexception caught");
            return null;
        }
    }

    /**
     * when save part is clicked
     * try loading a new part
     *      if errorMessage.length == 0 when its validated then close dialog pane
     *          then set part values
     *          get every other input
     *          add part with Inventory add part method
     * @param event the ActionEvent that needs to happen to save the outSourced part
     * @return the saveOutSourcedPart Part instance
     *      else alert user and get new OutSourced Part instance
     * @throws NumberFormatException catches whenever numerical input is a non-numerical value and return null
     * @throws NullPointerException catches whenever input is null and return oldOutSourcedPart object
     *                              to fix the program from not allowing the part to save with new values
     *                              when clicking cancel
     * @throws IndexOutOfBoundsException when updating a different kind of part than selected which is easily fixed by returning null
     * set errorMessage to empty after every catch and fail so that we can actually update our part after
     * not saving successfully if this is not implemented then if product doesn't validate once then we will be in a
     * endless loop of trying to save but not being able to
     */
    @FXML
    OutSourced saveOutSourcedPart(ActionEvent event) throws NumberFormatException, NullPointerException {
        sourceButtonChosen(false);

        try {
            OutSourced savePart = getNewOutSourcedPart();
//            updateOutSourcedPart(savePart);
//            errorMessageOutSourced = outSourcedPartIsValid(savePart);
            errorMessage = outSourcedPartIsValid(savePart);
//            errorMessageInHouse = inHousePartIsValid(savePart);
            if (errorMessage.length() == 0 && !isInHouse) {
                sourceButtonChosen(false);
                Stage stage = (Stage) saveOutSourcedButton.getScene().getWindow();
                stage.close();
                updateOutSourcedPart(savePart);
                Inventory.addNewPart(savePart);
                System.out.println("Part saved");
                return savePart;
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding Part.");
                alert.setContentText(errorMessage);
                alert.showAndWait();
                errorMessage = "";
                return getNewOutSourcedPart();
            }
        } catch(NumberFormatException err) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Adding Part.");
            alert.setContentText("Numeric input field is empty or \ndoes not have a numeric value.\n");
            System.out.println("NumberFormatException Pointer in saveOutSourcedPart() catch statement @line 296");
            alert.showAndWait();
            errorMessage = "";
            return null;
        } catch (NullPointerException f) {
            System.out.println("Null Pointer exception in saveInHousePart() catch statement @line 301");
            errorMessage = "";
            return oldOutSourcedPart;
        } catch(IndexOutOfBoundsException f) {
            System.out.println("indexoutofboundsexception caught");
            return null;
        }
    }

    /**
     * when save part is clicked
     * try loading a new part
     *      if errorMessage.length == 0 when its validated then close dialog pane
     *          then set part values
     *          get every other input
     *          add part with Inventory add part method
     * @param event the ActionEvent that needs to happen to save the edited inHouse part
     * @return the saveInHousePart Part instance
     *      else alert user and get new InHouse Part instance
     * @throws NumberFormatException catches whenever numerical input is a non-numerical value and return null
     * @throws NullPointerException catches whenever input is null and return oldInHousePart object
     *                              to fix the program from not allowing the part to save with new values
     *                              when clicking cancel
     * @throws IndexOutOfBoundsException when updating a different kind of part than selected which is easily fixed by returning null
     * set errorMessage to empty after every catch and fail so that we can actually update our product after
     * not saving successfully if this is not implemented then if product doesn't validate once then we will be in a
     * endless loop of trying to save but not being able to
     */
    @FXML
    InHouse saveInHousePart(ActionEvent event) throws NumberFormatException, NullPointerException {
        sourceButtonChosen(true);

        try {
            InHouse savePart = getNewInHousePart();
//            updateInHousePart(savePart);
            errorMessage = inHousePartIsValid(savePart);
            if (errorMessage.length() == 0) {
                Stage stage = (Stage) saveInHouseButton.getScene().getWindow();
                stage.close();
                updateInHousePart(savePart);
                Inventory.addNewPart(savePart);
                System.out.println("Part saved");
                return savePart;
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding Part.");
                alert.setContentText(errorMessage);
                alert.showAndWait();
                errorMessage = "";
                return getNewInHousePart();
            }
        } catch (NumberFormatException err) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Adding Part.");
            alert.setContentText("Numeric input field is empty or \ndoes not have a numeric value.\n");
            System.out.println("NumberFormatException Pointer in saveOutSourcedPart() catch statement @line 296");
            alert.showAndWait();
            errorMessage = "";
            return null;

        } catch (NullPointerException f) {
            System.out.println("Null Pointer exception in saveInHousePart() catch statement @line 301");
            errorMessage = "";
            return oldInHousePart;
        } catch(IndexOutOfBoundsException f) {
            System.out.println("indexoutofboundsexception caught");
            return null;
        }
    }

    /**
     * validation method for InHouse parts that returns errorMessage to be used with alerts
     * @param part the inHouse part object to validate
     * @return errorMessage
     */
    public String inHousePartIsValid(InHouse part) {

        if (part.getName().equals("")) {
            errorMessage += ("Part name is blank.\n");
        }

        if (part.getMin() < 0) {
            errorMessage += ("Part Inventory cannot be less than 0.\n");
        }

        if (part.getPrice() < 0.01) {
            errorMessage += ("Part price must be more than $0.00\n");
        }

        if (part.getMin() > part.getMax()) {
            errorMessage += ("Inventory minimum cannot be more than the maximum.\n");
        }

        if (part.getStock() < part.getMin() || part.getStock() > part.getMax()) {
            errorMessage += ("In stock amount cannot be less than the minimum\nor greater than the maximum.\n");
        }

        if (part.getMachineID() < 0) {
            errorMessage += ("Machine ID cannot be less than 0.\n");
        }

        return errorMessage;
    }

    /**
     * validation method for OutSourced parts that returns errorMessage to be used with alerts
     * @param part the outSourced part object to validate
     * @return errorMessage
     */
    public String outSourcedPartIsValid(OutSourced part) {

        if (part.getName().equals("")) {
            errorMessage += ("Part name is blank.\n");
        }

        if (part.getMin() < 0) {
            errorMessage += ("Part Inventory cannot be less than 0.\n");
        }

        if (part.getPrice() < 0.01) {
            errorMessage += ("Part price must be more than $0.00\n");
        }

        if (part.getMin() > part.getMax()) {
            errorMessage += ("Inventory minimum cannot be more than the maximum.\n");
        }

        if (part.getStock() < part.getMin() || part.getStock() > part.getMax()) {
            errorMessage += ("In stock amount cannot be less than the minimum\nor greater than the maximum.\n");
        }

        if (part.getCompanyName().equals("")) {
            errorMessage += ("Company name is blank.\n");
        }

        return errorMessage;
    }



}
