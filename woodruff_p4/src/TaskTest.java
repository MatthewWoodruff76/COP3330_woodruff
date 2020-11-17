import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    public void
    newTaskItem() {
        TaskItem Task = new TaskItem("title","description","YYYY-MM-DD", false);
        assertEquals("title",Task.title);
        assertEquals("description",Task.description);
        assertEquals("YYYY-MM-DD",Task.due_date);
        assertFalse(Task.complete);
    }   //Confirms object creation.
    @Test
    public void
    inputTaskItemInvalidDueDate() {
        assertEquals(210,TaskItem.Due_DateHandler("1776-02*300"));
        assertFalse(TaskItem.Due_DateReport(210));
    }   //The first test tests all subclasses of Due_DateHandler. 210 is the score achieved when all errors are flagged.
    @Test
    public void
    inputTaskItemInvalidDueDateProtectionTriggered() {
        assertEquals(11,TaskItem.Due_DateHandler("0"));
        assertFalse(TaskItem.Due_DateReport(11));
    }   //Verifies that index-sensitive functions do not encounter too-short due dates.
    @Test public void
    inputTaskItemValidDueDate() {
        assertEquals(1,TaskItem.Due_DateHandler("9999-12-25"));
    }   //Tests that an issue-free submission is not flagged.
    @Test public void
    invalidDueDateReport() {
        assertFalse(TaskItem.Due_DateReport(15));
    }   //Verifies that the DueDateReport recognizes a flagged date.
    @Test public void
    validDueDateReport() {
        assertTrue(TaskItem.Due_DateReport(1));
    }   //Verifies that the DueDateReport recognizes a clear date.
    @Test public void
    inputTaskItemInvalidTitle() {
        assertFalse(TaskItem.TitleHandler(""));
    }   //The TitleHandler returns a boolean based on success.
    @Test public void
    inputTaskItemValidTitle() {
        assertTrue(TaskItem.TitleHandler("Title"));
    }   //Tests that a valid title is passed.
    @Test public void
    isolateUnitInvalid() {
        assertEquals(0,TaskItem.IsolateUnit(0,3,"Not-A-Date"));
    }   //Verifies that the string to int transform flags erroneous entries.
    @Test public void
    isolateUnitValid() {
        assertEquals(0,TaskItem.IsolateUnit(2020,3,"2020-10-20"));
    }   //Verifies that the string to int transform flags erroneous entries.
    @Test public void
    amassTaskInfoTest() {
        TaskList.ClearTaskList();
        TaskList.addTask("title","description","YYYY-MM-DD");
        String expectedString = "title\ndescription\nYYYY-MM-DD\nfalse\n";
        assertEquals(expectedString,TaskList.List.get(0).AmassTaskInfo());
    }   //Verifies that the string to int transform flags erroneous entries.
    @Test public void
    addingTaskItem() {
        TaskList.ClearTaskList();
        int before = TaskList.List.size();
        TaskList.addTask("title","description","YYYY-MM-DD");
        int after = TaskList.List.size();
        assertEquals(before + 1, after);
    }   //Verifies the list updates with a new task.
    @Test public void
    printableFullListTest() {
        TaskList.ClearTaskList();
        TaskList.addTask("title","description","YYYY-MM-DD");
        String expectedString = "\n\n\n\n\nCurrent Tasks\n-------------\n\n1) Incomplete [YYYY-MM-DD] title: description\n";
        assertEquals(expectedString,TaskList.PrintableList());
    }   //Tests the full task list printout.
    @Test public void
    clearingListTest() {
        TaskList.ClearTaskList();
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.ClearTaskList();
        assertEquals(0, TaskList.List.size());
    }   //Verifies task list clearing.
    @Test public void
    completingIndexKeyValidation() {
        TaskList.ClearTaskList();
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.editCompletionStatus(0,true);
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.editCompletionStatus(3,true);
        int[] key = TaskList.GenerateCompletionKey(true);
        assertEquals(0,key[0]);
        assertEquals(3,key[1]);
    }   //Verifies that the key for editing complete/incomplete tasks is valid.
    @Test public void
    printablePartialListTest() {
        TaskList.ClearTaskList();
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.editCompletionStatus(0,true);
        TaskList.addTask("title","description","YYYY-MM-DD");
        String expectedString = "Current Complete Tasks\n----------------------\n" +
                "\n1) [YYYY-MM-DD] title: description\n\n\nSelect a task to mark complete: ";
        assertEquals(expectedString,TaskList.PartialTasks(true));
    }   //Tests the full task list printout.
    @Test public void
    printablePartialListEmptyTest() {
        TaskList.ClearTaskList();
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.addTask("title","description","YYYY-MM-DD");
        String expectedString = "\n\nThere are no complete tasks.";
        assertEquals(expectedString,TaskList.PartialTasks(true));
    }   //Tests the full task list printout.
    


    @Test public void
    completingTaskItemChangesStatus() {
        TaskList.ClearTaskList();
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.editCompletionStatus(0,true);
        assertTrue(TaskList.List.get(0).complete);
    }   //Verifies that the function for editing status works.

    @Test public void
    completingListPrintsValidation() {
        TaskList.ClearTaskList();
        String expectedString = "Current Complete Tasks\n" +
                "----------------------\n\n" +
                "1) [YYYY-MM-DD] title: description\n" +
                "2) [YYYY-MM-DD] title: description\n\n\n" +
                "Select a task to mark complete: ";
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.editCompletionStatus(0,true);
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.editCompletionStatus(3,true);
        assertEquals(expectedString,TaskList.PartialTasks(true));
    }   //Verifies the print system recognizes status.
    @Test public void
    editingTaskItemChangesValues() {
        TaskList.ClearTaskList();
        TaskItem ExpectedTask = new TaskItem("No","Nope","5050-05-05", false);
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.editTask("No","Nope","5050-05-05", 2);
        assertEquals(ExpectedTask.title,TaskList.List.get(2).title);
        assertEquals(ExpectedTask.description,TaskList.List.get(2).description);
        assertEquals(ExpectedTask.due_date,TaskList.List.get(2).due_date);
    }   //Valid-invalid arguments are irrelevant here, as errors are handled previously.
    @Test public void
    inputIsValidMenuSelection() {
        assertEquals(1,App.MenuHandler(1,2));
    }   //All menus use the same function.
    @Test public void
    inputIsInvalidMenuSelection() {
        assertEquals(-1,App.MenuHandler(7,2));
    }   //The menu function returns valid choices or a recognized invalid selection.
    @Test public void
    inputIsNotNumber() {
        assertEquals(0,App.StringToInt("1A"));
    }   //Verifies that inputs which are not valid numbers are flagged.
    @Test public void
    inputIsNumber() {
        assertEquals(7,App.StringToInt("000000007"));
    }   //Verifies that numbers in string format are processed correctly.
    @Test public void
    testClearTaskList() {
        TaskList.ClearTaskList();
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.addTask("title","description","YYYY-MM-DD");
        assertEquals(4,TaskList.List.size());
        TaskList.ClearTaskList();
        assertEquals(0,TaskList.List.size());
    }   //Verifies that clearing a static list (which equivalent to making a new one) works.
    @Test public void
    removingTaskItemsDecreasesSize() {
        TaskList.ClearTaskList();
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.addTask("title","description","YYYY-MM-DD");
        assertEquals(4,TaskList.List.size());
        TaskList.removeTask(2);
        assertEquals(3,TaskList.List.size());
    }   //Verifies that a task is removed.
        //It is not necessary to test invalid indexes, as that is handled by the MenuHandler.
    @Test public void
    savedTaskListCanBeLoaded() {
        TaskList.ClearTaskList();
        String expectedTitle = "No", expectedDescription = "Nope", expectedDue_Date = "5050-05-05";
        TaskList.addTask("No","Nope","5050-05-05");
        String FileName = TaskList.ValidateFileName("TestFile") + TaskList.extension;
        TaskList.CreateFile(FileName);
        TaskList.SaveTaskList(TaskList.AmassListInfo(), FileName);
        String input = "TestFile" + TaskList.extension;
        TaskList.LoadFile(TaskList.ReadFile(input));
        assertEquals(expectedTitle,TaskList.List.get(1).title);
        assertEquals(expectedDescription,TaskList.List.get(1).description);
        assertEquals(expectedDue_Date,TaskList.List.get(1).due_date);
    }   //Tests file creation, list saving, file opening, and list loading functions.
    @Test public void
    inputInvalidFileName() {
        assertFalse(TaskList.ValidateFileName("efee/!.txt"));
    }   //Attempts to use characters illegal in Windows
    @Test public void
    inputValidFileName() {
        assertTrue(TaskList.ValidateFileName("Valid Title.txt"));
    }   //Verifies that a (potentially) valid file name passes.

}
