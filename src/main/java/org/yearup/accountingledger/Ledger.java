package org.yearup.accountingledger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Ledger {

    public static ArrayList<Transaction> transactions = getTransactions();

    // creates a method that holds an arraylist of all the transactions read from the transactions.csv file
    // also parses the strings inside the file an assigns then to appropiate variables
    public static ArrayList<Transaction> getTransactions() {
        //arraylist of transaction objects named transactions
        ArrayList<Transaction> transactions = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader("transactions.csv");
            BufferedReader bufreader = new BufferedReader(fileReader);

            String input;
            while ((input = bufreader.readLine()) != null) {
                String[] details = input.split("\\|");
                LocalDate date = LocalDate.parse(details[0]);
                LocalTime time = LocalTime.parse(details[1]);
                String desciption = details[2];
                String vendor = details[3];
                double amount = Double.parseDouble(details[4]);

                Transaction transaction = new Transaction(date, time, desciption, vendor, amount);
                transactions.add(transaction);
            }
        } catch (IOException e) {
            System.out.println("The File not found");
            System.exit(0);
        }
        return transactions;
    }

    //displays the ledger menu
    public static void showLedger() {
        Scanner scanner = new Scanner(System.in);
        String menu = """
                Ledger Menu
                [A] View All Transactions
                [D] View Deposits Only
                [P] View Payments Only
                [R] Reports
                [H] Home
                """;

        System.out.print(menu);
        String input = scanner.nextLine();

        switch (input.toUpperCase()) {
            case "A" -> viewEntries();
            case "D" -> viewDeposits();
            case "P" -> viewPayments();
            case "R" -> reports();
            case "H" -> {
                System.out.println("Returning to Home screen");
                AccountingApp.homeScreen();
            }
            default -> System.out.println("Invalid input. Please try again!");
        }
    }

    //prints out all the trasactions from the csv file by iterating through each entry
    public static void viewEntries() {
        System.out.println("All Transactions: ");
        Collections.sort(transactions, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction t1, Transaction t2) {
                return t2.getDate().compareTo(t1.getDate());
            }
        });
        for (Transaction i : transactions) {
            System.out.println(
                    i.getDate() + " " +
                            i.getTime() + " " +
                            i.getDescription() + " " +
                            i.getVendor() + " " +
                            i.getAmount()
            );
        }
    }
    public static void viewDeposits() {
        System.out.println("All Deposits: ");
        for (Transaction i : transactions) {
            if (i.getAmount() > 0) {
                System.out.println(
                        i.getDate() + " " +
                                i.getTime() + " " +
                                i.getDescription() + " " +
                                i.getVendor() + " " +
                                i.getAmount()
                );
            }
        }
    }

    public static void viewPayments() {
        System.out.println("All Payments: ");
        for (Transaction i : transactions) {
            if (i.getAmount() < 0) {
                System.out.println(
                        i.getDate() + " " +
                                i.getTime() + " " +
                                i.getDescription() + " " +
                                i.getVendor() + " " +
                                i.getAmount()
                );
            }
        }
    }

    //calls the reposts menu method from the reports class
    public static void reports(){
        Reports.reportsMenu();
    }
}
