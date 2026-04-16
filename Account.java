
/**
 * This is a constructor that will construct and edit peoples accounts
 *
 * @Santoso Winatan
 * @19/03/26
 */

public class Account
{

    private String customerName;
    private String accountNumber;// how to hyphenate or should i concatenate c
    //create error prevention system
    private String customerAdress;
    private AccountType accountType;
    private float accountBalance;  
    //@Override
    //public String toString() {
        //return "[" + customerName + "; " + accountNumber+ "; " + customerAdress + "; "+ accountType + "; "+ accountBalance + "]" ;
   // }

    /**
     * This is a constructor that sets the values of the accounts
     *
     */
    public Account(String customerName, String accountNumber, String customerAdress, AccountType accountType,float accountBalance){
        //final 
        this.customerName=customerName;
        this.accountNumber=accountNumber;
        this.customerAdress=customerAdress;
        this.accountType=accountType;
        this.accountBalance=accountBalance;
        
        
        /*
        switch(accountType) {
            case "EVERYDAY":
                this.accountType= EVERYDAY;
                break;
            case "SAVINGS":
                accountType= "SAVINGS";
                break;
            case "CURRENT":
                accountType= "CURRENT";
                break;                
        }
        */
    }

    /**
     * This allows a customer name to be changed from outside this class
     */
    public void setcustomerName(String newValue){
        this.customerName = newValue;
    }

    /**
     * This allows a customer name to be accessed from outside this class
     */
    public String getcustomerName(){
        return(this.customerName);
    }

    /**
     * This allows a account number to be changed from outside this class
     */
    public void setaccountNumber(String newValue){
        this.accountNumber = newValue;
    }

    /**
     * This allows a account number to be accessed from outside this class
     */
    public String getaccountNumber(){
        return(this.accountNumber);
    }

    /**
     * This allows a customer adress to be changed from outside this class
     */
    public void setcustomerAdress(String newValue){
        this.customerAdress = newValue;
    }

    /**
     * This allows a customer adress to be accessed from outside this class
     */
    public String getcustomerAdress(){
        return(this.customerAdress);
    }

    /**
     * This allows a account type to be changed from outside this class
     */
    public void setaccountType(AccountType newValue){
        this.accountType = newValue;
    }

    /**
     * This allows a account type to be accessed from outside this class
     */
    public AccountType getaccountType(){
        return(this.accountType);
    }

    /**
     * This allows a account balance to be changed from outside this class
     */
    public void setaccountBalance(float newValue){
        this.accountBalance = newValue;
    }

    /**
     * This allows a account balance to be accessed from outside this class
     */
    public float getaccountBalance(){
        return(this.accountBalance);
    }

}
