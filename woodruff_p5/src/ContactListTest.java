import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class ContactListTest {
    @Test
    public void addingItemsIncreasesSize(){
        int before = ContactList.contacts.size();
        ContactList.addContact("John", "Smith", "123-456-7890", "dude@thing.net");
        int after = ContactList.contacts.size();
        assertEquals(before+1, after);
    }
    @Test
    public void editingItemsFailsWithAllBlankValues(){
        assertFalse(ContactList.addContact("", "", "", ""));
    }
    @Test
    public void editingItemsFailsWithInvalidIndex(){
        ContactList.addContact("John", "Smith", "123-456-7890", "dude@thing.net");
        assertFalse(ContactList.editContact("M", "T", "999-999-9999", "No@Yes.Maybe", ContactList.contacts.size()));
    }
    @Test
    public void editingSucceedsWithBlankFirstName(){
        ContactList.addContact("John", "Smith", "123-456-7890", "dude@thing.net");
        assertTrue(ContactList.editContact("", "T", "999-999-9999", "No@Yes.Maybe", ContactList.contacts.size()-1));
    }
    @Test
    public void editingSucceedsWithBlankLastName(){
        ContactList.addContact("John", "Smith", "123-456-7890", "dude@thing.net");
        assertTrue(ContactList.editContact("M", "", "999-999-9999", "No@Yes.Maybe", ContactList.contacts.size()-1));
    }
    @Test
    public void editingSucceedsWithBlankPhone(){
        ContactList.addContact("John", "Smith", "123-456-7890", "dude@thing.net");
        assertTrue(ContactList.editContact("M", "T", "", "No@Yes.Maybe", ContactList.contacts.size()-1));
    }
    @Test
    public void editingSucceedsWithNonBlankValues(){
        ContactList.addContact("John", "Smith", "123-456-7890", "dude@thing.net");
        assertTrue(ContactList.editContact("M", "T", "999-999-9999", "No@Yes.Maybe", ContactList.contacts.size()-1));
    }
    @Test
    public void newListIsEmpty(){
        ContactList.addContact("John", "Smith", "123-456-7890", "dude@thing.net");
        ContactList.newList();
        assertEquals(0,ContactList.contacts.size());
    }
    @Test
    public void removingItemsDecreasesSize(){
        int before = ContactList.contacts.size();
        ContactList.addContact("John", "Smith", "123-456-7890", "dude@thing.net");
        ContactList.removeContact(ContactList.contacts.size()-1);
        assertEquals(before, ContactList.contacts.size());
    }
    @Test
    public void removingItemsFailsWithInvalidIndex(){
        int before = ContactList.contacts.size();
        ContactList.addContact("John", "Smith", "123-456-7890", "dude@thing.net");
        ContactList.removeContact(ContactList.contacts.size());
        assertEquals(before+1, ContactList.contacts.size());
    }
    @Test
    public void savedContactListCanBeLoaded() throws IOException {
        String FileName = "Test.txt", info = "Em\r\nTee\r\n999-999-9999\r\ndude@thing.net";
        FileWriter writer = new FileWriter(FileName);
        writer.write(info);
        writer.close();
        assertTrue(ContactList.loadList(FileName));
    }
}
