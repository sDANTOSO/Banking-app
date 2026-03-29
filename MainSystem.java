
/**from here the user should be able to do these thing or call functions that do these things
 * *account creator (addAccount)
 *close an account (closeAccount)
 *get balance of an account (getBalance)
 *set account balance (setBalance)
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
    String account = "accounts";
    
    
    
    public MainSystem()
    {
        Scanner keyboard = new Scanner (System.in);
        accountDatabase.loadFromFile("accounts");
        boolean user = true;
        
        
        String userInput = keyboard.nextLine();
        final String VIEW_ACCOUNT = "view";
        final String SET_ACCOUNT = "set";
        final String CREATE_ACCOUNT = "create";
        final String DELETE_ACCOUNT = "delete";
        final String END_DAY = "quit";
        System.out.println("view - to view account");
        System.out.println("set - to change account balance");
        System.out.println("create - to create an account");
        System.out.println("delete - to create an account");
        System.out.println("quit - to end day");
        
        String userAccountNameInput = keyboard.nextLine();
        float newBalance = keyboard.nextFloat();
        if(userInput.equals(VIEW_ACCOUNT)){
            accountDatabase.getAccountBalance("accounts",userAccountNameInput);
        }
        if(userInput.equals(SET_ACCOUNT)){
            accountDatabase.setAccountBalance("accounts",userAccountNameInput,newBalance);
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
