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
        //Asks
        String Buffer;
        System.out.println("\nWould you like to add another entry?\nPlease enter 'Y' for 'yes' or 'N' for 'no.'");
        Scanner in = new Scanner(System.in);
        Buffer = in.nextLine();
        return errorCheck(Buffer);
    }

    private static double getUserHeight() {
        //Asks
        double Buffer;
        System.out.println("Please enter your height in inches.");
        Scanner in = new Scanner(System.in);
        Buffer = in.nextInt();
        return errorCheck(Buffer, 1);
    }
    private static double getUserWeight() {
        //Asks
        double Buffer;
        System.out.println("Please enter your weight in pounds.");
        Scanner in = new Scanner(System.in);
        Buffer = in.nextInt();
        return errorCheck(Buffer, 2);
    }

    private static void displayBmiInfo(BodyMassIndex bmi) {
        //This gets triggered after every entry
        double Status = bmi.stored;
        if(Status < 18.5)       System.out.println("\nYou're underweight!");
        else if(Status < 24.9)  System.out.println("\nYou're normal!");
        else if(Status < 29.9)  System.out.println("\nYou're overweight!");
        else if(Status >=30.0)  System.out.println("\nYou're obese!");
    }

    private static void displayBmiStatistics(ArrayList<BodyMassIndex> bmiData) {
        //Creates an intermediate variable to sum the array values.
        double summation = 0;
        for(int i=0; i<bmiData.size(); i++) summation = summation + bmiData.get(i).stored;
        //Uses the array's size data to obtain the average value.
        double average = summation/bmiData.size();
        System.out.println("\nYour average BMI was " + average + ".");
    }

    private static Boolean errorCheck(String Buffer){
        if(Buffer.matches("Y")) return true;
        if(Buffer.matches("N")) return false;
        //Recursive repeat upon error.
        System.out.println("\nThat is an invalid entry.");
        return moreInput();
    }

    private static double errorCheck(double Buffer, int Version){
        Scanner in = new Scanner(System.in);
        if(Buffer > 0) {
            return Buffer;
        }
        if(Buffer == 0){ //The project specifies nonzero entries as rejects, so 0 values are accepted, but require special prompting.
            System.out.println("\nThat is technically allowed, but will cause problems!\nPlease enter your final choice: ");
            Buffer = in.nextInt();
            if(Buffer >=0) return Buffer;
        }
        //Recursive repeat upon error.
        System.out.println("\nThat is an invalid entry!");
        if(Version == 1) return getUserHeight();
        return getUserWeight();
    }

}