import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;


public class App {
    public static Scanner in = new Scanner(System.in);
    public static int trigger = 0;
    public static String AllOutput = "";
    public static void main(String[] args) {
        TaskList Archive = new TaskList();
        int MainSelection;
        do {
            int OperationSelection = -1;
            PrintMainMenu();
            while (OperationSelection == -1) OperationSelection = MenuHandler(3, getInput(in));
            MainSelection = OperationSelection;
            if(MainSelection == 1){
                System.out.println("A new task list has been created.\n\n");
                OperationMenu(Archive.Docket);
            }
            if(MainSelection == 2){
                System.out.println("Output file name (no extension required): ");
                String FileNameIn = in.nextLine() + ".txt";
                String placeholder, titleIn = "Error", descriptionIn = "Error", due_dateIn = "Error";
                boolean statusIn;
                trigger = 0;
                try {
                    BufferedReader input = new BufferedReader(new FileReader(FileNameIn));
                    int tally = 1;
                    while((placeholder = input.readLine()) != null) {
                        if((tally + 1) % 4 == 0) titleIn = placeholder;
                        if((tally + 2) % 4 == 0) descriptionIn = placeholder;
                        if((tally + 3) % 4 == 0) due_dateIn = placeholder;
                        if(tally % 4 == 0){
                            statusIn = Boolean.parseBoolean(placeholder);
                            TaskItem Task = new TaskItem(titleIn, descriptionIn, due_dateIn, statusIn);
                            Archive.Docket.add(Task);
                        }
                        tally++;
                    }
                } catch (IOException e) {
                    trigger = 1;
                }
                if(trigger == 1) System.out.println("\nThe file could not be found.\n");
                else{
                    System.out.println("\nThe list was loaded successfully.\n");
                    OperationMenu(Archive.Docket);
                }
            }
        } while(MainSelection != 3);
    }
    private static void     OperationMenu(ArrayList<TaskItem> Docket) {
        int OperationSelection;
        do {
            int TaskSelection = -1;
            PrintOperationMenu();
            while (TaskSelection == -1) TaskSelection = MenuHandler(8, getInput(in));
            OperationSelection = TaskSelection;
            TaskAction(TaskSelection, Docket);
        } while (OperationSelection != 8);
    }
    private static void     TaskAction(int selection, ArrayList<TaskItem> Docket) {
        int index = -1;
        if(selection == 1) PrintAllTasks(Docket);
        if(selection == 2) {
            String titleIn = "#null#", descriptionIn = "#null#", due_dateIn = "#null#";
            trigger = 0;
            System.out.print("Task title: ");
            while(trigger!=1)   titleIn =        TitleHandler(getInput(in));
            trigger = 0;
            System.out.print("Task description: ");
            while(trigger!=1)   descriptionIn =  DescriptionHandler((getInput(in)));
            System.out.print("Task due date (YYYY-MM-DD): ");
            trigger = 0;
            while(trigger!=1)   due_dateIn =     Due_DateHandler(getInput(in));
            TaskItem Task = new TaskItem(titleIn, descriptionIn, due_dateIn, false);
            Docket.add(Task);
        }
        if(selection == 3) {
            PrintAllTasks(Docket);
            System.out.println("\n\nSelect a task to edit: ");
            while (index == -1) index = MenuHandler(Docket.size(), getInput(in)) - 1;
            trigger = 0;
            System.out.print("New Task title: ");
            while(trigger!=1)   Docket.get(index).title =           TitleHandler(getInput(in));
            trigger = 0;
            System.out.print("New Task description: ");
            while(trigger!=1)   Docket.get(index).description =     DescriptionHandler((getInput(in)));
            trigger = 0;
            System.out.print("New Task due date (YYYY-MM-DD): ");
            while(trigger!=1)   Docket.get(index).due_date =        Due_DateHandler(getInput(in));
        }
        if(selection == 4) {
            index = -1;
            PrintAllTasks(Docket);
            System.out.println("\n\nSelect a task to remove: ");
            while (index == -1) index = MenuHandler(Docket.size(), getInput(in)) - 1;
            Docket.remove(index);
        }
        if(selection == 5) {
            index = -1;
            PrintTasksByCompletion(Docket,false);
            int[] key = GenerateCompletionKey(Docket, false);
            if(key.length > 0) {
                System.out.println("\n\nSelect a task to mark complete: ");
                while (index == -1) index = MenuHandler(key.length, getInput(in)) - 1;
                Docket.get(key[index]).complete = true;
            }
        }
        if(selection == 6) {
            index = -1;
            PrintTasksByCompletion(Docket,true);
            int[] key = GenerateCompletionKey(Docket, true);
            if(key.length > 0) {
                System.out.println("\n\nSelect a task to mark incomplete: ");
                while (index == -1) index = MenuHandler(key.length,getInput(in)) - 1;
                Docket.get(key[index]).complete = false;
            }
        }
        if(selection == 7) {
            System.out.print("\n\nOutput file name (no extension required): ");
            String FileName = "Anonymous";
            trigger = 0;
            while(trigger!=1)   FileName = CreateFile(FileHandler(getInput(in)));
            SaveTaskList(Docket, FileName);
        }
    }
    private static void    SaveTaskList(ArrayList<TaskItem> Docket, String FileName) {
        try {
            FileWriter SavePoint = new FileWriter(FileName);
            SavePoint.write(AmassListInfo(Docket));
            SavePoint.close();
            System.out.println("\nTasks saved to " + FileName + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static String   AmassListInfo(ArrayList<TaskItem> Docket) {
        String ListInfo = "";                                           //Empty opening statement.
        for(int index = 0; index < Docket.size(); index++){
            ListInfo += AmassTaskInfo(Docket.get(index));
        }
        return ListInfo;
    }
    private static String   AmassTaskInfo(TaskItem TaskItem) {
        return  TaskItem.title + "\n" + TaskItem.description + "\n"
                + TaskItem.due_date + "\n" + TaskItem.complete + "\n" ;
    }
    private static String     CreateFile(String FileName) {
        try {
            File document = new File(FileName + ".txt");
            if (document.createNewFile()) {
                System.out.println("The task list was saved under this name: " + document.getName());
            } else {
                System.out.println("A file with that name already exists there...");
            }
        } catch (IOException e) {
            System.out.println("Something went wrong...");
            e.printStackTrace();
        }
        return FileName;
    }
    private static int[]    GenerateCompletionKey(ArrayList<TaskItem> Docket, boolean complete) {
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
    private static String   getInput(Scanner scanner){
        String input = scanner.nextLine();
        return input;
    }
    private static void     PrintOperationMenu() {
        AllOutput = "\nList Operation Menu\n" +
                "---------\n\n" +
                "1) view the list\n" +
                "2) add an item\n" +
                "3) edit an item\n" +
                "4) remove an item\n" +
                "5) mark an item as completed\n" +
                "6) unmark an item as completed\n" +
                "7) save the current list\n" +
                "8) quit to the main menu\n\n";
        System.out.println(AllOutput);
    }
    private static void     PrintMainMenu() {
        AllOutput = "Main Menu\n---------\n\n" +
                "1) create a new list\n" +
                "2) load an existing list\n" +
                "3) quit\n\n";
        System.out.println(AllOutput);
    }
    private static void     PrintTasksByCompletion(ArrayList<TaskItem> Docket, boolean complete) {
        if(!complete)         AllOutput = "Current Incomplete Tasks\n------------------------\n\n";
        else         AllOutput = "Current Complete Tasks\n----------------------\n\n";
        int position = 0;
        for(int index = 0; index < Docket.size();index ++) {
            if(Docket.get(index).complete == complete) {
                AllOutput += (position + 1) + ") [" + Docket.get(index).due_date + "] "
                        + Docket.get(index).title + ": " + Docket.get(index).description + "\n";
                position++;
            }
        }
        System.out.println(AllOutput);
    }
    private static void     PrintAllTasks(ArrayList<TaskItem> Docket) {
        AllOutput = "Current Tasks\n-------------\n\n";
        for(int index = 0; index < Docket.size();index ++) {
            AllOutput += (index + 1) + ") [" + Docket.get(index).due_date + "] "
                    + Docket.get(index).title + ": " + Docket.get(index).description + "\n";
        }
        System.out.println(AllOutput);
    }
    private static String   FileHandler(String input){  //Checks for blatant violations in naming.
        String[] criminals = { "/", "\n", "\r", "\t", "\0", "\f", "`", "?", "*", "<", ">", "|", ":", String.valueOf((char)34)};
        for(int index = 0; index < criminals.length; index++) {
            if (input.contains(criminals[index])){
                System.out.println("\nYour file name has invalid character.\nOutput file name (no extension required): ");
                return input;
            }
        }
        trigger = 1;
        return input;
    }
    private static String   DescriptionHandler(String input) {
        return input;   //Does not have error handling.
    }
    private static String   Due_DateHandler(String dateIn) {
        int[] validDays =   {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date = formatter.format(new Date());
        if(dateIn.length()!=10){
            System.out.println("\nThe entry is not a valid length.\n");
            return dateIn;
        }     //Handles length errors before reaching sensitive code.
        int year, yearNow, month, monthNow, day, dayNow, valid = 1;

        year        = StringToInt(0,4,dateIn);
        month       = StringToInt(5,7,dateIn);
        day         = StringToInt(8,10,dateIn);
        yearNow     = StringToInt(0,4,date);
        monthNow    = StringToInt(5,7,date);
        dayNow      = StringToInt(8,10,date);

        valid *= StringToIntHandler(0, 4, dateIn);
        valid *= StringToIntHandler(5,7,dateIn);
        valid *= StringToIntHandler(8,10,dateIn);
        valid *= FormulaHandler(dateIn);
        valid *= PastHandler(year, yearNow, month, monthNow, day, dayNow);
        valid *= CalendarHandler(year, month, day, validDays);
        if(valid % 2 == 0) System.out.println("\nYour entry does not adhere to the required formula.\n");
        if(valid % 3 == 0) System.out.println("\nYour entry is in the past.\n");
        if(valid % 5 == 0) System.out.println("\nYour entry is not a valid date.\n");
        if(valid == 1) {
            trigger = 1;
            return dateIn;   //Only returns if all conditions are met.
        }
        System.out.print("Task due date (YYYY-MM-DD): ");
        return dateIn;
    }
    private static String   TitleHandler(String titleIn) {
        if(titleIn.length()==0) {
            System.out.println("\nAt least one character is required.\n\nTask title: ");
        } else trigger = 1;
        return titleIn;   //Verifies length.
    }
    private static int      MenuHandler(int length, String selection) {
        int choice;
        try{
            choice = StringToInt(0, selection.length(), selection);
        }
        catch (Exception e){
            System.out.println("\nThat wasn't even a number...  Try again.\n");
            choice = -1;
        }
        if (choice > length || choice <= 0){
            System.out.println("\nThat was not a listed option. Try again.\n");
            choice = -1;
        }
        return choice;
    }
    private static int      FormulaHandler(String input) {
        if(input.charAt(4) != 45 || input.charAt(7) != 45) return 2; //Checks for dashes.
        return 1;
    }
    private static int      StringToIntHandler(int start, int end, String input) {
        for(int index = start; index < end; index ++) {
            int test = input.charAt(index) - 48;
            if (test > 9 || test < 0) return 2;
        }
        return 1;
    }
    private static int      StringToInt(int start, int end, String input) {
        int output = 0, digit, index;
        for(index = start; index < end; index ++){
            digit = input.charAt(index)-48;
            output = 10 * output + digit;
        }
        return output;
    }
    private static int      CalendarHandler(int year, int month, int day, int[] validDays) {
        if(year  % 4 == 0) validDays[1] = 29;                       //February will be destroyed
        if(month > 12 || month < 1) return 5;
        if(day > validDays[month - 1]) return 5;
        return 1;
    }
    private static int      PastHandler(int year, int yearNow, int month, int monthNow, int day, int dayNow) { //Checks for due dates in the past.
        if(year >= yearNow) {
            if(year == yearNow){
                if(month>=monthNow) {
                    if (month == monthNow) {
                        if(day >= dayNow){
                            if(day == dayNow){
                                System.out.println("\nNose to the grind, huh?\n");
                            }
                        } else return 3;
                    }
                } else return 3;
            }
        } else return 3;
        return 1;
    }

    @Test public void RunAll(){
        GenerateCompletionKeyComplete();
        GenerateCompletionKeyIncomplete();
        getInputTest();
        PrintOperationMenuTest();
        PrintMainMenuTest();
        PrintTasksByCompletionComplete();
        PrintTasksByCompletionIncomplete();
        PrintAllTasksTest();
        FileHandlerValid();
        FileHandlerInvalid();
        DescriptionHandlerValid();
        Due_DateHandlerValid();
        Due_DateHandlerInvalid();
        TitleHandlerValid();
        TitleHandlerInvalid();
        MenuHandlerValid();
        MenuHandlerInvalid();
        FormulaHandlerValid();
        FormulaHandlerInvalid();
        StringToIntHandlerValid();
        StringToIntHandlerInvalid();
        StringToIntValid();
        StringToIntInvalid();
        CalendarHandlerValid();
        CalendarHandlerInvalid();
        PastHandlerValid();
        PastHandlerInvalid();
    }
    @Test public void GenerateCompletionKeyIncomplete(){
        int[] expectedInt = {0,2,3,4};
        TaskList Archive = new TaskList();
        TaskItem Task = new TaskItem("Title", "Description", "3999-12-01", false);
        Archive.Docket.add(Task);
        Task = new TaskItem("Blank", "Nothing", "9999-09-25", true);
        Archive.Docket.add(Task);
        Task = new TaskItem("Something", "Tag", "3999-12-01", false);
        Archive.Docket.add(Task);
        Task = new TaskItem("Null", "Red", "9999-09-25", false);
        Archive.Docket.add(Task);
        Task = new TaskItem("Yes", "blue", "3999-12-01", false);
        Archive.Docket.add(Task);
        Task = new TaskItem("No", "_orange", "3030-09-11", true);
        Archive.Docket.add(Task);
        int[] receivedInt = GenerateCompletionKey(Archive.Docket, false);
        for(int index = 0; index < receivedInt.length; index++) assertEquals(expectedInt[index],receivedInt[index]);
    }
    @Test public void GenerateCompletionKeyComplete(){
        int[] expectedInt = {1,5};
        TaskList Archive = new TaskList();
        TaskItem Task = new TaskItem("Title", "Description", "3999-12-01", false);
        Archive.Docket.add(Task);
        Task = new TaskItem("Blank", "Nothing", "9999-09-25", true);
        Archive.Docket.add(Task);
        Task = new TaskItem("Something", "Tag", "3999-12-01", false);
        Archive.Docket.add(Task);
        Task = new TaskItem("Null", "Red", "9999-09-25", false);
        Archive.Docket.add(Task);
        Task = new TaskItem("Yes", "blue", "3999-12-01", false);
        Archive.Docket.add(Task);
        Task = new TaskItem("No", "_orange", "3030-09-11", true);
        Archive.Docket.add(Task);
        int[] receivedInt = GenerateCompletionKey(Archive.Docket, true);
        for(int index = 0; index < receivedInt.length; index++) assertEquals(expectedInt[index],receivedInt[index]);
    }
    @Test public void getInputTest(){
        String input = "Something Clever";
        InputStream virtualIn = System.in;
        System.setIn(virtualIn);
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        String output = getInput(scanner);
        assertEquals(input,output);
        System.out.println(output);
    }
    @Test public void PrintOperationMenuTest(){
        String expectedString = "\nList Operation Menu\n" +
                "---------\n\n" +
                "1) view the list\n" +
                "2) add an item\n" +
                "3) edit an item\n" +
                "4) remove an item\n" +
                "5) mark an item as completed\n" +
                "6) unmark an item as completed\n" +
                "7) save the current list\n" +
                "8) quit to the main menu\n\n";
        PrintOperationMenu();
        assertEquals(expectedString,AllOutput);
    }
    @Test public void PrintMainMenuTest(){
        String expectedString = "Main Menu\n---------\n\n" +
                "1) create a new list\n" +
                "2) load an existing list\n" +
                "3) quit\n\n";
        PrintMainMenu();
        assertEquals(expectedString,AllOutput);
    }
    @Test public void PrintTasksByCompletionComplete(){
        TaskList Archive = new TaskList();
        TaskItem Task = new TaskItem("Title", "Description", "3999-12-01", false);
        Archive.Docket.add(Task);
        Task = new TaskItem("Blank", "Nothing", "9999-09-25", true);
        Archive.Docket.add(Task);
        PrintTasksByCompletion(Archive.Docket, true);
        String expectedString = "Current Complete Tasks\n" +
                "----------------------\n\n1) [9999-09-25] Blank: Nothing\n";
        assertEquals(expectedString,AllOutput);
    }
    @Test public void PrintTasksByCompletionIncomplete(){
        TaskList Archive = new TaskList();
        TaskItem Task = new TaskItem("Title", "Description", "3999-12-01", false);
        Archive.Docket.add(Task);
        Task = new TaskItem("Blank", "Nothing", "9999-09-25", true);
        Archive.Docket.add(Task);
        PrintTasksByCompletion(Archive.Docket, false);
        String expectedString = "Current Incomplete Tasks\n" +
                "------------------------\n\n1) [3999-12-01] Title: Description\n";
        assertEquals(expectedString,AllOutput);
    }
    @Test public void PrintAllTasksTest(){
        TaskList Archive = new TaskList();
        TaskItem Task = new TaskItem("Title", "Description", "3999-12-01", false);
        Archive.Docket.add(Task);
        Task = new TaskItem("Blank", "Nothing", "9999-09-25", true);
        Archive.Docket.add(Task);
        PrintAllTasks(Archive.Docket);
        String expectedString = "Current Tasks\n" + "-------------\n" + "\n" +
                "1) [3999-12-01] Title: Description\n" + "2) [9999-09-25] Blank: Nothing\n";
        assertEquals(expectedString,AllOutput);
    }
    @Test public void FileHandlerValid(){
        trigger = 0;
        FileHandler("Valid File");
        assertEquals(1,trigger);
    }
    @Test public void FileHandlerInvalid(){
        trigger = 0;
        FileHandler("Invalid:File");
        assertEquals(0,trigger);
    }
    @Test public void DescriptionHandlerValid(){
        assertEquals("Valid Title",DescriptionHandler("Valid Title"));
    }
    @Test public void Due_DateHandlerValid(){
        trigger = 0;
        Due_DateHandler("2020-12-25");
        assertEquals(1,trigger);
    }
    @Test public void Due_DateHandlerInvalid(){
        trigger = 0;
        Due_DateHandler("2019*12-41");
        assertEquals(0,trigger);
    }
    @Test public void TitleHandlerValid(){
        trigger = 0;
        TitleHandler("Task 1");
        assertEquals(trigger,1);
    }
    @Test public void TitleHandlerInvalid(){
        trigger = 0;
        TitleHandler("");
        assertEquals(trigger,0);
    }
    @Test public void MenuHandlerValid(){
        assertEquals(1,MenuHandler(2,"1"));
    }
    @Test public void MenuHandlerInvalid(){
        assertEquals(-1,MenuHandler(20,"21"));
    }
    @Test public void FormulaHandlerValid(){
        assertEquals(1,FormulaHandler("2024-02-29"));
    }
    @Test public void FormulaHandlerInvalid(){        //Flags characters other than '-' which are used as spacers.
        assertEquals(2,FormulaHandler("2024*02+29"));
    }
    @Test public void StringToIntHandlerValid(){
        assertEquals(1,StringToIntHandler(0,3,"123"));
    }
    @Test public void StringToIntHandlerInvalid(){    //Flags invalid inputs for feedback later.
        assertEquals(2,StringToIntHandler(0,3,"ABC"));
    }
    @Test public void StringToIntValid(){
        assertEquals(123,StringToInt(0,3,"123"));
    }
    @Test public void StringToIntInvalid(){           //Verifies that errors are not produced by invalid entries.
        assertEquals(1899,StringToInt(0,3,"ABC"));
    }
    @Test public void CalendarHandlerValid(){
        int[] validDays =   {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        assertEquals(1,CalendarHandler(20244,02,29, validDays));
    }
    @Test public void CalendarHandlerInvalid(){
        int[] validDays =   {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        assertEquals(5,CalendarHandler(2021,02,29, validDays));
    }
    @Test public void PastHandlerValid(){
        assertEquals(1,PastHandler(2020,2020,12,11,04,25));
    }
    @Test public void PastHandlerInvalid(){
        assertEquals(3,PastHandler(1776,2020,07,11,04,25));
    }
}
