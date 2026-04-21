/**
 * This class can:
 * Save to a .txt file
 * Fill an arraylist from a .txt file
 * Add a account to the arraylist
 * Delete a account from the arraylist
 * Get a accounts name,balance,type
 * Get the total amount of $ held by all accounts
 * Check if an account number is unique
 * Set a accounts balance
 * 
 * @Santoso Winatan
 * @21/04/26
 */

import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
public class AccountDatabase
{
    private List<Account> accountDatabase = new ArrayList <Account>();

    /**
     * This saves the current accounts data to a file
     */
    public void saveToFile(){
        List<String> accountDetails= new ArrayList<String>(); 

        File myFile=new File ("accounts.txt");// chooses file
        try{
            FileWriter myWriter = new FileWriter(myFile);

            for (Account thisAccount: this.accountDatabase){
                myWriter.write(thisAccount.getcustomerName()+";"+thisAccount.getaccountNumber()+";"+thisAccount.getcustomerAdress()+";"+thisAccount.getaccountType()+";"+thisAccount.getaccountBalance()+"\n");
            }// Writes the account database to a file splitting the variables with a semicolon and the accounts with a line

            myWriter.flush();
            myWriter.close();
        }catch(IOException e){
            System.out.println("Error: could not write to the file.");
        }
    }

    /**
     * This loads the current accounts data to a file
     */
    public void loadFromFile(){
        try{
            File myFile=new File ("accounts.txt"); // chooses file 
            Scanner myReader = new Scanner(myFile);    

            while (myReader.hasNextLine()){
                String line = myReader.nextLine();
                String[] accountDetails = line.split(";");

                accountDatabase.add(new Account(accountDetails[0],
                        accountDetails[1],
                        accountDetails[2],
                        AccountType.valueOf(accountDetails[3].toUpperCase()),
                        Float.parseFloat(accountDetails[4])));

            }// Adds each accounts data to the account database
        }catch(IOException e){
            System.out.println(e);
        }
    }

    /**
     * This creates an account by passing all the data to the constructor
     */
    public void createAccount(String customerName, String accountNumber, String customerAdress, AccountType accountType,float accountBalance){
        accountDatabase.add(new Account(customerName,
                accountNumber,
                customerAdress,
                accountType,
                accountBalance ));
        // Takes all the data and adds it to the database
    }

    /**
     * This deletes an account by deleting it from the database.
     */
    public void deleteAccount( String customerID){
        Account deletedAccount=null;

        for (Account thisAccount: this.accountDatabase ){
            if (thisAccount.getcustomerName().equals(customerID)){
                deletedAccount=thisAccount;
            }
            else if (thisAccount.getaccountNumber().equals(customerID)){
                deletedAccount=thisAccount;
            }
        }// Loops through database

        accountDatabase.remove(accountDatabase.indexOf(deletedAccount));//Deletes account from database
    }

    /**
     * This matches a name to an account and checks the amount of names that are matched.
     */
    public int getAccountName(String customername){
        final int NO_ACCOUNTS_FOUND=0; 
        final int ACCOUNT_FOUND=1;

        int counter =0;// The amount of accounts that match the name in the database
        Account userAccount=null;

        for (Account thisAccount: this.accountDatabase ){
            if (thisAccount.getcustomerName().equals(customername )){
                userAccount= thisAccount;
                counter++;
            }// Adds to counter each time a name is matched with one in the database
        }

        if(counter==NO_ACCOUNTS_FOUND){
            System.out.println("That is not a valid name");
        }
        else if (counter>ACCOUNT_FOUND){
            System.out.println("There are multiple accounts with that name please also enter account number");
        }

        return counter; // returns the amount of names that match in the database
    }

    /**
     * This gets a customers balance with account name or number.
     */
    public float getAccountBalance(String customerID){
        float currentBalance=0; // Defines variable to be returned

        for (Account thisAccount: this.accountDatabase ){
            if (thisAccount.getcustomerName().equals(customerID)){
                currentBalance=thisAccount.getaccountBalance();
                System.out.println("Your current balance is:"+thisAccount.getaccountBalance());
            }
            else if (thisAccount.getaccountNumber().equals(customerID)){
                currentBalance=thisAccount.getaccountBalance();
                System.out.println("Your current balance is:"+thisAccount.getaccountBalance());
            }
        }// Prints the users current balance

        return currentBalance;// Returns the customers current balance
    }

