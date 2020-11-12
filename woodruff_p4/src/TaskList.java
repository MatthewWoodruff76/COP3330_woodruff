import org.junit.Test;
import java.io.*;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskList {
    public static ArrayList<TaskItem> Docket = new ArrayList<TaskItem>();
    protected static String extension = ".txt";
    public static void addTask(String title, String description, String due_date){
        TaskList.Docket.add(new TaskItem(title,description,due_date, false));
    }
    @Test public void PrintListTest(){
        assertEquals("\n\n\n\n\nCurrent Tasks\n-------------\n\n",PrintList());
    }
    public static String PrintList(){
        String Tasks = "\n\n\n\n\nCurrent Tasks\n-------------\n\n";
        for(int index = 0; index < Docket.size();index ++) {
            Tasks += (index + 1) + ") [" + Docket.get(index).due_date + "] "
                    + Docket.get(index).title + ": " + Docket.get(index).description + "\n";
        }
        return Tasks;
    }
    protected static int[]    GenerateCompletionKey(boolean complete) {
        int AmountValid = 0;
        for (int index = 0; index < Docket.size(); index++) {
            if (Docket.get(index).complete == complete) AmountValid++;
        }
        int[] key = new int[AmountValid];
        for (int index = 0, position = 0; index < Docket.size(); index++) {
            if (Docket.get(index).complete == complete) {
                key[position] = index;
                position++;
            }
        }
        return key;
    }
    public static void PrintPartialTasks(boolean complete){
        String Prefix = "in", Tasks = "Current Complete Tasks\n----------------------\n\n";
        int position = 1;
        for(int index = 0; index < Docket.size();index ++) {
            if(Docket.get(index).complete == complete) {
                Tasks += (position) + ") [" + Docket.get(index).due_date + "] "
                        + Docket.get(index).title + ": " + Docket.get(index).description + "\n";
                position++;
            }
        }
        System.out.println(Tasks);
        if(position == 1){
            System.out.print("\n\nThere are no " + Prefix + "complete tasks.");
            return;
        }
        if(complete) Prefix = "";
        System.out.println("\n\nSelect a task to mark " + Prefix + "complete: ");
    }
    public static boolean ValidateTask(String title, String due_date){
        boolean validDate = TaskItem.Due_DateReport(TaskItem.Due_DateHandler(due_date));
        boolean validTitle = TaskItem.TitleHandler(title);
        return validDate && validTitle;
    }
    public static void editVisibleTask(String title, String description, String due_date, int index){
        Docket.get(index).title = title;
        Docket.get(index).description = description;
        Docket.get(index).due_date = due_date;
    }
    public static void removeTask(int index){
        Docket.remove(index);
    }

    protected static void editInvisibleTask(int index, boolean complete){
        Docket.get(index).complete = complete;
    }
    protected static boolean ReadProtection(String FileName){
        try {
            new BufferedReader(new FileReader(FileName));
        } catch (IOException e) {
            System.out.println("\nThe file could not be found.\n");
            return false;
        }
        System.out.println("\nThe file was found.\n");
        return true;
    }
    protected static BufferedReader ReadFile(String FileName){
        BufferedReader input = null;
        try {
            input = new BufferedReader(new FileReader(FileName));
        } catch (IOException e) {
            System.out.println("\nThe file could not be found.\n");
        }
        System.out.println("\nThe list was found.\n");
        return input;
    }

    protected static void LoadFile(BufferedReader input){
        int tally = 0;
        String placeholder = "", title = "#null#", description = "#null#", due_date = "#null#";
        do {
            try {
                if ((placeholder = input.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            if((tally + 4) % 4 == 0) title = placeholder;
            if((tally + 3) % 4 == 0) description = placeholder;
            if((tally + 2) % 4 == 0) due_date = placeholder;
            if((tally + 1) % 4 == 0) {
                TaskItem Task = new TaskItem(title, description, due_date, Boolean.parseBoolean(placeholder));
                Docket.add(Task);
            }
            tally++;
        } while(true);
    }

    public static void PrintSavePrompt() {
        System.out.print("\n\nYou have chosen to save your progress.\n" +
                "After saving, you can close the task Docket with 8, or continue modifying it.\n" +
                "Output file name (no extension required): ");
    }
    protected static void   CreateFile(String FileName){
        File file = new File(FileName);
        try {
            if (file.createNewFile()) {
                System.out.println("\nThe file was created.\n");
            } else{
                System.out.println("\nA file of that name already exists, and will be overwritten.\n");
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
    protected static String   ValidateFileName(String input){
        String[] criminals = { "/", "\n", "\r", "\t", "\0", "\f", "`", "?", "*", "<", ">", "|", ":", String.valueOf((char)34)};
        if(input.length() == 0) {
            System.out.print("\nYour file name needs at least one character.\n");
            return "";
        }
        for(int index = 0; index < criminals.length; index++) {
            if (input.contains(criminals[index])){
                System.out.print("\nYour file name has illegal characters.\n");
                return "";
            }
        }
        return input;
    }
    protected static String   AmassListInfo() {
        String DocketInfo = "";                                           //Empty opening statement.
        for(int index = 0; index < Docket.size(); index++){
            DocketInfo += AmassTaskInfo(index);
        }
        return DocketInfo;
    }
    protected static String   AmassTaskInfo(int index) {
        return  Docket.get(index).title + "\n" + Docket.get(index).description + "\n"
                + Docket.get(index).due_date + "\n" + Docket.get(index).complete + "\n" ;
    }
    protected static void     SaveTaskList(String info, String FileName) {
        try {
            FileWriter writer = new FileWriter(FileName);
            writer.write(info);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test  public void ItemFormulaTest(){
        assertEquals(1,TaskItem.FormulaHandler("2020-12-25"));
    }
}
