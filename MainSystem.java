/**
 * From here the user can: 
 *Create a account
 *Delete a account
 *Deposit to a account
 *Withdraw from a accont
 *View a account balance
 *Call an end day function that saves the file, prints the total amount held by the bank, and the net deposits/withdrawals for the day
 *
 * @Santoso Winatan
 * @21/04/26
 */

import java.util.ArrayList;
import java.util.Scanner;
public class MainSystem
{
    ArrayList <Account> database = new ArrayList<Account>();
    AccountDatabase accountDatabase= new AccountDatabase();
    ReliableInput reliableInput= new ReliableInput();

    /**
     * This checks what the customer wants to do then calls the right function
     */
    public MainSystem()
    {
        Scanner keyboard = new Scanner (System.in);
        accountDatabase.loadFromFile();// Loads from the file so that the database is filled
        
        final String CREATE_ACCOUNT = "CREATE";
        final String END_DAY = "QUIT";
        final boolean NOT_MADE = false;
        
        boolean userChoice = false;
        float dailyChangeAmount=0;
        String[] changeTypes={"VIEW","SET","CREATE","DELETE","QUIT"};
       
        while (userChoice==NOT_MADE){
            System.out.println("view - to view account");
            System.out.println("set - to change account balance");
            System.out.println("create - to create an account");
            System.out.println("delete - to create an account");
            System.out.println("quit - to end day");
            
            String userInput = keyboard.nextLine();
            userInput = userInput.toUpperCase();
            
            for (int i =0; i<changeTypes.length; i++){
                if (userInput.equals(changeTypes[i])){
                    if (userInput.equals(CREATE_ACCOUNT)){
                        createAccount(userInput);
                    }
                    else if(userInput.equals(END_DAY)){
                        endDay(dailyChangeAmount);
                        userChoice=true;
                    }
                    else{
                        dailyChangeAmount=dailyChangeAmount+editAccount(userInput);
                    }                   
                }                
            }
        }// This gets the users action then calls the method for it
    }

    /**
     * This handles anything that is manipulating an account
     * this is:
     * viewing accounts, setting accounts balances, deleting accounts
     */
    public float editAccount( String userInput){
        Scanner keyboard = new Scanner (System.in);
        
        final String VIEW_ACCOUNT = "VIEW";
        final String SET_ACCOUNT = "SET";
        final String ACCOUNT_DEPOSIT = "deposit";
        final String ACCOUNT_WITHDRAWAL = "withdrawal";
        final String DELETE_ACCOUNT = "DELETE";
        final int MAX_DEPOSIT=5000; // Maximum deposit amount for savings and everyday accounts.
        final int MINIMUMACCOUNTBALANCE=0; // Minimum account balance for savings and everyday accounts.
        final int OVERDRAFT_LIMIT=-1000; // Change this if overdraft limits change
        final int MATCHED_ACCOUNT=1;
        final boolean NOT_MADE = false;
        final boolean ACCOUNT_FOUND = false;

        float changeAmount;// This is the value of change that the user wants to enact on an account balance
        float dailyChangeAmount=0; // This is the overall daily change amount it is passed to the main at the end of each time this function is called
        String customerID=null; // This is the users name on the account
        boolean accountChoice= false; // This signals to the while loop when an account identifier has been inputted by the user
        boolean accountNumChoice= false;// This signals to the while loop when a valid account number has been inputted by the user
        
        while (accountChoice==NOT_MADE){
            System.out.println("Please enter the customers name as listed on the account");
            customerID = keyboard.nextLine();

            int result = (accountDatabase.getAccountName(customerID));// checks if there is a match beetween input and names in the database and if there are multiple

            if (result==MATCHED_ACCOUNT){
                accountChoice=true;
            }
            else if (result>MATCHED_ACCOUNT) {
                while (accountNumChoice==NOT_MADE){
                    customerID=reliableInput.readAccountNum();    
                    if (accountDatabase.newAccountNumChecker(customerID,false)==ACCOUNT_FOUND){
                        accountChoice=true;
                        accountNumChoice=true;                   
                    }                                        
                }
            }
            /*Because there might be two people with the same name there needs to be something else to identify so the program doesnt only show the first instance of that name
            so the account number will be the unique identifier in this situation*/
            
        }// Finds a way to define the singular account the user wants.

        if(userInput.equals(VIEW_ACCOUNT)){
            accountDatabase.getAccountBalance(customerID);
        }// Calls method that prints account balance.

        if(userInput.equals(DELETE_ACCOUNT)){            
            accountDatabase.deleteAccount(customerID);           
        }// Calls method that deletes account.

        if(userInput.equals(SET_ACCOUNT)){
            final boolean NEGATIVE_ALLOWED=false;
            final boolean NEGATIVE_NOT_ALLOWED=false;
            final boolean OVERDRAFT=true;
            boolean rightAmount=false;    
            
            System.out.println("Please enter deposit or withdrawal");
            String accountChangeType = keyboard.nextLine();

            if(accountChangeType.equals(ACCOUNT_DEPOSIT)){
                while (rightAmount==NOT_MADE){
                    System.out.println("Please enter the amount to be deposited");
                    
                    changeAmount=reliableInput.readNum(NEGATIVE_ALLOWED);

                    if  (changeAmount<=MAX_DEPOSIT){
                        accountDatabase.setAccountBalance(customerID,changeAmount,ACCOUNT_DEPOSIT);
                        dailyChangeAmount=dailyChangeAmount+changeAmount;
                        rightAmount=true;
                    } // Makes sure the deposit is not over the max
                    else{
                        System.out.println("Deposits should not exceeed 5000");
                    }// Calls method that sets account and makes sure that transactions are within the banks parameters.
                }
                
            }else if(accountChangeType.equals(ACCOUNT_WITHDRAWAL)){
                while (rightAmount==NOT_MADE){
                    System.out.println("Please enter the amount to be withdrawn");
                    changeAmount=reliableInput.readNum(NEGATIVE_ALLOWED);

                    if(accountDatabase.getAccountType(customerID)==NEGATIVE_NOT_ALLOWED){
                        if(accountDatabase.getAccountBalance(customerID)-changeAmount<MINIMUMACCOUNTBALANCE){
                            System.out.println("Savings and Everyday accounts cannot go into debt");  
                        }
                        else{
                            accountDatabase.setAccountBalance(customerID,changeAmount,ACCOUNT_WITHDRAWAL);
                            dailyChangeAmount=dailyChangeAmount-changeAmount;
                            rightAmount=true;
                        }// Makes sure that savings and everyday accounts don't go into debt
                    }
                    else if(accountDatabase.getAccountType(customerID) ==OVERDRAFT){
                        if (accountDatabase.getAccountBalance(customerID)-changeAmount>=OVERDRAFT_LIMIT){
                            accountDatabase.setAccountBalance(customerID,changeAmount,ACCOUNT_WITHDRAWAL);
                            dailyChangeAmount=dailyChangeAmount-changeAmount;
                            rightAmount=true;                  
                        }
                        else{                           
                            System.out.println("Accounts can't exceed $1000 debt");   
                        }
                    }//Makes sure that current accounts dont go lower then -1000;
                }
            }
        }

        return dailyChangeAmount;// Returns the amount to change the current change amount by
    }

