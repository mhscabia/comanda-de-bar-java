package FuckinAe;

abstract class TabItem {
    private String itemName;
    private double itemPrice;

    public TabItem(String itemName, double itemPrice) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    public abstract double calculateCost();

    // Getters and setters
    public String getItemName() {
        return itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }
    public abstract String getItemType();
}
