package kernel_three;

import java.util.*;

public class Items {
    public static Random rn = new Random();
    ArrayList<Item> items = new ArrayList<Item>();

    public Items(int num) {
        for (int i = 0; i < num; i++) {
            double value = rn.nextDouble() * 100;
            items.add(new Item(value));
        }
    }

    public double getSum() {
        double sum = 0;
        for (Item item : items) {
            sum += item.getValue();
        }
        return sum;
    }
}
