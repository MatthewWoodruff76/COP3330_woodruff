import java.util.ArrayList;

public class TaskList {
    public int length;
    public ArrayList<TaskList> TaskList(){
        ArrayList<TaskList> Docket = new ArrayList<TaskList>();  //This may be done.  It just stores information
        length = Docket.size();
        return TaskList();
    }
    public String Xanada(){
        String titleOut = "1";
        String dateOut = "2";
        return "0";
    }

}
/*
A task list shall contain 0 or more task items

    An task item shall contain a title
        A title shall be 1 or more characters in length
        An task item shall contain a description
        A description shall be 0 or more characters in length
        An task item shall contain a due date
        A due date shall be in the format of YYYY-MM-DD
*/