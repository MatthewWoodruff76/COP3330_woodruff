import java.util.Scanner;
import java.util.ArrayList;
public class App {
    public static void main(String[] args) {
        int MainSelection = 1776;
        while(MainSelection != 3){
            PrintMainMenu();
            MainSelection = MenuVerification(3);
            if(MainSelection == 1){
                TaskList Docket = new TaskList();
                System.out.println("A new task list has been created.\n\n");
                int OperationSelection = 1776;
                while(OperationSelection != 8) {
                    PrintOperationMenu();
                    OperationSelection = MenuVerification(8);
                    TaskAction(OperationSelection, Docket);
                }
                //Closeout actions for exiting the current task list?
            }
            if(MainSelection == 2){
                System.out.println("You entered 2!");
            }
        }
    }
    private static void TaskAction(int selection, TaskList List) {
        if(selection == 1) {
            //You have chosen to view the list of task items
            System.out.println("Current Tasks\n" +
                    "-------------\n\n");
            PrintTasks(List);
        }
        if(selection == 2) {
            //You have chosen to add a task item to the list
            String titleIn = GetTitle();
            String descriptionIn = GetDescription();
            String due_dateIn = GetDue_Date();
            TaskItem Task = new TaskItem(titleIn, descriptionIn, due_dateIn, 0);
        }
        if(selection == 3) {
            //You have chosen to edit a task item
        }
        if(selection == 4) {
            //You have chosen to remove an item
        }
        if(selection == 5) {
            //You have chosen to mark an item as completed
        }
        if(selection == 6) {
            //You have chosen to unmark an item as completed
        }
        if(selection == 7) {
            //You have chosen to save the current list
        }
    }

    private static String GetTitle() {
        System.out.println("Task title: ");
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        return TitleHandler(input);
    }
    private static String GetDescription() {
        System.out.println("Task description: ");
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        return DescriptionHandler(input);
    }
    private static String GetDue_Date() {
        System.out.println("Task due date (YYYY-MM-DD): ");
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        return Due_dateHandler(input);
    }
    private static void PrintOperationMenu() {
        System.out.println("List Operation Menu\n" +
                "---------\n\n" +
                "1) view the list\n" +
                "2) add an item\n" +
                "3) edit an item\n" +
                "4) remove an item\n" +
                "5) mark an item as completed\n" +
                "6) unmark an item as completed\n" +
                "7) save the current list\n" +
                "8) quit to the main menu\n\n");
    }
    private static void PrintMainMenu() {
        System.out.println("Main Menu\n---------\n\n" +
                "1) create a new list\n" +
                "2) load an existing list\n" +
                "3) quit\n\n");
    }
    private static void PrintTasks(TaskList Docket){
        for(int index = 0; index < Docket.length; index ++){
            System.out.println(index + ") [" + Docket.Xanada());
        }
    }
    private static int MenuVerification(int length) {
        Scanner in = new Scanner(System.in);
        int selection;
        try{
            selection = in.nextInt();
        }
        catch (Exception e){
            System.out.println("I'm sorry, it appears that you messed something up.");
            selection = MenuVerification(length);
        }
        if (selection > length || selection <= 0){
            System.out.println("Greg, this is getting old. 1, 2, or 3!  It's not rocket science!!!");
            selection = MenuVerification(length);
        }
        return selection;
    }
    private static String TitleHandler(String input) {
        return input;
    }
    private static String DescriptionHandler(String input) {
        return input;
    }
    private static String Due_dateHandler(String input) {
        return input;
    }
}
/*
Your solution must possess a class called App to handle interaction with the user.


You must handle input errors, including invalid titles, invalid due dates, and trying to access a task that doesn't exist.
You must also handle the case when there are no tasks to edit, remove, mark, unmark, or save.
None of these expected errors should crash the program.


A user shall be able to create a new task list
A user shall be able to load an existing task list

A user shall be able to view the current task list
A user shall be able to save the current task list
A user shall be able to add an item to the current task list
A user shall be able to edit an item in the current task list
A user shall be able to remove an item from the current task list
A user shall be able to mark an item in the current task list as completed
A user shall be able to unmark an item in the current task list as completed
 */

