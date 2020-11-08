import java.util.ArrayList;

public class TaskList {
    public TaskList(){

    }
    public static void addTask(ArrayList<TaskItem> Docket, String titleIn, String descriptionIn, String due_dateIn, boolean statusIn) {
        TaskItem Task = new TaskItem(titleIn, descriptionIn, due_dateIn, false);
        Docket.add(Task);
    }
    public static void removeTask(ArrayList<TaskItem> Docket, int index) {
        Docket.remove(index);
    }
}