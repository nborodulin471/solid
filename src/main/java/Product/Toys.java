package Product;

public class Toys extends Gift {
    int price;
    public Toys(String name, int price) {
        super(name);
        this.price = price;
    }

    @Override
    public int getPrice() {
        return price;
    }

}
