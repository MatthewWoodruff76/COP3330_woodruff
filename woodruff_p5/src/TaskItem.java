public class TaskItem {
    private String title;
    private String due_date;
    private String description;
    private boolean complete;
    protected TaskItem(String titleIN, String descriptionIN, String due_dateIN, boolean completeIN){
        title = titleIN;
        due_date = due_dateIN;
        description = descriptionIN;
        complete = completeIN;
    }
    public static boolean DateIsValid(String dateIN) {
        if (dateIN.length() != 10) {
            System.out.print("\nYour date must be 10 characters long.\n");
            return false;
        }
        if (!FormulaHandler(dateIN)) {
            System.out.print("\nYour date does not adhere to the proper formula.\n");
            return false;
        }
        String dates[]  = dateIN.split("-",0);
        return CalendarHandler(dates);
    }
    protected static int StringToInt(String String) {
        try {
            Integer.parseInt(String);
        } catch (NumberFormatException e) {
            System.out.println("\nYour input contains non-integer characters.\n");
            return 0;
        }
        int output = 0, digit;
        for(int i = 0; i < String.length(); i ++){
            digit = String.charAt(i)-48;
            output = 10 * output + digit;
        }
        return output;
    }    //Isolates a section of the input string and converts it to its integer value.
    protected static boolean CalendarHandler(String[] dates) {
        int year, month, day;
        year        = StringToInt(dates[0]);
        month       = StringToInt(dates[1]);
        day         = StringToInt(dates[2]);
        int[] validDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (year % 4 == 0) validDays[1] = 29;
        if (month > 12 || month < 1) {
            System.out.print("\nYour month is invalid.\n");
            return false;
        }
        if (day > validDays[month - 1] | day < 1) {
            System.out.print("\nThat is not a valid day for your listed month.\n");
            return false;
        }
        return true;
    }    //Verifies the date exists.
    protected static boolean FormulaHandler(String input) {
        if (input.charAt(4) != '-' || input.charAt(7) != '-') return false;   //Checks for dashes.
        return input.length() == 10;                           //Checks for length issues.
    }    //Verifies the date follows the formula.
    public static boolean TitleIsValid(String title) {
        if(title.length() == 0){
            System.out.print("\nYour title needs at least one character.\n");
            return false;
        }
        return true;
    }
    public boolean setTitle(String titleIN){
        if(TitleIsValid(titleIN)) {
            title = titleIN;
            return true;
        }
        return false;
    }
    public boolean setDue_date(String due_dateIN){
        if(DateIsValid(due_dateIN)) {
            due_date = due_dateIN;
            return true;
        }
        return false;
    }
    public boolean setDescription(String descriptionIN){
        description = descriptionIN;
        return true;
    }
    public void setComplete(){
        complete = !complete;
    }
    public boolean getComplete(){
        return complete;
    }
    public String getDescription() {
        return description;
    }
    public String getTitle() {
        return title;
    }
    public String getDue_date() {
        return due_date;
    }

    public String AmassTaskInfo() {
        return  title + "\r\n" + description + "\r\n"
                + due_date + "\r\n" + complete + "\r\n" ;
    }
}
