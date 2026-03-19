
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

    public Account(String customerName, String accountNumber, String customerAdress, String accountType,float accountBalance){

        this.customerName=customerName;
        this.accountNumber=accountNumber;
        this.customerAdress=customerAdress;
        this.accountBalance=accountBalance;
        switch(accountType) {
            case "EVERYDAY":
                accountType= "EVERYDAY";
                break;
            case "SAVINGS":
                accountType= "SAVINGS";
                break;
            case "CURRENT":
                accountType= "CURRENT";
                break;                
        }
    }

    /**
     * This allows a customer name to be changed from outside the program
     */
    public void setcustomerName(String newValue){
        this.customerName = newValue;
    }
    /**
     * This allows the customer name to be accessed from outside the program
     */
    public String getcustomerName(){
        return(this.customerName);
    }
    
    public void setaccountNumber(String newValue){
        this.accountNumber = newValue;
    }
    
    public String getaccountNumber(){
        return(this.accountNumber);
    }
   

    public void setcustomerAdress(String newValue){
        this.customerAdress = newValue;
    }

    public String getcustomerAdress(){
        return(this.customerAdress);
    }

    public void setaccountType(AccountType newValue){
        this.accountType = newValue;
    }

    public AccountType getaccountType(){
        return(this.accountType);
    }

    public void setaccountBalance(float newValue){
        this.accountBalance = newValue;
    }

    public float getaccountBalance(){
        return(this.accountBalance);
    }

}
