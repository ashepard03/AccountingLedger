package org.yearup.accountingledger;

import java.time.LocalDate;
import java.util.Scanner;

import static org.yearup.accountingledger.Ledger.transactions;

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
        for (Transaction i : transactions){
            //
            if (isBetween(currentDate, firstOfCurrentMonth, i)){
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

    //compares the date of each item to be in an incusize range from the current date back to the first of the month
    private static boolean isBetween(LocalDate currentDate, LocalDate firstOfCurrentMonth, Transaction i) {
        return (i.getDate().isBefore(currentDate) || i.getDate().isEqual(currentDate)) && (i.getDate().isAfter(firstOfCurrentMonth) || i.getDate().isEqual(firstOfCurrentMonth));
    }

    public static void previousMonth() {

    }

    public static void yearCurrentDate() {

    }

    public static void previousYear() {

    }

    public static void searchVendor() {

    }

}
