package sample.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 *
 * @author Alexander Padilla
 */

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static int partIdIteration = 99;
    public static int productIdIteration = 999;

    public Inventory() {
    }

    /**
     * adds new part to list of allParts
     * @param part the new part to be added to inventory
     */
    public static void addNewPart(Part part) {
        allParts.add(part);
    }

    /**
     * adds new product to list of allProducts
     * @param product the new product to be added to inventory
     */
    public static void addNewProduct(Product product) {
        allProducts.add(product);
    }

    /**
     * gives new part a unique partID
     * @return the partIdIteration
     */
    public static int getUniquePartID() {
        partIdIteration++;
        return partIdIteration;
    }

    /**
     * gives new product a unique productID
     * @return the productIdIteration
     */
    public static int getUniqueProductID() {
        productIdIteration++;
        return productIdIteration;
    }

    /**
     * removes part from allParts list
     * @param part the part to be deleted from inventory
     */
    public static void deletePart(Part part) {
        allParts.remove(part);
    }

    /**
     * removes product from allProducts list
     * @param product the product to be deleted from inventory
     */
    public static void deleteProduct(Product product) {
        allProducts.remove(product);
    }

    /**
     * finds if part exists in inventory using part object
     * if true will return index
     * if false will return -1
     * @param part the part object to search for
     * @return the index of given part object
     */
    private static int findPart(Part part) {
        return allParts.indexOf(part);// if true it'll return index number or else it'll return -1
    }

    /**
     * finds if part exists in inventory using partID
     * if true will return index
     * if false will return -1
     * @param partID the partID to search for
     * @return the index of given partID
     */
    private static int findPart(int partID) {
        for(int i=0; i < allParts.size(); i++) {
            Part part = allParts.get(i);
            if(part.getId() == partID) {
                return i;
            }
        }
        return -1;
    }

    /**
     * finds if product exists in inventory using productID
     * if true will return index
     * if false will return -1
     * @param productID the the productID to search for
     * @return the index of given productID
     */
    private static int findProduct(int productID) {
        for(int i=0; i < allProducts.size(); i++) {
            Product product = allProducts.get(i);
            if(product.getId() == productID) {
                return i;
            }
        }
        return -1;
    }

    /**
     * finds if product exists in inventory using product object
     * if true will return index
     * if false will return -1
     * @param product the part object to search for
     * @return the index of given product
     */
    private static int findProduct(Product product) {
        return allProducts.indexOf(product);// if true it'll return index number or else it'll return -1
    }

    /**
     * searches for matching part object in inventory using user input for partID
     * if true will return part
     * if false will return null
     * @param partID the partID to search for in allParts list
     * @return the matching part object of given partID
     */
    //Look up part or product by ID
    public static Part lookUpPart(int partID) {
        if(findPart(partID) >= 0) {
            return allParts.get(findPart(partID));
        } else {
            return null;
        }
    }

    /**
     * searches for matching part objects in inventory using user input for part name
     * @param name the part name to search for in allParts list
     * @return the filtered part list of any instance containing given String name
     */
    public static ObservableList<Part> lookupPart(String name) {
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();
        FilteredList<Part> filteredList = new FilteredList<Part>(allParts, part -> true);
        filteredList.setPredicate(filteredItem -> {
            if(name == null || name.isEmpty()) {
                return true;
            }
            //returns true or false
            return filteredItem.getName().contains(name);
        });

        filteredParts.setAll(filteredList);
        return filteredParts;
    }

    /**
     * searches for matching product objects in inventory using user input for product name
     * @param name the product name to search for in allProducts list
     * @return the filtered product list of any instance containing given String name
     */
    public static ObservableList<Product> lookupProduct(String name) {
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
        FilteredList<Product> filteredList = new FilteredList<Product>(allProducts, product -> true);
        filteredList.setPredicate(filteredItem -> {
            if(name == null || name.isEmpty()) {
                return true;
            }
            //returns true or false
            return filteredItem.getName().contains(name);
        });

        filteredProducts.setAll(filteredList);
        return filteredProducts;
    }

    /**
     * searches for matching product object in inventory using user input for productID
     * if true will return product
     * if false will return null
     * @param productID the productID to search for in allProducts list
     * @return the product object of given productID
     */
    public static Product lookUpProduct(int productID) {
        if(findProduct(productID) >= 0) {
            System.out.println("Found Product: " + allProducts.get(findProduct(productID)));
            return allProducts.get(findProduct(productID));
        } else {
            System.out.println("Unable to find Product with name: " + productID);
            return null;
        }
    }

    /**
     * updates old part by finding its index and replacing its info with the newPart info leaving its ID intact
     * @param oldPart the old part to be updated
     * @param newPart the new part to replace the old part
     */
    public static void updatePart(Part oldPart, Part newPart) {
        int foundPosition = findPart(oldPart);
        allParts.set(foundPosition, newPart);
    }

    /**
     * updates old product by finding its index and replacing its info with the newProduct info leaving its ID intact
     * @param oldProduct the old product to be updated
     * @param newProduct the new product to replace the old product
     */
    public static void updateProduct(Product oldProduct, Product newProduct) {
        int foundPosition = findProduct(oldProduct);
        allProducts.set(foundPosition, newProduct);
    }

    //Getters
    /**
     * list of all the parts
     * @return the allParts list
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * list of all the products
     * @return the allProducts list
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * checks if input is an integer
     * @param input the input to check
     * @return the boolean value of given input
     * if true input is integer
     * if false input is not a integer
     */
    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
