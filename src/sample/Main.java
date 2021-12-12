package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.datamodel.*;

/**
 *  <p>Main application class.</p>
 *  <p>Javadoc folder located in sample folder</p>
 *
 *  <p><b>FUTURE ENHANCEMENTS:</b></p>
 *
 *  <ul>
 *      <li>Implement search on keystrokes to make a better streamlined search bar.</li>
 *      <li>Implement an email API to notify when the shop is low or empty on certain parts and products.</li>
 *      <li>Add functionality to keep track of stock to know if there are enough resources when making a new products or adding more inventory to products</li>
 *      <li>Make into a webapp so that users can check inventory in real time on different devices using a cloud service</li>
 *  </ul>
 *
 * @author Alexander Padilla
 */

public class Main extends Application {

    /**
     * sets main scene to start
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }


    /**
     *initiate program with preset parts and products
     * @param args string array of command line arguments
     */
    public static void main(String[] args) {
        InHouse part1 = new InHouse("Wheel",96, 90, 2, 200, 0.99, 1702);
        InHouse part2 = new InHouse("Board",97, 32, 2, 202, 4.99, 2019);
        OutSourced part3 = new OutSourced("Bridge", 98, 22, 18, 140,9.99, "James Inc.");
        OutSourced part4 = new OutSourced("Glue", 99, 92, 2, 121,2.99, "Hammer Industries");

        Inventory.addNewPart(part1);
        Inventory.addNewPart(part2);
        Inventory.addNewPart(part3);
        Inventory.addNewPart(part4);

        Inventory.addNewProduct(new Product("Toy Car", 996,200,2, 1000, 18.99, Inventory.getAllParts()));
        Inventory.addNewProduct(new Product("Toy Train",997, 228, 6,1980,88.99, Inventory.getAllParts()));
        Inventory.addNewProduct(new Product("Toy Boat", 998, 5, 1,43,35.99, Inventory.getAllParts()));
        Inventory.addNewProduct(new Product("Toy Plane",999,51,0,500,42.50, Inventory.getAllParts()));

        launch(args);
    }
}
