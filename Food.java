package FuckinAe;

class Food extends TabItem {
    public Food(String itemName, double itemPrice) {
        super(itemName, itemPrice);
    }

    @Override
    public double calculateCost() {
        // Food cost doesn't depend on quantity, so just return the item price
        return getItemPrice();
    }
    @Override
    public String getItemType() {
        return "Food";
    }
}
