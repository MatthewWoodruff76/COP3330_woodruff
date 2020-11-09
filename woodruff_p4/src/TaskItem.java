public class TaskItem {     //Task item is just a structure that stores information.
    protected String title         =   "Blank";
    public String description   =   "No";
    public String due_date      =   "0000-00-00";   //YYYY-MM-DD
    public boolean complete =   false;              //Status is 1 if complete, 0 if incomplete.
    public TaskItem(String titleIn, String descriptionIn, String due_dateIn, boolean statusIn){ //Creates a new TaskItem;
        title       =   titleIn;
        description =   descriptionIn;
        due_date    =   due_dateIn;
        complete    =   statusIn;
    }
}