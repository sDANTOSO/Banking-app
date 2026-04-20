
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
    private List<Account> accountDatabase = new ArrayList <Account>();

    /**
     * This saves the current accounts data to a file
     */
    public void saveToFile(){
        List<String> accountDetails= new ArrayList<String>();
        //filename = "accounts";
        File myFile=new File ("accounts.txt");
        try{
            FileWriter myWriter = new FileWriter(myFile);

            for (Account thisAccount: this.accountDatabase){
                myWriter.write(thisAccount.getcustomerName()+";"+thisAccount.getaccountNumber()+";"+thisAccount.getcustomerAdress()+";"+thisAccount.getaccountType()+";"+thisAccount.getaccountBalance()+"\n");
            }

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
            
            File myFile=new File ("accounts.txt");
            Scanner myReader = new Scanner(myFile);    

            while (myReader.hasNextLine()){
                String line = myReader.nextLine();
                String[] accountDetails = line.split(";");

                accountDatabase.add(new Account(accountDetails[0],
                        accountDetails[1],
                        accountDetails[2],
                        AccountType.valueOf(accountDetails[3].toUpperCase()),
                        Float.parseFloat(accountDetails[4])));

            }
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
            if (thisAccount.getaccountNumber().equals(customerID)){
                deletedAccount=thisAccount;
            }
        }
        
        accountDatabase.remove(accountDatabase.indexOf(deletedAccount));
    }
    
    /**
     * This matches a name to an account and checks the amount of names that are matched.
     */
    public int getAccountName(String customername){
        int counter =0;
        Account userAccount=null;
        for (Account thisAccount: this.accountDatabase ){
            if (thisAccount.getcustomerName().equals(customername )){
                userAccount= thisAccount;
                counter++;
            }
        }
        if(counter==0){
            System.out.println("That is not a valid name");
        }
        else if (counter>1){
            System.out.println("There are multiple accounts with that name please also enter account number");
        }

        return (counter);
    }

    /**
     * This gets a customers balance with account name or number.
     */
    public float getAccountBalance(String customerID){
        float currentBalance=0;
        for (Account thisAccount: this.accountDatabase ){
            if (thisAccount.getcustomerName().equals(customerID)){
                currentBalance=thisAccount.getaccountBalance();
                System.out.println("Your current balance is:"+thisAccount.getaccountBalance());
            }
            else if (thisAccount.getaccountNumber().equals(customerID)){
                currentBalance=thisAccount.getaccountBalance();
                System.out.println("Your current balance is:"+thisAccount.getaccountBalance());
            }
        }
        
        return currentBalance;
    }

    /**
     * This gets the total balance of all the accounts in the database added together.
     */
    public float getTotalBalance(){
        float  totalBalance=0;

        for (Account thisAccount: this.accountDatabase ){
            totalBalance=(thisAccount.getaccountBalance()+totalBalance);
        }

        return totalBalance;
    }
    
    /**
     * This gets the account type and checks if that type of account has an overdraft.
     */
    public boolean getAccountType(String customerID){
        boolean overdraft =true;
        final AccountType EVERYDAY=AccountType.EVERYDAY;
        final AccountType SAVINGS=AccountType.SAVINGS;
        final AccountType CURRENT=AccountType.CURRENT;

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
        }
        
        return overdraft;
    }

    /**
     * This adds the deposit or withdraws money from the account.
     */
    public void setAccountBalance(String customerID,float amount, String changeType){
        for (Account thisAccount: this.accountDatabase ){
            if (changeType.equals("deposit")){            
                if (thisAccount.getcustomerName().equals(customerID)){
                    thisAccount.setaccountBalance((thisAccount.getaccountBalance())+amount);
                    System.out.println("Your new balance is:"+thisAccount.getaccountBalance());
                }
                if (thisAccount.getaccountNumber().equals(customerID)){
                    thisAccount.setaccountBalance((thisAccount.getaccountBalance())+amount);
                    System.out.println("Your new balance is:"+thisAccount.getaccountBalance());
                }
            }else if (changeType.equals("withdrawal")){
                if (thisAccount.getcustomerName().equals(customerID)){
                    thisAccount.setaccountBalance((thisAccount.getaccountBalance())-amount);
                    System.out.println("Your new balance is:"+thisAccount.getaccountBalance());
                }
                if (thisAccount.getaccountNumber().equals(customerID)){
                    thisAccount.setaccountBalance((thisAccount.getaccountBalance())-amount);
                    System.out.println("Your new balance is:"+thisAccount.getaccountBalance());
                }
            }
        }
    }
}
