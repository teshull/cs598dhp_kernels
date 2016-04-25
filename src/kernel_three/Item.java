package kernel_three;

public class Item {
    Double value;
    public Item(double v) {
        value = new Double(v);
    }
    public double getValue(){
        return value.doubleValue();
    }
}
