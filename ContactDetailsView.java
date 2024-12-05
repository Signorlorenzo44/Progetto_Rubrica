import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ContactDetailsView {
    private VBox root;
    private Label contactDetails;
    private Button editButton;
    private Button backButton;

    public ContactDetailsView() {
        root = new VBox();
        contactDetails = new Label("Contact Details");
        editButton = new Button("Edit");
        backButton = new Button("Back");
        root.getChildren().addAll(contactDetails, editButton, backButton);
    }

    public VBox getRoot() {
        return root;
    }

    public Label getContactDetails() {
        return contactDetails;
    }

    public Button getEditButton() {
        return editButton;
    }

    public Button getBackButton() {
        return backButton;
    }
}