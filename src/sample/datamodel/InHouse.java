package sample.datamodel;

/**
 *
 * @author Alexander Padilla
 */

public class InHouse extends Part {

    private int machineID;

    /**
     * InHouse constructor inherits Part()
     *
     * @param name the name of the part
     * @param id the id to set
     * @param stock the amount in stock
     * @param min the minimum stock allowed
     * @param max the maximum stock allowed
     * @param price the price of the part
     * @param machineID the ID for the machine that makes the part
     */
    public InHouse(String name, int id, int stock, int min, int max, double price, int machineID) {
        super(name, id, stock, min, max, price);
        this.machineID = machineID;
    }

    /**
     * @return the machineID
     */
    public int getMachineID() {
        return machineID;
    }

    /**
     * @param machineID the machineID to set
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
