import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Transaction {

    // create variables for transaction class.
    LocalDate date;
    LocalTime time;
    String description;
    String idOfTransaction;
    String vendor;
    double amount;
    LocalDateTime dateTime;


    // Creating a constructor this activates after add a deposit, make payment, read transactions
    public Transaction (LocalDate date, LocalTime time, String description, String vendor,String idOfTransaction,double amount){
        this.dateTime = LocalDateTime.of(date,time);
        this.date = date;
        this.time = time;
        this.description = description;
        this.idOfTransaction = idOfTransaction;
        this.vendor = vendor;
        this.amount = amount;

    }
    // toString() grabs user input and formats into a string and saves to csv
    @Override
    public String toString() {
        return date.format(AccountLedgerApp.dateFormatter) + "|" + time.format(AccountLedgerApp.timeFormatter) + "|" + description + "|" + vendor + "|" + idOfTransaction + "|" + amount;
    }

    // Create getters and setters


    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

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