/*
Main Menu
---------

1) create a new list
2) load an existing list
3) quit

> 1
new task list has been created

List Operation Menu
---------

1) view the list
2) add an item
3) edit an item
4) remove an item
5) mark an item as completed
6) unmark an item as completed
7) save the current list
8) quit to the main menu

> 1
Current Tasks
-------------




List Operation Menu
---------

1) view the list
2) add an item
3) edit an item
4) remove an item
5) mark an item as completed
6) unmark an item as completed
7) save the current list
8) quit to the main menu

> 2
Task title: Task 1
Task description: My first task
Task due date (YYYY-MM-DD): 2020-01-01

List Operation Menu
---------

1) view the list
2) add an item
3) edit an item
4) remove an item
5) mark an item as completed
6) unmark an item as completed
7) save the current list
8) quit to the main menu

> 1
Current Tasks
-------------

0) [2020-01-01] Task 1: My first task


List Operation Menu
---------

1) view the list
2) add an item
3) edit an item
4) remove an item
5) mark an item as completed
6) unmark an item as completed
7) save the current list
8) quit to the main menu

> 2
Task title: Task 2
Task description: My second task
Task due date (YYYY-MM-DD): 2021-01-01

List Operation Menu
---------

1) view the list
2) add an item
3) edit an item
4) remove an item
5) mark an item as completed
6) unmark an item as completed
7) save the current list
8) quit to the main menu

> 1
Current Tasks
-------------

0) [2020-01-01] Task 1: My first task
1) [2021-01-01] Task 2: My second task


List Operation Menu
---------

1) view the list
2) add an item
3) edit an item
4) remove an item
5) mark an item as completed
6) unmark an item as completed
7) save the current list
8) quit to the main menu

> 3
Current Tasks
-------------

0) [2020-01-01] Task 1: My first task
1) [2021-01-01] Task 2: My second task

Which task will you edit? 0
Enter a new title for task 0: Create new assignment
Enter a new description for task 0: Create assignment 4: todo list application
Enter a new task due date (YYYY-MM-DD) for task 0: 2020-11-16

List Operation Menu
---------

1) view the list
2) add an item
3) edit an item
4) remove an item
5) mark an item as completed
6) unmark an item as completed
7) save the current list
8) quit to the main menu

> 1
Current Tasks
-------------

0) [2020-11-16] Create new assignment: Create assignment 4: todo list application
1) [2021-01-01] Task 2: My second task


List Operation Menu
---------

1) view the list
2) add an item
3) edit an item
4) remove an item
5) mark an item as completed
6) unmark an item as completed
7) save the current list
8) quit to the main menu

> 5
Uncompleted Tasks
-------------

0) [2020-11-16] Create new assignment: Create assignment 4: todo list application
1) [2021-01-01] Task 2: My second task

Which task will you mark as completed? 0

List Operation Menu
---------

1) view the list
2) add an item
3) edit an item
4) remove an item
5) mark an item as completed
6) unmark an item as completed
7) save the current list
8) quit to the main menu

> 1
Current Tasks
-------------

0) *** [2020-11-16] Create new assignment: Create assignment 4: todo list application
1) [2021-01-01] Task 2: My second task


List Operation Menu
---------

1) view the list
2) add an item
3) edit an item
4) remove an item
5) mark an item as completed
6) unmark an item as completed
7) save the current list
8) quit to the main menu

> 6
Completed Tasks
-------------

0) [2020-11-16] Create new assignment: Create assignment 4: todo list application

Which task will you unmark as completed? 0

List Operation Menu
---------

1) view the list
2) add an item
3) edit an item
4) remove an item
5) mark an item as completed
6) unmark an item as completed
7) save the current list
8) quit to the main menu

> 1
Current Tasks
-------------

0) [2020-11-16] Create new assignment: Create assignment 4: todo list application
1) [2021-01-01] Task 2: My second task


List Operation Menu
---------

1) view the list
2) add an item
3) edit an item
4) remove an item
5) mark an item as completed
6) unmark an item as completed
7) save the current list
8) quit to the main menu

> 4
Current Tasks
-------------

0) [2020-11-16] Create new assignment: Create assignment 4: todo list application
1) [2021-01-01] Task 2: My second task

Which task will you remove? 1

List Operation Menu
---------

1) view the list
2) add an item
3) edit an item
4) remove an item
5) mark an item as completed
6) unmark an item as completed
7) save the current list
8) quit to the main menu

> 1
Current Tasks
-------------

0) [2020-11-16] Create new assignment: Create assignment 4: todo list application


List Operation Menu
---------

1) view the list
2) add an item
3) edit an item
4) remove an item
5) mark an item as completed
6) unmark an item as completed
7) save the current list
8) quit to the main menu

> 7
Enter the filename to save as: tasks.txt
task list has been saved

List Operation Menu
---------

1) view the list
2) add an item
3) edit an item
4) remove an item
5) mark an item as completed
6) unmark an item as completed
7) save the current list
8) quit to the main menu

> 8

Main Menu
---------

1) create a new list
2) load an existing list
3) quit

> 2
Enter the filename to load: tasks.txt
task list has been loaded

List Operation Menu
---------

1) view the list
2) add an item
3) edit an item
4) remove an item
5) mark an item as completed
6) unmark an item as completed
7) save the current list
8) quit to the main menu

> 1
Current Tasks
-------------

0) [2020-11-16] Create new assignment: Create assignment 4: todo list application


List Operation Menu
---------

1) view the list
2) add an item
3) edit an item
4) remove an item
5) mark an item as completed
6) unmark an item as completed
7) save the current list
8) quit to the main menu

> 8

Main Menu
---------

1) create a new list
2) load an existing list
3) quit

> 3

Process finished with exit code 0
 */