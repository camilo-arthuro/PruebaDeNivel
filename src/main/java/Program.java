import Classes.Client;
import Classes.Distributor;
import Classes.Exceptions.NoDistributorAvailable;
import Classes.Order;
import Classes.Product;

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

        do {
            System.out.println("Welcome, how may I help you?");
            System.out.println("1. Create new order\n " +
                    "2. Mark order as delivered\n " +
                    "3. Pending orders\n " +
                    "4. Orders delivered\n " +
                    "0. Exit");
            option = input.nextInt();
            switch (option){
                case 1 :
                    createOrder(orderList);
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

    public static void createOrder(ArrayList<Order> orderList) throws NoDistributorAvailable {
        ArrayList<Distributor> verifyDistributors = distributorList();
        Client client = null;
        Distributor distributor = null;
        ArrayList<Product> productForClient = null;

        if (verifyDistributors.isEmpty()){
            throw new NoDistributorAvailable("No distributors available, please mark orders as delivered");
        }else{
            client = chooseClient();
            distributor = assignDistributor();
            productForClient = addProducts();

            Order order = new Order(client, distributor, productForClient);
            createTicket(order);
            giftMessage(order.getProductsList());
            System.out.println("Order " + order.getOrderId() + " was created");
            orderList.add(order);
            verifyDistributors.remove(distributor);
        }
    }

    public static void createTicket(Order order){
        ArrayList<Product> products = order.getProductsList();
        double totalPrice = 0d;
        double additional = 0d;

        for (int i = 0; i < products.size(); i++) {
            System.out.println("Product : " + products.get(i).getName() +
                    ". Price : " + products.get(i).getPrice() + " €");
            totalPrice += products.get(i).getPrice();
        }
        additional = additional(totalPrice,order.getDistributor());

        System.out.println("Additional : " + additional +" €\n" +
                "Total price : " + (totalPrice + additional) + " €");
    }

    public static double additional(double price, Distributor distributor){
        double additional = 0d;
        int option = 0;
        boolean working = true;

        do {
            System.out.println("What do you need to deliver the order? \n " +
                    "1. Bicycle\n " +
                    "2. Motorcycle\n " +
                    "3. Walk");
            option = input.nextInt();
            switch (option){
                case 1 :
                    additional = distributor.bicycleDelivery(price);
                    working = false;
                    break;
                case 2 :
                    additional = distributor.motorcycleDelivery(price);
                    working = false;
                    break;
                case 3 :
                    working = false;
                    break;
                default:
                    System.out.println("invalid option, try again");
            }
        } while (working);
        return additional;
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
        ArrayList<Distributor> addDistributor = distributorList();
        Distributor distributor = null;
        Order order = null;

        System.out.println("What order was delivered?");
        for (int i = 0; i < orderList.size(); i++) {
            System.out.println((i+1) + ". " + orderList.get(i).getOrderId());
        }
        option = input.nextInt();
        order = orderList.get(option-1);
        distributor = order.getDistributor();
        addDistributor.add(distributor);
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
        System.out.println("> " + (option = input.nextInt()));
        client = clientList.get(option-1);

        return client;
    }

    public static Distributor assignDistributor(){
        ArrayList<Distributor> distributorList = distributorList();
        Random random = new Random();
        int position = random.nextInt(distributorList.size());

        Distributor distributor = distributorList.get(position);

        return distributor;
    }

    public static ArrayList<Product> addProducts(){
        ArrayList<Product> productList = productList();
        ArrayList<Product> products = null;
        int option = -1;
        boolean working = true;

        do {
            System.out.println("Choose a product or press 0 to finish: ");
            for (int i = 0; i < productList.size(); i++) {
                System.out.println((i + 1) + ". " + productList.get(i).getName());
            }
            option = input.nextInt();

            if (option == 0){
                System.out.println("Good bye!");
                working = false;
            } else if (option < 0 || option >= productList.size()){
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

        Distributor distributor1 = new Distributor("Pol");
        Distributor distributor2 = new Distributor("Daniel");
        Distributor distributor3 = new Distributor("David");

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

        Product burrito = new Product("Burrito", 6.5);
        Product hamburger = new Product("Hamburger", 8.9);
        Product kebab = new Product("Kebab", 4.5);
        Product pizza = new Product("Pizza", 7.9);

        products.add(burrito);
        products.add(hamburger);
        products.add(kebab);
        products.add(pizza);

        return products;
    }


}
