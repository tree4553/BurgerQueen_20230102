package app;

import app.product.Product;
import app.product.ProductRepository;
import app.product.subproduct.Drink;
import app.product.subproduct.Hamburger;
import app.product.subproduct.Side;
import app.product.subproduct.BugerSet;

import java.util.Scanner;

public class Cart {
    private Menu menu;
    private ProductRepository productRepository;
    private Product[] items = new Product[0];
    private Scanner scanner = new Scanner(System.in);

    public Cart(ProductRepository productRepository, Menu menu) {
        this.productRepository = productRepository;
        this.menu = menu;
    }

    public void printCart() {
        System.out.println("๐งบ ์ฅ๋ฐ๊ตฌ๋");
        System.out.println("-".repeat(60));

        printCartItemDetails();

        System.out.println("-".repeat(60));
        System.out.printf("ํฉ๊ณ : %d์\n", calculateTotalPrice());

        System.out.println("์ด์ ์ผ๋ก ๋์๊ฐ๋ ค๋ฉด ์ํฐ๋ฅผ ๋๋ฅด์ธ์. ");
        scanner.nextLine();
    }

    public void printCartItemDetails() {

        for (Product product : items) {
            if (product instanceof BugerSet) {
                BugerSet burgerSet = (BugerSet) product; // ์๋ ์ค๋ช ์ฐธ๊ณ 
                System.out.printf(
                        "  %s %6d์ (%s(์ผ์ฒฉ %d๊ฐ), %s(๋นจ๋ %s))\n",
                        product.getName(),
                        product.getPrice(),
                        burgerSet.getSide().getName(),
                        burgerSet.getSide().getKetchup(),
                        burgerSet.getDrink().getName(),
                        burgerSet.getDrink().hasStraw() ? "์์" : "์์"
                );
            }
            else if (product instanceof Hamburger) {
                System.out.printf(
                        "  %-8s %6d์ (๋จํ)\n",
                        product.getName(),
                        product.getPrice()
                );
            }
            else if (product instanceof Side) {
                System.out.printf(
                        "  %-8s %6d์ (์ผ์ฒฉ %d๊ฐ)\n",
                        product.getName(),
                        product.getPrice(),
                        ((Side) product).getKetchup()  // ์๋ ์ค๋ช ์ฐธ๊ณ 
                );
            }
            else if (product instanceof Drink) {
                System.out.printf(
                        "  %-8s %6d์ (๋นจ๋ %s)\n",
                        product.getName(),
                        product.getPrice(),
                        ((Drink) product).hasStraw() ? "์์" : "์์"  // ์๋ ์ค๋ช ์ฐธ๊ณ 
                );
            }
        }
    }

    public int calculateTotalPrice() {
        int totalPrice = 0;
        for (Product product : items) {
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }

    public void addToCart(int productId) {

        Product product = productRepository.findById(productId);

        chooseOption(product);

        //Todo : ์ข์ด๋ฐํ์ธ

        if (product instanceof Hamburger) {
            Hamburger hamburger = (Hamburger) product;
            if (hamburger.isBurgerSet()) product = composeSet(hamburger);
        }

        Product newProduct;
        if (product instanceof Hamburger) newProduct = new Hamburger((Hamburger) product);
        else if (product instanceof Side) newProduct = new Side((Side) product);
        else if (product instanceof Drink) newProduct = new Drink((Drink) product);
        else newProduct = new BugerSet((BugerSet) product);


//Todo New Product

        Product[] newItems = new Product[items.length + 1];  // NI = [0]  I =[]
        System.arraycopy(items, 0, newItems, 0, items.length); // NI = [product]  I =[]
        newItems[newItems.length - 1] = newProduct; // NI = [0]  I =[]
        items = newItems; // NI = [product]  I = [product]

        System.out.printf("[๐ฃ] %s๋ฅผ(์) ์ฅ๋ฐ๊ตฌ๋์ ๋ด์์ต๋๋ค.\n", product.getName());
    }



    private void chooseOption(Product product) {

        String input;

        if (product instanceof Hamburger) {
            System.out.printf(
                    "๋จํ์ผ๋ก ์ฃผ๋ฌธํ์๊ฒ ์ด์? (1)_๋จํ(%d์) (2)_์ธํธ(%d์)\n",
                    product.getPrice(),
                    ((Hamburger) product).getBurgerSetPrice()
            );
            input = scanner.nextLine();
            if (input.equals("2")) ((Hamburger) product).setIsBurgerSet(true);
            //Todo ํ๋ฒ๊ฑฐ 1๋ฒ false
            else if (input.equals("1")) ((Hamburger) product).setIsBurgerSet(false);
        }
        else if (product instanceof Side) {
            System.out.println("์ผ์ฒฉ์ ๋ช๊ฐ๊ฐ ํ์ํ์ ๊ฐ์?");
            input = scanner.nextLine();
            ((Side) product).setKetchup(Integer.parseInt(input));
        }
        else if (product instanceof Drink) {
            System.out.println("๋นจ๋๊ฐ ํ์ํ์ ๊ฐ์? (1)_์ (2)_์๋์ค");
            input = scanner.nextLine();
            if (input.equals("2")) ((Drink) product).setHasStraw(false);

        }
    }

    private BugerSet composeSet(Hamburger hamburger) {
        System.out.println("์ฌ์ด๋๋ฅผ ๊ณจ๋ผ์ฃผ์ธ์");
        menu.printSides(false);

        String sideId = scanner.nextLine();
        Side side = (Side) productRepository.findById(Integer.parseInt(sideId));
        Side newSide = new Side(side);
        chooseOption(side);

        System.out.println("์๋ฃ๋ฅผ ๊ณจ๋ผ์ฃผ์ธ์.");
        menu.printDrinks(false);

        String drinkId = scanner.nextLine();
        Drink drink = (Drink) productRepository.findById(Integer.parseInt(drinkId));
        Drink newDrink = new Drink(drink);
        chooseOption(drink);

        String name = hamburger.getName() + "์ธํธ";
        int price = hamburger.getBurgerSetPrice();
        int kcal = hamburger.getKcal() + side.getKcal() + drink.getKcal();

        return new BugerSet(name, price, kcal, hamburger, newSide, newDrink);
    }



}
