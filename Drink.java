package FuckinAe;

class Drink extends TabItem {
    public Drink(String itemName, double itemPrice) {
        super(itemName, itemPrice);
    }

    @Override
    public double calculateCost() {
        // Drink cost doesn't depend on quantity, so just return the item price
        return getItemPrice();
    }
    @Override
    public String getItemType() {
        return "Drink";
    }
}
