package org.yearup.accountingledger;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static org.yearup.accountingledger.Ledger.*;

public class Reports {
    public static void reportsMenu() {
        Scanner scanner = new Scanner(System.in);
        String menu = """
                Reports Menu
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
            case 1 -> monthCurrentDate();
            case 2 -> previousMonth();
            case 3 -> yearCurrentDate();
            case 4 -> previousYear();
            case 5 -> searchVendor();
            case 0 -> reportsMenu();
            default -> System.out.println("Invalid input. Please try again!");
        }
    }


    public static void monthCurrentDate() {
        //range from 1st of month to current date
        // for every date within the range print them
        LocalDate currentDate = LocalDate.now();
        LocalDate firstOfCurrentMonth = currentDate.withDayOfMonth(1);
        System.out.println("\n-------- All Transactions From " + firstOfCurrentMonth.format(DateTimeFormatter.ofPattern("MMMM, dd")) + " To " +
                currentDate.format(DateTimeFormatter.ofPattern("MMMM, dd")) +"--------\n");
        for (Transaction i : transactions) {
            //
            if (isBetween(currentDate, firstOfCurrentMonth, i)) {
                System.out.println(
                        i.getDate() + " " +
                                i.getTime() + " " +
                                i.getDescription() + " " +
                                i.getVendor() + " " +
                                i.getAmount()
                );
            }
        } returnMenus();
    }

    //compares the date of each item to be in an inclusive range from the current date back to the first of the month
    private static boolean isBetween(LocalDate currentDate, LocalDate firstOfCurrentMonth, Transaction i) {
        return (i.getDate().isBefore(currentDate) || i.getDate().isEqual(currentDate)) && (i.getDate().isAfter(firstOfCurrentMonth) || i.getDate().isEqual(firstOfCurrentMonth));
    }

    public static void previousMonth() {
        transactions = getTransactions();
        Collections.sort(transactions, new Comparator<Transaction>() {
            public int compare(Transaction t1, Transaction t2) {
                return t2.getDate().compareTo(t1.getDate());
            }
        });
        LocalDate today = LocalDate.now();
        LocalDate previousMonth = today.minusMonths(1);

        //creates an array list that add the transactions that only match the previous month and prints them out
        List<Transaction> previousMonthTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.getDate();
            if (transactionDate.isAfter(previousMonth.withDayOfMonth(1).minusDays(1))
                    && transactionDate.isBefore(today.withDayOfMonth(1))) {
                previousMonthTransactions.add(transaction);
            }
        }
        System.out.println("\n-------- Previous Month: " + previousMonth.getMonth() + "--------");
        for (Transaction i : previousMonthTransactions) {
            System.out.println(
                    i.getDate() + " " +
                            i.getTime() + " " +
                            i.getDescription() + " " +
                            i.getVendor() + " " +
                            i.getAmount()
            );
        } returnMenus();
    }

    public static void yearCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate begOfYear = currentDate.with(firstDayOfYear());
        System.out.println("\n-------- All Transactions From " + begOfYear.format(DateTimeFormatter.ofPattern("MMMM, dd")) + " To " +
                currentDate.format(DateTimeFormatter.ofPattern("MMMM, dd")) +"--------\n");
        printHeader();
        for (Transaction i : transactions) {
            //
            if (between(currentDate, begOfYear, i)) {
                System.out.printf("%-15s %-15s %-25s %-15s %-10.2f\n", i.getDate(), i.getTime(), i.getDescription(), i.getVendor(), i.getAmount());
            }
        } returnMenus();
    }

    //compares the date of each item to be in an inclusive range from the current date back to the first of the year
    private static boolean between(LocalDate currentDate, LocalDate begOfYear, Transaction i) {
        return (i.getDate().isBefore(currentDate) || i.getDate().isEqual(currentDate)) && (i.getDate().isAfter(begOfYear) || i.getDate().isEqual(begOfYear));
    }

    public static void previousYear() {
        System.out.println("Enter the year for the transactions you would like to search by: ");
        Scanner scanner = new Scanner(System.in);

    }

    //allows the user to search by the vendor name
    //for loop goes through each transaction and only prints those that match the user selection
    public static void searchVendor() {
        System.out.println("Enter the vendor's name you would like to search by: ");
        Scanner scanner = new Scanner(System.in);
        String vendor = scanner.nextLine();

        System.out.println("--------------------------------All Transactions From " + vendor + "-------------------------------- ");
        printHeader();
        for (Transaction i : transactions) {
            if (i.getVendor().equalsIgnoreCase(vendor)) {
                System.out.printf("%-15s %-15s %-25s %-15s %-10.2f\n", i.getDate(), i.getTime(), i.getDescription(), i.getVendor(), i.getAmount());
            }
        } returnMenus();
    }

    //displays all menus the user has encountered and allows them to select one or exit the program
    public static void returnMenus() {
        Scanner scanner = new Scanner(System.in);
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
            case "X" -> {
                System.out.println("Exiting Java 10 Accounting ledger");
                System.exit(0);
            }
            default -> System.out.println("Invalid input. Please try again!");
        }
    }
}
