import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskItemTest {
    @Test
    protected void constructorFailsWithInvalidDueDate(){ assertFalse(TaskItem.DateIsValid("1010-90-121")); }
    @Test
    protected void constructorFailsWithInvalidTitle(){
        assertFalse(TaskItem.TitleIsValid(""));
    }
    @Test
    protected void constructorSucceedsWithValidDueDate(){
        assertTrue(TaskItem.DateIsValid("2090-03-11"));
    }
    @Test
    protected void constructorSucceedsWithValidTitle(){
        assertTrue(TaskItem.TitleIsValid("title"));
    }
    @Test
    protected void editingDescriptionSucceedsWithExpectedValue(){
        String expectedString = "NewDescription";
        TaskItem test = new TaskItem("No","Description","2090-03-29", false);
        test.setDescription(expectedString);
        assertEquals(expectedString,test.getDescription());
    }
    @Test
    protected void editingDueDateFailsWithInvalidDateFormat(){
        String newString = "3000-02+02";
        String oldString = "2090-03-29";
        TaskItem test = new TaskItem("No","Description",oldString, false);
        test.setDue_date(newString);
        assertEquals(oldString,test.getDue_date());
    }
    @Test
    protected void editingDueDateFailsWithInvalidYYYMMDD(){
        String newString = "3000-02-31";
        String oldString = "2090-03-29";
        TaskItem test = new TaskItem("No","Description",oldString, false);
        test.setDue_date(newString);
        assertEquals(oldString,test.getDue_date());
    }
    @Test
    protected void editingDueDateSucceedsWithExpectedValue(){
        String expectedString = "3000-10-11";
        String oldString = "2090-03-29";
        TaskItem test = new TaskItem("No","Description",oldString, false);
        test.setDue_date(expectedString);
        assertEquals(expectedString,test.getDue_date());
    }
    @Test
    protected void editingTitleFailsWithEmptyString(){
        String newString = "";
        String oldString = "No";
        TaskItem test = new TaskItem(oldString,"Description","2090-03-29", false);
        test.setTitle(newString);
        assertEquals(oldString,test.getTitle());
    }
    @Test
    protected void editingTitleSucceedsWithExpectedValue(){
        String newString = "Yes";
        String oldString = "No";
        TaskItem test = new TaskItem(oldString,"Description","2090-03-29", false);
        test.setTitle(newString);
        assertEquals(newString,test.getTitle());
    }

}
