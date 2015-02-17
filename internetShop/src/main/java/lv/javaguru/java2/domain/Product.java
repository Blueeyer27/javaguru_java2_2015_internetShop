package lv.javaguru.java2.domain;

public class Product {

    private long productId;
    private String name;
    private String description;
    private float price;

    public Product() {

    }

    public Product(String name, String description, float price) {
        this. name = name;
        this.description = description;
        this.price = price;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
