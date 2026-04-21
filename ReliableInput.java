/**
 * This has functions that return floats, account numbers, and account types reliably
 *
 * @Santoso Winatan
 * @21/04/26
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
import java.lang.*;
public class ReliableInput
{
    /**
     * This makes sure a float is returned and that the float isn't negative if that is necessary
     */
    public Float readNum (boolean negativeAllowed){
        Scanner keyboard=new Scanner(System.in);
        
        final float MINIMUM_ACCOUNTBALANCE= 0; //The minimum account balance for everyday and savings accounts, also used to make sure values are positive
        final boolean NEGATIVE_ALLOWED=true; // This means that a negative can be returned as it is valid for that account type
        final boolean NEGATIVE_NOT_ALLOWED=false; // This means a negative cannot be returned as it is not valid for that account type or the float is needed for a deposit/withdrawal
        final boolean NOT_FOUND=false;
        
        boolean correctInput=false; // False= user has not inputted a suitable float. True = user has inputted a suitable float.
        float amount=0;// Defines float so it can be returned

        while(correctInput==NOT_FOUND){
            while (!keyboard.hasNextFloat()){
                keyboard.nextLine();
                System.out.println("That is not an float");
            }// Makes sure code doesn't break if a string is entered
            
            amount=keyboard.nextFloat();
            
            if (negativeAllowed == NEGATIVE_NOT_ALLOWED && amount>=MINIMUM_ACCOUNTBALANCE){
                correctInput=true;
            }
            else if (negativeAllowed == NEGATIVE_ALLOWED){
                correctInput=true;
            }
            else{
                System.out.println("The float has to be positive");
            }//Makes sure that the account balances adhere to the rules of that type of account
        }

        return amount;// Returns the amount that the user wants that is allowed
    }

    /**
     * This makes sure a properly formatted account number is returned
     */
    public String readAccountNum (){
        Scanner keyboard=new Scanner(System.in);
        
        final boolean NOT_FOUND=false; // This means that no valid input has been entered yet
        final boolean UNIQUE_ACCOUNT=true; // This means that an account is unique
        final boolean IS_DIGIT=true; // This means that a character is a digit
        final int ACCOUNT_NUMBER_LENGTH= 18;
        final char HYPHEN='-';
        final int[] HYPHEN_POSITIONS={-1,0,2,0,0,0,0,7,0,0,0,0,0,0,0,15,0,0,0};// Has the position of where each hyphen must be in an account number
        final int[] INT_POSITIONS={0,1,0,3,4,5,6,0,8,9,10,11,12,13,14,0,16,17,0};// Has the position of where each int must be in an account number
        
        boolean accountUnique=true; 
        boolean correctFormat=false;
        String userInput="null";
        
        System.out.println("Please enter an account number with format 2-4-7-2");
        System.out.println("Example: 01-0123-0123456-01");
        while (correctFormat==NOT_FOUND){
            int rightCounter=0;
            userInput = keyboard.nextLine();
            char[] arr = userInput.toCharArray(); // This splits string into indvidual characters
            
            if (arr.length==ACCOUNT_NUMBER_LENGTH){                
                for (int i =0; i<ACCOUNT_NUMBER_LENGTH; i++){
                    if (i==HYPHEN_POSITIONS[i] && arr[i]==HYPHEN){                   
                        rightCounter++;
                    }
                    else if (i==INT_POSITIONS[i] && Character.isDigit(arr[i])== IS_DIGIT){ 
                        rightCounter++;
                    }
                }
            }else{
                System.out.println("That is an incorrect length" );
            }// This checks if individual characters are the right type and in the right position
            
            if ((rightCounter)==ACCOUNT_NUMBER_LENGTH && accountUnique==UNIQUE_ACCOUNT){
                correctFormat=true;
            }// If every character is correct then the format is right
            else{
                System.out.println("That format is incorrect");
            }
        }// This makes sure a account number with the right format is returned

        return userInput; // Returns a account number with the right format
    }

    /**
     * This gets a string and returns an enum value of AccountType.
     */
    public AccountType readAccountType (){
        Scanner keyboard=new Scanner(System.in);
        
        final boolean NOT_FOUND=false; // This means that no valid input has been entered yet
        
        boolean correctFormat =false;
        AccountType accountType=null;
        
        System.out.println("Please enter an account type of either 1: Everyday 2: Savings 3: Current");
        while (correctFormat==NOT_FOUND){    
            String userInput = keyboard.nextLine();
            userInput=userInput.toUpperCase();

            switch(userInput) {//
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
            }// Checks input and if it matches an account type it sets the account as that
            // After adding an enum add another case with the new account type here if a new account type is ever needed     
        }

        System.out.println("Account type is: "+ accountType);
        return (accountType);// Returns a valid account typer 
    }
}