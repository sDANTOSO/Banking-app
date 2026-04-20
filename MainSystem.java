
/**from here the user should be able to do these thing or call functions that do these things
 * create an account
 *close an account
 *get balance of an account
 *set account balance
 *end of day printing and saving
 *
 * @Santoso Winatan
 * @20/03/26
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
        accountDatabase.loadFromFile();
        boolean user = false;
        float dailyChangeAmount=0;
        final String CREATE_ACCOUNT = "CREATE";
        final String END_DAY = "QUIT";
        
        while (user==false){
            System.out.println("view - to view account");
            System.out.println("set - to change account balance");
            System.out.println("create - to create an account");
            System.out.println("delete - to create an account");
            System.out.println("quit - to end day");
            String userInput = keyboard.nextLine();
            userInput = userInput.toUpperCase();
            String[] changeTypes={"VIEW","SET","CREATE","DELETE","QUIT"};
            
            for (int i =0; i<changeTypes.length; i++){
                if (userInput.equals(changeTypes[i])){
                    if (userInput.equals(CREATE_ACCOUNT)){
                        createAccount(userInput);
                    }
                    else if(userInput.equals(END_DAY)){
                        endDay(dailyChangeAmount);
                    }
                    else{
                        dailyChangeAmount=dailyChangeAmount+editAccount(userInput);
                    }                   
                }                
            }
        }
    }

    /**
     * This handles anything that is manipulating an account
     * this is:
     * viewing accounts, setting accounts balances, deleting accounts and 
     */
    public float editAccount( String userInput){
        Scanner keyboard = new Scanner (System.in);

        float changeAmount;// This is the value of change that the user wants to enact on an account balance
        float dailyChangeAmount=0; // This is the overall daily change amount it is passed to the main at the end of each time this function is called

        final String VIEW_ACCOUNT = "VIEW";
        final String SET_ACCOUNT = "SET";
        final String ACCOUNT_DEPOSIT = "deposit";
        final String ACCOUNT_WITHDRAWAL = "withdrawal";
        final String DELETE_ACCOUNT = "DELETE";
        final int MAX_DEPOSIT=5000; // Maximum deposit amount for savings and everyday accounts.
        final int MINIMUMACCOUNTBALANCE=0; // Minimum account balance for savings and everyday accounts.
        final int MAX_OVERDRAFT=-1000; // The maximum amount of debt a current account can go into. 

        String userAccountNameInput=null; // This is the users name on the account
        String userAccountNum=null; // This is the users account number

        boolean accountChoice= false; // This signals to the while loop when an account identifier has been inputted by the user
        boolean nameIdentifier=true; // If the account is identified by name then true. If identified by account number then false.
        
        while (accountChoice==false){
            System.out.println("Please enter the customers name as listed on the account");
            userAccountNameInput = keyboard.nextLine();

            int result = (accountDatabase.getAccountName(userAccountNameInput));// checks if there is a match beetween input and names in the database and if there are multiple

            if (result==1){
                accountChoice=true;
            }else if (result>1) {
                userAccountNum=reliableInput.readAccountNum();
                accountChoice=true;
                nameIdentifier=false;
            }
            /*Because there might be two people with the same name there needs to be something else to identify so the program doesnt only show the first instance of that name
              so the account number will be the unique identifier in this situation*/
        }// Finds a way to define the singular account the user wants.

        if(userInput.equals(VIEW_ACCOUNT)){
            if (nameIdentifier==true){
                accountDatabase.getAccountBalance(userAccountNameInput);
            }else if (nameIdentifier==false){
                accountDatabase.getAccountBalance(userAccountNum);            
            }
        }// Calls method that prints account balance.

        if(userInput.equals(DELETE_ACCOUNT)){
            if (nameIdentifier==true){
                accountDatabase.deleteAccount(userAccountNameInput);
            }else if (nameIdentifier==false){
                accountDatabase.deleteAccount(userAccountNum);            
            }
        }// Calls method that deletes account.

        if(userInput.equals(SET_ACCOUNT)){
            boolean rightAmount=false;
            final boolean negativeAllowed=false;
            
            System.out.println("Please enter deposit or withdrawal");
            String accountChangeType = keyboard.nextLine();
           
            if(accountChangeType.equals(ACCOUNT_DEPOSIT)){
                System.out.println("Please enter the amount to be deposited");

                while (rightAmount==false){
                    changeAmount=reliableInput.readNum(negativeAllowed);

                    if  (changeAmount<=MAX_DEPOSIT){
                        accountDatabase.setAccountBalance(userAccountNameInput,changeAmount,ACCOUNT_DEPOSIT);
                        dailyChangeAmount=dailyChangeAmount+changeAmount;
                        rightAmount=true;
                    }
                    else{
                        System.out.println("Deposits should not exceeed 5000");
                    }// Calls method that sets account and makes sure that transactions are within the banks parameters.
                }
            }else if(accountChangeType.equals(ACCOUNT_WITHDRAWAL)){
                while (rightAmount==false){
                    System.out.println("Please enter the amount to be withdrawn");
                    changeAmount=reliableInput.readNum(negativeAllowed);

                    if(accountDatabase.getAccountType(userAccountNameInput)==false){
                        if(accountDatabase.getAccountBalance(userAccountNameInput)-changeAmount<MINIMUMACCOUNTBALANCE){
                            System.out.println("Savings and Everyday accounts cannot go into debt");  
                        }
                        else{
                            accountDatabase.setAccountBalance(userAccountNameInput,changeAmount,ACCOUNT_WITHDRAWAL);
                            dailyChangeAmount=dailyChangeAmount-changeAmount;
                            rightAmount=true;
                        }// Makes sure that savings and everyday accounts don't go into debt
                    }
                    else if(accountDatabase.getAccountType(userAccountNameInput) ==true){
                        if (accountDatabase.getAccountBalance(userAccountNameInput)-changeAmount>=MAX_OVERDRAFT){
                            accountDatabase.setAccountBalance(userAccountNameInput,changeAmount,ACCOUNT_WITHDRAWAL);
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
    
        return dailyChangeAmount;
    }

    /**
     * This handles creating an account which involves setting the customer name, account number,customer adress, account type and the current balance
     */
    public void createAccount( String changeType){
        Scanner keyboard = new Scanner (System.in);
        
        boolean inputCorrect=false;
        final float MINIMUM_ACCOUNTBALANCE= -1000;
        final boolean NEGATIVE_ALLOWED=true;
        final boolean NEGATIVE_NOT_ALLOWED=false;
        final AccountType EVERYDAY=AccountType.EVERYDAY;
        final AccountType SAVINGS=AccountType.SAVINGS;
        final AccountType CURRENT=AccountType.CURRENT;
        
        // Gets customer name
        System.out.println("Please enter the customers name to be listed on the account");
        String customerName = keyboard.nextLine();
        // Gets a unique account number
        String accountNumber =reliableInput.readAccountNum();//check if its the same number as one that already exists
        // Gets customer adress
        System.out.println("Please enter the address to be listed on the account");
        String customerAdress=keyboard.nextLine();
        // Gets account type
        AccountType accountType=reliableInput.readAccountType();
        
        float accountBalance=0;

        while(inputCorrect==false){
            System.out.println("Please enter the account balance");
            if (accountType==EVERYDAY||accountType==SAVINGS){
                accountBalance=reliableInput.readNum(NEGATIVE_NOT_ALLOWED);
                inputCorrect=true;
            }else if (accountType==CURRENT){
                accountBalance=reliableInput.readNum(NEGATIVE_ALLOWED);
                if (accountBalance>=MINIMUM_ACCOUNTBALANCE){
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
        float  totalBalance=accountDatabase.getTotalBalance();

        System.out.println("The banks total balance is: $"+ totalBalance);// prints the total amount of money held by all accounts in the banks database
        System.out.println("The net deposits/withdrawals is: $"+ netCashflow);// prints the overall net deposits/withdrawals of the day
        accountDatabase.saveToFile(); // saves the file to an actual file
        System.exit(0);// ends the program 
    }
}