
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
    
    public Account(String customerName, String accountNumber, String customerAdress, AccountType accountType,float accountBalance){
        
        this.customerName=customerName;
        this.accountNumber=accountNumber;
        this.customerAdress=customerAdress;
        this.accountType=accountType;
        this.accountBalance=accountBalance;
    }
    
    public void setcustomerName(String newValue){
        this.customerName = newValue;
    }
    
    public String getcustomerName(){
        return(this.customerName);
    }
    
    // add in account number
    
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
