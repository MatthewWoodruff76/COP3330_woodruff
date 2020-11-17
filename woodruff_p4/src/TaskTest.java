import org.junit.jupiter.api.Test;
import java.io.File;
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
    validateTasksTestValid() {
        assertTrue(TaskList.ValidateTask("title", "2050-12-29"));
    }   //Verifies that valid tasks are passed.
    @Test public void
    validateTasksTestInvalid() {
        assertFalse(TaskList.ValidateTask("", "2050-12-29"));
    }   //Verifies that invalid tasks are rejected.
    @Test public void
    editingTaskTest() {
        TaskList.ClearTaskList();
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.editTask("NewTitle", "NewDescription", "2050-12-29",0);
        String expectedString = "NewTitle\nNewDescription\n2050-12-29\nfalse\n";
        assertEquals(expectedString,TaskList.List.get(0).AmassTaskInfo());
    }   //Verifies that editing a task applies the new fields.
    @Test public void
    removingTaskTest() {
        TaskList.ClearTaskList();
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.removeTask(0);
        assertEquals(0,TaskList.List.size());
    }   //Verifies that removing a test removes the test.
    @Test public void
    completingTaskItemChangesStatus() {
        TaskList.ClearTaskList();
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.editCompletionStatus(0,true);
        assertTrue(TaskList.List.get(0).complete);
    }   //Verifies that the function for editing status works.
    @Test public void
    invalidReadProtectionTest() {
        String FileName = "\\\2.2.2.2ThisIsNotPossible";
        assertFalse(TaskList.ReadProtection(FileName));
    }   //Verifies that files must be found to be read.
    @Test public void
    validReadProtectionTest() {
        String FileName = "Test.txt";
        TaskList.CreateFile(FileName);
        assertTrue(TaskList.ReadProtection(FileName));
    }   //Verifies that files which exist are passed.
    @Test public void
    validReadFile() {
        String FileName = "Test.txt";
        TaskList.ClearTaskList();
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.CreateFile(FileName);
        assertNotNull(TaskList.ReadFile(FileName));
    }   //Verifies that valid files are read.
    @Test public void
    invalidReadFile() {
        String FileName = "Test.txt";
        File file = new File(FileName);
        file.delete();
        assertNull(TaskList.ReadFile(FileName));
    }   //Verifies that valid files are read.
    @Test public void
    loadFileTestValid() {
        String FileName = "Test.txt";
        TaskList.ClearTaskList();
        TaskList.addTask("title","description","2050-12-15");
        TaskList.CreateFile(FileName);
        TaskList.SaveTaskList(TaskList.AmassListInfo(), FileName);
        TaskList.ClearTaskList();
        assertTrue(TaskList.LoadFile(TaskList.ReadFile(FileName)));
    }   //Verifies that files are loaded.
    @Test public void
    loadFileTestInvalid() {
        String FileName = "Test.txt";
        TaskList.ClearTaskList();
        TaskList.addTask("","description","2000-12-15");
        TaskList.CreateFile(FileName);
        TaskList.SaveTaskList(TaskList.AmassListInfo(), FileName);
        TaskList.ClearTaskList();
        assertFalse(TaskList.LoadFile(TaskList.ReadFile(FileName)));
    }   //Verifies that invalid file contents are flagged.

    @Test public void
    createFileTest() {
        String FileName = "Test.txt";
        TaskList.ClearTaskList();
        TaskList.addTask("title","description","2050-12-15");
        TaskList.CreateFile(FileName);
        assertTrue(TaskList.LoadFile(TaskList.ReadFile(FileName)));
    }   //Verifies that a file is created.
    @Test public void
    inputInvalidFileName() {
        assertFalse(TaskList.ValidateFileName("Test/!.txt"));
    }   //Attempts to use characters illegal in Windows
    @Test public void
    inputValidFileName() {
        assertTrue(TaskList.ValidateFileName("Valid Title.txt"));
    }   //Verifies that a (potentially) valid file name passes.
    @Test public void
    amassListInfoTest() {
        TaskList.ClearTaskList();
        TaskList.addTask("title","description","YYYY-MM-DD");
        TaskList.addTask("title","description","YYYY-MM-DD");
        String expectedString = "title\ndescription\nYYYY-MM-DD\nfalse\n" +
                "title\ndescription\nYYYY-MM-DD\nfalse\n";
        assertEquals(expectedString,TaskList.AmassListInfo());
    }   //Verifies that a string is created with all TaskList information for saving.
    @Test public void
    saveFileTest() {
        TaskList.ClearTaskList();
        TaskList.addTask("title","description","2090-09-02");
        TaskList.addTask("title","description","3030-01-06");
        String expectedString = "title\ndescription\n2090-09-02\nfalse\n" +
                "title\ndescription\n3030-01-06\nfalse\n";
        String FileName = "Test.txt";
        TaskList.CreateFile(FileName);
        TaskList.SaveTaskList(TaskList.AmassListInfo(), FileName);
        TaskList.ClearTaskList();
        TaskList.LoadFile(TaskList.ReadFile(FileName));
        assertEquals(expectedString,TaskList.AmassListInfo());
    }   //Verifies that a string is created with all TaskList information for saving.
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
}
