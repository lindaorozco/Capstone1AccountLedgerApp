
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    //The home screen should give the user the following options.  The
    //application should continue to run until the user chooses to exit.
    //§ D) Add Deposit - prompt user for the deposit information and
    //save it to the csv file
    //§ P) Make Payment (Debit) - prompt user for the debit
    //information and save it to the csv file
    //§ L) Ledger - display the ledger screen
    //§ X) Exit - exit the application

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
                    D. Add Deposit
                    P. Make Payment
                    L. Display Ledger Screen
                    X. Exit""");

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
                    homeMenuRunning = false; //case running = false to get the program to stop running
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

        System.out.println("Your information has been deposited! ");
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

        System.out.println("Your information has been deposited! ");
        String idOfTransaction = "P";

        return new Transaction(ld, lt, description, idOfTransaction, vendor, amount);

    }
    //• Ledger - All entries should show the newest entries first
    //o A) All - Display all entries
    //o D) Deposits - Display only the entries that are deposits into the
    //account
    //o P) Payments - Display only the negative entries (or payments)
    //o R) Reports - A new screen that allows the user to run pre-defined
    //reports or to run a custom search
    //§ 1) Month To Date
    //§ 2) Previous Month
    //§ 3) Year To Date
    //§ 4) Previous Year
    //§ 5) Search by Vendor - prompt the user for the vendor name
    //and display all entries for that vendor
    //§ 0) Back - go back to the report page

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
                    A. Display all entries
                    D. Deposits
                    P. Payments
                    R. Reports
                    H. Home
                    """);

            // How the user makes their selection
            String userInputLedger = scanner.nextLine().trim().toUpperCase();

            // Create switch statement to allow user to make a choice
            switch (userInputLedger) {
                case "A":
                    List<Transaction> allTransactions = getTransactionsFromFile(transactionFileName);
                    displayTransaction(allTransactions);
                    break;
                case "D":
                    List<Transaction> depositTransactions = searchTransactionById("D", "Deposits");
                    displayTransaction(depositTransactions);
                    break;
                case "P":
                    List<Transaction> paymentTransactions = searchTransactionById("P", "Payments");
                    displayTransaction(paymentTransactions);
                    break;
                case "R":
                    reportMenu();
                    break;
                case "H":
                    ledgerMenuRunning = false;
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
        System.out.println("\n                     *****   Showing All Reports: *****      ");
        System.out.println(""" 
                 \n ------------  Please select from the following options:  ------------
                1. Month To Date
                2. Previous Month
                3. Year To Date
                4. Previous Year
                5. Search by Vendor
                0. Back
                """);
        int userChoice = Integer.parseInt(scanner.nextLine());

        //create switch statement
        switch (userChoice) {
            case 1:
                List<Transaction> monthToDateTransactions = monthToDate(transactionFileName);
                displayTransaction(monthToDateTransactions);
                break;
            case 2:
                List<Transaction> previousMonthTransactions = previousMonth(transactionFileName);
                displayTransaction(previousMonthTransactions);
                break;
            case 3:
                List<Transaction> yearToDateTransactions = yearToDate(transactionFileName);
                displayTransaction(yearToDateTransactions);
                break;
            case 4:
                List<Transaction> previousYearTransaction = previousYear(transactionFileName);
                displayTransaction(previousYearTransaction);

                break;
            case 5:
                List<Transaction> searchVendorTransaction = searchByVendor(transactionFileName);
                displayTransaction(searchVendorTransaction);
                break;
            case 0:
                break;
        }

    }

    public static List<Transaction> searchByVendor(String fileName) {
        // all trans list
        List<Transaction> transactions = getTransactionsFromFile(fileName);
        // new list containing only the matching vendor info of transactions
        List<Transaction> matchingVendors = new ArrayList<>();
        System.out.println("Please enter the vendors name: ");

        String userVendorsName = scanner.nextLine();

        for (Transaction transaction : transactions) {
            if (transaction.getVendor().equals(userVendorsName)) {
                matchingVendors.add(transaction);
            }
        }
        return matchingVendors;
    }

    public static List<Transaction> yearToDate(String fileName) {
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
        return yearToDate;
    }

    public static List<Transaction> monthToDate(String fileName) {
        // Old list
        List<Transaction> transactions = getTransactionsFromFile(fileName);
        // new list
        List<Transaction> monthToDate = new ArrayList<>();

        LocalDateTime todayDate = LocalDateTime.now();
        LocalDateTime firstDayOfTheMonth = todayDate.withDayOfMonth(1);

        // create loop
        for (Transaction transaction : transactions) {

            LocalDateTime transactionDate = transaction.getDateTime();

            if ((transactionDate.isEqual(firstDayOfTheMonth)|| transactionDate.isAfter(firstDayOfTheMonth)) &&
                   ((transactionDate.isEqual(todayDate)) || transactionDate.isBefore(todayDate))){

                monthToDate.add(transaction);
            }
        }
        return monthToDate;
    }
    public static List<Transaction> previousMonth (String fileName){

        List<Transaction> transactions =getTransactionsFromFile(fileName);
        List<Transaction> previousMonth = new ArrayList<>();

        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime firstDayOfMonth = currentDate.withDayOfMonth(1);
        LocalDateTime lastDayOfTheMonth = firstDayOfMonth.minusSeconds(1);

        for (Transaction transaction : transactions){
            LocalDateTime prevMonthTransaction = transaction.getDateTime();

            if ((prevMonthTransaction.isEqual(firstDayOfMonth) || prevMonthTransaction.isAfter(firstDayOfMonth)) && ((prevMonthTransaction.isEqual(lastDayOfTheMonth)) || prevMonthTransaction.isBefore(lastDayOfTheMonth))){
            }
        }
        return previousMonth;
    }

    public static List<Transaction> previousYear (String fileName){

        List<Transaction> transactions = getTransactionsFromFile(fileName);
        List<Transaction> previousYear = new ArrayList<>();

        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime firstDayOfYear = currentDate.minusYears(1).withDayOfYear(1);
        LocalDateTime lastDayOfYear = LocalDateTime.of(firstDayOfYear.getYear(),12,31,23,59,59);

        for (Transaction transaction : transactions){

            LocalDateTime dateTimeCombined = transaction.getDateTime();

            if ((transaction.getDateTime().isEqual(firstDayOfYear) || transaction.getDateTime().isAfter(firstDayOfYear)) && (transaction.getDateTime().isEqual(lastDayOfYear)) || transaction.getDateTime().isBefore(lastDayOfYear));
            previousYear.add(transaction);
        }
        return previousYear;
    }


}









