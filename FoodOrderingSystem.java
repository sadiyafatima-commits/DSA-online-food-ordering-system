
import java.io.*;
import java.util.*;

class FoodOrderingSystem {

    static ArrayList<String> menu = new ArrayList<>();
    static ArrayList<Integer> price = new ArrayList<>();

    static Queue<String> orders = new LinkedList<>();
    static Stack<String> undo = new Stack<>();

    static int orderId = 101;

    static Scanner sc = new Scanner(System.in);

    static void showMenu() {
        System.out.println("\n--- FOOD MENU ---");
        for (int i = 0; i < menu.size(); i++) {
            System.out.println(menu.get(i) + " - Rs." + price.get(i));
        }
    }

    static void placeOrder() {
        showMenu();

        System.out.print("Enter item code (M101/M102/M103): ");
        String item = sc.next();

        int index = menu.indexOf(item);

        if (index == -1) {
            System.out.println("Item " + item + " not available.");
            return;
        }

        System.out.print("Enter customer name: ");
        String name = sc.next();

        System.out.print("Enter quantity: ");
        int qty = sc.nextInt();

        if (qty <= 0) {
            System.out.println("Invalid quantity!");
            return;
        }

        int total = price.get(index) * qty;

        String order = "O" + orderId++ + " | " + name
                + " | " + menu.get(index) + " x" + qty
                + " | Total: " + total;

        orders.add(order);
        undo.push(order);

        System.out.println("Order placed: " + order);
    }

    static void processOrder() {
        if (orders.isEmpty()) {
            System.out.println("No pending orders.");
        } else {
            System.out.println("Order prepared: " + orders.poll());
        }
    }

    static void viewOrders() {
        if (orders.isEmpty()) {
            System.out.println("No pending orders.");
        } else {
            System.out.println("\nPending Orders:");
            for (String o : orders) {
                System.out.println(o);
            }
        }
    }

    static void undoOrder() {
        if (undo.isEmpty()) {
            System.out.println("Nothing to undo.");
            return;
        }

        String last = undo.pop();
        Queue<String> temp = new LinkedList<>();

        while (!orders.isEmpty()) {
            String o = orders.poll();
            if (!o.equals(last)) {
                temp.add(o);
            }
        }

        orders = temp;
        System.out.println("Last order removed.");
    }

    static void sortMenu() {
        for (int i = 0; i < price.size(); i++) {
            for (int j = i + 1; j < price.size(); j++) {
                if (price.get(i) > price.get(j)) {
                    Collections.swap(price, i, j);
                    Collections.swap(menu, i, j);
                }
            }
        }
        System.out.println("Menu sorted by price.");
        showMenu();
    }

    static void saveOrders() {
        try {
            PrintWriter pw = new PrintWriter("orders.txt");
            for (String o : orders) {
                pw.println(o);
            }
            pw.close();
        } catch (Exception e) {
            System.out.println("File error.");
        }
    }

    public static void main(String[] args) {

        menu.add("M101 Veg Burger");
        price.add(120);

        menu.add("M102 Paneer Wrap");
        price.add(150);

        menu.add("M103 Cold Coffee");
        price.add(90);

        int choice;

        do {
            System.out.println("\n===== ONLINE FOOD ORDERING =====");
            System.out.println("1. View Menu");
            System.out.println("2. Place Order");
            System.out.println("3. Process Next Order");
            System.out.println("4. View Pending Orders");
            System.out.println("5. Sort Menu by Price");
            System.out.println("6. Undo Last Order");
            System.out.println("7. Exit");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    showMenu();
                    break;
                case 2:
                    placeOrder();
                    break;
                case 3:
                    processOrder();
                    break;
                case 4:
                    viewOrders();
                    break;
                case 5:
                    sortMenu();
                    break;
                case 6:
                    undoOrder();
                    break;
                case 7:
                    saveOrders();
                    System.out.println("Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 7);
    }
}
