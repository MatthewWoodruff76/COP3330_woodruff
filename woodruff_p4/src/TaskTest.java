import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    //The program provides exact details for fixing issues and blocks inputs until errors are cleared.

    @Test
    public void
    newTaskItem() {
        TaskItem Task = new TaskItem("title","description","YYYY-MM-DD", false);
        assertEquals("title",Task.title);
        assertEquals("description",Task.description);
        assertEquals("YYYY-MM-DD",Task.due_date);
        assertFalse(Task.complete);
    }   //Confirms reality
    @Test
    public void
    inputTaskItemInvalidDueDate() {
        assertEquals(210,TaskItem.Due_DateHandler("1776-02*300"));
        assertFalse(TaskItem.Due_DateReport(210));
    }   //The first test tests all subclasses of Due_DateHandler.
    @Test public void
    inputTaskItemInvalidTitle() {
        assertFalse(TaskItem.TitleHandler(""));
    }
    @Test public void
    inputTaskItemValidDueDate() {
        assertEquals(1,TaskItem.Due_DateHandler("9999-12-25"));
    }
    @Test public void
    inputTaskItemValidTitle() {
        assertTrue(TaskItem.TitleHandler("Title"));
    }
    @Test public void
    inputTaskItemDescription() {
        assertTrue(TaskItem.DescriptionHandler("Anything"));
    }
    @Test public void
    addingTaskItem() {
        App.ClearTaskList();
        int before = TaskList.List.size();
        TaskList.addTask("title","description","YYYY-MM-DD");
        int after = TaskList.List.size();
        assertEquals(before + 1, after);
    }
    @Test public void
    completingTaskItemChangesStatus() {
        App.ClearTaskList();
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.editInvisibleTask(0,true);
        assertTrue(TaskList.List.get(0).complete);
    }
    @Test public void
    completingIndexKeyValidation() {
        App.ClearTaskList();
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.editInvisibleTask(0,true);
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.editInvisibleTask(3,true);
        int[] key = TaskList.GenerateCompletionKey(true);
        assertEquals(0,key[0]);
        assertEquals(3,key[1]);
    }
    @Test public void
    completingListPrintsValidation() {
        App.ClearTaskList();
        String expectedString = "Current Complete Tasks\n" +
                "----------------------\n\n" +
                "1) [YYYY-MM-DD] title: description\n" +
                "2) [YYYY-MM-DD] title: description\n\n\n" +
                "Select a task to mark complete: ";
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.editInvisibleTask(0,true);
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.editInvisibleTask(3,true);
        assertEquals(expectedString,TaskList.PartialTasks(true));
    }
    @Test public void
    editingTaskItemChangesValues() {
        App.ClearTaskList();
        TaskItem ExpectedTask = new TaskItem("No","Nope","5050-05-05", false);
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.editVisibleTask("No","Nope","5050-05-05", 2);
        assertEquals(ExpectedTask.title,TaskList.List.get(2).title);
        assertEquals(ExpectedTask.description,TaskList.List.get(2).description);
        assertEquals(ExpectedTask.due_date,TaskList.List.get(2).due_date);
    }   //Valid-invalid arguments are irrelevant here, as errors are handled elsewhere.

    @Test public void
    inputIsValidMenuSelection() {
        assertEquals(1,App.MenuHandler(1,2));
    }   //All menus use the same function.
    @Test public void
    inputIsInvalidMenuSelection() {
        assertEquals(-1,App.MenuHandler(7,2));
    }   //All menus use the same function.
    @Test public void
    inputIsNotNumber() {
        assertEquals(0,App.StringToInt("1A"));
    }   //This function is used to determine if the MenuHandler's input is valid.
    @Test public void
    inputIsNumber() {
        assertEquals(1,App.StringToInt("000000001"));
    }   //Leading zeroes are ignored out of courtesy.
    @Test public void
    testClearTaskList() {
        App.ClearTaskList();
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.addTask("title","description","YYYY-MM-DD");
        assertEquals(4,TaskList.List.size());
        App.ClearTaskList();
        assertEquals(0,TaskList.List.size());
    }
    @Test public void
    removingTaskItemsDecreasesSize() {
        App.ClearTaskList();
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.addTask("title","description","YYYY-MM-DD");
        assertEquals(4,TaskList.List.size());
        TaskList.removeTask(2);
        assertEquals(3,TaskList.List.size());
    }   //It is not necessary to test invalid indexes, as separate functions handle such errors.
    @Test public void
    savedTaskListCanBeLoaded() {
        App.ClearTaskList();
        String expectedTitle = "No", expectedDescription = "Nope", expectedDue_Date = "5050-05-05";
        TaskList.addTask("No","Nope","5050-05-05");
        String FileName = TaskList.ValidateFileName("TestFile") + TaskList.extension;
        TaskList.CreateFile(FileName);
        TaskList.SaveTaskList(TaskList.AmassListInfo(), FileName);
        String input = TaskList.ValidateFileName("TestFile" + TaskList.extension);
        TaskList.LoadFile(TaskList.ReadFile(input));
        assertEquals(expectedTitle,TaskList.List.get(1).title);
        assertEquals(expectedDescription,TaskList.List.get(1).description);
        assertEquals(expectedDue_Date,TaskList.List.get(1).due_date);
    }
}
