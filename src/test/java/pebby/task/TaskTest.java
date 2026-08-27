package pebby.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TaskTest {

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
}
