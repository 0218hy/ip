package pebby;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class PebbyTest {

    @Test
    void getResponse_todoThenList_runsTheSameCommandsAsTheConsole() throws IOException {
        Path storagePath = Files.createTempFile("pebby-gui-test", ".txt");
        Files.delete(storagePath);
        System.setProperty("pebby.storage.path", storagePath.toString());
        Pebby pebby = new Pebby();

        String addResponse = pebby.getResponse("todo read book");
        String listResponse = pebby.getResponse("list");

        assertTrue(addResponse.contains("[T] [ ] read book"));
        assertTrue(listResponse.contains("1. [T] [ ] read book"));
    }

    @Test
    void getResponse_help_returnsTheCommandReference() {
        Pebby pebby = new Pebby();

        String response = pebby.getResponse("help");

        assertTrue(response.contains("ADD TASKS"));
        assertTrue(response.contains("• todo <task description>"));
        assertTrue(response.contains("event <task description> /from <yyyy-MM-dd> /to <yyyy-MM-dd>"));
        assertTrue(response.contains("schedule <yyyy-MM-dd>"));
    }
}
