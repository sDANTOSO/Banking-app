
/**
 * create reliable input of stirngs and ints that can be called from main
 *
 * @Santoso Winatan
 * @version (a version number or a date)
 */
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
import java.lang.*;

public class ReliableInput
{
    ArrayList <Account> database = new ArrayList<Account>();
    AccountDatabase accountDatabase= new AccountDatabase();
    /**
     * This makes sure a float is returned and checks whether it is negative.
     */
    public Float readNum (boolean negativeAllowed){
        Scanner keyboard=new Scanner(System.in);
        
        final float MINIMUM_ACCOUNTBALANCE= 0;
        final boolean NEGATIVE_ALLOWED=true;
        final boolean NEGATIVE_NOT_ALLOWED=false;
        boolean correctInput=false;
        float amount=0;

        while( correctInput==false){
            while (!keyboard.hasNextFloat()){
                keyboard.nextLine();
                System.out.println("That is not an float");

            }
            amount=keyboard.nextFloat();
            if (negativeAllowed == NEGATIVE_NOT_ALLOWED && amount>=MINIMUM_ACCOUNTBALANCE){
                correctInput=true;
            }else if (negativeAllowed == NEGATIVE_ALLOWED){
                correctInput=true;
            }else{
                System.out.println("The float has to be positive");
            }
        }

        return amount;
    }

    /**
     * This makes sure a properly formatted account number is returned
     */
    public String readAccountNum (){
        Scanner keyboard=new Scanner(System.in);

        boolean accountUnique=true; 
        boolean correctFormat=false;
        char hyphen='-';

        String userInput="null";
        System.out.println("Please enter an account number with format 2-4-7-2");
        System.out.println("Example: 01-0123-0123456-01");
        while (correctFormat==false){
            int rightCounter=0;
            userInput = keyboard.nextLine();
            char[] arr = userInput.toCharArray();

            if (arr.length==18){                
                for (int i =0; i< 18; i++){
                    if (i==2||i==7||i==15 && arr[i]==hyphen){//turn into finals                     
                        rightCounter++;
                    }
                    else if (i==0||i==1||i==3||i==4||i==5||i==6||i==8||i==9||i==10||i==11||i==12||i==13||i==14||i==16||i==17 && Character.isDigit(arr[i])== true){ 
                        rightCounter++;
                    }
                }
            }else{
                System.out.println("That is an incorrect length" );
            }

            for (Account thisAccount: this.database ){
                if (thisAccount.getaccountNumber().equals(userInput)){
                    accountUnique=false;
                    System.out.println("That number is not unique" );
                }
                System.out.println("That number is not unique" );
            }

            if ((rightCounter)==18&& accountUnique==true){
                correctFormat=true;
            }else{
                System.out.println("That format is incorrect");
            }
        }

        return userInput;
    }

    /**
     * This gets a string and returns an enum value of AccountType.
     */
    public AccountType readAccountType (){
        Scanner keyboard=new Scanner(System.in);
        boolean correctFormat =false;
        AccountType accountType=null;
        System.out.println("Please enter an account type of either 1: Everyday 2: Savings 3: Current");
        while (correctFormat==false){    
            String userInput = keyboard.nextLine();
            userInput=userInput.toUpperCase();

            switch(userInput) {
                case "EVERYDAY":
                    accountType = AccountType.EVERYDAY;
                    correctFormat =true;
                    break;
                case "SAVINGS":
                    accountType = AccountType.SAVINGS;
                    correctFormat =true;
                    break;
                case "CURRENT":
                    accountType = AccountType.CURRENT;
                    correctFormat =true;
                    break; 
            }            
        }

        System.out.println("Account type is: "+ accountType);
        return (accountType);
    }
}
