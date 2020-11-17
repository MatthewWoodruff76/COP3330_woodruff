import java.util.Scanner;

public class App {
    static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        int selection,choice;
        String input;
        do {
            Scanner in = new Scanner(System.in);
            do {
                System.out.print(MainMenu());
                selection = MenuHandler(StringToInt(in.nextLine()),3);
                screenClear();
            } while(selection == -1);
            if (selection == 1) {
                System.out.print("\nA new task has been created.\n");   //Placebo.
            }
            if (selection == 2) {
                String FileName = "";
                do {
                    do{
                        System.out.print("\nEnter file name (no extension): ");
                        FileName = in.nextLine() + TaskList.extension;
                    }while (!TaskList.ValidateFileName(FileName));
                } while (!TaskList.ReadProtection(FileName));
                boolean isValidFile = TaskList.LoadFile(TaskList.ReadFile(FileName));
                if(!isValidFile){
                    System.out.print("\nThe file you specified is not a valid task file.\nCreating new list.\n");
                    TaskList.ClearTaskList();
                }
            }
            //Ops menu
            if(selection != 3) {
                do {
                    do {
                        screenClear();
                        System.out.print(OpsMenu());
                        choice = MenuHandler(StringToInt(in.nextLine()), 8);
                    } while (choice == -1);
                    OpsAction(choice);
                } while (choice != 8);
            }
        } while (selection != 3);
    }
    protected static void OpsAction(int choice) {
        String title, description, due_date;
        int index;
        int[] key;
        if(choice == 1) {
            System.out.print(TaskList.PrintableList());
            System.out.print("\n\nPress enter to continue.\n>");
            in.nextLine();
        }
        if(choice == 2) {
            do{
                System.out.print("\nTask title: ");
                title = in.nextLine();
                System.out.print("\nTask description: ");
                description = in.nextLine();
                System.out.print("\nTask due date (YYYY-MM-DD): ");
                due_date = in.nextLine();
            } while (!TaskList.ValidateTask(title, due_date));
            TaskList.addTask(title, description, due_date);
        }
        if(choice == 3) {
            System.out.print(TaskList.PrintableList());
            System.out.print("\n\nSelect a task to edit: ");
            do{
                index = MenuHandler(StringToInt(in.nextLine()),TaskList.List.size());
            } while (index == -1);
            do{
                System.out.print("\nTask title: ");
                title = in.nextLine();
                System.out.print("\nTask description: ");
                description = in.nextLine();
                System.out.print("\nTask due date (YYYY-MM-DD): ");
                due_date = in.nextLine();
            } while (!TaskList.ValidateTask(title, due_date));
            TaskList.editTask(title, description, due_date, index);
        }
        if(choice == 4) {
            screenClear();
            System.out.print(TaskList.PrintableList());
            System.out.print("\n\nSelect a task to remove: ");
            do{
                index = MenuHandler(StringToInt(in.nextLine()),TaskList.List.size());
            } while(index == -1);
            TaskList.removeTask(index-1);
        }
        if(choice == 5 || choice == 6) {
            screenClear();
            boolean complete = false;
            if(choice == 6) complete = true;
            System.out.print(TaskList.PartialTasks(complete));
            key = TaskList.GenerateCompletionKey(complete);
            if(key.length > 0) {
                do {
                    index = MenuHandler(StringToInt(in.nextLine()), key.length);

                } while (index == -1);
                TaskList.editCompletionStatus(key[index-1],!complete);
            }
        }
        if(choice == 7) {
            screenClear();
            TaskList.PrintSavePrompt();
            String FileName = "";
            do{
                System.out.print("\nEnter file name (no extension): ");
                FileName = in.nextLine() + TaskList.extension;
            }while (!TaskList.ValidateFileName(FileName));
            TaskList.CreateFile(FileName);
            TaskList.SaveTaskList(TaskList.AmassListInfo(), FileName);
        }
        if(choice == 8) {
            TaskList.ClearTaskList();
        }
    }
    protected static String     OpsMenu() {
        return "\n\nList Operation Menu\n" +
                "---------\n\n" +
                "1) view the list\n" +
                "2) add an item\n" +
                "3) edit an item\n" +
                "4) remove an item\n" +
                "5) mark an item as completed\n" +
                "6) unmark an item as completed\n" +
                "7) save the current list\n" +
                "8) quit to the main menu\n\nEnter your selection: ";
    }
    protected static String    MainMenu() {
        screenClear();
        return "\n\nMain Menu\n---------\n\n" +
                "1) create a new list\n" +
                "2) load an existing list\n" +
                "3) quit\n\nEnter your selection: ";
    }
    protected static int      MenuHandler(int choice, int length) {
        if (choice <= length && choice > 0) return choice;
        System.out.print("\nThat was not a listed option. Try again.\n");
        return -1;
    }
    protected static int StringToInt(String string){
        int number = 0, digit = 0;
        for(int index = 0; index < string.length(); index++){
            digit = string.charAt(index) - 48;
            if(digit > 9 || digit < 0) return 0;
            number = 10*number + digit;
        }
        return number;
    }
    protected static void screenClear(){
        System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
}
