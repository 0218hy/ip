package pebby;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MainWindowTest {

    @Test
    void guiWelcome_describesPebbyAsSleepyAndAvailable() {
        assertEquals("Hi, I’m Pebby. I may be sleepy, but I’m here.", MainWindow.GUI_WELCOME);
    }

    @Test
    void addFormatGuidance_invalidDeadline_addsDeadlineExample() {
        String response = "Invalid deadline: Please include /by followed by a deadline.";

        String guidedResponse = MainWindow.addFormatGuidance(response);

        assertEquals(response + "\n\nCorrect format: deadline <task description> "
                        + "/by <yyyy-MM-dd or d MMMM yyyy>",
                guidedResponse);
    }

    @Test
    void addFormatGuidance_invalidEvent_addsFullEventDateExample() {
        String response = "Invalid event: Please include /to followed by an end time.";

        String guidedResponse = MainWindow.addFormatGuidance(response);

        assertEquals(response + "\n\nCorrect format: event <task description> "
                + "/from <yyyy-MM-dd or d MMMM yyyy> /to <yyyy-MM-dd or d MMMM yyyy>",
                guidedResponse);
    }

    @Test
    void addFormatGuidance_successResponse_leavesResponseUnchanged() {
        String response = "Here are the tasks in your list:";

        assertEquals(response, MainWindow.addFormatGuidance(response));
    }
}
