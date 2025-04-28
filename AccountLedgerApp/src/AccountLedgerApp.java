
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AccountLedgerApp {

    // Import Scanner in the class using static
    static Scanner scanner = new Scanner(System.in);
    static String transactionFileName = "src/transaction.csv"; // inputting here so it can be accessible
    static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss");
    static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    // Where I store the methods
    public static void main(String[] args){

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

    // Create Home Menu method with prompts
    public static void homeMenu(){
        // Welcome the user
        System.out.println("*****  Welcome to your Account Ledger Application ***** ");

        // How the application will continue to run until the user decided to exit
        boolean homeMenuRunning = true;

        /* Create while loop to repeat a block of code until the condition is met */
        while (homeMenuRunning) {
            // Ask the user what they'd like to do/ Give prompts
            System.out.println(""" 
                ------------  What would you like to do today? ------------
                D. Add Deposit
                P. Make Payment
                L. Display Ledger Screen
                X. Exit"""); //
            // How the user makes their selection
            String userInput = scanner.nextLine().trim().toUpperCase();
            // Create switch statement to allow user to choose between options
            switch (userInput){
                case "D":
                    // Writing what is in the addDeposit() to the file created
                    Transaction newDeposit = addDeposit();
                    writeToFile(transactionFileName, newDeposit);
                    break;
                case "P":
                case "X":
                    homeMenuRunning = false; //case running = false to get the program to stop running
                    break;


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
        System.out.println( "Your information has been deposited! ");
        String idOfTransaction = "D";

        // Call constructor out
        // This will take the user input and create a single deposit transaction
        return new Transaction(ld, lt, description, idOfTransaction, vendor, amount);
    }

        // Create a file method to write
        // In () add the String variable, Class and object
    public static void writeToFile(String fileName, Transaction transaction){
                                                    // allows to add more to the file w.o losing what was inputted before
        try (FileWriter fw = new FileWriter(fileName, true)) {
            fw.write(transaction.toString()+ "\n");
        } catch (IOException e){
            System.out.println("Something went wrong "+ e.getMessage()); // this is incase something goes wrong

            }


    }
    // make Payment screen
    public static void makePayment(){

    }









}
