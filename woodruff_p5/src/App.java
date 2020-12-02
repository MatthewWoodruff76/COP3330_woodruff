import java.util.Scanner;

public abstract class App {
    public static void main(String[] args) {
        String choice = "0";
        Scanner in = new Scanner(System.in);
        while(!choice.equals("3")) {
            System.out.print("\nSelect An Application\n" +
                    "---------------------\n\n" +
                    "1) Task List\n2) Contact List\n3) Exit Program\n\n> ");
            choice = in.nextLine();
            App List = null;
            if (choice.equals("1")) {
                List = new TaskApp();
                List.runMenu();
            } else if (choice.equals("2")) {
                List = new ContactApp();
                List.runMenu();
            } else if (!choice.equals("3")) {
                System.out.print("That wasn't an option.\n");
            }
        }
    }
    Scanner in = new Scanner(System.in);

    void runMenu(){
        String choice = "0";
        while(!choice.equals("3")) {
            print("\nMain Menu\n" +
                    "---------\n" +
                    "\n" +
                    "1) create a new list\n" +
                    "2) load an existing list\n" +
                    "3) quit\n" +
                    "\n> ");
            choice = in.nextLine();
            if(choice.equals("1")){
                createList();
                ManageList();
            } else if(choice.equals("2")){
                if(loadList()) ManageList();
            } else if (!choice.equals("3")) {
                print("That wasn't an option.\n");
            }
        }
    }
    abstract void createList();
    abstract void ManageList();
    abstract boolean loadList();
    abstract void editItem(int index);
    abstract int specifyItem(String condition, String action, int length);
    static void print(String ToPrint){
        System.out.print(ToPrint);
    }   //Quality of life function.

    int getSelection(String nextLine, int size) {
        int selection;
        try {
            selection = Integer.parseInt(nextLine);
            if(selection > size || selection < 1){
                selection = 0;
                print("\nYour selection is not a valid index.\n");
            }
        } catch (NumberFormatException e) {
            selection = 0;
            print("\nThat wasn't a number.\n");
        }
        return selection;
    }

}
