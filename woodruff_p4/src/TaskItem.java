public class TaskItem {     //Task item is just a structure that stores information.
    public String title         =   "Blank";
    public String description   =   "No";
    public String due_date      =   "0000-00-00";   //YYYY-MM-DD
    public int status           =   1;              //Status is 1 if complete, 0 if incomplete.
    public TaskItem(String titleIn, String descriptionIn, String due_dateIn, int statusIn){ //Creates a new TaskItem;
        title       =   titleIn;
        description =   descriptionIn;
        due_date    =   due_dateIn;
        status      =   statusIn;
    }
}
/*
Your solution must encapsulate item data in a class called TaskItem and list data in a class called TaskList.
 */
