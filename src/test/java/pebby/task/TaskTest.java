package pebby.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import pebby.parser.CommandType;

class TaskTest {

    @Test
    void taskListConstructor_varargs_createsListContainingTasksInOrder() {
        Todo firstTask = new Todo("read book");
        Todo secondTask = new Todo("buy groceries");

        TaskList tasks = new TaskList(firstTask, secondTask);

        assertEquals(List.of(firstTask, secondTask), tasks.asList());
    }

    @Test
    void constructor_newTask_initializesDescriptionAndIncompleteState() {
        Task task = new Task("read book");

        assertEquals("read book", task.getDescription());
        assertFalse(task.isDone());
        assertEquals(" ", task.getStatusIcon());
        assertEquals("[ ] read book", task.toString());
    }

    @Test
    void markAsDone_incompleteTask_marksTaskComplete() {
        Task task = new Task("read book");

        task.markAsDone();

        assertTrue(task.isDone());
        assertEquals("X", task.getStatusIcon());
        assertEquals("[X] read book", task.toString());
    }

    @Test
    void markAsNotDone_completedTask_marksTaskIncomplete() {
        Task task = new Task("read book");
        task.markAsDone();

        task.markAsNotDone();

        assertFalse(task.isDone());
        assertEquals(" ", task.getStatusIcon());
    }

    @Test
    void setDone_true_marksTaskComplete() {
        Task task = new Task("read book");

        task.setDone(true);

        assertTrue(task.isDone());
    }

    @Test
    void setDone_false_marksCompletedTaskIncomplete() {
        Task task = new Task("read book");
        task.markAsDone();

        task.setDone(false);

        assertFalse(task.isDone());
    }

    @Test
    void findTasks_keywordMatchesDescriptions_returnsTasksInTaskListOrder() {
        TaskList tasks = new TaskList();
        Todo firstMatch = tasks.addTodo("read book");
        tasks.addTodo("buy groceries");
        Todo secondMatch = tasks.addTodo("return book");

        List<Task> matchingTasks = tasks.findTasks("book");

        assertEquals(List.of(firstMatch, secondMatch), matchingTasks);
    }

    @Test
    void findTasks_keywordMatchesNoDescriptions_returnsEmptyList() {
        TaskList tasks = new TaskList();
        tasks.addTodo("read book");

        assertTrue(tasks.findTasks("lecture").isEmpty());
    }

    @Test
    void findTasks_emptyKeyword_returnsEveryTaskInTaskListOrder() {
        TaskList tasks = new TaskList();
        Todo firstTask = tasks.addTodo("read book");
        Todo secondTask = tasks.addTodo("buy groceries");

        assertEquals(List.of(firstTask, secondTask), tasks.findTasks(""));
    }

    @Test
    void event_validDates_formatsDatesAndRetainsThemForStorage() {
        Event event = new Event("project meeting", "Dec 02 2019", "Dec 03 2019");

        assertEquals("2019-12-02", event.getFrom());
        assertEquals("2019-12-03", event.getTo());
        assertEquals("[E] [ ] project meeting (from: 2019-12-02 to: 2019-12-03)", event.toString());
    }

    @Test
    void argumentFrom_recognizedCommand_returnsTextAfterKeyword() {
        assertEquals("read book", CommandType.TODO.argumentFrom("todo read book"));
    }
}
