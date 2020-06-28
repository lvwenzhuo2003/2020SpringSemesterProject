public class Order {
    private final String productType;
    private final int    amount;
    private final String customerName;

    public Order(String productType, int amount, String customerName) {
        this.productType = productType;
        this.amount = amount;
        this.customerName = customerName;
    }

    public String getProductType() {
        return productType;
    }

    public int getAmount() {
        return amount;
    }

    public String getCustomerName() {
        return customerName;
    }
}
