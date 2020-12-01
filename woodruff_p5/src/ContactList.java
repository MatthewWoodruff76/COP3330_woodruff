import java.io.*;
import java.util.ArrayList;

public class ContactList {
    protected static ArrayList<ContactItem> contacts = new ArrayList<>();

    public static boolean addContact(String first_name, String last_name, String phone_number, String email_address) {
        ContactItem contact = ContactItem.newContact(first_name, last_name, phone_number, email_address);
        if(contact!=null) {
            contacts.add(contact);
            return true;
        }
        return false;
    }
    public static boolean editContact(String first_name, String last_name, String phone_number, String email_address, int index){
        if(index>=contacts.size()||index<0) return false;
        ContactItem contact = ContactItem.newContact(first_name, last_name, phone_number, email_address);
        if(contact!=null) {
            contacts.set(index,contact);
            return true;
        }
        return false;
    }
    public static void newList(){
        contacts = new ArrayList<>();
    }
    public static boolean removeContact(int index) {
        if(index < contacts.size()) {
            contacts.remove(index);
            return true;
        }
        return false;
    }
    protected static boolean saveList(String ListInfo, String FileName){
        try {
            FileWriter writer = new FileWriter(FileName);
            writer.write(ListInfo);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return  false;
        }
        return true;
    }
    public static String SavePrompt() {
        return "\nYou have chosen to save your progress.\n" +
                "After saving, you can close the List with 6 or continue modifying it.\n";
    }
    public static String getExtension() {
        return ".txt";
    }
    public static boolean ValidateFileName(String FileName) {
        String[] criminals = {"/", "\n", "\r", "\t", "\0", "\f", "`", "?", "*", "<", ">", "|", ":", String.valueOf((char) 34)};
        if (FileName.length() == 0) {
            System.out.print("\nYour file name needs at least one character.\n");
            return false;
        }
        for (int index = 0; index < criminals.length; index++) {
            if (FileName.contains(criminals[index])) {
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
        for(int index = 0; index < contacts.size(); index++){
            ListInfo += contacts.get(index).AmassContactInfo();
        }
        return ListInfo;
    }
    protected static boolean loadList(String FileName){
        BufferedReader input = null;
        String placeholder = null, first = null, last = null, phone = null, email = null;
        int tally = 0;
        try { input = new BufferedReader(new FileReader(FileName));
        } catch (IOException e) { return false; }
        do {
            try { if ((placeholder = input.readLine()) == null) break;
            } catch (IOException e) { e.printStackTrace(); }
            if((tally + 4) % 4 == 0) {
                first = placeholder;
            }
            if((tally + 3) % 4 == 0) {
                last = placeholder;
            }
            if((tally + 2) % 4 == 0) {
                phone = placeholder;
                if(!ContactItem.PhoneIsValid(phone)) return false;
            }
            if((tally + 1) % 4 == 0) {
                if(!ContactItem.EmailIsValid(placeholder)) return false;
                ContactItem contact = ContactItem.newContact(first,last,phone,placeholder);
                if(contact!=null) contacts.add(contact);
                else return false;
            }
            tally++;
        } while(true);
        return true;
    }

    public static String ListInfo() {
        if(contacts.size()==0) return "\nThere are no contacts to print.\n";
        String info = "\nCurrent contacts\n" +
                        "----------------\n\n", first, last, phone, email;
        for(int index = 0; index < contacts.size(); index++){
            first = contacts.get(index).getFirst();
            last = contacts.get(index).getLast();
            phone = contacts.get(index).getPhone();
            email = contacts.get(index).getEmail();
            info += "\n" + (index + 1) + ") ";
            if(first.length()!=0) info += "First Name: " + first + "\n   ";
            if(last.length()!=0) info +=  "Last Name:  " + last + "\n   ";
            if(phone.length()!=0) info += "Phone:      " + phone + "\n   ";
            if(email.length()!=0) info += "Email:      " + email;
        }
        return info;
    }
}
