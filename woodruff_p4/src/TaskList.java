import java.io.*;
import java.util.ArrayList;

public class TaskList {
    protected static ArrayList<TaskItem> List = new ArrayList<>();
    protected static String extension = ".txt";

    protected static void addTask(String title, String description, String due_date){
        TaskList.List.add(new TaskItem(title,description,due_date, false));
    }

    protected static String PrintableList(){
        String Tasks = "\n\n\n\n\nCurrent Tasks\n-------------\n\n";
        for(int index = 0; index < List.size();index ++) {
            Tasks += (index + 1) + ") [" + List.get(index).due_date + "] "
                    + List.get(index).title + ": " + List.get(index).description + "\n";
        }
        return Tasks;
    }
    protected static int[]    GenerateCompletionKey(boolean complete) {
        int AmountValid = 0;
        for (int index = 0; index < List.size(); index++) {
            if (List.get(index).complete == complete) AmountValid++;
        }
        int[] key = new int[AmountValid];
        for (int index = 0, position = 0; index < List.size(); index++) {
            if (List.get(index).complete == complete) {
                key[position] = index;
                position++;
            }
        }
        return key;
    }
    protected static String PartialTasks(boolean complete){
        String Prefix = "", Tasks = "Current Complete Tasks\n----------------------\n\n";
        int position = 1;
        for(int index = 0; index < List.size();index ++) {
            if(List.get(index).complete == complete) {
                Tasks += (position) + ") [" + List.get(index).due_date + "] "
                        + List.get(index).title + ": " + List.get(index).description + "\n";
                position++;
            }
        }
        if(position == 1){
            return Tasks + "\n\nThere are no " + Prefix + "complete tasks.";
        }
        if(!complete) Prefix = "in";
        return Tasks + "\n\nSelect a task to mark " + Prefix + "complete: ";
    }
    protected static boolean ValidateTask(String title, String due_date, String description){
        boolean validDate = TaskItem.Due_DateReport(TaskItem.Due_DateHandler(due_date));
        boolean validTitle = TaskItem.TitleHandler(title);
        boolean validDescription = TaskItem.DescriptionHandler(description);
        return validDate && validTitle && validDescription;
    }
    protected static void editVisibleTask(String title, String description, String due_date, int index){
        List.get(index).title = title;
        List.get(index).description = description;
        List.get(index).due_date = due_date;
    }
    protected static void removeTask(int index){
        List.remove(index);
    }

    protected static void editInvisibleTask(int index, boolean complete){
        List.get(index).complete = complete;
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
                List.add(Task);
            }
            tally++;
        } while(true);
    }
    protected static void PrintSavePrompt() {
        System.out.print("\n\nYou have chosen to save your progress.\n" +
                "After saving, you can close the task List with 8, or continue modifying it.\n" +
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
            ListInfo += AmassTaskInfo(index);
        }
        return ListInfo;
    }
    protected static String   AmassTaskInfo(int index) {
        return  List.get(index).title + "\n" + List.get(index).description + "\n"
                + List.get(index).due_date + "\n" + List.get(index).complete + "\n" ;
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
