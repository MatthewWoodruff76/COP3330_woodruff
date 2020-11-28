import org.junit.jupiter.api.Test;
import java.io.FileWriter;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {
    @Test
    public void addingItemsIncreasesSize(){
        int expected = TaskList.tasks.size() + 1;
        TaskList.addTask("title", "description", "2090-03-29", false);
        assertEquals(expected, TaskList.tasks.size());
    }
    @Test
    public void completingTaskItemChangesStatus(){
        TaskList.addTask("title", "description", "2090-03-29", false);
        assertTrue(TaskList.editComplete(TaskList.tasks.size()-1));
    }
    @Test
    public void completingTaskItemFailsWithInvalidIndex(){
        assertFalse(TaskList.editComplete(TaskList.tasks.size()));
    }
    @Test
    public void editingItemDescriptionFailsWithInvalidIndex(){
        TaskList.addTask("title", "description", "2090-03-29", false);
        String newDescription = "Yes";
        assertFalse(TaskList.editDescription(TaskList.tasks.size(), newDescription));
    }
    @Test
    public void editingItemDescriptionSucceedsWithExpectedValue(){
        TaskList.addTask("title", "description", "2090-03-29", false);
        String newDescription = "Yes";
        assertTrue(TaskList.editDescription(TaskList.tasks.size()-1, newDescription));
    }
    @Test
    public void editingItemDueDateSucceedsWithExpectedValue(){
        TaskList.addTask("title", "description", "2090-03-29", false);
        String newDue_Date = "3000-03-29";
        assertTrue(TaskList.editDue_Date(TaskList.tasks.size()-1, newDue_Date));
    }
    @Test
    public void editingItemTitleFailsWithEmptyString(){
        TaskList.addTask("title", "description", "2090-03-29", false);
        String newTitle = "";
        assertFalse(TaskList.editTitle(TaskList.tasks.size()-1, newTitle));
    }
    @Test
    public void editingItemTitleFailsWithInvalidIndex(){
        TaskList.addTask("title", "description", "2090-03-29", false);
        String newTitle = "maybe";
        assertFalse(TaskList.editTitle(TaskList.tasks.size(), newTitle));
    }
    @Test
    public void editingItemTitleSucceedsWithExpectedValue(){
        TaskList.addTask("title", "description", "2090-03-29", false);
        String newTitle = "maybe";
        assertTrue(TaskList.editTitle(TaskList.tasks.size()-1, newTitle));
    }
    @Test
    public void editingTaskItemDueDateFailsWithInvalidDateFormat(){
        TaskList.addTask("title", "description", "2090-03-29", false);
        String newDate = "2090+03*29";
        assertFalse(TaskList.editDue_Date(TaskList.tasks.size()-1, newDate));
    }
    @Test
    public void editingTaskItemDueDateFailsWithInvalidIndex(){
        TaskList.addTask("title", "description", "2090-03-29", false);
        String newDate = "2091-03-29";
        assertFalse(TaskList.editDue_Date(TaskList.tasks.size(), newDate));
    }
    @Test
    public void editingTaskItemDueDateFailsWithInvalidYYYMMDD(){
        TaskList.addTask("title", "description", "2090-03-29", false);
        String newDate = "2090-03-90";
        assertFalse(TaskList.editDue_Date(TaskList.tasks.size()-1, newDate));
    }
    @Test
    public void gettingItemDescriptionFailsWithInvalidIndex(){
        TaskList.addTask("title", "description", "2090-03-29", false);
        assertFalse(TaskList.getDescription(TaskList.tasks.size()));
    }
    @Test
    public void gettingItemDescriptionSucceedsWithValidIndex(){
        TaskList.addTask("title", "description", "2090-03-29", false);
        assertTrue(TaskList.getDescription(TaskList.tasks.size()-1));
    }
    @Test
    public void gettingItemDueDateFailsWithInvalidIndex(){
        TaskList.addTask("title", "description", "2090-03-29", false);
        assertFalse(TaskList.getDue_Date(TaskList.tasks.size()));
    }
    @Test
    public void gettingItemDueDateSucceedsWithValidIndex(){
        TaskList.addTask("title", "description", "2090-03-29", false);
        assertTrue(TaskList.getDue_Date(TaskList.tasks.size()-1));
    }
    @Test
    public void gettingItemTitleFailsWithInvalidIndex(){
        TaskList.addTask("title", "description", "2090-03-29", false);
        assertFalse(TaskList.getTitle(TaskList.tasks.size()));
    }
    @Test
    public void gettingItemTitleSucceedsWithValidIndex(){
        TaskList.addTask("title", "description", "2090-03-29", false);
        assertTrue(TaskList.getTitle(TaskList.tasks.size()-1));
    }
    @Test
    public void newListIsEmpty(){
        TaskList.addTask("title", "description", "2090-03-29", false);
        TaskList.newList();
        assertEquals(0,TaskList.tasks.size());
    }
    @Test
    public void removingItemsDecreasesSize(){
        int before = TaskList.tasks.size();
        TaskList.addTask("title", "description", "2090-03-29", false);
        TaskList.removeTask(TaskList.tasks.size()-1);
        assertEquals(before, TaskList.tasks.size());
    }
    @Test
    public void removingItemsFailsWithInvalidIndex(){
        TaskList.addTask("title", "description", "2090-03-29", false);
        assertFalse(TaskList.removeTask(TaskList.tasks.size()));
    }
    @Test
    public void savedTaskListCanBeLoaded() throws IOException {
        String FileName = "Test.txt", info = "title\r\ndescription\r\n2090-03-29\r\nfalse";
        FileWriter writer = new FileWriter(FileName);
        writer.write(info);
        writer.close();
        assertTrue(TaskList.loadList(FileName));
    }
    @Test
    public void uncompletingTaskItemChangesStatus(){
        TaskList.addTask("title", "description", "2090-03-29", false);
        TaskList.editComplete(TaskList.tasks.size()-1);
        assertTrue(TaskList.tasks.get(TaskList.tasks.size()-1).getComplete());
    }
    @Test
    public void uncompletingTaskItemFailsWithInvalidIndex(){
        TaskList.addTask("title", "description", "2090-03-29", false);
        assertFalse(TaskList.editComplete(TaskList.tasks.size()));
    }
}
