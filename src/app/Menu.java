package app;

import app.product.Product;
import app.product.subproduct.Drink;
import app.product.subproduct.Hamburger;
import app.product.subproduct.Side;

import java.util.Arrays;
import java.util.Scanner;

public class Menu {
    private Product[] products;

    public Menu(Product[] products) {
        this.products = products;
    }

    public void printMenu() {
        System.out.println("[๐ป] ๋ฉ๋ด");
        System.out.println("-".repeat(60));

        printHanburgers(true);
        printSides(true);
        printDrinks(true);

        System.out.println();
        System.out.println("๐งบ (0) ์ฅ๋ฐ๊ตฌ๋");
        System.out.println("๐ฆ (+) ์ฃผ๋ฌธํ๊ธฐ");
        System.out.println("-".repeat(60));
        System.out.print("[๐ฃ] ๋ฉ๋ด๋ฅผ ์ ํํด์ฃผ์ธ์ : ");
    }

    protected void printDrinks(boolean printPrice) {
        System.out.println("๐ฅค ์๋ฃ");
        for (Product product : products) {
            if (product instanceof Drink) {
                printEachMenu(product,printPrice);
            }
        }
        System.out.println();
    }

    protected void printSides(boolean printPrice) {
        System.out.println("๐ ์ฌ์ด๋");
        for (Product product : products) {
            if (product instanceof Side) {
                printEachMenu(product,printPrice);
            }
        }
        System.out.println();
    }

    private void printHanburgers(boolean printPrice) {
        System.out.println("๐ ํ๋ฒ๊ฑฐ");
        for (Product product : products) {
            if (product instanceof Hamburger) {
                printEachMenu(product,printPrice);
            }
        }
        System.out.println();
    }

    private static void printEachMenu(Product product, boolean printPrice) {
        if (printPrice) System.out.printf("   (%d) %s %5dKcal %5d์\n", product.getId(), product.getName(), product.getKcal(), product.getPrice());
        else System.out.printf("   (%d) %s %5dKcal\n", product.getId(), product.getName(), product.getKcal());
    }


    //์ด๋ํด๋์ค ์ ์ธ!
    class Info {
        int id;
        String name;
        int price;
        int kcal;

    }

//    sudo
    /*
    *์ ๋ฐ์ผ๋ฉด!
    * Product๋ฅผ ์ถ๊ฐ!
    * void
    * ๋งค๊ฐ๋ณ์ X
    /
     */
    public void addMenu() {
        /*
        ๋ฉ๋ด๋ฅผ ์์์ผํจ.(id name kcal price setprice)
        Scanner๋ก ์๋ ฅ์ ๋ฐ์์ผ ํจ
        ์ถ๊ฐ ! (setter)
        ์ถ๊ฐ ๋๋์ง ํ๋ฉด ์ถ๋ ฅ ํ ์ํฐ ์๋ ฅํ๋ฉด ์ด๊ธฐํ๋ฉด์ผ๋ก!
         */


        System.out.println("ํ์ฌ ๋ฉ๋ด");
        System.out.println("-".repeat(60));
        printHanburgers(true);
        printSides(true);
        printDrinks(true);
        System.out.println("-".repeat(60));
        System.out.println("๋ฉ๋ด๋ฅผ ์ถ๊ฐํด์ฃผ์ธ์");

        /*
        ์๋ ฅ์ ๋ฐ์์ผ ํ๋ ์๋ฃ (int id, String name, int price, int kcal, boolean isBurgerSet, int burgerSetPrice)
         - ์นดํ๊ณ ๋ฆฌ์ ๋ฐ๋ผ ๋ค๋ฆ
         - ์นดํ๊ณ ๋ฆฌ๋ณ๋ก ๋ค๋ฅธ ํํ๋ก ๋ฐ์์ผ ํจ.

         ์ค์บ๋๋ก ์นดํ๊ณ ๋ฆฌ๋ฅผ ๋ฐ์์ผํจ.
         */

        Info info = new Info();
        Product newProduct;
        boolean isBurgerSet;
        int burgerSetPrice;

        Scanner scanner = new Scanner(System.in);
        System.out.println("์ถ๊ฐํ์ค ์นดํ๊ณ ๋ฆฌ๋ฅผ ์ ํํด์ฃผ์ธ์ (1)_ํ๋ฒ๊ฑฐ (2)_์ฌ์ด๋ (3)_์๋ฃ");
        String input = scanner.nextLine();
        switch(input) {
            case "1":
                commonInput(info, scanner);

                System.out.println("์ธํธ ๊ฐ๊ฒฉ์ ์๋ ฅํด์ฃผ์ธ์");
                burgerSetPrice = Integer.parseInt(scanner.nextLine());

                newProduct = new Hamburger(info.id,info.name,info.price,info.kcal,false,burgerSetPrice);
                break;
            case "2" :
                commonInput(info, scanner);
                newProduct = new Side(info.id,info.name,info.price,info.kcal,1);
                break;
            default :
                commonInput(info, scanner);
                newProduct = new Drink(info.id,info.name,info.price,info.kcal,true);
        }
        Product[] newProducts = new Product[products.length+1];

        newProducts = Arrays.copyOf(products,newProducts.length);
        newProducts[newProducts.length-1] = newProduct;

        products = Arrays.copyOf(newProducts,newProducts.length);





    }

    private void commonInput(Info info, Scanner scanner) {
        info.id = products.length + 1;
        System.out.println(info.id +"๋ฒ์งธ ์ํ์ ์ ๋ณด ์๋ ฅํด์ฃผ์ธ์");

        System.out.println("์ด๋ฆ์ ์๋ ฅํด์ฃผ์ธ์");
        info.name = scanner.nextLine();

        System.out.println("๊ฐ๊ฒฉ์ ์๋ ฅํด์ฃผ์ธ์");
        info.price = Integer.parseInt(scanner.nextLine());

        System.out.println("์ด๋์ ์๋ ฅํด์ฃผ์ธ์");
        info.kcal = Integer.parseInt(scanner.nextLine());
    }

}