    /**
     * This handles creating an account which involves setting the customer name, account number,customer adress, account type and the current balance
     */
    public void createAccount( String changeType){
        Scanner keyboard = new Scanner (System.in);
       
        final float OVERDRAFT_LIMIT= -1000; //Change this if overdraft limit changes
        final boolean NEGATIVE_ALLOWED=true; 
        final boolean NEGATIVE_NOT_ALLOWED=false;
        final boolean UNIQUE_NECESSARY=true;
        final boolean NOT_MADE = false;
        final AccountType EVERYDAY=AccountType.EVERYDAY;
        final AccountType SAVINGS=AccountType.SAVINGS;
        final AccountType CURRENT=AccountType.CURRENT;
        
        
        boolean inputCorrect=false;
        boolean accountNumberInput=false;
        String accountNumber = null;

        // Gets customer name
        System.out.println("Please enter the customers name to be listed on the account");
        String customerName = keyboard.nextLine();
        
        // Gets a unique account number
        while(accountNumberInput==NOT_MADE){            
            accountNumber =reliableInput.readAccountNum();
            if (accountDatabase.newAccountNumChecker(accountNumber,UNIQUE_NECESSARY)==true){
                accountNumberInput=true;
            }
        }
        // Gets customer adress
        System.out.println("Please enter the address to be listed on the account");
        String customerAdress=keyboard.nextLine();
        
        // Gets account type
        AccountType accountType=reliableInput.readAccountType();

        float accountBalance=0;// So account balance can be returned
        
        while(inputCorrect==NOT_MADE){
            System.out.println("Please enter the account balance");
            if (accountType==EVERYDAY||accountType==SAVINGS){
                accountBalance=reliableInput.readNum(NEGATIVE_NOT_ALLOWED);
                inputCorrect=true;
            }else if (accountType==CURRENT){
                accountBalance=reliableInput.readNum(NEGATIVE_ALLOWED);
                if (accountBalance>=OVERDRAFT_LIMIT){
                    inputCorrect=true;
                }
            }
        }// Makes sure the account balances that are being set don't break the rules for accounts.

        accountDatabase.createAccount(customerName,accountNumber,customerAdress,accountType,accountBalance);// creates the object
        System.out.println("The account has been created");
    }

    /**
     * This gets the total balance of the bank,net deposits/withdrawals prints them then saves and exits the program.
     */    
    public void endDay( float netCashflow ){
        float  totalBalance=accountDatabase.getTotalBalance(); // Gets balance of all accounts in the bank

        System.out.println("The banks total balance is: $"+ totalBalance);// Prints the total amount of money held by all accounts in the banks database
        System.out.println("The net deposits/withdrawals is: $"+ netCashflow);// Prints the overall net deposits/withdrawals of the day
        accountDatabase.saveToFile(); // Saves the file to an actual file
        System.exit(0);// Ends the program 
    }
}