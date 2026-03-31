
/**
 * create reliable input ofr stirngs and ints that can be called from main
 *
 * @Santoso Winatan
 * @version (a version number or a date)
 */
import java.util.Scanner;
public class ReliableInput
{
    public Float readNum (String prompt){

        Scanner keyboard=new Scanner(System.in);
        System.out.println(prompt);
        while (!keyboard.hasNextFloat()){
            keyboard.nextLine();
            System.out.println("That is not an int");

        }
        return keyboard.nextFloat();
    }
}
