import product.Gift;
import product.Toys;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final int MAX_SIZE_LIST_PRODUCTS = 3;

    public static void main(String[] args) {

        Gift[] listProducts = getListProducts(); // принцип избегания магических чисел внутри
        printListProducts("Добро пожаловать в онлайн магазин подарков" +
                " Вам доступны следующие подарки:", listProducts); // DRY - избегание повторений.

        System.out.println("Выберите один или несколько подарков для продолжения, для это введите номер подарки. \n" +
                "А также мы можем вам помочь выбрать подарок, для этого введите '!'. Для выхода 'end'");

        List<Gift> currentProduct = new ArrayList<>();

        try(Scanner scanner = new Scanner(System.in)){

            while (true){

                String input = scanner.nextLine();
                Gift product;
                if (input.equals("end")){
                    break;
                }

                if (input.equals("!")){
                    // принцип единой ответственности
                    // вместо того, что бы пихать все в Product я создал отдельный класс
                    product = RandomProduct.getRandomProduct(listProducts);
                }else {
                    product = listProducts[Integer.parseInt(input) - 1];
                }

                if (product == null){
                   System.out.println("Вы ничего не выбрали!");
                   break;
                }else {
                    System.out.println("Вы выбрали: " + product);
                }

                currentProduct.add(product);

            }

        }catch(Exception e){
            e.printStackTrace();
        }

        System.out.println("Вам выбрали следующие подарки: ");
        currentProduct.stream().forEach(gift -> System.out.print(gift + "|"));
        System.out.println("");

        // Принцип открытости/закрытости. Для подсчета суммы не придется менять calcSum
        // т.к каждый наследник подарка обязан уметь отдавать сумму
        System.out.println("Общая стоимость подарков составила: " + calcSum(currentProduct));

    }

    private static int calcSum(List<Gift> currentGift){

        int sum = 0;
        for (Gift product : currentGift) {
            // Это Принцип инверсии зависимостей - зависьте от абстракций, а не от имплементаций
            // т.к мы не зависим от деталей реализации классов наследников Product
            // а имеем зависим только от абстракции получения цены
            // логика получения расчета цены для каждого наследника товаров может отличается
            // например от спроса на данный момент на сайте
            sum += product.getPrice();
        }

        return sum;

    }

    private static void printListProducts(String heading, Gift[] listProducts) {

        StringBuilder builder = new StringBuilder(heading
                + "\n"
                + "_______________\n");

        System.out.println();
        for (int i = 0 ; i < listProducts.length; i ++) {
            builder.append(String.format("№ %d. Подарок: %s\n", i + 1, listProducts[i] ));
        }

        builder.append("_______________" + "\n");

        System.out.println(builder.toString());

    }

    private static Gift[] getListProducts() {

        // избегание магических чисел, а также принцип замены Барбары Лисков
        // далее в этом методе мы создаем объект класса игрушка, который является наследником
        // класса подарок т.к игрушка может быть подарком, то это есть реализация данного принципа
        Gift[] possibleProducts = new Gift[MAX_SIZE_LIST_PRODUCTS]; // магические числа

        // Также класс подарок имплементирует интерфейс Priceble
        // что является реалзацией Принципа сегрегации (разделения) интерфейса - разделяй большие интерфейсы на маленькие
        // т.к этот интерфейс имеет описание только одного метода, характерезующего наличие цены (у каждого товара есть цена)
        // и не предполагает другого функционала, также можно добавить другой интерфейс(напрмер transportable)
        // который бы предпологал возможность транспортировки, но реализацию
        // (способ реализации, например: сани или самолоет) таковой на усмотрение наследников
        possibleProducts[0] = new Toys("Снеговик", 100);
        possibleProducts[1] = new Toys("Робот", 150);
        possibleProducts[2] = new Toys("Машина", 50);

        return possibleProducts;

    }

}
