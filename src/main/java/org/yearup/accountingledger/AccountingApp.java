package org.yearup.accountingledger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AccountingApp {
    public static void main(String[] args) {
        homeScreen();

    }

    //display the home screen menu to the user and allows the user to select a menu option
    public static void homeScreen() {
        Scanner scanner = new Scanner(System.in);
        String heading = """
                Welcome to the Java 10 Account Manager
                """;
        String menu = """
                Main Menu
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
                System.out.println("Exiting Jave 10 Accounting ledger");
                System.exit(0);
            }
            default -> System.out.println("Invalid input. Please try again!");
        }
    }
    //adds a deposit to the transactions csv file
    public static void addDeposit() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Date YYYY-MM-DD: ");
        String date = scanner.nextLine();
        System.out.println("Enter the time HH:MM:SS");
        String time = scanner.nextLine();
        System.out.println("Enter item description: ");
        String description = scanner.nextLine();
        System.out.println("Enter Vendor: ");
        String vendor = scanner.nextLine();
        System.out.println("Enter deposit amount: ");
        double amount = scanner.nextDouble();

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
        homeScreen();
    }

    //allows the user to enter a payment details
    public static void makePayment() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Date YYYY-MM-DD: ");
        String date = scanner.nextLine();
        System.out.println("Enter the time HH:MM:SS");
        String time = scanner.nextLine();
        System.out.println("Enter item description: ");
        String description = scanner.nextLine();
        System.out.println("Enter Vendor: ");
        String vendor = scanner.nextLine();
        System.out.println("Enter deposit amount: ");
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
        homeScreen();
    }

    //takes user to the ledger class menu to carry out those tasks
    public static void showLedger() {
        Ledger.showLedger();
    }

}
