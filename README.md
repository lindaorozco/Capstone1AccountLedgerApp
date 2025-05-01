# 🧾 Account Ledger Application 

This is a simple Java console application that acts as a **personal finance ledger**. You can add deposits, make payments, view all transactions, and generate reports based on date or vendor.

----------

## ✅ Features

-   Add **Deposits** and **Payments**
    
-   View all transactions in a **Ledger**
    
-   Generate reports:
    
    -   Month-to-date
        
    -   Previous month
        
    -   Year-to-date
        
    -   Previous year
        
    -   Search by vendor

<details> 
<summary> 📁 Screenshots </summary>

### Home Menu:
   ![alt text](/pictures/homeMenu.png)
### Ledger Menu:
   ![alt text](/pictures/ledgerMenu.png)
### Report Menu:
   ![alt text](/pictures/reportMenu.png)

</details>




----------

## 📁 Files

-   `AccountLedgerApp.java`: Main application file
    
-   `Transaction.java`: Class that represents a single transaction
    
-   `transaction.csv`: File where all transactions are stored (created automatically if not present)
    

----------

## ▶️ How to Run

1.  Make sure you have **Java 17+** installed.
    
2.  Compile the program:
    
    `javac AccountLedgerApp.java Transaction.java` 
    
3.  Run the app:
    
    `java AccountLedgerApp` 
    

----------

## 📝 How to Use

When you run the app, you’ll see a menu:

`What would you like to do today?
(D.) Add Deposit
(P.) Make Payment
(L.) Display Ledger Screen
(X.) Exit` 

Just enter the letter that matches your choice and follow the prompts!

----------

## 📦 Example Transaction Format (in CSV)

`2025-04-28 | 11:02:15 AM | Grocery Shopping | P | Trader Joe's | -52.30` 

----------

## 📌 Notes

-   Deposits are saved with positive amounts.
    
-   Payments are saved as **negative** numbers.
    
-   Transactions are saved to and read from a `transaction.csv` file.
    
-   Ledger and report views are sorted by date (most recent first).
