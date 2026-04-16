
/**
 * create reliable input ofr stirngs and ints that can be called from main
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
     * This makes sure a float is returned
     */
    public Float readNum (boolean negativeAllowed){
        boolean correctInput=false;
        float amount=0;
        Scanner keyboard=new Scanner(System.in);
        //System.out.println(prompt);
        while( correctInput==false){
            while (!keyboard.hasNextFloat()){
                keyboard.nextLine();
                System.out.println("That is not an float");

            }
            amount=keyboard.nextFloat();
            if (negativeAllowed == false && amount>0){
                correctInput=true;
            }else if (negativeAllowed == true){
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
                    if (i==2||i==7||i==15){
                        if (arr[i]==hyphen){                            
                            rightCounter++;
                        }
                    }
                    if (i==0||i==1||i==3||i==4||i==5||i==6||i==8||i==9||i==10||i==11||i==12||i==13||i==14||i==16||i==17){ 
                        if (Character.isDigit(arr[i])== true){
                            rightCounter++;
                        }
                    }
                }
            }else{
                System.out.println("That is an incorrect length" );
            }
            //System.out.println(rightCounter );
            if ((rightCounter)==18){
                correctFormat=true;
            }else{
                System.out.println("That format is incorrect");
            }
        }
        //loop through two arrays one array is just looping through the length of the user input.length 
        // the other loop is an array that tells us what to test for at each point in the arrray
        // for this to work both arrays have to be the same 'length' 
        return userInput;
    }

    public AccountType readAccountType (){
        Scanner keyboard=new Scanner(System.in);
        boolean correctFormat =false;
        AccountType accountType=null;
         System.out.println("Please enter an account type of either 1: Everyday 2: Savings 3: Current");
        while (correctFormat==false){    
            String userInput = keyboard.nextLine();
            userInput=userInput.toUpperCase();
            System.out.println(userInput);
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
        System.out.println(accountType);
        return (accountType);
    }
}
