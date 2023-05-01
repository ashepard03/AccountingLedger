package org.yearup.accountingledger;

import java.io.FileReader;
import java.util.Scanner;

public class Ledger {
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


    }

    public static void viewDeposits() {

    }

    public static void viewPayments() {

    }

    public static void reportsMenu(){

    }
}
