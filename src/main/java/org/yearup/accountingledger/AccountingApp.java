package org.yearup.accountingledger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AccountingApp {
    public static void main(String[] args) {
        homeScreen();

    }

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

        switch (input.toUpperCase()) {
            case "D":
                addDeposit();
                break;
            case "P":
                makePayment();
                break;
            case "L":
                showLedger();
                break;
            case "X":
                System.out.println("Exiting Jave 10 Accounting ledger");
                System.exit(0);
            default:
                System.out.println("Invalid input. Please try again!");
        }
    }

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
            FileWriter fileWriter = new FileWriter("transactions.csv", true);
            fileWriter.write("\n" +
                    date + "|" +
                            time + "|" +
                            description + "|" +
                            vendor + "|" +
                            amount);
            System.out.println("Deposit added successfully");
        } catch (IOException e) {
            System.out.println("Error inputting data ");
            throw new RuntimeException(e);
        }
        homeScreen();
    }

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

        try {
            FileWriter fileWriter = new FileWriter("transactions.csv", true);
            fileWriter.write("\n" +
                    date + "|" +
                    time + "|" +
                    description + "|" +
                    vendor + "|" + "-" +
                    amount);
            System.out.println("Payment added successfully");
        } catch (IOException e) {
            System.out.println("Error inputting data ");
            throw new RuntimeException(e);
        }
        homeScreen();
    }

    public static void showLedger() {
        Ledger.showLedger();
    }

}
