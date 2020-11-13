import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {



    //TaskItem tests

    //Errors are handled by the user, not the program.
    //The program provides exact details for fixing issues and blocks inputs until errors are cleared.

    @Test
    public void
    newTaskItem() {
        TaskItem Task = new TaskItem("title","description","YYYY-MM-DD", false);
        assertEquals("title",Task.title);
        assertEquals("description",Task.description);
        assertEquals("YYYY-MM-DD",Task.due_date);
        assertEquals(false,Task.complete);
    }   //Confirms reality
    @Test
    public void
    inputTaskItemInvalidDueDate() {
        assertEquals(210,TaskItem.Due_DateHandler("1776-02*300"));
        assertEquals(false,TaskItem.Due_DateReport(210));
    }   //The first test tests all subclasses of Due_DateHandler.
    @Test public void
    inputTaskItemInvalidTitle() {
        assertEquals(false,TaskItem.TitleHandler(""));
    }
    @Test public void
    inputTaskItemValidDueDate() {
        assertEquals(1,TaskItem.Due_DateHandler("9999-12-25"));
    }
    @Test public void
    inputTaskItemValidTitle() {
        assertEquals(true,TaskItem.TitleHandler("Title"));
    }
    @Test public void
    inputTaskItemDescription() {
        assertEquals(true,TaskItem.DescriptionHandler("Anything"));
    }


    //TaskList tests

    @Test public void
    addingTaskItem() {
        int before = TaskList.List.size();
        TaskList.addTask("title","description","YYYY-MM-DD");
        int after = TaskList.List.size();
        assertEquals(before + 1, after);
    }
    @Test public void
    completingTaskItemChangesStatus() {
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.editInvisibleTask(0,true);
        assertEquals(true,TaskList.List.get(0).complete);
    }
    @Test public void
    completingIndexKeyValidation() {
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
        String expectedString = "Current Complete Tasks\n" +
                "----------------------\n" +
                "\n" +
                "1) [YYYY-MM-DD] title: description\n" +
                "2) [YYYY-MM-DD] title: description\n" +
                "\n" +
                "\n" +
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
    gettingTaskItemDescriptionInvalidIndex() {

    }
    @Test public void
    gettingTaskItemDescriptionValidIndex() {

    }
    @Test public void
    gettingTaskItemDueDateInvalidIndex() {

    }
    @Test public void
    gettingTaskItemDueDateValidIndex() {

    }
    @Test public void
    gettingTaskItemTitleInvalidIndex() {

    }
    @Test public void
    gettingTaskItemTitleValidIndex() {

    }
    @Test public void
    newTaskListIsEmpty() {

    }
    @Test public void
    removingTaskItemsDecreasesSize() {

    }
    @Test public void
    removingTaskItemsInvalidIndex() {

    }
    @Test public void
    savedTaskListCanBeLoaded() {
    }

    @Test public void
    uncompletingTaskItemChangesStatus() {

    }
    @Test public void
    uncompletingTaskItemInvalidIndex() {

    }
}
