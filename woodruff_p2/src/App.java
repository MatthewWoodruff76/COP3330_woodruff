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
        double Buffer = in.nextDouble();
        return errorCheck(Buffer, 1);                   //Validates entry.
    }
    private static double getUserWeight() {
        System.out.println("Please enter your weight in pounds.");
        Scanner in = new Scanner(System.in);                    //Clears the input cache and reads into a Buffer double.
        double Buffer = in.nextDouble();
        return errorCheck(Buffer, 2);                    //Validates entry.
    }

    private static void displayBmiInfo(BodyMassIndex bmi) {     //Prints out the bmi score.
        System.out.printf("\nYour index of %.1f means that you are %s", bmi.index, bmi.category);
    }

    private static void displayBmiStatistics(ArrayList<BodyMassIndex> bmiData) {
        double average = calculateAverage(bmiData);
        System.out.printf("\nYour average BMI was %.1f.", average);
    }

    private static Boolean errorCheck(String Buffer){           //This errorCheck function validates the moreInput method.
        if(Buffer.matches("Y")) return true;
        if(Buffer.matches("N")) return false;
        screenClear();                                          //Clears the screen and applies an error message.
        return moreInput();                                     //Recursive repeat upon error.
    }

    private static double errorCheck(double Buffer, int Version){
        if(Buffer > 0) return Buffer;                           //Entries of 0 are not positive and are thus invalid.
        screenClear();                                          //Clears the screen and applies an error message.
        if(Version == 1) return getUserHeight();                //Recursive repeat upon error.
        return getUserWeight();
    }

    private static double calculateAverage(ArrayList<BodyMassIndex> bmiData){
        double summation = 0;                                   //Creates an intermediate variable to sum the array values.
        for(int i=0; i<bmiData.size(); i++) summation = summation + bmiData.get(i).index;
        return Math.round(10.0*summation/bmiData.size())/10.0;  //Uses the array's size data to obtain the average value.

    }

    private static void screenClear(){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\nThat is an invalid entry.\n\n\n\n");
    }
}