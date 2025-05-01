
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AccountLedgerApp {

    // Import Scanner in the class using static
    static Scanner scanner = new Scanner(System.in);
    static String transactionFileName = "src/transaction.csv"; // Name of csv file where are transactions
    static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
    static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Where I store the methods
    public static void main(String[] args) {

        homeMenu();


    }

    /// //////////////////////////////////////////////////////////////////////////////////////

    // Create HOME MENU  method with prompts
    public static void homeMenu() {
        // Welcome the user
        System.out.println("\n      *****  Welcome to your Account Ledger Application *****       \n");

        // How the application will continue to run until the user decides to exit
        boolean homeMenuRunning = true;

        /* Create while loop to repeat a block of code until the condition is met */
        while (homeMenuRunning) {
            // Ask the user what they'd like to do/ Give prompts
            System.out.println(""" 
                    \n------------  What would you like to do today? ------------
                    (D.) Add Deposit
                    (P.) Make Payment
                    (L.) Display Ledger Screen
                    (X.) Exit""");

            // How the user makes their selection
            String userInput = scanner.nextLine().trim().toUpperCase();

            // Create switch statement to allow user to choose between options
            switch (userInput) {
                case "D":
                    // Writing what is in the addDeposit() to the file created
                    Transaction newDeposit = addDeposit();
                    writeToFile(transactionFileName, newDeposit);
                    break;
                case "P":
                    Transaction newPayment = makePayment();
                    writeToFile(transactionFileName, newPayment);
                    break;
                case "L":
                    ledgerMenu();
                    break;
                case "X":
                    System.out.println("Are you sure you want to exit? (Y/N)");
                    String userChoice = scanner.nextLine().trim().toUpperCase();

                    if (userChoice.equalsIgnoreCase("Y")) {
                        System.out.println("Thank you for using your Account Ledger Application ");
                        homeMenuRunning = false;
                    }    else {
                        homeMenuRunning = true;
                    }
                    break;
                default:
                    System.out.println("Invalid entry. Please select from the following options: 'D','P','L','X' ");


            }
        }
    }

    // addDeposit screen
    public static Transaction addDeposit() {
        // Create primitives after user input

        LocalDate ld = LocalDate.now();
        LocalTime lt = LocalTime.now();

        System.out.println("Please provide the amount of deposit");
        double amount = Double.parseDouble(scanner.nextLine());

        System.out.println("Please enter the vendor name: ");
        String vendor = scanner.nextLine().trim();

        System.out.println("Please provide a description: ");
        String description = scanner.nextLine().trim();

        System.out.println("You deposited " + amount + " to " + vendor + " on " + ld + " at " + lt.format(timeFormatter) );
        String idOfTransaction = "D";

        // Call constructor out
        // This will take the user input and create a single deposit transaction
        return new Transaction(ld, lt, description, idOfTransaction, vendor, amount);
    }

    // Create a file method to write
    // In () add the String variable, Class and object
    public static void writeToFile(String fileName, Transaction transaction) {
        // allows to add more to the file w.o losing what was inputted before
        try (FileWriter fw = new FileWriter(fileName, true)) {
            fw.write(transaction.toString() + "\n");
        } catch (IOException e) {
            System.out.println("Something went wrong " + e.getMessage()); // this is incase something goes wrong
        }
    }

    // make Payment screen
    public static Transaction makePayment() {

        LocalDate ld = LocalDate.now();
        LocalTime lt = LocalTime.now();

        System.out.println("Please enter the payment amount: ");
        double amount = (Double.parseDouble(scanner.nextLine())) * -1; //-1 to negate the amount of the payment.

        System.out.println("Please enter the vendor name: ");
        String vendor = scanner.nextLine().trim();

        System.out.println("Please provide a description: ");
        String description = scanner.nextLine().trim();

        System.out.println("You made a payment of $" + String.format("%.2f", amount) + " to " + vendor + " on " + ld);
        String idOfTransaction = "P";

        return new Transaction(ld, lt, description, idOfTransaction, vendor, amount);

    }

    //  Create LEDGER MENU with methods
    public static void ledgerMenu() {
        // Welcome the user
        System.out.println("\n                     *****  Ledger Menu *****      \n");

        // How the ledger screen will continue to run until the user decides to exit
        boolean ledgerMenuRunning = true;

        /* Create while loop to repeat a block of code until the condition is met */
        while (ledgerMenuRunning) {
            // Ask the user which option they'd like to pick
            // D 'Deposits' show all deposits
            // 'P' Payments will show all payments

            System.out.println(""" 
                     \n ------------  Please select from the following options:  ------------
                    (A.) Display all entries
                    (D.) Deposits
                    (P.) Payments
                    (R.) Reports
                    (H.) Home
                    """);

            // How the user makes their selection
            String userInputLedger = scanner.nextLine().trim().toUpperCase();

            // Create switch statement to allow user to make a choice
            switch (userInputLedger) {
                case "A":
                    List<Transaction> allTransactions = getTransactionsFromFile(transactionFileName);
                    allTransactions.sort(Comparator.comparing(Transaction::getDateTime).reversed());
                    displayTransaction(allTransactions);
                    break;
                case "D":
                    List<Transaction> depositTransactions = searchTransactionById("D", "Deposits");
                    depositTransactions.sort(Comparator.comparing(Transaction::getDateTime).reversed());
                    displayTransaction(depositTransactions);
                    break;
                case "P":
                    List<Transaction> paymentTransactions = searchTransactionById("P", "Payments");
                    paymentTransactions.sort(Comparator.comparing(Transaction::getDateTime).reversed());
                    displayTransaction(paymentTransactions);
                    break;
                case "R":
                    reportMenu();
                    break;
                case "H":
                    ledgerMenuRunning = false;
                    System.out.println("Returning to Home page ... ");
                    break;
                default:
                    System.out.println("Invalid entry. Please select from the following options: 'D','P','R','H' ");


            }

        }

    }

    public static List<Transaction> getTransactionsFromFile(String fileName) {
        // Create a list to store all transactions from user entries
        List<Transaction> transactions = new ArrayList<>();

        // Create a BufferedReader for transactions
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] arrayTransactions = line.split("\\|");

                Transaction transaction = new Transaction(LocalDate.parse(arrayTransactions[0], dateFormatter), LocalTime.parse(arrayTransactions[1], timeFormatter), arrayTransactions[2], arrayTransactions[3], arrayTransactions[4], Double.parseDouble(arrayTransactions[5]));

                // Start adding into transactions list
                transactions.add(transaction);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return transactions;
    }

    // Displaying a list formatter
    public static void displayTransaction(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            // printing the list in a certain format %s for strings,%.2f%$%n for two numbers after '.'
            System.out.printf("%s | %s | %s | %s | %s | %.2f%n", transaction.getDate().format(dateFormatter), transaction.getTime().format(timeFormatter), transaction.getDescription(), transaction.getIdOfTransaction(), transaction.getVendor(), transaction.getAmount());

        }
    }

    // Showing all deposits
    public static List<Transaction> searchTransactionById(String id, String transactionTypeName) {
        System.out.println(" \n ------------  Showing all " + transactionTypeName + ":  ------------\n");

        // List contains all transactions
        List<Transaction> transactions = getTransactionsFromFile(transactionFileName);

        // This is the empty list where I will store all deposit transactions
        List<Transaction> matchingIdTransactions = new ArrayList<>();

        // for each loop where it will enter into csv file and sort out 'D' transactions into a new list
        for (Transaction transaction : transactions) {
            if (transaction.getIdOfTransaction().equals(id)) {
                matchingIdTransactions.add(transaction);
            }
        }
        return matchingIdTransactions;

    }

    public static void reportMenu() {

        boolean runningReportMenu = true;

        while (runningReportMenu) {
            System.out.println("\n                     *****   Showing All Reports: *****      ");
            System.out.println(""" 
                     \n ------------  Please select from the following options:  ------------
                    (1.) Month To Date
                    (2.) Previous Month
                    (3.) Year To Date
                    (4.) Previous Year
                    (5.) Search by Vendor
                    (0.) Back to Reports
                    """);

            int userChoice = Integer.parseInt(scanner.nextLine());

            //create switch statement
            switch (userChoice) {
                case 1:
                    monthToDate(transactionFileName);
                    break;
                case 2:
                    previousMonth(transactionFileName);
                    break;
                case 3:
                    yearToDate(transactionFileName);
                    break;
                case 4:
                    previousYear(transactionFileName);
                    break;
                case 5:
                    searchByVendor(transactionFileName);
                    break;
                case 0:
                    runningReportMenu = false;
                    break;
            }
        }

    }

    public static void searchByVendor(String fileName) {
        // all trans list
        List<Transaction> transactions = getTransactionsFromFile(fileName);
        // new list containing only the matching vendor info of transactions
        List<Transaction> matchingVendors = new ArrayList<>();
        System.out.println("Please enter the vendors name: ");

        String userVendorsName = scanner.nextLine();

        for (Transaction transaction : transactions) {
            if (transaction.getVendor().equalsIgnoreCase(userVendorsName)) {

                matchingVendors.add(transaction);
            }
        }
        sortingDateTime(matchingVendors);
    }

    public static void yearToDate(String fileName) {

        // List
        List<Transaction> transactions = getTransactionsFromFile(fileName);
        // Will create a new list year to date.
        List<Transaction> yearToDate = new ArrayList<>();

        // LDT to get todays date
        LocalDateTime todayDate = LocalDateTime.now();
        // Beginning of the year
        LocalDateTime firstDayOfYear = todayDate.withDayOfYear(1);

        for (Transaction transaction : transactions) {
            // 'dtc' was created in transaction class, in trans builder by combing date & time
            LocalDateTime dateTimeCombined = transaction.getDateTime();

            if ((dateTimeCombined.isEqual(firstDayOfYear) || dateTimeCombined.isAfter(firstDayOfYear)) && ((dateTimeCombined.isEqual(todayDate) || dateTimeCombined.isBefore(todayDate)))) {

                yearToDate.add(transaction);
            }
        }
        sortingDateTime(yearToDate);
    }

    public static void monthToDate(String fileName) {
        // Old list
        List<Transaction> transactions = getTransactionsFromFile(fileName);
        // new list
        List<Transaction> monthToDate = new ArrayList<>();

        LocalDateTime todayDate = LocalDateTime.now();
        LocalDateTime firstDayOfTheMonth = todayDate.withDayOfMonth(1);

        //                 one at a time       list
        for (Transaction transaction : transactions) {
            // Call out variable transaction and loop through every transaction
            LocalDateTime transactionDate = transaction.getDateTime();

            if ((transactionDate.isEqual(firstDayOfTheMonth) || transactionDate.isAfter(firstDayOfTheMonth)) &&
                    ((transactionDate.isEqual(todayDate)) || transactionDate.isBefore(todayDate))) {

                monthToDate.add(transaction);
            }
        }
        sortingDateTime(monthToDate);
    }

    public static void previousMonth(String fileName) {
        List<Transaction> transactions = getTransactionsFromFile(fileName);
        List<Transaction> previousMonth = new ArrayList<>();

        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime firstDayOfMonth = currentDate.withDayOfMonth(1).minusMonths(1);
        LocalDateTime lastDayOfTheMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.getMonth().length(LocalDate.of(firstDayOfMonth.getYear(), 1, 1).isLeapYear()));

        for (Transaction transaction : transactions) {
            LocalDateTime prevMonthDateTime = transaction.getDateTime();

            if ((prevMonthDateTime.isEqual(firstDayOfMonth) || prevMonthDateTime.isAfter(firstDayOfMonth)) && ((prevMonthDateTime.isEqual(lastDayOfTheMonth)) || prevMonthDateTime.isBefore(lastDayOfTheMonth))) {

                previousMonth.add(transaction);
            }
        }
        sortingDateTime(previousMonth);
    }

    public static void previousYear(String fileName) {

        List<Transaction> transactions = getTransactionsFromFile(fileName);
        List<Transaction> previousYear = new ArrayList<>();

        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime firstDayOfYear = currentDate.minusYears(1).withDayOfYear(1);
        LocalDateTime lastDayOfYear = LocalDateTime.of(firstDayOfYear.getYear(), 12, 31, 23, 59, 59);

        for (Transaction transaction : transactions) {

            LocalDateTime dateTimeCombined = transaction.getDateTime();

            if ((dateTimeCombined.isEqual(firstDayOfYear) || dateTimeCombined.isAfter(firstDayOfYear)) && (dateTimeCombined.isEqual(lastDayOfYear)) || dateTimeCombined.isBefore(lastDayOfYear)) {

                previousYear.add(transaction);
            }
        }
       sortingDateTime(previousYear);
    }
    public static void sortingDateTime (List<Transaction> unsortedDateTimeList){

        unsortedDateTimeList.sort(Comparator.comparing(Transaction::getDateTime).reversed());
        displayTransaction(unsortedDateTimeList);
    }


}





