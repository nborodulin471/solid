
import product.Gift;

import java.util.Random;

public class RandomProduct {
    public static Gift getRandomProduct(Gift[] list){
        Random random = new Random();
        int index = random.nextInt(list.length);
        return list[index];
    }
}
