package sample.datamodel;

import javafx.collections.ObservableList;

/**
 *
 * @author Alexander Padilla
 */

public class Product {

    private String name;
    private int id;
    private int stock;
    private int min;
    private int max;
    private double price;
    private ObservableList<Part> associatedParts;

    /**
     * Product constructor
     *
     * @param name the name of the product
     * @param id the id to set
     * @param stock the amount in stock
     * @param min the minimum stock allowed
     * @param max the maximum stock allowed
     * @param price the price of the product
     * @param associatedParts the list of parts associated with the product
     */
    public Product(String name, int id, int stock, int min, int max, double price, ObservableList<Part> associatedParts) {
        this.name = name;
        this.id = id;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.price = price;
        this.associatedParts = associatedParts;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the associatedParts
     */
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }

    /**
     * @param associatedParts the associatedParts to set
     */
    public void setAssociatedParts(ObservableList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }

}
