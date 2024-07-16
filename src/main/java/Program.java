import Classes.*;
import Classes.Distributors.BicycleDistributor;
import Classes.Distributors.Distributor;
import Classes.Distributors.MotorcycleDistributor;
import Classes.Distributors.WalkingDistributor;
import Classes.Exceptions.NoDistributorAvailable;
import Classes.Products.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Program {

    static Scanner input = new Scanner(System.in);

    public static void runProgram() throws NoDistributorAvailable {
        int option = -1;
        boolean working = true;
        ArrayList<Order> orderList = new ArrayList<>();
        ArrayList<Order> ordersDelivered = new ArrayList<>();
        ArrayList<Distributor> distributorList = distributorList();

        do {
            System.out.println("Welcome, how may I help you?");
            System.out.println(" 1. Create new order\n " +
                    "2. Mark order as delivered\n " +
                    "3. Pending orders\n " +
                    "4. Orders delivered\n " +
                    "0. Exit");
            System.out.print("> ");
            option = input.nextInt();
            switch (option){
                case 1 :
                    createOrder(orderList, distributorList);
                    break;
                case 2 :
                    markAsDelivered(orderList, ordersDelivered);
                    break;
                case 3 :
                    pendingOrders(orderList);
                    break;
                case 4 :
                    ordersMarkedAsDelivered(ordersDelivered);
                    break;
                case 0 :
                    System.out.println("Good bye!");
                    working = false;
                    break;
                default:
                    System.out.println("Invalid option, try again!");
            }
        }while (working);

    }

    public static void createOrder(ArrayList<Order> orderList, ArrayList<Distributor> distributorList) {
        try {
            Distributor distributor = assignDistributor(distributorList);
            Client client = chooseClient();
            ArrayList<Product> productForClient = addProducts();
            Order order = new Order(client, distributor, productForClient);

            createTicket(order);
            giftMessage(order.getProductsList());
            System.out.println("Order " + order.getOrderId() + " was created\n");
            orderList.add(order);
        } catch (NoDistributorAvailable e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createTicket(Order order){
        ArrayList<Product> products = order.getProductsList();
        double totalPrice = 0d;
        double additional = 0d;

        System.out.println("-----------------------------------------------");
        System.out.println("Ticket number : " + order.getOrderId() + " \n" +
                "Distributor assigned : " + order.getDistributor().getName() + " \n" +
                "Product / Price");
        for (int i = 0; i < products.size(); i++) {
            System.out.println(products.get(i).getName() + " " + products.get(i).getPrice() + " €");
            totalPrice += products.get(i).getPrice();
        }
        additional = additional(totalPrice,order.getDistributor());

        System.out.println("\nAdditional : " + additional +" €\n" +
                "Total price : " + (totalPrice + additional) + " €");
        System.out.println("-----------------------------------------------");
    }

    public static double additional(double price, Distributor distributor){

        return distributor.additional(price);
    }

    public static void giftMessage(ArrayList<Product> products){
        int counterBurrito = 0;
        int counterHamburger = 0;

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getName().equalsIgnoreCase("Burrito")){
                counterBurrito++;
            } else if (products.get(i).getName().equalsIgnoreCase("Hamburger")){
                counterHamburger++;
            }
        }
        if (counterBurrito > 0 && counterHamburger > 0){
            System.out.println(counterBurrito + products.get(0).burritoGift());
            System.out.println(counterHamburger + products.get(0).hamburgerGift());
        } else if (counterBurrito > 0 && counterHamburger == 0) {
            System.out.println(counterBurrito + products.get(0).burritoGift());
        } else if (counterBurrito == 0 && counterHamburger > 0) {
            System.out.println(counterHamburger + products.get(0).hamburgerGift());
        }
    }

    public static void markAsDelivered(ArrayList<Order> orderList, ArrayList<Order> ordersDelivered){
        int option = -1;
        Distributor distributor = null;
        Order order = null;

        System.out.println("What order was delivered?");
        for (int i = 0; i < orderList.size(); i++) {
            System.out.println((i+1) + ". " + orderList.get(i).getOrderId());
        }
        option = input.nextInt();
        order = orderList.get(option-1);
        distributor = order.getDistributor();
        distributor.setAvailability(true);
        ordersDelivered.add(order);
        orderList.remove(order);

        System.out.println("Order " + order.getOrderId() + " marked as delivered");
        System.out.println("Distributor " + distributor.getName() + " is ready to work");
    }

    public static void pendingOrders(ArrayList<Order> orderList){
        System.out.println("Pending orders : ");
        for (int i = 0; i < orderList.size(); i++) {
            System.out.println((i+1) + ". " + orderList.get(i).getOrderId());
        }
    }

    public static void ordersMarkedAsDelivered(ArrayList<Order> ordersDelivered){
        System.out.println("Orders delivered");
        for (int i = 0; i < ordersDelivered.size(); i++) {
            System.out.println((i+1) + ". " + ordersDelivered.get(i).getOrderId());
        }
    }

    public static Client chooseClient(){
        ArrayList<Client> clientList = clientList();
        Client client = null;
        int option = -1;

        System.out.println("Choose a client : ");
        for (int i = 0; i < clientList.size(); i++) {
            System.out.println((i + 1) + ". " + clientList.get(i).getName());
        }
        System.out.print("> ");
        option = input.nextInt();
        client = clientList.get(option-1);

        return client;
    }

    public static Distributor assignDistributor(ArrayList<Distributor> distributorList) throws NoDistributorAvailable {
        ArrayList<Distributor> verification = new ArrayList<>();
        Random random = new Random();
        int position = 0;
        Distributor distributorAssigned = null;

        for (Distributor distributor : distributorList) {
            if (distributor.isAvailability()){
                verification.add(distributor);
            }
        }
        if (verification.isEmpty()) {
            throw new NoDistributorAvailable("No distributors available, please mark orders as delivered");
        } else {
            position = random.nextInt(verification.size());
            distributorAssigned = verification.get(position);
            distributorAssigned.setAvailability(false);
        }
        return distributorAssigned;
    }

    public static ArrayList<Product> addProducts(){
        ArrayList<Product> productList = productList();
        ArrayList<Product> products = new ArrayList<>();
        int option = -1;
        boolean working = true;

        do {
            System.out.println("Choose a product or press 0 to finish: ");
            for (int i = 0; i < productList.size(); i++) {
                System.out.println((i + 1) + ". " + productList.get(i).getName());
            }
            System.out.print("> ");
            option = input.nextInt();

            if (option == 0){
                System.out.println("Products added to the list!\n");
                working = false;
            } else if (option < 0 || option > productList.size()){
                System.out.println("Invalid option, try again");
            } else {
                products.add(productList.get(option-1));
                System.out.println("Product " + productList.get(option-1).getName() + " added!");
            }
        }while (working);

        return products;
    }

    public static ArrayList<Distributor> distributorList(){
        ArrayList<Distributor> distributors = new ArrayList<>();

        Distributor distributor1 = new BicycleDistributor("Pol");
        Distributor distributor2 = new MotorcycleDistributor("Daniel");
        Distributor distributor3 = new WalkingDistributor("David");

        distributors.add(distributor1);
        distributors.add(distributor2);
        distributors.add(distributor3);

        return distributors;
    }

    public static ArrayList<Client> clientList(){
        ArrayList<Client> clients = new ArrayList<>();

        Client client1 = new Client("Carlos", "Carre uno 111, 1 - 1");
        Client client2 = new Client("Arthuro", "Carre dos 222, 2 - 2");
        Client client3 = new Client("Camilo", "Carre tres 333, 3 - 3");

        clients.add(client1);
        clients.add(client2);
        clients.add(client3);

        return clients;
    }

    public static ArrayList<Product> productList(){
        ArrayList<Product> products = new ArrayList<>();

        Product burrito = new Burrito("Burrito", 6.5);
        Product hamburger = new Hamburger("Hamburger", 8.9);
        Product kebab = new Kebab("Kebab", 4.5);
        Product pizza = new Pizza("Pizza", 7.9);

        products.add(burrito);
        products.add(hamburger);
        products.add(kebab);
        products.add(pizza);

        return products;
    }


}
