import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction {

    // create variables for transaction class. This class activates after the user chooses to add a deposit.
    LocalDate date;
    LocalTime time;
    String description;
    String idOfTransaction;
    String vendor;
    double amount;

    // Creating a constructor and add in all the variables I created
    public Transaction (LocalDate date, LocalTime time, String description, String idOfTransaction, String vendor, double amount){
        this.date = date;
        this.time = time;
        this.description = description;
        this.idOfTransaction = idOfTransaction;
        this.vendor = vendor;
        this.amount = amount;

    }
    // toString() grabs user input and formats
    @Override
    public String toString() {
        return date.format(AccountLedgerApp.dateFormatter) + "|" + time.format(AccountLedgerApp.timeFormatter) + "|" + description + "|" + vendor + "|" + idOfTransaction + "|" + amount;
    }

    // Create getters and setters

    public String getIdOfTransaction() {
        return idOfTransaction;
    }

    public void setIdOfTransaction(String idOfTransaction) {
        this.idOfTransaction = idOfTransaction;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
