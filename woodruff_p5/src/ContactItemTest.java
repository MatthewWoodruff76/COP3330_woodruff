import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContactItemTest {
    @Test
    public void creationFailsWithAllBlankValues() {
        assertEquals(null,ContactItem.newContact("","","",""));
    }

    @Test
    public void creationSucceedsWithBlankEmail() {
        ContactItem expected = new ContactItem("M","T","999-999-9999","");
        ContactItem created = ContactItem.newContact("M","T","999-999-9999","");
        assertEquals(expected.getFirst(),created.getFirst());
    }

    @Test
    public void creationSucceedsWithBlankFirstName() {
        ContactItem expected = new ContactItem("","T","999-999-9999","");
        ContactItem created = ContactItem.newContact("","T","999-999-9999","");
        assertEquals(expected.getLast(),created.getLast());
    }

    @Test
    public void creationSucceedsWithBlankLastName() {
        ContactItem expected = new ContactItem("","","999-999-9999","");
        ContactItem created = ContactItem.newContact("","","999-999-9999","");
        assertEquals(expected.getPhone(),created.getPhone());
    }

    @Test
    public void creationSucceedsWithBlankPhone() {
        ContactItem expected = new ContactItem("","T","","");
        ContactItem created = ContactItem.newContact("","T","","");
        assertEquals(expected.getLast(),created.getLast());
    }

    @Test
    public void creationSucceedsWithNonBlankValues() {
        String expected = "M";
        ContactItem created = ContactItem.newContact("M","T","999-999-9999","Yes@Yes.yes");
        assertEquals(expected,created.getFirst());
    }

    @Test
    public void editingFailsWithAllBlankValues() {
        ContactItem contact = new ContactItem("","","","Yes");
        assertFalse(contact.setEmail(""));
    }

    @Test
    public void editingSucceedsWithBlankEmail() {
        String expected = "";
        ContactItem contact = new ContactItem("M","T","999-999-9999","No@no.com");
        contact.setEmail(expected);
        assertEquals(expected,contact.getEmail());
    }

    @Test
    public void editingSucceedsWithBlankFirstName() {
        String expected = "";
        ContactItem contact = new ContactItem("M","T","999-999-9999","No@no.com");
        contact.setFirst(expected);
        assertEquals(expected,contact.getFirst());
    }

    @Test
    public void editingSucceedsWithBlankLastName() {
        String expected = "";
        ContactItem contact = new ContactItem("M","expected","999-999-9999","No@no.com");
        contact.setLast(expected);
        assertEquals(expected,contact.getLast());
    }

    @Test
    public void editingSucceedsWithBlankPhone() {
        String expected = "";
        ContactItem contact = new ContactItem("M","expected","999-999-9999","No@no.com");
        contact.setPhone(expected);
        assertEquals(expected,contact.getPhone());
    }

    @Test
    public void editingSucceedsWithNonBlankValues() {
        String expected = "111-111-1111";
        ContactItem contact = new ContactItem("M","expected","999-999-9999","No@no.com");
        contact.setPhone(expected);
        assertEquals(expected,contact.getPhone());
    }

    @Test
    public void testToString() {

    }
}