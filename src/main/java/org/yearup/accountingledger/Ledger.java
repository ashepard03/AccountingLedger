package org.yearup.accountingledger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Ledger {

    public static ArrayList<Transaction> transactions = getTransactions();

    public static ArrayList<Transaction> getTransactions(){
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

                Transaction transaction = new Transaction(date, time, desciption,vendor,amount);
                transactions.add(transaction);
            }
        }
        catch (IOException e){
            System.out.println("File not found");
            System.exit(0);
        }
        return transactions;
    }
    public static void showLedger(){
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
            case "A":
                viewEntries();
                break;
            case "D":
                viewDeposits();
                break;
            case "P":
                viewPayments();
                break;
            case "R":
                reportsMenu();
                break;
            case "H":
                System.out.println("Returning to Home screen");
                AccountingApp.homeScreen();
            default:
                System.out.println("Invalid input. Please try again!");
        }
    }

    public static void viewEntries(){
        System.out.println("All transactions: ");
        for (Transaction i : transactions){
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

    }

    public static void viewPayments() {

    }

    public static void reportsMenu(){

    }
}
