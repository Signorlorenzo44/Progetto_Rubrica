import javafx.scene.layout.VBox;
import javafx.scene.control.*;

public class MainView {
    private VBox root;
    private TextField searchField;
    private Button addContactButton;
    private Button filterButton;
    private ListView<Contact> contactListView;
    public VBox getRoot() {
        return root;
    }

}

