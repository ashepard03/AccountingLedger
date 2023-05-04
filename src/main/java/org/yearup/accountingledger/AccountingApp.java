package org.yearup.accountingledger;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AccountingApp {
    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        homeScreen();
    }

    //display the home screen menu to the user and allows the user to select a menu option
    public static void homeScreen() {
        boolean screenDone = false;
        while (!screenDone) {
            String heading = """
                    Welcome to the Java 10 Account Manager
                    --------------------------------------
                    """;
            String menu = """
                    --------Main Menu-------
                    [D] Add deposit
                    [P] Make payment (debit)
                    [L] Ledger
                    [E] Exit
                    """;

            System.out.println(heading);
            System.out.print(menu);
            String input = scanner.nextLine();
            //allows me to use the switch statement without accidentally falling through to another case
            // completes the method associated with th user menu choice
            switch (input.toUpperCase()) {
                case "D" -> addDeposit();
                case "P" -> makePayment();
                case "L" -> showLedger();
                case "X" -> {
                    screenDone = true;
                    System.out.println("Exiting Jave 10 Accounting ledger");
                    System.exit(0);
                }
                default -> System.out.println("Please select another option from the menu");
            }
        }
    }

    public static void addDeposit() {//adds a deposit to the transactions csv file
        //automatically adds the date and time for any deposit added to the transactions file
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter tf = DateTimeFormatter.ofPattern("HH:mm:ss");
        String date = now.format(df);
        String time = now.format(tf);

        System.out.println("Enter item description: ");
        String description = scanner.nextLine();
        System.out.println("Enter Vendor: ");
        String vendor = scanner.nextLine();
        System.out.println("Enter deposit amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        try {
            try (FileWriter fileWriter = new FileWriter("transactions.csv", true)) {
                fileWriter.write("\n" +
                        date + "|" +
                        time + "|" +
                        description + "|" +
                        vendor + "|" +
                        amount);
            }
            System.out.println("Deposit added successfully");
        } catch (IOException e) {
            System.out.println("Error inputting data ");
            throw new RuntimeException(e);
        }
    }

    public static void makePayment() {//allows the user to enter a payment details
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter tf = DateTimeFormatter.ofPattern("HH:mm:ss");
        String date = now.format(df);
        String time = now.format(tf);

        System.out.println("Enter item description: ");
        String description = scanner.nextLine();
        System.out.println("Enter Vendor: ");
        String vendor = scanner.nextLine();
        System.out.println("Enter payment amount: ");
        double amount = scanner.nextDouble();

        try { //writes the payment entered above in the transactions.csv file with the proper format
            try (FileWriter fileWriter = new FileWriter("transactions.csv", true)) {
                fileWriter.write("\n" +
                        date + "|" +
                        time + "|" +
                        description + "|" +
                        vendor + "|" + "-" +
                        amount);
            }
            System.out.println("Payment added successfully");
        } catch (IOException e) {
            System.out.println("Error inputting data ");
            throw new RuntimeException(e);
        }
    }

    //takes user to the ledger class menu to carry out those tasks
    public static void showLedger() {
        Ledger.showLedger();
    }

}
