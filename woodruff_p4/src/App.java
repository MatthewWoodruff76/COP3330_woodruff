import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        TaskList Archive = new TaskList();
        int MainSelection = 1776;
        while(MainSelection != 3){
            PrintMainMenu();
            MainSelection = MenuHandler(3);
            if(MainSelection == 1){
                System.out.println("A new task list has been created.\n\n");
                int OperationSelection = 1776;
                while(OperationSelection != 8) {
                    PrintOperationMenu();
                    OperationSelection = MenuHandler(8);
                    TaskAction(OperationSelection, Archive.Docket);
                }
            }
            if(MainSelection == 2){
                System.out.println("You entered 2!");
            }
        }
    }
    private static void TaskAction(int selection, ArrayList<TaskItem> Docket) {
        if(selection == 1) {
            PrintAllTasks(Docket);
        }
        if(selection == 2) {
            String titleIn =        GetTitle();
            String descriptionIn =  GetDescription();
            String due_dateIn =     GetDue_Date();
            TaskItem Task = new TaskItem(titleIn, descriptionIn, due_dateIn, false);
            Docket.add(Task);
        }
        if(selection == 3) {
            PrintAllTasks(Docket);
            System.out.println("\n\nSelect a task to edit: ");
            int index = MenuHandler(Docket.size()) - 1;
            Docket.get(index).title = GetTitle();
            Docket.get(index).description = GetDescription();
            Docket.get(index).due_date = GetDue_Date();
        }
        if(selection == 4) {
            PrintAllTasks(Docket);
            System.out.println("\n\nSelect a task to remove: ");
            int index = MenuHandler(Docket.size()) - 1;
            Docket.remove(index);
        }
        if(selection == 5) {
            PrintTasksByCompletion(Docket,false);
            int[] key = GenerateCompletionKey(Docket, false);
            if(key.length > 0) {
                System.out.println("\n\nSelect a task to mark complete: ");
                int index = MenuHandler(key.length) - 1;
                Docket.get(key[index]).complete = true;
            }
        }
        if(selection == 6) {
            PrintTasksByCompletion(Docket,true);
            int[] key = GenerateCompletionKey(Docket, true);
            if(key.length > 0) {
                System.out.println("\n\nSelect a task to mark incomplete: ");
                int index = MenuHandler(key.length) - 1;
                Docket.get(key[index]).complete = false;
            }
        }
        if(selection == 7) {
            //You have chosen to save the current list
        }
    }
    private static int[] GenerateCompletionKey(ArrayList<TaskItem> Docket, boolean complete) {
        int AmountValid = 0;
        for (int index = 0; index < Docket.size(); index++) {
            if (Docket.get(index).complete == complete) AmountValid++;
        }
        int[] key = new int[AmountValid];
        int position = 0;
        for (int index = 0; index < Docket.size(); index++) {
            if (Docket.get(index).complete == complete) {
                key[position] = index;
                position++;
            }
        }
        return key;
    }
    private static void PrintTasksByCompletion(ArrayList<TaskItem> Docket, boolean complete) {
        if(complete == false) System.out.println("Current Incomplete Tasks\n------------------------\n\n");
        else System.out.println("Current Complete Tasks\n----------------------\n\n");
        int position = 0;
        for(int index = 0; index < Docket.size();index ++) {
            if(Docket.get(index).complete == complete) {
                System.out.print((position + 1) + ") [" + Docket.get(index).due_date + "] "
                        + Docket.get(index).title + ": " + Docket.get(index).description + "\n");
                position++;
            }
        }
    }
    private static void PrintAllTasks(ArrayList<TaskItem> Docket) {
        System.out.println("Current Tasks\n" +
                "-------------\n\n");
        for(int index = 0; index < Docket.size();index ++) {
            System.out.print((index + 1) + ") [" + Docket.get(index).due_date + "] "
                    + Docket.get(index).title + ": " + Docket.get(index).description + "\n");
        }
    }
    private static String GetTitle() {
        System.out.print("\nTask title: ");
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        return TitleHandler(input);
    }
    private static String GetDescription() {
        System.out.print("Task description: ");
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        return DescriptionHandler(input);
    }
    private static String GetDue_Date() {
        System.out.print("Task due date (YYYY-MM-DD): ");
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        return Due_DateHandler(input);
    }
    private static void PrintOperationMenu() {
        System.out.println("\nList Operation Menu\n" +
                "---------\n\n" +
                "1) view the list\n" +
                "2) add an item\n" +
                "3) edit an item\n" +
                "4) remove an item\n" +
                "5) mark an item as completed\n" +
                "6) unmark an item as completed\n" +
                "7) save the current list\n" +
                "8) quit to the main menu\n\n");
    }
    private static void PrintMainMenu() {
        System.out.println("Main Menu\n---------\n\n" +
                "1) create a new list\n" +
                "2) load an existing list\n" +
                "3) quit\n\n");
    }
    private static int MenuHandler(int length) {
        Scanner in = new Scanner(System.in);
        int selection;
        try{
            selection = in.nextInt();
        }
        catch (Exception e){
            System.out.println("\nThat wasn't even a number...  Try again.\n");
            selection = MenuHandler(length);
        }
        if (selection > length || selection <= 0){
            System.out.println("\nThat was not a listed option. Try again.\n");
            selection = MenuHandler(length);
        }
        return selection;
    }
    private static String TitleHandler(String input) {
        if(input.length()==0) {
            return GetTitle();
        }
        return input;   //Verifies length.
    }
    private static String DescriptionHandler(String input) {
        return input;   //Does not have error handling.
    }
    private static String Due_DateHandler(String input) {
        int[] validDays =   {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date = formatter.format(new Date());
        if(input.length()!=10){
            System.out.println("\nThe entry is not a valid length.\n");
            return GetDue_Date();
        }     //Handles length errors before reaching sensitive code.

        int year = 0, yearNow = 0, month = 0, monthNow = 0, day = 0, dayNow = 0, valid = 1, test, testNow;

        year        = StringToInt(0,4,input);
        month       = StringToInt(5,7,input);
        day         = StringToInt(8,10,input);
        yearNow     = StringToInt(0,4,date);
        monthNow    = StringToInt(5,7,date);
        dayNow      = StringToInt(8,10,date);

        valid = valid * StringToIntHandler(0,4,input);
        valid = valid * StringToIntHandler(5,7,input);
        valid = valid * StringToIntHandler(8,10,input);
        valid = valid * FormulaHandler(input);
        valid = valid * PastHandler(year, yearNow, month, monthNow, day, dayNow);
        valid = valid * CalendarHandler(year, month, day, validDays);

        if(valid % 2 == 0) System.out.println("\nYour entry does not adhere to the required formula.\n");
        if(valid % 3 == 0) System.out.println("\nYour entry is in the past.\n");
        if(valid % 5 == 0) System.out.println("\nYour entry is not a valid date.\n");
        if(valid == 1) return input;   //Only returns if all conditions are met.

        return GetDue_Date();
    }
    private static int FormulaHandler(String input) {
        if(input.charAt(4) != 45 || input.charAt(7) != 45) return 2; //Checks for dashes.
        return 1;
    }
    private static int StringToIntHandler(int start, int end, String input) {
        int valid = 1;
        for(int index = start; index < end; index ++) {
            int test = input.charAt(index) - 48;
            if (test > 9 || test < 0) valid = valid * 2;
        }
        return valid;
    }
    private static int StringToInt(int start, int end, String input) {
        int output = 0, digit, index;
        for(index = start; index < end; index ++){
            digit = input.charAt(index)-48;
            output = 10 * output + digit;
        }
        return output;
    }
    private static int CalendarHandler(int year, int month, int day, int[] validDays) {
        if(year  % 4 == 0) validDays[1] = 29;                       //February will be destroyed
        if(month > 12 || month < 1) return 5;
        if(day > validDays[month - 1]) return 5;
        return 1;
    }
    private static int PastHandler(int year, int yearNow, int month, int monthNow, int day, int dayNow) { //Checks for due dates in the past.
        if(year >= yearNow) {
            if(year == yearNow){
                if(month>=monthNow) {
                    if (month == monthNow) {
                        if(day >= dayNow){
                            if(day == dayNow){
                                System.out.println("\nNose the grind, huh?\n");
                            }
                        } else return 3;
                    }
                } else return 3;
            }
        } else return 3;
        return 1;
    }
}