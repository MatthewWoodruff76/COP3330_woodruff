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
        TaskList TaskList = new TaskList();
        int MainSelection;
        do {
            TaskList = ClearTaskList(TaskList);
            int OperationSelection = -1;
            PrintMainMenu();
            while (OperationSelection == -1) OperationSelection = MenuHandler(3, GetInput(in));
            MainSelection = OperationSelection;
            if(MainSelection == 1){
                System.out.println("A new task list has been created.\n\n");
                OperationMenu(TaskList.TaskItem);
            }
            if(MainSelection == 2){
                System.out.println("Input file name (no extension required): ");
                String FileNameIn = in.nextLine() + ".txt";
                String placeholder, titleIn = "#null#", descriptionIn = "#null#", due_dateIn = "#null#";
                boolean statusIn;
                trigger = 0;
                try {
                    BufferedReader input = new BufferedReader(new FileReader(FileNameIn));
                    int tally = 0;
                    while((placeholder = input.readLine()) != null) {
                        if((tally + 4) % 4 == 0) titleIn = placeholder;
                        if((tally + 3) % 4 == 0) descriptionIn = placeholder;
                        if((tally + 2) % 4 == 0) due_dateIn = placeholder;
                        if((tally + 1) % 4 == 0) {
                            statusIn = Boolean.parseBoolean(placeholder);
                            TaskItem Task = new TaskItem(titleIn, descriptionIn, due_dateIn, statusIn);
                            TaskList.TaskItem.add(Task);
                        }
                        tally++;
                    }
                } catch (IOException e) {
                    trigger = 1;
                }
                if(trigger == 1) System.out.println("\nThe file could not be found.\n");
                else{
                    System.out.println("\nThe list was loaded successfully.\n");
                    OperationMenu(TaskList.TaskItem);
                }
            }
        } while(MainSelection != 3);
    }

    private static TaskList ClearTaskList(TaskList TaskList) {
        for(int index = TaskList.TaskItem.size() - 1; index >= 0; index --) TaskList.TaskItem.remove(index);
        return TaskList;
    }
    private static void     OperationMenu(ArrayList<TaskItem> TaskItem) {
        int OperationSelection;
        do {
            int TaskSelection = -1;
            PrintOperationMenu();
            while (TaskSelection == -1) TaskSelection = MenuHandler(8, GetInput(in));
            OperationSelection = TaskSelection;
            TaskAction(TaskSelection, TaskItem);
        } while (OperationSelection != 8);
    }
    private static void     TaskAction(int selection, ArrayList<TaskItem> TaskItem) {
        int index = -1;
        if(selection == 1) PrintAllTasks(TaskItem);
        if(selection == 2) {
            TaskItem Task = selectionTwo(TaskItem);
            TaskItem.add(Task);
        }
        if(selection == 3) {
            PrintAllTasks(TaskItem);
            System.out.println("\n\nSelect a task to edit: ");
            while (index == -1) index = MenuHandler(TaskItem.size(), GetInput(in)) - 1;
            trigger = 0;
            System.out.print("New Task title: ");
            while(trigger!=1)   TaskItem.get(index).title =           TitleHandler(GetInput(in));
            trigger = 0;
            System.out.print("New Task description: ");
            while(trigger!=1)   TaskItem.get(index).description =     DescriptionHandler((GetInput(in)));
            trigger = 0;
            System.out.print("New Task due date (YYYY-MM-DD): ");
            while(trigger!=1)   TaskItem.get(index).due_date =        Due_DateHandler(GetInput(in));
        }
        if(selection == 4) {
            index = -1;
            PrintAllTasks(TaskItem);
            System.out.println("\n\nSelect a task to remove: ");
            while (index == -1) index = MenuHandler(TaskItem.size(), GetInput(in)) - 1;
            TaskItem.remove(index);
        }
        if(selection == 5 || selection == 6) {
            index = -1;
            boolean completion = false;
            String suffix = "";
            if(selection == 6) { completion = true; suffix = "in"; }
            PrintTasksByCompletion(TaskItem,completion);
            int[] key = GenerateCompletionKey(TaskItem, completion);
            if(key.length > 0) {
                System.out.println("\n\nSelect a task to mark " + suffix + "complete: ");
                while (index == -1) index = MenuHandler(key.length, GetInput(in)) - 1;
                TaskItem.get(key[index]).complete = completion;
            }
        }
        if(selection == 7) {
            System.out.print("\n\nYou have chosen to save your progress.\n" +
                    "After saving, you can close the task list with 8, or continue modifying it.\n" +
                    "Output file name (no extension required): ");
            trigger = 0;
            String FileName = CreateFile(FileHandler(GetInput(in))+".txt");
            SaveTaskList(AmassListInfo(TaskItem), FileName);
        }
    }
    private static TaskItem selectionTwo(ArrayList<TaskItem> TaskItem) {
        String titleIn = "#null#", descriptionIn = "#null#", due_dateIn = "#null#";
        trigger = 0;
        System.out.print("Task title: ");
        while (trigger != 1) titleIn = TitleHandler(GetInput(in));
        trigger = 0;
        System.out.print("Task description: ");
        while (trigger != 1) descriptionIn = DescriptionHandler((GetInput(in)));
        System.out.print("Task due date (YYYY-MM-DD): ");
        trigger = 0;
        while (trigger != 1) due_dateIn = Due_DateHandler(GetInput(in));
        TaskItem Task = new TaskItem(titleIn, descriptionIn, due_dateIn, false);
        return Task;
    }
    private static void     SaveTaskList(String info, String FileName) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(FileName);
            writer.write(info);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String    CreateFile(String FileName){
        File file = new File(FileName);
        try {
            if (file.createNewFile()) {
                System.out.println("\nThe file was created.\n");
            } else{
                System.out.println("\nA file of that name already exists, and will be overwritten.\n");
                trigger = 1;            //Only for testing
            }
        } catch (IOException e) { e.printStackTrace(); }
        return FileName;
    }
    private static String   AmassListInfo(ArrayList<TaskItem> TaskItem) {
        String ListInfo = "";                                           //Empty opening statement.
        for(int index = 0; index < TaskItem.size(); index++){
            ListInfo += AmassTaskInfo(TaskItem.get(index));
        }
        return ListInfo;
    }
    private static String   AmassTaskInfo(TaskItem TaskItem) {
        return  TaskItem.title + "\n" + TaskItem.description + "\n"
                + TaskItem.due_date + "\n" + TaskItem.complete + "\n" ;
    }
    private static int[]    GenerateCompletionKey(ArrayList<TaskItem> TaskItem, boolean complete) {
        int AmountValid = 0;
        for (int index = 0; index < TaskItem.size(); index++) {
            if (TaskItem.get(index).complete == complete) AmountValid++;
        }
        int[] key = new int[AmountValid];
        int position = 0;
        for (int index = 0; index < TaskItem.size(); index++) {
            if (TaskItem.get(index).complete == complete) {
                key[position] = index;
                position++;
            }
        }
        return key;
    }
    private static String   GetInput(Scanner scanner){
        return scanner.nextLine();
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
    private static void     PrintTasksByCompletion(ArrayList<TaskItem> TaskItem, boolean complete) {
        if(!complete)         AllOutput = "Current Incomplete Tasks\n------------------------\n\n";
        else         AllOutput = "Current Complete Tasks\n----------------------\n\n";
        int position = 0;
        for(int index = 0; index < TaskItem.size();index ++) {
            if(TaskItem.get(index).complete == complete) {
                AllOutput += (position + 1) + ") [" + TaskItem.get(index).due_date + "] "
                        + TaskItem.get(index).title + ": " + TaskItem.get(index).description + "\n";
                position++;
            }
        }
        System.out.println(AllOutput);
    }
    private static void     PrintAllTasks(ArrayList<TaskItem> TaskItem) {
        AllOutput = "Current Tasks\n-------------\n\n";
        for(int index = 0; index < TaskItem.size();index ++) {
            AllOutput += (index + 1) + ") [" + TaskItem.get(index).due_date + "] "
                    + TaskItem.get(index).title + ": " + TaskItem.get(index).description + "\n";
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
        trigger = 1;
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

    @Test public void ClearTaskListTest(){
        TaskList expectedTaskList = new TaskList();  //Creates a blank TaskList to compare with later.
        TaskList TaskList = new TaskList();          //Creates an TaskList to fill.
        TaskItem Task = new TaskItem("Title", "Description", "3999-12-01", false);
        TaskList.TaskItem.add(Task);
        Task = new TaskItem("Blank", "Nothing", "9999-09-25", true);
        TaskList.TaskItem.add(Task);
        Task = new TaskItem("Something", "Tag", "3999-12-01", false);
        TaskList.TaskItem.add(Task);
        TaskList = ClearTaskList(TaskList);
        assertEquals(expectedTaskList.TaskItem.size(),TaskList.TaskItem.size()); //Verifies that the TaskList was emptied.
    }
    @Test public void SaveTaskListTest(){
        String name = "xXNoOneShouldEverNameTheirTaskThisXx.txt";
        String output = "";
        CreateFile(name);                                   //Creates a file to save to
        SaveTaskList(name,name);                            //Saves the file name as its contents.
        BufferedReader input = null;
        try {
            input = new BufferedReader(new FileReader(name));
            output = input.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File patsy = new File(name);
        patsy.delete();                                     //Cleanup.  This specific test does not
        assertEquals(name,output);                          //properly delete the file (for unknown reasons).
    }
    @Test public void AmassListInfoTest(){
        String expectedString = "Title\nDescription\n3999-12-01\nfalse\n" +
                "Blank\nNothing\n9999-09-25\ntrue\n";
        TaskList TaskList = new TaskList();
        TaskItem Task = new TaskItem("Title", "Description", "3999-12-01", false);
        TaskList.TaskItem.add(Task);
        Task = new TaskItem("Blank", "Nothing", "9999-09-25", true);
        TaskList.TaskItem.add(Task);
        assertEquals(expectedString,AmassListInfo(TaskList.TaskItem));
    }
    @Test public void AmassTaskInfoTest(){
        String expectedString = "Title\nDescription\n3999-12-01\nfalse\n";
        TaskList TaskList = new TaskList();
        TaskItem Task = new TaskItem("Title", "Description", "3999-12-01", false);
        TaskList.TaskItem.add(Task);
        assertEquals(expectedString,AmassTaskInfo(TaskList.TaskItem.get(0)));
    }
    @Test public void CreateFileDuplicate(){
        String name = "xXNoOneShouldEverNameTheirTaskThisXx.txt";
        File patsy = new File(name);
        CreateFile(name);               //Creates a file to block the write.
        trigger = 0;
        CreateFile(name);               //Attempts to create a duplicate.
        patsy.delete();                 //Cleanup.
        assertEquals(1, trigger);
    }
    @Test public void CreateFileNewFile(){
        String name = "xXNoOneShouldEverNameTheirTaskThisXx.txt";
        File patsy = new File(name);
        CreateFile(name);
        assertTrue(patsy.delete());                     //Verifies success by deleting the file.
    }
    @Test public void GenerateCompletionKeyIncomplete(){
        int[] expectedInt = {0,2,3,4};
        TaskList TaskList = new TaskList();
        TaskItem Task = new TaskItem("Title", "Description", "3999-12-01", false);
        TaskList.TaskItem.add(Task);
        Task = new TaskItem("Blank", "Nothing", "9999-09-25", true);
        TaskList.TaskItem.add(Task);
        Task = new TaskItem("Something", "Tag", "3999-12-01", false);
        TaskList.TaskItem.add(Task);
        Task = new TaskItem("Null", "Red", "9999-09-25", false);
        TaskList.TaskItem.add(Task);
        Task = new TaskItem("Yes", "blue", "3999-12-01", false);
        TaskList.TaskItem.add(Task);
        Task = new TaskItem("No", "_orange", "3030-09-11", true);
        TaskList.TaskItem.add(Task);
        int[] receivedInt = GenerateCompletionKey(TaskList.TaskItem, false);
        for(int index = 0; index < receivedInt.length; index++) assertEquals(expectedInt[index],receivedInt[index]);
    }
    @Test public void GenerateCompletionKeyComplete(){
        int[] expectedInt = {1,5};
        TaskList TaskList = new TaskList();
        TaskItem Task = new TaskItem("Title", "Description", "3999-12-01", false);
        TaskList.TaskItem.add(Task);
        Task = new TaskItem("Blank", "Nothing", "9999-09-25", true);
        TaskList.TaskItem.add(Task);
        Task = new TaskItem("Something", "Tag", "3999-12-01", false);
        TaskList.TaskItem.add(Task);
        Task = new TaskItem("Null", "Red", "9999-09-25", false);
        TaskList.TaskItem.add(Task);
        Task = new TaskItem("Yes", "blue", "3999-12-01", false);
        TaskList.TaskItem.add(Task);
        Task = new TaskItem("No", "_orange", "3030-09-11", true);
        TaskList.TaskItem.add(Task);
        int[] receivedInt = GenerateCompletionKey(TaskList.TaskItem, true);
        for(int index = 0; index < receivedInt.length; index++) assertEquals(expectedInt[index],receivedInt[index]);
    }
    @Test public void GetInputTest(){
        String input = "Something Clever";
        InputStream virtualIn = System.in;
        System.setIn(virtualIn);
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        String output = GetInput(scanner);
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
        TaskList TaskList = new TaskList();
        TaskItem Task = new TaskItem("Title", "Description", "3999-12-01", false);
        TaskList.TaskItem.add(Task);
        Task = new TaskItem("Blank", "Nothing", "9999-09-25", true);
        TaskList.TaskItem.add(Task);
        PrintTasksByCompletion(TaskList.TaskItem, true);
        String expectedString = "Current Complete Tasks\n" +
                "----------------------\n\n1) [9999-09-25] Blank: Nothing\n";
        assertEquals(expectedString,AllOutput);
    }
    @Test public void PrintTasksByCompletionIncomplete(){
        TaskList TaskList = new TaskList();
        TaskItem Task = new TaskItem("Title", "Description", "3999-12-01", false);
        TaskList.TaskItem.add(Task);
        Task = new TaskItem("Blank", "Nothing", "9999-09-25", true);
        TaskList.TaskItem.add(Task);
        PrintTasksByCompletion(TaskList.TaskItem, false);
        String expectedString = "Current Incomplete Tasks\n" +
                "------------------------\n\n1) [3999-12-01] Title: Description\n";
        assertEquals(expectedString,AllOutput);
    }
    @Test public void PrintAllTasksTest(){
        TaskList TaskList = new TaskList();
        TaskItem Task = new TaskItem("Title", "Description", "3999-12-01", false);
        TaskList.TaskItem.add(Task);
        Task = new TaskItem("Blank", "Nothing", "9999-09-25", true);
        TaskList.TaskItem.add(Task);
        PrintAllTasks(TaskList.TaskItem);
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
        assertEquals(1,CalendarHandler(20244,2,29, validDays));
    }
    @Test public void CalendarHandlerInvalid(){
        int[] validDays =   {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        assertEquals(5,CalendarHandler(2021,2,29, validDays));
    }
    @Test public void PastHandlerValid(){
        assertEquals(1,PastHandler(2020,2020,12,11,4,25));
    }
    @Test public void PastHandlerInvalid(){
        assertEquals(3,PastHandler(1776,2020,7,11,4,25));
    }

}