    public boolean newAccountNumChecker(String customerID,boolean uniqueNecessary){
        final boolean UNIQUE_NUMBER_NEEDED=true;
        final boolean NUMBER_NOT_UNIQUE=false;
        final boolean UNIQUE_NUMBER_NOT_NEEDED=false;
        final boolean UNIQUE_NUMBER=true;
        
        boolean accountUnique=true;
        
        for (Account thisAccount: this.accountDatabase){
            if (thisAccount.getaccountNumber().equals(customerID)){
                accountUnique=false;
            }
        }// If there is a match the number will be marked as not unique
        
        if (uniqueNecessary == UNIQUE_NUMBER_NEEDED && accountUnique==NUMBER_NOT_UNIQUE){
            System.out.println("That number is not unique" );
        }else if (uniqueNecessary == UNIQUE_NUMBER_NOT_NEEDED && accountUnique==UNIQUE_NUMBER){
            System.out.println("That number does not match an account" );
        }//Gives user information about their input

        return accountUnique;// Returns whether the number is unique or not
    }

    /**
     * This gets the total balance of all the accounts in the database added together.
     */
    public float getTotalBalance(){
        float  totalBalance=0;

        for (Account thisAccount: this.accountDatabase ){
            totalBalance=(thisAccount.getaccountBalance()+totalBalance);
        }// Loops through the accounts adding up all balances

        return totalBalance;//Returns the combined balance of all the accounts
    }

    /**
     * This gets the account type and checks if that type of account has an overdraft.
     */
    public boolean getAccountType(String customerID){
        final AccountType EVERYDAY=AccountType.EVERYDAY;
        final AccountType SAVINGS=AccountType.SAVINGS;
        final AccountType CURRENT=AccountType.CURRENT;
        
        boolean overdraft =true;

        for (Account thisAccount: this.accountDatabase ){
            if (thisAccount.getcustomerName().equals(customerID)){
                if (thisAccount.getaccountType()==EVERYDAY||thisAccount.getaccountType()==SAVINGS){
                    overdraft=false;
                }
                else if(thisAccount.getaccountType()==CURRENT){
                    overdraft=true;
                }
            }
            else if (thisAccount.getaccountNumber().equals(customerID)){
                if (thisAccount.getaccountType()==EVERYDAY||thisAccount.getaccountType()==SAVINGS){
                    overdraft=false;
                }
                else if(thisAccount.getaccountType()==CURRENT){
                    overdraft=true;
                }
            }
        }// Sets whether an account can have an overdraft (debt) or not

        return overdraft;// Returns whether the users account can have an overdraft or not
    }

    /**
     * This adds the deposit or withdraws money from the account.
     */
    public void setAccountBalance(String customerID,float amount, String changeType){

        for (Account thisAccount: this.accountDatabase ){
            if (changeType.equals("deposit")){            
                if (thisAccount.getcustomerName().equals(customerID) ){
                    thisAccount.setaccountBalance((thisAccount.getaccountBalance())+amount);
                    System.out.println("Your new balance is:"+thisAccount.getaccountBalance());
                }
                else if (thisAccount.getaccountNumber().equals(customerID)){
                    thisAccount.setaccountBalance((thisAccount.getaccountBalance())+amount);
                    System.out.println("Your new balance is:"+thisAccount.getaccountBalance());
                }
            }else if (changeType.equals("withdrawal")){
                if (thisAccount.getcustomerName().equals(customerID)){
                    thisAccount.setaccountBalance((thisAccount.getaccountBalance())-amount);
                    System.out.println("Your new balance is:"+thisAccount.getaccountBalance());
                }
                else if (thisAccount.getaccountNumber().equals(customerID)){
                    thisAccount.setaccountBalance((thisAccount.getaccountBalance())-amount);
                    System.out.println("Your new balance is:"+thisAccount.getaccountBalance());
                }
            }
        }// This loops through the account setting the new balance of the account whether the user deposited or withdrew
    }
}