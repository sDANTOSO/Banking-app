
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

    /**
     * This saves the current accounts data to a file
     */
    public void saveToFile(String filename){
        List<String> accountDetails= new ArrayList<String>();
        filename = "accounts";
        File myFile=new File (filename+".txt");
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
    public void loadFromFile(String filename){
        try{
            filename = "accounts";
            File myFile=new File (filename+".txt");
            Scanner myReader = new Scanner(myFile);    

            while (myReader.hasNextLine()){
                String line = myReader.nextLine();
                String[] accountDetails = line.split(";");
                //System.out.println(accountDetails.length);
                accountDatabase.add(new Account(accountDetails[0],
                        accountDetails[1],
                        accountDetails[2],
                        AccountType.valueOf(accountDetails[3].toUpperCase()),//Day.valueOf(input.toUpperCase())
                        Float.parseFloat(accountDetails[4])));

            }
        }catch(IOException e){
            System.out.println(e);
        }
    }

    public void createAccount(String customerName, String accountNumber, String customerAdress, AccountType accountType,float accountBalance){
        accountDatabase.add(new Account(customerName,
                accountNumber,
                customerAdress,
                accountType,//Day.valueOf(input.toUpperCase())
                accountBalance ));        
    }

    public int getAccountName(String filename,String customername){
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
     * This gets a customers balance// with account name or number
     */
    public float getAccountBalance(String filename,String customerID){
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

    public float getTotalBalance(String filename){
        float  totalBalance=0;

        for (Account thisAccount: this.accountDatabase ){
            totalBalance=(thisAccount.getaccountBalance()+totalBalance);
        }

        return totalBalance;
    }

    public boolean getAccountType(String filename,String customerID){
        boolean overdraft =true;
        final AccountType EVERYDAY=AccountType.EVERYDAY;
        final AccountType SAVINGS=AccountType.SAVINGS;
        final AccountType CURRENT=AccountType.CURRENT;

        for (Account thisAccount: this.accountDatabase ){
            if (thisAccount.getcustomerName().equals(customerID)){
                if (thisAccount.getaccountType()==EVERYDAY||thisAccount.getaccountType()==SAVINGS){
                    overdraft=false;
                }else if(thisAccount.getaccountType()==CURRENT){
                    overdraft=true;
                }
            }
            else if (thisAccount.getaccountNumber().equals(customerID)){
                if (thisAccount.getaccountType()==EVERYDAY||thisAccount.getaccountType()==SAVINGS){
                    overdraft=false;
                    //System.out.println("Your current balance is:"+thisAccount.getaccountBalance());
                }else if(thisAccount.getaccountType()==CURRENT){
                    overdraft=true;
                }
            }
        }
        System.out.println(overdraft);
        return overdraft;
    }

    public void deleteAccount(String filename, String customerID){
        Account deletedAccount=null;
        for (Account thisAccount: this.accountDatabase ){
            if (thisAccount.getcustomerName().equals(customerID)){// get rid of half of if statements
                deletedAccount=thisAccount;
                //System.out.println(accountDatabase.indexOf(thisAccount));
                //accountDatabase.remove(accountDatabase.indexOf(thisAccount));
                //System.out.println("Your new balance is:"+thisAccount.getaccountBalance());
            }
            if (thisAccount.getaccountNumber().equals(customerID)){
                deletedAccount=thisAccount;
                //thisAccount.setaccountBalance((thisAccount.getaccountBalance())-amount);
                //System.out.println("Your new balance is:"+thisAccount.getaccountBalance());
            }
        }
        accountDatabase.remove(accountDatabase.indexOf(deletedAccount));

    }

    /*
    public void getAccountBalance(String filename,String accountNumber){
    System.out.println("fuk"+ accountNumber);
    for (Account thisAccount: this.accountDatabase ){
    System.out.println(thisAccount.getaccountNumber());
    if (thisAccount.getaccountNumber().equals(accountNumber )){

    System.out.println(thisAccount.getaccountBalance());
    }
    }
    }

    /**
     * This sets a customers balance
     */
    public void setAccountBalance(String filename,String customerID,float amount, String changeType){
        for (Account thisAccount: this.accountDatabase ){

            if (changeType.equals("deposit")){            
                if (thisAccount.getcustomerName().equals(customerID)){
                    thisAccount.setaccountBalance((thisAccount.getaccountBalance())+amount);
                    System.out.println("Your new balance is:"+thisAccount.getaccountBalance());
                }
                if (thisAccount.getaccountNumber().equals(customerID)){
                    thisAccount.setaccountBalance((thisAccount.getaccountBalance())-amount);
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
