
/**
 * create a load from file 
 *account creator (addAccount)
 *close an account (closeAccount)
 *get balance of an account (getBalance)
 *set account balance (setBalance)
 * @Santoso Winatan
 * @19/03/26
 */triple s accountType Float
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
    // instance variables - replace the example below with your own
    private List<Account> accountDatabase = new ArrayList <Account>();
    
    public void saveToFile(String filename){
        //List
    }

    public void loadFromFile(String filename){
        try{
            filename = "accounts";
            File myFile=new File (filename+".txt");
            Scanner myReader = new Scanner(myFile);    
            
            while (myReader.hasNextLine()){
                String line = myReader.nextLine();
                String[] AccountDetails = line.split(";");
                accountDatabase.add(newAccount(AccountDetails[0],
                                                AccountDetails[1],
                                                AccountDetails[3],
                                                String.toUpperCase(AccountDetails[4])
                                                Float.parseFloat(AccountDetails[5]));
                
            }
        }catch(IOException e){
            System.out.println(e);
        }
    }
    
    public void getBalance(String filename){
        System.out.println(accountDatabase[1].getaccountBalance());
    }
    
    

}
