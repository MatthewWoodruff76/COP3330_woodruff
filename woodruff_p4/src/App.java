import java.util.Scanner;

public class App {
    static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        int selection,choice;
        new TaskList();
        String input;
        do {
            Scanner in = new Scanner(System.in);
            do {
                System.out.print(MainMenu());
                selection = MenuHandler(StringToInt(in.nextLine()),3);
            } while(selection == -1);
            if (selection == 1) {
            }
            if (selection == 2) {
                do {
                    screenClear();
                    System.out.print("Enter file name (no extension): ");
                    input = TaskList.ValidateFileName(in.nextLine() + TaskList.extension);
                }while(input.length() == 0);
                TaskList.LoadFile(TaskList.ReadFile(input));
            }
            //Ops menu
            if(selection != 3) {
                do {
                    do {
                        System.out.println(OpsMenu());
                        choice = MenuHandler(StringToInt(in.nextLine()), 8);
                    } while (choice == -1);
                    OpsAction(choice);
                } while (choice != 8);
            }
        } while (selection != 3);
    }

    private static void OpsAction(int choice) {
        String title, description, due_date;
        int index;
        int[] key;
        if(choice == 1) TaskList.PrintList();
        if(choice == 2) {
            do{
                System.out.print("Task title: ");
                title = in.nextLine();
                System.out.print("Task description: ");
                description = in.nextLine();
                System.out.print("Task due date (YYYY-MM-DD): ");
                due_date = in.nextLine();
            } while (!TaskList.ValidateTask(title, due_date));
            TaskList.addTask(title, description, due_date);
        }
        if(choice == 3) {
            TaskList.PrintList();
            System.out.print("\n\nSelect a task to edit: ");
            do{
                index = MenuHandler(StringToInt(in.nextLine()),TaskList.List.size());
            } while (index == -1);
            do{
                System.out.print("Task title: ");
                title = in.nextLine();
                System.out.print("Task description: ");
                description = in.nextLine();
                System.out.print("Task due date (YYYY-MM-DD): ");
                due_date = in.nextLine();
            } while (!TaskList.ValidateTask(title, due_date));
            TaskList.editVisibleTask(title, description, due_date);
        }
        if(choice == 4) {
            TaskList.PrintList();
            System.out.println("\n\nSelect a task to remove: ");
            do{
                index = MenuHandler(StringToInt(in.nextLine()),TaskList.List.size());
            } while(index == -1);
            TaskList.removeTask(index);
        }
        if(choice == 5) {
            TaskList.PrintPartialTasks(false);
            key = TaskList.GenerateCompletionKey(false);
            if(key.length > 0) {
                do {
                    index = MenuHandler(StringToInt(in.nextLine()), key.length);

                } while (index == -1);
                TaskList.editInvisibleTask(index-1,true);
            }
        }
        if(choice == 6) {
            TaskList.PrintPartialTasks(true);
            key = TaskList.GenerateCompletionKey(true);
            if(key.length > 0) {
                do {
                    index = MenuHandler(StringToInt(in.nextLine()), key.length);
                } while (index == -1);
                TaskList.editInvisibleTask(index-1,true);
            }
        }
        if(choice == 7) {
            TaskList.PrintSavePrompt();
            String FileName = TaskList.ValidateFileName(in.nextLine()) + TaskList.extension;
            TaskList.CreateFile(FileName);
            TaskList.SaveTaskList(TaskList.AmassListInfo(), FileName);
        }
    }
    private static String     OpsMenu() {
        return "\nList Operation Menu\n" +
                "---------\n\n" +
                "1) view the list\n" +
                "2) add an item\n" +
                "3) edit an item\n" +
                "4) remove an item\n" +
                "5) mark an item as completed\n" +
                "6) unmark an item as completed\n" +
                "7) save the current list\n" +
                "8) quit to the main menu\n\n";
    }
    private static String    MainMenu() {
        return "Main Menu\n---------\n\n" +
                "1) create a new list\n" +
                "2) load an existing list\n" +
                "3) quit\n\nEnter your selection: ";
    }
    private static int      MenuHandler(int choice, int length) {
        if (choice <= length && choice > 0) return choice;
        System.out.println("\nThat was not a listed option. Try again.\n");
        return -1;
    }
    private static int StringToInt(String string){
        int number = 0;
        for(int index = 0; index < string.length(); index++)
            number += string.charAt(index)-48;
        return number;
    }

    private static void screenClear(){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

}
