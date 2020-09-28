import java.util.ArrayList;
import java.util.Scanner;


public class App {


    public static void main(String[] args) {
        ArrayList<BodyMassIndex> bmiData = new ArrayList<BodyMassIndex>();

        while (moreInput()) {
            double height = getUserHeight();
            double weight = getUserWeight();

            BodyMassIndex bmi = new BodyMassIndex(height, weight);
            bmiData.add(bmi);

            displayBmiInfo(bmi);
        }

        displayBmiStatistics(bmiData);
    }

    private static boolean moreInput() {
        System.out.println("\nWould you like to add an entry?\nPlease enter 'Y' for 'yes' or 'N' for 'no.'");
        Scanner in = new Scanner(System.in);                   //Clears the input cache and reads into a Buffer string.
        String Buffer = in.nextLine();
        return errorCheck(Buffer);                             //Validates entry.
    }

    private static double getUserHeight() {
        System.out.println("Please enter your height in inches.");
        Scanner in = new Scanner(System.in);                   //Clears the input cache and reads into a Buffer double.
        double Buffer = in.nextInt();
        return errorCheck(Buffer, 1);                   //Validates entry.
    }
    private static double getUserWeight() {
        System.out.println("Please enter your weight in pounds.");
        Scanner in = new Scanner(System.in);                    //Clears the input cache and reads into a Buffer double.
        double Buffer = in.nextInt();
        return errorCheck(Buffer, 2);                   //Validates entry.
    }

    private static void displayBmiInfo(BodyMassIndex bmi) {
        //This gets triggered after every entry
        double Status = bmi.index;
             if(Status < 18.5)  System.out.println("\nYou're underweight.");
        else if(Status < 24.9)  System.out.println("\nYou're normal.");
        else if(Status < 29.9)  System.out.println("\nYou're overweight.");
        else if(Status >=30.0)  System.out.println("\nYou're obese.");
    }

    private static void displayBmiStatistics(ArrayList<BodyMassIndex> bmiData) {

        double summation = 0;                                   //Creates an intermediate variable to sum the array values.
        for(int i=0; i<bmiData.size(); i++) summation = summation + bmiData.get(i).index;
        double average = summation/bmiData.size();              //Uses the array's size data to obtain the average value.
        average = Math.round(average * 10.0) / 10.0;            //Rounds to one decimal precision.
        System.out.println("\nYour average BMI was " + average + ".");
    }

    private static Boolean errorCheck(String Buffer){           //This errorCheck function validates the moreInput method.
        if(Buffer.matches("Y")) return true;
        if(Buffer.matches("N")) return false;
        System.out.println("\nThat is an invalid entry.");
        return moreInput();                                     //Recursive repeat upon error.
    }

    private static double errorCheck(double Buffer, int Version){
        Scanner in = new Scanner(System.in);
        if(Buffer > 0) {
            return Buffer;
        }
        if(Buffer == 0){                                        //Special prompting for accepted but problematic cases.
            System.out.println("\nThat is technically allowed, but will cause problems.\nPlease enter your final choice: ");
            Buffer = in.nextInt();
            if(Buffer >=0) return Buffer;
        }
        System.out.println("\nThat is an invalid entry!");      //Recursive repeat upon error.
        if(Version == 1) return getUserHeight();
        return getUserWeight();
    }

}