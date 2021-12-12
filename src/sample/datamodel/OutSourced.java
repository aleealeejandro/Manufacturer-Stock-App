package sample.datamodel;

/**
 *
 * @author Alexander Padilla
 */

public class OutSourced extends Part {

    private String companyName;

    /**
     * OutSourced constructor inherits Part()
     *
     * @param name the name of the part
     * @param id the id to set
     * @param stock the amount in stock
     * @param min the minimum stock allowed
     * @param max the maximum stock allowed
     * @param price the price of the part
     * @param companyName the name of the company that makes the part
     */
    public OutSourced(String name, int id, int stock, int min, int max, double price, String companyName) {
        super(name, id, stock, min, max, price);
        this.companyName = companyName;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }


    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}



