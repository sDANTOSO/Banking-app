
/**
 * create a load from file 
 *account creator (addAccount)
 *close an account (closeAccount)
 *get balance of an account (getBalance)
 *set account balance (setBalance)
 * @Santoso Winatan
 * @19/03/26
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
    // instance variables - replace the example below with your own
    private List<Account> accountDatabase = new ArrayList <Account>();
    
    
     
    
    public void saveToFile(String filename){
        List<String> accountDetails= new ArrayList<String>();
        filename = "accounts";
        File myFile=new File (filename+".txt");
        try{
            FileWriter myWriter = new FileWriter(myFile);
            
            for (Account thisAccount: this.accountDatabase){
                myWriter.write(thisAccount.getcustomerName()+";"+thisAccount.getaccountNumber()+";"+thisAccount.getcustomerAdress()+";"+thisAccount.getaccountType()+";"+thisAccount.getaccountBalance());
            }
            
            myWriter.flush();
            myWriter.close();
        }catch(IOException e){
            System.out.println("Error: could not write to the file.");
        }
        
    }

    public void loadFromFile(String filename){
        try{
            filename = "accounts";
            File myFile=new File (filename+".txt");
            Scanner myReader = new Scanner(myFile);    
            
            while (myReader.hasNextLine()){
                String line = myReader.nextLine();
                String[] AccountDetails = line.split(";");
                accountDatabase.add(new Account(AccountDetails[0],
                                            AccountDetails[1],
                                            AccountDetails[2],
                                            AccountDetails[3].toUpperCase(),
                                            Float.parseFloat(AccountDetails[4])));
                
            }
        }catch(IOException e){
            System.out.println(e);
        }
    }
    
    public void getAccountBalance(String filename,String customername){
        for (Account thisAccount: this.accountDatabase ){
            if (thisAccount.getcustomerName().equals(customername )){
                System.out.println(thisAccount.getaccountBalance());
            }
        }
        }
    
    public void setAccountBalance(String filename,String customername,float amount){
        for (Account thisAccount: this.accountDatabase ){
            if (thisAccount.getcustomerName().equals(customername )){
                thisAccount.setaccountBalance(amount);
            }
        }
        }

}
