package pebby;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Pebby pebby;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image pebbyImage = new Image(this.getClass().getResourceAsStream("/images/DaPebby.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Pebby instance */
    public void setPebby(Pebby p) {
        pebby = p;
        dialogContainer.getChildren().add(DialogBox.getPebbyDialog(commandGuide(), pebbyImage));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = pebby.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getPebbyDialog(addFormatGuidance(response), pebbyImage)
        );
        userInput.clear();
    }

    /**
     * Adds a command example to error messages displayed in the graphical interface.
     *
     * @param response Pebby's original response
     * @return the response with a relevant command format when it is an error
     */
    static String addFormatGuidance(String response) {
        if (response.startsWith("Invalid todo")) {
            return response + "\n\nCorrect format: todo <task description>";
        }
        if (response.startsWith("Invalid deadline")) {
            return response + "\n\nCorrect format: deadline <task description> /by <yyyy-MM-dd>";
        }
        if (response.startsWith("Invalid event")) {
            return response + "\n\nCorrect format: event <task description> /from <yyyy-MM-dd> "
                    + "/to <yyyy-MM-dd>";
        }
        if (response.startsWith("Invalid mark")) {
            return response + "\n\nCorrect format: mark <task number>";
        }
        if (response.startsWith("Invalid unmark")) {
            return response + "\n\nCorrect format: unmark <task number>";
        }
        if (response.startsWith("Invalid delete")) {
            return response + "\n\nCorrect format: delete <task number>";
        }
        if (response.startsWith("Invalid find")) {
            return response + "\n\nCorrect format: find <keyword>";
        }
        if (response.startsWith("Invalid schedule")) {
            return response + "\n\nCorrect format: schedule <yyyy-MM-dd>";
        }
        if (response.startsWith("Hmmm")) {
            return response + "\n\nType help to see the available commands.";
        }
        return response;
    }

    /**
     * Returns the command reference shown when the graphical interface opens.
     */
    private String commandGuide() {
        return "Hello! I'm Pebby. Type help to see what I can do.";
    }
}
