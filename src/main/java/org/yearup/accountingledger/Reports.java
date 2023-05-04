package org.yearup.accountingledger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static org.yearup.accountingledger.Ledger.*;
import static org.yearup.accountingledger.AccountingApp.scanner;
public class Reports {
    //displays the menu for the reports section of the ledger and allows the user to select an option from menu
    public static void reportsMenu() {
        String menu = """
                ------Reports Menu-------
                [1] Month to Current Date
                [2] Previous Month
                [3] Year to Current Date
                [4] Previous Year
                [5] Search by Vendor
                [0] Return to Reports Menu
                """;

        System.out.print(menu);
        int input = scanner.nextInt();
        scanner.nextLine();

        switch (input) {
            case 1 -> monthToDate();
            case 2 -> previousMonth();
            case 3 -> yearToDate();
            case 4 -> previousYear();
            case 5 -> searchVendor();
            case 0 -> reportsMenu();
            default -> System.out.println("Invalid input. Please try again!");
        }
    }

    public static void monthToDate() {
        //range from 1st of month to current date
        // for every date within the range print them
        LocalDate today = LocalDate.now();
        LocalDate firstOfCurrentMonth = today.withDayOfMonth(1);
        System.out.println("\n-------- All Transactions From " + firstOfCurrentMonth.format(DateTimeFormatter.ofPattern("MMMM, dd")) + " To " +
                today.format(DateTimeFormatter.ofPattern("MMMM, dd")) +"--------");
        printHeader();

        for (Transaction i : transactions) {
            if (isBetween(today, firstOfCurrentMonth, i)) {
                System.out.printf("%-15s %-15s %-25s %-15s %-20.2f\n", i.getDate(), i.getTime(), i.getDescription(), i.getVendor(), i.getAmount());
            }
        } returnMenus();
    }

    //compares the date of each item to be in an inclusive range from the current date back to the first of the month
    private static boolean isBetween(LocalDate today, LocalDate firstOfCurrentMonth, Transaction i) {
        return (i.getDate().isBefore(today) || i.getDate().isEqual(today))
                && (i.getDate().isAfter(firstOfCurrentMonth) || i.getDate().isEqual(firstOfCurrentMonth));
    }

    public static void previousMonth() {
        LocalDate today = LocalDate.now();
        LocalDate prevMonth = today.minusMonths(1);

        //creates an array list that add the transactions that only match the previous month and prints them out
        List<Transaction> prevMonthTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.getDate();
            if (transactionDate.isAfter(prevMonth.withDayOfMonth(1).minusDays(1))
                    && transactionDate.isBefore(today.withDayOfMonth(1))) {
                prevMonthTransactions.add(transaction);
            }
        }
        System.out.println("\n------------------------------Previous Month: " + prevMonth.getMonth() + "--------------------------------");
        printHeader();
        for (Transaction i : prevMonthTransactions) {
            System.out.printf("%-15s %-15s %-25s %-15s %-20.2f\n", i.getDate(), i.getTime(), i.getDescription(), i.getVendor(), i.getAmount());
        } returnMenus();
    }

    public static void yearToDate() {//search transactions from beg. of year to the current date
        LocalDate today = LocalDate.now();
        LocalDate begOfYear = today.with(firstDayOfYear());
        System.out.println("\n---------------- All Transactions From " + begOfYear.format(DateTimeFormatter.ofPattern("MMMM, dd")) + " To " +
                today.format(DateTimeFormatter.ofPattern("MMMM, dd")) +"---------------------\n");
        printHeader();

        for (Transaction i : transactions) {
            if (between(today, begOfYear, i)) {
                System.out.printf("%-15s %-15s %-25s %-15s %-20.2f\n", i.getDate(), i.getTime(), i.getDescription(), i.getVendor(), i.getAmount());
            }
        } returnMenus();
    }

    //compares the date of each item to be in an inclusive range from the current date back to the first of the year
    private static boolean between(LocalDate today, LocalDate begOfYear, Transaction i) {
        return (i.getDate().isBefore(today) || i.getDate().isEqual(today)) && (i.getDate().isAfter(begOfYear) || i.getDate().isEqual(begOfYear));
    }

    public static void previousYear() { //searches for previous year only
        LocalDate today = LocalDate.now();
        System.out.println("--------------------------------Previous Year--------------------------------------\n");
        printHeader();
        for (Transaction i : transactions){
            LocalDate year =  i.getDate();
            if (year.getYear() == today.getYear() -1){
                System.out.printf("%-15s %-15s %-25s %-15s %-20.2f\n", i.getDate(), i.getTime(), i.getDescription(), i.getVendor(), i.getAmount());
            }
        }returnMenus();
    }

    //allows the user to search by the vendor name. For loop goes through each transaction and only prints those that match the user selection.
    public static void searchVendor() {
        System.out.println("Enter the vendor's name you would like to search by: ");
        Scanner scanner = new Scanner(System.in);
        String vendor = scanner.nextLine();

        System.out.println("--------------------------------All Transactions From " + vendor + "-------------------------------- ");
        printHeader();
        for (Transaction i : transactions) {
            if (i.getVendor().equalsIgnoreCase(vendor)) {
                System.out.printf("%-15s %-15s %-25s %-15s %-20.2f\n", i.getDate(), i.getTime(), i.getDescription(), i.getVendor(), i.getAmount());
            }
        } returnMenus();
    }

    //displays all menus the user has encountered and allows them to select one or exit the program
    public static void returnMenus() {
        String menu = """
                [H] Home Screen
                [L] Ledger Menu
                [R] Reports Menu
                [E] Exit program
                """;
        System.out.println("\nWhich menu would you like to return to?");
        System.out.println("---------------------------------------");
        System.out.println(menu);

        String input = scanner.nextLine();
        switch (input.toUpperCase()) {
            case "H" -> AccountingApp.homeScreen();
            case "L" -> Ledger.showLedger();
            case "R" -> reportsMenu();
            case "E" -> {
                System.out.println("Exiting Java 10 Accounting ProgramS");
                System.exit(0);
            }
            default -> System.out.println("Invalid input. Please try again!");
        }
    }
}
