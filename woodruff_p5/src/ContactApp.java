class ContactApp extends App {

    @Override
    void createList() { ContactList.newList(); }
    @Override
    int specifyItem(String condition, String action, int length) {
        int selection;
        do {
            print(ContactList.ListInfo());
            print("\nWhich item would you like to " + action + "?\n> ");
            selection = getSelection(in.nextLine(), length);
        } while(selection==0);
        return selection;
    }
    @Override
    void ManageList(){
        String choice = "0";
        while (!choice.equals("6")) {
            int selection = 0;
            print("\n\nList Operation Menu\n" +
                    "-------------------\n\n" +
                    "1) view the list\n" +
                    "2) add an item\n" +
                    "3) edit an item\n" +
                    "4) remove an item\n" +
                    "5) save the current list\n" +
                    "6) quit to the main menu\n> ");
            choice = in.nextLine();
            if((ContactList.contacts.size()==0)&&!(choice.equals("2")||choice.equals("8"))){
                print("\nThe list is empty.\n");
            }
            else {
                if (choice.equals("1")) {
                    print(ContactList.ListInfo());
                } else if (choice.equals("2")) {
                    ContactList.addContact("", "Smith", "", "");
                    editItem(ContactList.contacts.size() - 1);
                } else if (choice.equals("3")) {
                    selection = specifyItem("All", "edit", ContactList.contacts.size());
                    editItem(selection-1);
                } else if (choice.equals("4")) {
                    selection = specifyItem("All", "remove", ContactList.contacts.size());
                    ContactList.removeContact(selection-1);
                } else if (choice.equals("5")) {
                    print(ContactList.SavePrompt());
                    String FileName;
                    do {
                        print("\nEnter file name (no extension): ");
                        FileName = in.nextLine() + ContactList.getExtension();
                    } while (!ContactList.ValidateFileName(FileName));
                    ContactList.CreateFile(FileName);
                    ContactList.saveList(ContactList.AmassListInfo(), FileName);
                } else if (!choice.equals("6")) {
                    print("\nThat wasn't an option.\n");
                }
            }
        }
        ContactList.newList();
    }
    @Override
    boolean loadList() {
        String FileName;
            do{
                print("\nEnter file name (no extension): ");
                FileName = in.nextLine() + ContactList.getExtension();
            }while (!ContactList.ValidateFileName(FileName));
        if(!ContactList.fileExists(FileName)||!ContactList.loadList(FileName)){
            print("\nThe file you specified is not a valid contact list.\n");
            return false;
        }
        return true;
    }
    @Override
    void editItem(int index) {
        String first, last, phone, email;
        boolean done = false;
        do {
            print("\nFirst name: ");
            first = in.nextLine();
            print("\nLast name: ");
            last = in.nextLine();
            print("\nPhone number (###-###-####): ");
            phone = in.nextLine();
            print("\nEmail Address (name@host.domain): ");
            email = in.nextLine();
            if(ContactItem.ContactIsValid(first,last,phone,email)){
                ContactList.editContact(first,last,phone,email, index);
                done = true;
            } else print("\nYour contact did not meet the required criteria, please try again.\n");
        } while (!done);
    }
}