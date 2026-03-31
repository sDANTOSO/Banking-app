
/**from here the user should be able to do these thing or call functions that do these things
 * *account creator (addAccount)
 *close an account (closeAccount)
 *get balance of an account (getBalance)
 *set account balance (setBalance)
 *end of day print
 *print total amount of money in bank
 *print net withdrawals and deposits (overall change over day)
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

    
    public MainSystem()
    {
        Scanner keyboard = new Scanner (System.in);
        accountDatabase.loadFromFile("accounts");
        boolean user = true;
        float newBalance;

        final String VIEW_ACCOUNT = "view";
        final String SET_ACCOUNT = "set";
        final String ACCOUNT_DEPOSIT = "deposit";
        final String ACCOUNT_WITHDRAWAL = "withdrawal";
        final String CREATE_ACCOUNT = "create";
        final String DELETE_ACCOUNT = "delete";
        final String END_DAY = "quit";
        System.out.println("view - to view account");
        System.out.println("set - to change account balance");
        System.out.println("create - to create an account");
        System.out.println("delete - to create an account");
        System.out.println("quit - to end day");

        String userInput = keyboard.nextLine();
        
        if(userInput.equals(VIEW_ACCOUNT)){
            
            String userAccountNameInput = keyboard.nextLine();
            accountDatabase.getAccountBalance("accounts",userAccountNameInput);
        }
        if(userInput.equals(SET_ACCOUNT)){
            System.out.println("Please enter the account name ");
            String userAccountNameInput = keyboard.nextLine();// run check to see it is a customer name
            System.out.println("Please enter deposit or withdrawal");
            String accountChangeType = keyboard.nextLine();// make to lowercase in string checker
            if(accountChangeType.equals(ACCOUNT_DEPOSIT)){
                System.out.println("Please enter the amount to be deposited");
                String accountChangeAmount = keyboard.nextLine();
                
                newBalance=reliableInput.readNum(accountChangeAmount);
                accountDatabase.setAccountBalance("accounts",userAccountNameInput,newBalance);
            }else if(accountChangeType.equals(ACCOUNT_WITHDRAWAL)){
                System.out.println("Please enter the amount to be withdrawn");
                String accountChangeAmount = keyboard.nextLine();
                newBalance=reliableInput.readNum(accountChangeAmount);
                accountDatabase.setAccountBalance("accounts",userAccountNameInput,newBalance);
            } 
            
            
        }

    }

    public void getBalance(String filename){
        Scanner keyboard = new Scanner (System.in);
        ArrayList <Account> database = new ArrayList<Account>();
        AccountDatabase accountDatabase= new AccountDatabase();

        boolean user = true;
        String userInput = keyboard.nextLine();
        final String VIEW_ACCOUNT = "view";
        if(userInput.equals(VIEW_ACCOUNT)){
            //accountDatabase.displayAll("accounts");
        }
    }
}
