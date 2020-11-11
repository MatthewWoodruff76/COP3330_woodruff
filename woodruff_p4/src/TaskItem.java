import org.junit.Test;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class TaskItem {     //Task item is just a structure that stores information.

    protected static String title           =   "Blank";
    protected static String description     =   "No";
    protected static String due_date        =   "0000-00-00";   //YYYY-MM-DD
    protected static boolean complete       =   false;              //Status is 1 if complete, 0 if incomplete.

    public TaskItem(String titleIn, String descriptionIn, String due_dateIn, boolean completeIN){
        title           =   titleIn;
        description     =   descriptionIn;
        due_date        =   due_dateIn;
        complete        =   completeIN;
    }
    @Test public void InvalidDue_Date(){
        assertEquals(210, Due_DateHandler("2020*02-300"));
    }
    @Test public void ValidDue_Date(){
        assertEquals(1, Due_DateHandler("3000-02-29"));
    }
    //Prints off issues with due_date
    protected static boolean TitleMistakesReport(int mistakes) {
        if(mistakes % 5 == 0) System.out.println("\nYour entry does not adhere to the required formula.\n");
        if(mistakes % 2 == 0) System.out.println("\nYour entry is in the past.\n");
        if(mistakes % 3 == 0) System.out.println("\nYour entry is not a valid date.\n");
        if(mistakes % 7 == 0) System.out.println("\nYour entry is not the right length.\n");
        if(mistakes == 1) return true;
        return false;
    }
    //Produces a scorecard based on errors present in the due date.
    protected static int Due_DateHandler(String due_dateIn){
        int mistakes, year, yearNow, month, monthNow, day, dayNow;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String current_date = formatter.format(new Date());
        year        = IsolateUnit(0,4,due_dateIn);
        month       = IsolateUnit(5,7,due_dateIn);
        day         = IsolateUnit(8,10,due_dateIn);
        yearNow     = IsolateUnit(0,4,current_date);
        monthNow    = IsolateUnit(5,7,current_date);
        dayNow      = IsolateUnit(8,10,current_date);
        mistakes  = PastDateHandler(year, yearNow, month, monthNow, day, dayNow);
        mistakes *= CalendarHandler(year, month, day);
        mistakes *= FormulaHandler(due_dateIn);
        return mistakes;
    }
    //Verifies the title has at least one character.
    protected static boolean TitleHandler(String titleIn) {
        if(titleIn.length()==0) System.out.println("\nAt least one character is required.\n");
        else return true;
        return false;
    }
    //Isolates a section of the input string and converts it to integer values.
    protected static int IsolateUnit(int start, int end, String Date) {
        int output = 0, digit;
        for(int index = start; index < end; index ++){
            digit = Date.charAt(index)-48;
            if((digit)<0||digit > 9) return 0;   //No valid date will be 00 or 0000, this will be caught later.
            output = 10 * output + digit;
        }
        return output;
    }
    //Verifies the date isn't in the past.
    protected static int PastDateHandler(int year, int yearNow, int month, int monthNow, int day, int dayNow){
        if(year<yearNow||(year==yearNow&&((month<monthNow)||(month==monthNow&&day<dayNow)))) return 2;
        return 1;
    }
    //Verifies the date exists.
    protected static int CalendarHandler(int year, int month, int day) {
        int[] validDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (year % 4 == 0) validDays[1] = 29;                       //February will be destroyed
        if (month > 12 || month < 1) return 3;
        if (day > validDays[month - 1] | day < 1) return 3;
        return 1;
    }
    //Verifies the date is entered correctly.
    protected static int FormulaHandler(String input) {
        int mistake1 = 1, mistake2 = 1;
        if (input.charAt(4) != 45 || input.charAt(7) != 45) mistake1 = 5;   //Checks for dashes.
        if (input.length() != 10)   mistake2 = 7;                           //Checks for length issues.
        return mistake1*mistake2;
    }
    @Test public void ValidTitleValid(){
        String titleIn = "ValidName";
        assertTrue(TitleHandler(titleIn));
    }
    @Test public void InvalidTitleValid(){
        String titleIn = "";
        assertFalse(TitleHandler(titleIn));
    }


}

