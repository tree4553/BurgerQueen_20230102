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
        System.out.println("[🔻] 메뉴");
        System.out.println("-".repeat(60));

        printHanburgers(true);
        printSides(true);
        printDrinks(true);

        System.out.println();
        System.out.println("🧺 (0) 장바구니");
        System.out.println("📦 (+) 주문하기");
        System.out.println("-".repeat(60));
        System.out.print("[📣] 메뉴를 선택해주세요 : ");
    }

    protected void printDrinks(boolean printPrice) {
        System.out.println("🥤 음료");
        for (Product product : products) {
            if (product instanceof Drink) {
                printEachMenu(product,printPrice);
            }
        }
        System.out.println();
    }

    protected void printSides(boolean printPrice) {
        System.out.println("🍟 사이드");
        for (Product product : products) {
            if (product instanceof Side) {
                printEachMenu(product,printPrice);
            }
        }
        System.out.println();
    }

    private void printHanburgers(boolean printPrice) {
        System.out.println("🍔 햄버거");
        for (Product product : products) {
            if (product instanceof Hamburger) {
                printEachMenu(product,printPrice);
            }
        }
        System.out.println();
    }

    private static void printEachMenu(Product product, boolean printPrice) {
        if (printPrice) System.out.printf("   (%d) %s %5dKcal %5d원\n", product.getId(), product.getName(), product.getKcal(), product.getPrice());
        else System.out.printf("   (%d) %s %5dKcal\n", product.getId(), product.getName(), product.getKcal());
    }


    //이너클래스 선언!
    class Info {
        int id;
        String name;
        int price;
        int kcal;

    }

//    sudo
    /*
    *을 받으면!
    * Product를 추가!
    * void
    * 매개변수 X
    /
     */
    public void addMenu() {
        /*
        메뉴를 알아야함.(id name kcal price setprice)
        Scanner로 입력을 받아야 함
        추가 ! (setter)
        추가 됐는지 화면 출력 후 엔터 입력하면 초기화면으로!
         */


        System.out.println("현재 메뉴");
        System.out.println("-".repeat(60));
        printHanburgers(true);
        printSides(true);
        printDrinks(true);
        System.out.println("-".repeat(60));
        System.out.println("메뉴를 추가해주세요");

        /*
        입력을 받아야 하는 자료 (int id, String name, int price, int kcal, boolean isBurgerSet, int burgerSetPrice)
         - 카테고리에 따라 다름
         - 카테고리별로 다른 형태로 받아야 함.

         스캐너로 카테고리를 받아야함.
         */

        Info info = new Info();
        Product newProduct;
        boolean isBurgerSet;
        int burgerSetPrice;

        Scanner scanner = new Scanner(System.in);
        System.out.println("추가하실 카테고리를 선택해주세요 (1)_햄버거 (2)_사이드 (3)_음료");
        String input = scanner.nextLine();
        switch(input) {
            case "1":
                commonInput(info, scanner);

                System.out.println("세트 가격을 입력해주세요");
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
        System.out.println(info.id +"번째 상품의 정보 입력해주세요");

        System.out.println("이름을 입력해주세요");
        info.name = scanner.nextLine();

        System.out.println("가격을 입력해주세요");
        info.price = Integer.parseInt(scanner.nextLine());

        System.out.println("열량을 입력해주세요");
        info.kcal = Integer.parseInt(scanner.nextLine());
    }

}
