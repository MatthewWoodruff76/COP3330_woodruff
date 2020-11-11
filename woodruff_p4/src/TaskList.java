import java.io.*;
import java.util.ArrayList;

public class TaskList {
    protected static ArrayList<TaskItem> List = null;
    protected static String extension = ".txt";
    public TaskList(){
        List = new ArrayList<>();
    }
    public static void addTask(String title, String description, String due_date){
        List.add(new TaskItem(title,description,due_date, false));
    }
    public static void PrintList(){
        String Tasks = "Current Tasks\n-------------\n\n";
        for(int index = 0; index < List.size();index ++) {
            Tasks += (index + 1) + ") [" + TaskItem.due_date + "] "
                    + TaskItem.title + ": " + TaskItem.description + "\n";
        }
        System.out.print(Tasks);
    }
    protected static int[]    GenerateCompletionKey(boolean complete) {
        int AmountValid = 0;
        for (int index = 0; index < List.size(); index++) {
            if (TaskItem.complete == complete) AmountValid++;
        }
        int[] key = new int[AmountValid];
        int position = 0;
        for (int index = 0; index < List.size(); index++) {
            List.get(1).ValidDue_Date();
            if (TaskItem.complete == complete) {
                key[position] = index;
                position++;
            }
        }
        return key;
    }
    public static void PrintPartialTasks(boolean complete){
        String Prefix = "inc", Tasks = "Current Complete Tasks\n----------------------\n\n";
        for(int index = 0; index < List.size();index ++) {
            if(TaskItem.complete == complete) {
                Tasks += (index + 1) + ") [" + TaskItem.due_date + "] "
                        + TaskItem.title + ": " + TaskItem.description + "\n";
            }
        }
        System.out.println(Tasks);
        if(complete) Prefix = "";
        System.out.println("\n\nSelect a task to mark " + Prefix + "complete: ");
    }
    public static boolean ValidateTask(String title, String due_date){
        boolean validDate = TaskItem.TitleMistakesReport(TaskItem.Due_DateHandler(due_date));
        boolean validTitle = TaskItem.TitleHandler(title);
        return validDate && validTitle;
    }
    public static void editVisibleTask(String title, String description, String due_date){
        TaskItem.title = title;
        TaskItem.description = description;
        TaskItem.due_date = due_date;
    }
    public static void removeTask(int index){
        List.remove(index);
    }

    protected static void editInvisibleTask(int index, boolean complete){
        TaskItem.complete = complete;
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
        int tally = 0, index = 0;
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
                editVisibleTask(title, description, due_date);
                editInvisibleTask(index, Boolean.parseBoolean(placeholder));
                List.add(Task);
                index++;
            }
            tally++;
        } while(true);
    }

    public static void PrintSavePrompt() {
        System.out.print("\n\nYou have chosen to save your progress.\n" +
                "After saving, you can close the task list with 8, or continue modifying it.\n" +
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
        String ListInfo = "";                                           //Empty opening statement.
        for(int index = 0; index < List.size(); index++){
            ListInfo += AmassTaskInfo();
        }
        return ListInfo;
    }
    protected static String   AmassTaskInfo() {
        return  TaskItem.title + "\n" + TaskItem.description + "\n"
                + TaskItem.due_date + "\n" + TaskItem.complete + "\n" ;
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
}
