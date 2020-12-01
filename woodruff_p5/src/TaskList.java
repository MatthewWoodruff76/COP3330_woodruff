import java.io.*;
import java.util.ArrayList;

public class TaskList {
    protected static ArrayList<TaskItem> tasks = new ArrayList<>();

    public static void newList(){
        tasks = new ArrayList<>();
    }
    public static boolean removeTask(int index){
        if(index < tasks.size()) {
            tasks.remove(index);
            return true;
        }
        return false;
    }
    public static void addTask(String title, String description, String date, boolean complete) {
        TaskItem task = new TaskItem(title, description, date, complete);
        tasks.add(task);
    }
    public static boolean editComplete(int index) {
        if(index < tasks.size()) {
            tasks.get(index).setComplete();
            return true;
        }
        return false;
    }
    public static boolean editDescription(int index, String newDescription) {
        if(index < tasks.size()) return tasks.get(index).setDescription(newDescription);
        return false;
    }
    public static boolean editDue_Date(int index, String newDue_Date) {
        if(index < tasks.size()) return tasks.get(index).setDue_date(newDue_Date);
        return false;
    }
    public static boolean editTitle(int index, String newTitle) {
        if(index < tasks.size()) return tasks.get(index).setTitle(newTitle);
        return false;
    }
    public static String getDescription(int index) {
         if(index < tasks.size() && index >= 0){
             return tasks.get(index).getDescription();
         }
         return null;
    }
    public static String getDue_Date(int index) {
        if(index < tasks.size() && index >= 0){
            return tasks.get(index).getDue_date();
        }
        return null;
    }
    public static String getTitle(int index) {
        if(index < tasks.size() && index >= 0){
            return tasks.get(index).getTitle();
        }
        return null;
    }
    protected static void saveList(String ListInfo, String FileName){
        try {
            FileWriter writer = new FileWriter(FileName);
            writer.write(ListInfo);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    protected static boolean loadList(String FileName){
        BufferedReader input = null;
        String placeholder = null, title = null, description = null, due_date = null;
        int tally = 0;
        try { input = new BufferedReader(new FileReader(FileName));
        } catch (IOException e) { return false; }
        do {
            try { if ((placeholder = input.readLine()) == null) break;
            } catch (IOException e) { e.printStackTrace(); }
            if((tally + 4) % 4 == 0) {
                title = placeholder;
                if(!TaskItem.TitleIsValid(title)) return false;
            }
            if((tally + 3) % 4 == 0) description = placeholder;
            if((tally + 2) % 4 == 0) {
                due_date = placeholder;
                if(!TaskItem.DateIsValid(due_date)) return false;
            }
            if((tally + 1) % 4 == 0) {
                TaskItem Task = new TaskItem(title, description, due_date, Boolean.parseBoolean(placeholder));
                tasks.add(Task); }
            tally++;
        } while(true);
        return true;
    }

    protected static String getIncompleteList(boolean complete){
        String completion = "Incomplete";
        int position = 0;
        if(complete) completion = "Complete  ";
        String Tasks = "\nCurrent " + completion + " Tasks\n------------------------\n\n";
        for(int index = 0; index < tasks.size();index ++) {
            if(tasks.get(index).getComplete() == complete) {
                Tasks += (position+1) + ") [" + tasks.get(index).getDue_date() + "] "
                        + tasks.get(index).getTitle() + ": " + tasks.get(index).getDescription() + "\n";
                position++;
            }
        }
        return Tasks;
    }
    protected static int[] getIncompleteKey(String Condition){
        int position = 0, size = 0;
        boolean complete = Condition.equals("Complete");
        for(int index = 0; index < tasks.size();index ++) {
            if(tasks.get(index).getComplete() == complete) {
                size++;
            }
        }
        int[] key = new int[size];
        for(int index = 0; index < tasks.size();index ++) {
            if(tasks.get(index).getComplete() == complete) {
                key[position] = index;
                position++;
            }
        }
        return key;
    }
    protected static String getCompleteList(){
        String Tasks = "\nCurrent Tasks\n-------------\n\n", completion;
        for(int index = 0; index < tasks.size();index ++) {
            if(tasks.get(index).getComplete()) completion = "Complete  ";
            else completion = "Incomplete";
            Tasks += (index + 1) + ") " + completion + " [" + tasks.get(index).getDue_date() + "] "
                    + tasks.get(index).getTitle() + ": " + tasks.get(index).getDescription() + "\n";
        }
        return Tasks;
    }
    public static String ListInfo(String Condition) {
        if(Condition.equals("Complete")) {
            return getIncompleteList(true);
        }
        if(Condition.equals("Incomplete")){
            return getIncompleteList(false);
        }
        return getCompleteList();
    }
    public static boolean ValidateTask(String title, String due_date) {
        boolean validDate = TaskItem.DateIsValid(due_date), validTitle = TaskItem.TitleIsValid(title);
        return (validDate && validTitle);
    }

    public static String SavePrompt() {
        return "\nYou have chosen to save your progress.\n" +
                "After saving, you can close the List with 8 or continue modifying it.\n";
    }

    public static String getExtension() {
        return ".txt";
    }

    public static boolean ValidateFileName(String FileName) {
        String[] criminals = { "/", "\n", "\r", "\t", "\0", "\f", "`", "?", "*", "<", ">", "|", ":", String.valueOf((char)34)};
        if(FileName.length() == 0) {
            System.out.print("\nYour file name needs at least one character.\n");
            return false;
        }
        for(int index = 0; index < criminals.length; index++) {
            if (FileName.contains(criminals[index])){
                System.out.print("\nYour file name has illegal characters.\n");
                return false;
            }
        }
        return true;
    }

    protected static void   CreateFile(String FileName){
        File file = new File(FileName);
        try {
            if (file.createNewFile()) {
                System.out.println("\nThe file was created.\n");
            } else{ System.out.println("\nA file of that name already exists, and will be overwritten.\n"); }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    protected static boolean fileExists(String FileName){
        try {
            new BufferedReader(new FileReader(FileName));
        } catch (IOException e) {
            System.out.println("\nThe file could not be found.\n");
            return false;
        }
        System.out.println("\nThe file was found.\n");
        return true;
    }
    public static String AmassListInfo() {
        String ListInfo = "";                                           //Empty opening statement.
        for(int index = 0; index < tasks.size(); index++){
            ListInfo += tasks.get(index).AmassTaskInfo();
        }
        return ListInfo;
    }
}
