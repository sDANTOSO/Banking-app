
/**from here the user should be able to do these thing or call functions that do these things
 * *account creator (addAccount)
 *close an account (closeAccount)
 *get balance of an account (getBalance)
 *set account balance (setBalance)
 *end of day print
 *print total amount of money in bank
 *print net withdrawals and deposits (overall change over day)
 *
 *to do add end of day
 *finsih up deleting account
 *check account type when checking account changes
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
    String account = "accounts";
    /**
     * This checks what the customer wants to do then calls the right function
     */

    public MainSystem()
    {
        Scanner keyboard = new Scanner (System.in);
        accountDatabase.loadFromFile("accounts");
        boolean user = false;
        float dailyChangeAmount=0;
        final String CREATE_ACCOUNT = "CREATE";
        final String END_DAY = "QUIT";
        
        //System.out.println(accountDatabase);

        //accountDatabase.getAccountBalance(account,"Christopher Wallace");
        //System.out.println(accountDatabase.getAccountBalance(account,"Christopher Wallace"));  
        //accountDatabase.getAccountName(account,"Christopher Wallace");
        while (user==false){
            System.out.println("view - to view account");
            System.out.println("set - to change account balance");
            System.out.println("create - to create an account");
            System.out.println("delete - to create an account");
            System.out.println("quit - to end day");
            String userInput = keyboard.nextLine();
            userInput = userInput.toUpperCase();
            String[] changeTypes={"VIEW","SET","CREATE","DELETE","QUIT"};
            //for (ChangeType type: ChangeType.values()){
            for (int i =0; i<changeTypes.length; i++){
                //System.out.println(type); 
                if (userInput.equals(changeTypes[i])){
                    if (userInput.equals(CREATE_ACCOUNT)){
                        createAccount(account,userInput);
                    }else if(userInput.equals(END_DAY)){
                        endDay(account,dailyChangeAmount);
                    }
                    else{
                        dailyChangeAmount=editAccount(account,userInput);
                    }                   
                }                
            }
        }
    }

    /**
     * This handles anything that is manipulating an account
     * this is
     * viewing accounts, setting accounts balances, deleting accounts and 
     * maybe viewing and dditing could be moved if this method gets to big 
     */
    public float editAccount(String filename, String userInput){
        Scanner keyboard = new Scanner (System.in);
        //ArrayList <Account> database = new ArrayList<Account>();
        //AccountDatabase accountDatabase= new AccountDatabase();

        float changeAmount;
        float dailyChangeAmount=0;

        final String VIEW_ACCOUNT = "VIEW";
        final String SET_ACCOUNT = "SET";
        final String ACCOUNT_DEPOSIT = "deposit";
        final String ACCOUNT_WITHDRAWAL = "withdrawal";
        final String DELETE_ACCOUNT = "DELETE";// turn into enums!!
        //

        // order of operations 
        // view account,set account, delete account all need a specific customer id 
        // run specific customer id program
        // then run view if the task was to view, delete if the task was to delete
        // then run deposit withdrawal 
        // if end day call end day function and pass them net deposit and withdrawals
        // end day function must 
        /* At the end of each day the program should print a summary of the total cash in the bank’s digital system, 
         * and the net deposits/withdrawals for the day, 
         * then save the banking data to a .txt file.
         */

        String userAccountNameInput="nothing";
        String userAccountNum="nothing";

        boolean accountChoice= false;
        boolean nameIdentifier=true;
        while (accountChoice==false){
            System.out.println("Please enter the customers name as listed on the account");
            userAccountNameInput = keyboard.nextLine();

            int result = (accountDatabase.getAccountName(account,userAccountNameInput));// checks if there is name and if there are multiple

            /*if (result==0){
            accountChoice=false;
            }else */
            if (result==1){
                accountChoice=true;
            }else if (result>1) {
                // call account number check then print out balance of account number
                userAccountNum=reliableInput.readAccountNum();
                accountChoice=true;
                nameIdentifier=false;
            }

        }

        if(userInput.equals(VIEW_ACCOUNT)){
            if (nameIdentifier==true){
                accountDatabase.getAccountBalance(account,userAccountNameInput);
            }else if (nameIdentifier==false){
                accountDatabase.getAccountBalance(account,userAccountNum);            
            }
        }

        if(userInput.equals(DELETE_ACCOUNT)){
            if (nameIdentifier==true){
                accountDatabase.deleteAccount(account,userAccountNameInput);
                //accountDatabase.saveToFile(account);
            }else if (nameIdentifier==false){
                accountDatabase.deleteAccount(account,userAccountNum);            
            }
        }

        if(userInput.equals(SET_ACCOUNT)){
            //System.out.println("Please enter the account name ");
            //userAccountNameInput = keyboard.nextLine();// run check to see if is a customer name

            System.out.println("Please enter deposit or withdrawal");
            String accountChangeType = keyboard.nextLine();// make to lowercase in string checker
            boolean rightAmount=false;
            final boolean negativeAllowed=false;
            if(accountChangeType.equals(ACCOUNT_DEPOSIT)){
                System.out.println("Please enter the amount to be deposited");
                //String accountChangeAmount = keyboard.nextLine();

                while (rightAmount==false){
                    changeAmount=reliableInput.readNum(negativeAllowed);

                    if  (changeAmount<5000){
                        accountDatabase.setAccountBalance("accounts",userAccountNameInput,changeAmount,ACCOUNT_DEPOSIT);
                        dailyChangeAmount=dailyChangeAmount+changeAmount;
                        rightAmount=true;
                    }else{
                        System.out.println("Deposits should not exceeed 5000");
                    }
                }
            }else if(accountChangeType.equals(ACCOUNT_WITHDRAWAL)){

                while (rightAmount==false){
                    System.out.println("Please enter the amount to be withdrawn");
                    changeAmount=reliableInput.readNum(negativeAllowed);// withdraw the amount not set it as that amount  

                    if(accountDatabase.getAccountType(account,userAccountNameInput)==false){

                        if(accountDatabase.getAccountBalance(account,userAccountNameInput)-changeAmount<0){
                            System.out.println("Savings and Everyday accounts cannot go into debt");  
                        }else{
                            accountDatabase.setAccountBalance("accounts",userAccountNameInput,changeAmount,ACCOUNT_WITHDRAWAL);
                            dailyChangeAmount=dailyChangeAmount-changeAmount;
                            rightAmount=true;
                        }
                    }
                    else if(accountDatabase.getAccountType(account,userAccountNameInput) ==true){
                        if (accountDatabase.getAccountBalance(account,userAccountNameInput)-changeAmount<-1000){
                            //String accountChangeAmount = keyboard.nextLine();
                            System.out.println("Accounts can't exceed $1000 debt");                    
                        }else{
                            accountDatabase.setAccountBalance("accounts",userAccountNameInput,changeAmount,ACCOUNT_WITHDRAWAL);
                            dailyChangeAmount=dailyChangeAmount-changeAmount;
                            rightAmount=true;
                        }
                    }
                }
            }

        }
        //boolean user = true;
        //String userInput = keyboard.nextLine();
        //final String VIEW_ACCOUNT = "view";
        //if(userInput.equals(VIEW_ACCOUNT)){
        //accountDatabase.displayAll("accounts");
        //}

        // view accounts 
        /*
        close an account needs
        checking account name if multiple or single
        then account number
        get the balance 
        needs checking account name if multiple or single 
        then account number
        deposit/withdraw needs
        checking account name if multiple or single
        then account number
        then checking whether the deposit or withdrawal is withing parameters

         */
            return dailyChangeAmount;
    }

    /**
     * This handles creating an account
     * which involves
     * setting
     * Customer name
    Account number
    Customer address
    Account type
    Current balance

     */
    public void createAccount(String filename, String changeType){

        Scanner keyboard = new Scanner (System.in);
        /*create an account needs 
         * if there is an account number already like that checking
         * greater then -1000 checking */
        String customerName;
        boolean inputCorrect=false;
        final AccountType EVERYDAY=AccountType.EVERYDAY;
        final AccountType SAVINGS=AccountType.SAVINGS;
        final AccountType CURRENT=AccountType.CURRENT;
        //customerName, String accountNumber, String customerAdress, AccountType accountType,float accountBalance
        System.out.println("Please enter the customers name to be listed on the account");
        customerName = keyboard.nextLine();
        
        String accountNumber =reliableInput.readAccountNum();
        System.out.println("Please enter the address to be listed on the account");
        String customerAdress=keyboard.nextLine();
        
        AccountType accountType=reliableInput.readAccountType();// make reliable input?
        float accountBalance=0;

        while(inputCorrect==false){
            if (accountType==EVERYDAY||accountType==SAVINGS){
                accountBalance=reliableInput.readNum(false);
                inputCorrect=true;
            }else if (accountType==CURRENT){
                accountBalance=reliableInput.readNum(false);

                if (accountBalance>-1000){
                    accountBalance=reliableInput.readNum(false);
                }

            }

        }

        accountDatabase.createAccount(customerName,accountNumber,customerAdress,accountType,accountBalance);
        accountDatabase.saveToFile("accounts");
    }

    public void endDay(String filename, float netCashflow ){
        // print total amount in bank by looping through array
        //print net cashflow value
        // call save file function
        float  totalBalance=accountDatabase.getTotalBalance(account);

        System.out.println("The banks total balance is: $"+ totalBalance);
        System.out.println("The net deposits/withdrawals is: $"+ netCashflow);
        accountDatabase.saveToFile(account);
        System.exit(0);
    }

}

