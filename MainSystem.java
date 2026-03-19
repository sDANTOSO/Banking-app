
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
    public MainSystem()
    {
        Scanner keyboard = new Scanner (System.in);
        ArrayList <Account> database = new ArrayList<Account>();
        AccountDatabase accountDatabase= new AccountDatabase();

        boolean user = true;
        String userInput = keyboard.nextLine();
        final String VIEW_ACCOUNT = "view";
        if(userInput.equals(VIEW_ACCOUNT)){
            accountDatabase.displayAll("accounts");
        }
    }
        public void getBalance(String filename){
        //System.out.println(accountDatabase[1].getaccountBalance());
    }
}
